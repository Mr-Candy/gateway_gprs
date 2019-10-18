package com.gateway.messagedown;

import com.gateway.inter.IRequest;
import com.gateway.server.Session;
import com.gateway.server.SessionManager;
import com.gateway.tool.BytesUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * 消息组帧
 */
public abstract class AbstractRequest implements IRequest<String, String> {
	protected final byte FRAME_HEAD = (byte) 0x2A;
	protected final byte FRAME_END = (byte) 0x23;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * 
	 * sendMessage 统一使用发送消息的函数
	 * 
	 * @param commandParam
	 *            参数
	 * @param commandType
	 *            消息ID
	 * @param boutStreamBody
	 *            消息体
	 */
	protected byte[] sendMessage(Map<String, String> commandParam,int commandType, ByteArrayOutputStream boutStreamBody) {
		try {
			ByteArrayOutputStream boutStream = new ByteArrayOutputStream();
			DataOutputStream dout = new DataOutputStream(boutStream);			
			
			byte[] body = boutStreamBody.toByteArray();
			// 消息体长度
			int body_len = boutStreamBody.toByteArray().length;

			//终端序列号
			int terminalId = Integer.valueOf(commandParam.get("terminalId"));

			//流水号
			int flow_num = getFlowId(String.valueOf(terminalId));

			dout.writeByte(FRAME_HEAD);
			dout.writeByte(commandType);
			dout.writeInt(terminalId);
			dout.writeShort(flow_num);
			dout.writeShort(body_len);
			dout.write(body);
			dout.write(calculateCheckSum(boutStream));
			dout.writeByte(FRAME_END);
			// 进行转义
			byte[] order = BytesUtils.reverseMeaning(boutStream.toByteArray());
			dout.write(order);
			return boutStream.toByteArray();
		} catch (IOException e) {
			return null;
		}
	}

	/**
	 * 获取流水号
	 * @param terminalId
	 * @return
	 */
	protected int getFlowId(String terminalId) {
		Session session = SessionManager.getInstance().findSession(terminalId);
		if (session == null) {
			return 0;
		}
		return session.getResponseNum();
	}

	/**
	 * 校验
	 * @param boutStream
	 * @return
	 */
	protected static byte calculateCheckSum(ByteArrayOutputStream boutStream) {
		// 起始字节设为0x2A，这是因为在计算过程中包含了标识位，标志位为0x2A，相同的数相异或为0
		byte checkSum = (byte) 0x2A;
		byte[] bytes = boutStream.toByteArray();
		for (byte bt : bytes) {
			checkSum = (byte) (checkSum ^ bt);
		}
		return checkSum;
	}

}
