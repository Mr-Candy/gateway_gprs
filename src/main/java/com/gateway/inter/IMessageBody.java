package com.gateway.inter;

public interface IMessageBody
{
	byte[] WriteToBytes();
	void ReadFromBytes(byte[] messageBodyBytes);
}