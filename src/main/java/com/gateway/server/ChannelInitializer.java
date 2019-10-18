package com.gateway.server;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class ChannelInitializer extends io.netty.channel.ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline pipeline = socketChannel.pipeline();
        
        //心跳
        pipeline.addLast("idleStateHandler",new IdleStateHandler(10, 0, 0, TimeUnit.MINUTES));

        //消息分隔
        ByteBuf delimiter = Unpooled.copiedBuffer(new byte[] { 0x23 });
        pipeline.addLast(new DelimiterBasedFrameDecoder(1024,delimiter));

        //自定义处理
        pipeline.addLast(new ServerHandler());

    }
}