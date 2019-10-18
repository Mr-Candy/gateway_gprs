package com.gateway.protocol;

import com.gateway.inter.IRequest;
import com.gateway.messagedown.PlatformUniversalAnswerRequest;
import com.gateway.messagedown.service.MessageDownService;
import com.gateway.tool.DateUtil;
import com.gateway.tool.LatLonUtil;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 卫星定位
 */
public class GpsLocation {

    private Message message;

    public GpsLocation(Message message){
        this.message = message;
    }

    public byte[] WriteToBytes() {
        return new byte[0];
    }

    public void ReadFromBytes() {
        int terminalId = message.getHeader().getTerminalId();
        byte[] messageBodyBytes = message.getMsgBodyBytes();
        DataInputStream din = new DataInputStream(new ByteArrayInputStream(messageBodyBytes));
        Map<String, String> commandInfo = new HashMap<String, String>();
        commandInfo.put("answer_flow_num", String.valueOf(message.getHeader().getMessageSerialNo()));
        commandInfo.put("answer_id","1");    // 应答ID
        try{
            int terId = din.readInt();  //终端序列号
            int warnFlag = din.readUnsignedShort();    //报警
            int status = din.readUnsignedByte();        //状态，0:未定位;1:定位
            int lat = din.readInt();    //纬度
            double latitude = LatLonUtil.M2D(lat);
            int lon = din.readInt();    //纬度
            double longitude = LatLonUtil.M2D(lat);
            int speed = din.readUnsignedByte();        //速度
            double direction = (din.readUnsignedShort())/10.0;  //方向
            int hight = din.readUnsignedByte();        //高度
            int gpsnum = din.readUnsignedByte();    //卫星个数
            double temp = (din.readUnsignedShort())/10.0;   //罐体温度
            double press = (din.readUnsignedShort())/10.0;   //罐体压力
            double pos = (din.readUnsignedShort())/10.0;   //罐体液位
            int volatage = din.readUnsignedByte();  //电池电量
            String body_date = DateUtil.readBCD6ToDate(din); //时间

            commandInfo.put("result","0");
            //数据处理


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
