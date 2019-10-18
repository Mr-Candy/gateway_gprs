package com.gateway.protocol;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * 终端通用应答
 */

public class TerminalUniversalResponse{
    private Message message;

    public TerminalUniversalResponse(Message message){
        this.message = message;
    }

    public void ReadFromBytes() {
        byte[] messageBodyBytes = message.getMsgBodyBytes();
        DataInputStream din = new DataInputStream(new ByteArrayInputStream(messageBodyBytes));
        try{
            int flownum = din.readUnsignedShort();  //应答消息流水号
            int respondid = din.readUnsignedShort();    //应答消息id
            int result = din.readUnsignedByte();        //结果
        }catch (IOException e) {
            e.printStackTrace();
        }

    }
}
