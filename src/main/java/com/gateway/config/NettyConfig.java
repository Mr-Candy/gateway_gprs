package com.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：Mr-Candy
 * @date ：Created in 2019-10-18 13:43
 * @description：netty参数配置
 * @modified By：
 * @version: 1.0
 */
@Configuration
public class NettyConfig {

    @Value("${server.port}")
    private int tcpPort;

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }


}
