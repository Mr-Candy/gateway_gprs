package com.gateway.server;

import io.netty.channel.Channel;

import java.net.SocketAddress;
import java.util.ArrayList;

public class Session {

	public String terminalId;
	public Channel channel = null;
	
	// 消息流水号
	public int responseNum = 0;

	public long lastCommunicateTimeStamp = 0l;

	public TerminalStatus terminalStatus = new TerminalStatus();

	public ArrayList<Object> alist = new ArrayList<Object>();

	public static Session buildSession(Channel channel, String terminalId) {
		Session session = new Session();
		session.channel = channel;
		session.terminalId = terminalId;
		session.lastCommunicateTimeStamp = System.currentTimeMillis();
		return session;
	}

	public long getLastCommunicateTimeStamp() {
		return lastCommunicateTimeStamp;
	}

	public void setLastCommunicateTimeStamp(long lastCommunicateTimeStamp) {
		this.lastCommunicateTimeStamp = lastCommunicateTimeStamp;
	}

	public SocketAddress getRemoteAddr() {
		System.out.println(this.channel.remoteAddress().getClass());
		return this.channel.remoteAddress();
	}

	public void cacheObject(Object object) {
		alist.add(object);
	}

	@Override
	public String toString() {
		return "Session [ terminalPhone=" + terminalId + ", channel=" + channel + "]";
	}

	public synchronized int getResponseNum() {
		if (responseNum >= 0xffff)
			responseNum = 0;
		return ++responseNum;
	}
}