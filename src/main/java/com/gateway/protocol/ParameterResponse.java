package com.gateway.protocol;

import com.gateway.inter.IRequest;
import com.gateway.messagedown.PlatformUniversalAnswerRequest;
import com.gateway.messagedown.service.MessageDownService;
import com.gateway.tool.BytesUtils;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 参数查询应答
 */
public class ParameterResponse {

    private Message message;

    public ParameterResponse(Message message){
        this.message = message;
    }

    public byte[] WriteToBytes() {
        return new byte[0];
    }

    public void ReadFromBytes() {
        int terminalId = message.getHeader().getTerminalId();
        Map<String, String> commandInfo = new HashMap<String, String>();
        commandInfo.put("answer_flow_num", String.valueOf(message.getHeader().getMessageSerialNo()));
        commandInfo.put("answer_id","5");    // 应答ID

        byte[] messageBodyBytes = message.getMsgBodyBytes();
        DataInputStream din = new DataInputStream(new ByteArrayInputStream(messageBodyBytes));
        try{
            int flowNum = din.readUnsignedShort();  //应答消息流水号
            int num = din.readUnsignedByte();    //应答参数个数
            String property;
            for(int i = 0; i < num ; i++){
                int propertyId = din.readUnsignedByte();    //参数id
                int propertyLength = din.readUnsignedByte();//参数长度
                if(propertyId == 2 || propertyId == 4 || propertyId == 16 || propertyId == 17 || propertyId == 18){
                    property = String.valueOf((din.readUnsignedByte()<<16)+din.readUnsignedShort());    //北斗卡号
                }else if(propertyId == 3 || propertyId == 19 || propertyId == 20 || propertyId == 21){
                    byte[] phoneNum = new byte[6];
                    din.read(phoneNum, 0, 6);
                    property = BytesUtils.bcd2String(phoneNum);    //SIM卡号
                }else if(propertyId == 5){
                    property = String.valueOf(din.readInt());
                }else if(propertyId == 8 || propertyId == 15){
                    property = String.valueOf(din.readUnsignedByte());
                }else{
                    property = String.valueOf(din.readUnsignedShort());
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
