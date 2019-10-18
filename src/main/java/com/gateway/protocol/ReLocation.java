package com.gateway.protocol;

import com.gateway.inter.IRequest;
import com.gateway.messagedown.PlatformUniversalAnswerRequest;
import com.gateway.messagedown.service.MessageDownService;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 位置补传
 */
public class ReLocation {

    private Message message;

    public ReLocation(Message message){
        this.message = message;
    }

    public byte[] WriteToBytes() {
        return new byte[0];
    }

    public void ReadFromBytes() {
        int terminalId = message.getHeader().getTerminalId();
        Map<String, String> commandInfo = new HashMap<String, String>();
        commandInfo.put("answer_flow_num", String.valueOf(message.getHeader().getMessageSerialNo()));
        commandInfo.put("answer_id","3");    // 应答ID

        byte[] messageBodyBytes = message.getMsgBodyBytes();
        DataInputStream din = new DataInputStream(new ByteArrayInputStream(messageBodyBytes));
        try{
            int num = din.readUnsignedShort();  //定位个数
            for(int i = 0;i < num; i++){
                int flag = din.readUnsignedByte();        //定位类型 0：卫星定位，1：基站定位
                if(flag==0){

                }else if(flag == 1){

                }
            }

            //数据处理

            //应答处理
            commandInfo.put("result","0");
        }catch (IOException e) {
            commandInfo.put("result","2");
            e.printStackTrace();
        }

        //应答处理
        int commandType = 0x80;
        IRequest<String, String> request = new PlatformUniversalAnswerRequest();		//终端注册应答
        byte[] bytes = request.message(commandType, commandInfo);
        MessageDownService transport = new MessageDownService(String.valueOf(terminalId),bytes);
        transport.sendToClient();
    }
}
