package com.gateway.server;

import com.gateway.protocol.Message;
import com.gateway.protocol.MessageFactory;
import com.gateway.tool.StringByteConvertor;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.util.AttributeKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServerHandler.class);
    private SessionManager sessionManager;
    private Message message;

    public ServerHandler(){
        this.sessionManager = SessionManager.getInstance();
        this.message = new Message();
    }

    /**
     * 获取数据
     * @param ctx 上下文
     * @param msg 获取的数据
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        ByteBuf readMessage= (ByteBuf) msg;
        if (readMessage.readableBytes() <= 0) {
            readMessage.clear();
            return;
        }

        byte[] bs = new byte[readMessage.readableBytes()];
        readMessage.readBytes(bs);
        readMessage.clear();
        LOGGER.info("接收数据："+ StringByteConvertor.bytesToHexString(bs));

        this.message.ReadFromBytes(bs);
        if(message.getErrorMessage() != null){
            return;
        }
        this.message.setChannel(ctx.channel());
        MessageFactory.Create(message);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        AttributeKey<String> terminalNumKey = AttributeKey.valueOf("terminalNum");
        String terminalNum=ctx.channel().attr(terminalNumKey).get();
        LOGGER.debug("......terminal "+terminalNum+" connection exception :"+cause.getMessage());
        cause.printStackTrace();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        LOGGER.debug("terminal connected channel id="+ctx.channel().id().asLongText());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        AttributeKey<String> terminalNumKey = AttributeKey.valueOf("terminalNum");
        String terminalNum=ctx.channel().attr(terminalNumKey).get();
        sessionManager.remove(terminalNum);
        LOGGER.debug("......terminal "+terminalNum+" connection break out.....");
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                AttributeKey<String> terminalNumKey = AttributeKey.valueOf("terminalNum");
                String terminalNum=ctx.channel().attr(terminalNumKey).get();
                this.sessionManager.remove(terminalNum);
                LOGGER.debug("......terminal "+terminalNum+" connection time out.....");
                ctx.close();
            }
        }
    }
}
