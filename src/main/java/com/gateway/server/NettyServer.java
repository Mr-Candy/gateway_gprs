package com.gateway.server;

import com.gateway.config.NettyConfig;
import com.gateway.entity.Device;
import com.gateway.service.DeviceService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

//终端接入服务启动
@Component
public class NettyServer {
    //NettyServerListener 日志输出器
    private static final Logger LOGGER = LoggerFactory.getLogger(NettyServer.class);

    //创建bootstrap
    ServerBootstrap serverBootstrap = new ServerBootstrap();
    // BOSS
    EventLoopGroup boss = new NioEventLoopGroup();
    //Worker
    EventLoopGroup work = new NioEventLoopGroup();

    private ChannelInitializer  channelInitializer = new ChannelInitializer();

    //服务器配置类
    @Resource
    private NettyConfig nettyConfig;

    //开启及服务线程
    @PostConstruct
    public void start() {
        // 从配置文件中获取服务端监听端口号
        int port = nettyConfig.getTcpPort();

        serverBootstrap.group(boss, work)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 100)
                .handler(new LoggingHandler(LogLevel.INFO));
        try {
            //设置事件处理
            serverBootstrap.childHandler(channelInitializer);
            LOGGER.info("netty服务器在[{}]端口启动监听", port);
            ChannelFuture f = serverBootstrap.bind(port).sync();
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            LOGGER.info("[出现异常] 释放资源");
            boss.shutdownGracefully();
            work.shutdownGracefully();
        }
    }

    //关闭服务器方法
    @PreDestroy
    public void close() {
        LOGGER.info("关闭服务器....");
        //退出
        boss.shutdownGracefully();
        work.shutdownGracefully();
        System.out.println("关闭服务器....");
    }
}
