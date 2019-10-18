package com.gateway.protocol;

import com.gateway.tool.BitConverter;

public class MessageHeader {

	/**
	 * 消息ID，2个字节，无符号16位
	 */
	private int messageType;

	/**
	 * 终端序列号
	 */
	private int terminalId;

	/**
	 * 消息序列号
	 */
	private int messageSerialNo;

	/**
	 * 消息体长度
	 */
	private int messageSize;

	public final int getMessageType() {
		return messageType;
	}

	public final void setMessageType(int value) {
		messageType = value;
	}

	public final int getMessageSerialNo() {
		return messageSerialNo;
	}

	public final void setMessageSerialNo(int value) {
		messageSerialNo = value;
	}

	public int getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(int terminalId) {
		this.terminalId = terminalId;
	}

	public int getMessageSize() {
		return messageSize;
	}

	public void setMessageSize(int messageSize) {
		this.messageSize = messageSize;
	}

	public final void ReadFromBytes(byte[] headerBytes) {
		if (BitConverter.IsLittleEndian) {
			setMessageType( headerBytes[0]);
			int terno = BitConverter.ToUInt32(headerBytes, 1);
			setTerminalId(terno);
			int no = BitConverter.ToUInt16(headerBytes, 5);
			setMessageSerialNo(no);
			int len = BitConverter.ToUInt16(headerBytes, 7);
			setMessageSize(len);
		}
	}

}