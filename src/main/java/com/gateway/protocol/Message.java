package com.gateway.protocol;

import com.gateway.tool.BytesUtils;
import com.gateway.tool.StringByteConvertor;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Message {

	private static final Logger LOGGER = LoggerFactory.getLogger(Message.class);

	/**
	 * 消息头 9byte
	 */
	private MessageHeader header = new MessageHeader();

	/**
	 * 消息体
	 */
	private byte[] msgBodyBytes;

	/**
	 * 校验码 1byte
	 */
	protected int checkSum;

	/**
	 * 连接channal
	 * @return
	 */
	private Channel channel;

	/**
	 * 错误信息
	 */
	private String errorMessage;

	/**
	 * 内容转16进制
	 * @return
	 */
	private String content;

	public final MessageHeader getHeader() {
		return header;
	}

	public final void setHeader(MessageHeader value) {
		header = value;
	}

	public byte[] getMsgBodyBytes() {
		return msgBodyBytes;
	}

	public void setMsgBodyBytes(byte[] msgBodyBytes) {
		this.msgBodyBytes = msgBodyBytes;
	}


	public int getCheckSum() {
		return checkSum;
	}

	public void setCheckSum(int checkSum) {
		this.checkSum = checkSum;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public final String getErrorMessage() {
		return errorMessage;
	}

	public final void setErrorMessage(String value) {
		errorMessage = value;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public final int getMessageType() {
		return getHeader().getMessageType();
	}

	public final void ReadFromBytes(byte[] messageBytes) {
		this.content = StringByteConvertor.bytesToHexString(messageBytes);
		byte[] validMessageBytes = BytesUtils.unreverseMeaning(messageBytes);
		try {
			// 检测校验码
			int xor = GetCheckXor(validMessageBytes, 1,
					validMessageBytes.length - 2);
			int realXor = validMessageBytes[validMessageBytes.length - 2];
			if (xor == realXor) {
				checkSum = 1;
				try {
					try {
						byte[] headerBytes = new byte[9];
						System.arraycopy(validMessageBytes, 1, headerBytes, 0,
								headerBytes.length);
						header.ReadFromBytes(headerBytes);

						int start = 10;
						if (header.getMessageSize() > 0) {
							byte[] sourceData = new byte[header.getMessageSize()];
							System.arraycopy(validMessageBytes, start,
									sourceData, 0, sourceData.length);
							setMsgBodyBytes(sourceData);
				//			setMessageContents(MessageFactory.Create(header.getMessageType(), sourceData));
						}
					} finally {
						// binaryReader.dispose();
					}
				} finally {
					// ms.dispose();
				}
			} else {
				checkSum = 0;
				setErrorMessage("校验码不正确");
				LOGGER.error("校验码不正确"+":"+ StringByteConvertor.bytesToHexString(messageBytes));
			}

		} catch (Exception ex) {
			LOGGER.error("Message : ReadFromBytes() "+ StringByteConvertor.bytesToHexString(messageBytes));
			setErrorMessage("解析异常:" + ex.getMessage());
			LOGGER.error(getErrorMessage(), ex);
		}
	}

	/**
	 * 获取校验和
	 * 
	 * @param data
	 * @param pos
	 * @param len
	 * @return
	 */
	private int GetCheckXor(byte[] data, int pos, int len) {
		int A = 0;
		for (int i = pos; i < len; i++) {
			A ^= data[i];
		}
		return A;
	}

}