package com.gateway.protocol;

public final class MessageFactory
{
	public static void Create(Message message )
	{
		int messageType = message.getMessageType();
//		byte[] messageBodyBytes = message.getMsgBodyBytes();
		if(messageType == 0x00){			//终端通用应答
			TerminalUniversalResponse tm = new TerminalUniversalResponse(message);
			tm.ReadFromBytes();
		}else if(messageType == 0x01){		//卫星定位数据
			GpsLocation tm = new GpsLocation(message);
			tm.ReadFromBytes();
		}else if(messageType == 0x02){		//基站定位数据
			LbsLocation tm = new LbsLocation(message);
			tm.ReadFromBytes();
		}else if(messageType == 0x03){		//定位补传
			ReLocation tm = new ReLocation(message);
			tm.ReadFromBytes();
		}else if(messageType == 0x04){		//本机状态及参数配置上报
			ParameterUp tm = new ParameterUp(message);
			tm.ReadFromBytes();
		}else if(messageType == 0x05){		//参数查询应答
			ParameterResponse tm = new ParameterResponse(message);
			tm.ReadFromBytes();
		}
	}

}