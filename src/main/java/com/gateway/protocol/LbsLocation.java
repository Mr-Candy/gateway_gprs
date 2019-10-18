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
 * 基站定位
 */
public class LbsLocation {

    private Message message;

    public LbsLocation(Message message){
        this.message = message;
    }

    public byte[] WriteToBytes() {
        return new byte[0];
    }

    public void ReadFromBytes() {
        int terminalId = message.getHeader().getTerminalId();
        Map<String, String> commandInfo = new HashMap<String, String>();
        commandInfo.put("answer_flow_num", String.valueOf(message.getHeader().getMessageSerialNo()));
        commandInfo.put("answer_id","2");    // 应答ID

        byte[] messageBodyBytes = message.getMsgBodyBytes();
        DataInputStream din = new DataInputStream(new ByteArrayInputStream(messageBodyBytes));
        try{
            int mcc = din.readUnsignedShort();  //国家代码（MCC）
            int mnc = din.readUnsignedByte();    //网络号码（MNC）
            int lac = din.readUnsignedShort();        //位置区域码（LAC）
            int cid = din.readInt();     //基站编号（CID）

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
