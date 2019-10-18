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
 * 参数上报
 */
public class ParameterUp {

    private Message message;

    public ParameterUp(Message message){
        this.message = message;
    }

    public byte[] WriteToBytes() {
        return new byte[0];
    }

    public void ReadFromBytes() {
        int terminalId = message.getHeader().getTerminalId();
        Map<String, String> commandInfo = new HashMap<String, String>();
        commandInfo.put("answer_flow_num", String.valueOf(message.getHeader().getMessageSerialNo()));
        commandInfo.put("answer_id","4");    // 应答ID

        byte[] messageBodyBytes = message.getMsgBodyBytes();
        DataInputStream din = new DataInputStream(new ByteArrayInputStream(messageBodyBytes));
        try{
            int version = din.readUnsignedShort();  //终端固件版本号
            int bdCard = (din.readUnsignedByte()<<16)+din.readUnsignedShort();    //终端北斗卡号
            byte[] phoneNum = new byte[6];
            din.read(phoneNum, 0, 6);
            String simCard = BytesUtils.bcd2String(phoneNum);    //终端2G SIM卡号
            /*StringBuilder phone_num = new StringBuilder();
            for (int i = 0; i < 6; i++) {
                int head_phonenum = phoneNum[i] & 0xFF;
                phone_num.append(head_phonenum >> 4).toString();
                phone_num.append(head_phonenum & 0x0F).toString();
            }
            String head_phone_num = phone_num.toString();*/
            int rdssAddress = (din.readUnsignedByte()<<16)+din.readUnsignedShort();    //RDSS上报目标地址
            String ip = din.readUnsignedByte() + "." + din.readUnsignedByte() + "." + din.readUnsignedByte()+"." + din.readUnsignedByte();  //GPRS上报IP地址
            int port = din.readUnsignedShort();        //GPRS上报端口号
            String time =  din.readUnsignedByte() + ":" + din.readUnsignedByte() + ";00";   //  终端定时采集时间:时分
            int voltageLow = din.readUnsignedByte();   //电池低压报警阈值
            int pressUp = din.readUnsignedShort();  //罐箱压力报警阈值（上限）
            int pressLow = din.readUnsignedShort(); //罐箱压力报警阈值（下限）
            int levelUp = din.readUnsignedShort();  //罐箱液位报警阈值（上限）
            int levelLow = din.readUnsignedShort(); //罐箱液位报警阈值（下限）
            int tempUp = din.readUnsignedShort();   //罐箱温度报警阈值（上限）
            int tempLow = din.readUnsignedShort();  //罐箱温度报警阈值（下限）
            int status = din.readUnsignedByte();   //功能开关状态
            int outPower = (status & (byte) 1) == 1 ? 1 : 0;    //对外供电功能开/关状态
            int lbsStatus = ((status >>> 1) & (byte) 1) == 1 ? 1 : 0; //基站定位功能开/关状态
            int dataSaveStatus = ((status >>> 2) & (byte) 1) == 1 ? 1 : 0; //数据缓存功能开/关状态
            int networkStatus = ((status >>> 3) & (byte) 1) == 1 ? 1 : 0; //自组网功能开/关状态
            int rdssStatus = ((status >>> 4) & (byte) 1) == 1 ? 1 : 0;   //RDSS功能开/关状态
            int gprsStatus = ((status >>> 5) & (byte) 1) == 1 ? 1 : 0;//GPRS功能开/关状态

            int bdWhiteCard1 = (din.readUnsignedByte()<<16)+din.readUnsignedShort();    //北斗卡白名单1
            int bdWhiteCard2 = (din.readUnsignedByte()<<16)+din.readUnsignedShort();    //北斗卡白名单2
            int bdWhiteCard3 = (din.readUnsignedByte()<<16)+din.readUnsignedShort();    // 北斗卡白名单3
            phoneNum = new byte[6];
            din.read(phoneNum, 0, 6);
            String simWhiteCard1 = BytesUtils.bcd2String(phoneNum);    //手机卡白名单1
            phoneNum = new byte[6];
            din.read(phoneNum, 0, 6);
            String simWhiteCard2 = BytesUtils.bcd2String(phoneNum);    //手机卡白名单2
            phoneNum = new byte[6];
            din.read(phoneNum, 0, 6);
            String simWhiteCard3 = BytesUtils.bcd2String(phoneNum);    //手机卡白名单3

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
