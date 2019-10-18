package com.gateway.messagedown.service;

import com.gateway.server.SessionManager;
import com.gateway.tool.StringByteConvertor;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 信息推送给client
 */
public class MessageDownService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageDownService.class);
    private SessionManager sessionManager;
    private Channel channel;
    private byte[] message;


    public MessageDownService(String terminalId, byte[] message) {
        this.sessionManager = SessionManager.getInstance();
   //     this.channel = channel;
        this.channel = SessionManager.getInstance().findSession(terminalId).channel;
        this.message = message;
    }

    public void sendToClient() {
        if (this.channel == null) {
            return;
        }
        System.out.println("send to client:" + StringByteConvertor.bytesToHexString(message));
        channel.writeAndFlush(Unpooled.copiedBuffer(message));
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    public Channel getChannel() {
        return channel;
    }

    public void setChannel(Channel channel) {
        this.channel = channel;
    }

    public byte[] getMessage() {
        return message;
    }

    public void setMessage(byte[] message) {
        this.message = message;
    }
}
