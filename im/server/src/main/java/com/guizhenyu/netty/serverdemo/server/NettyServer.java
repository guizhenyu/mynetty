package com.guizhenyu.netty.serverdemo.server;

import com.guizhenyu.netty.serverdemo.server.handler.NettyServerHandlerInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import java.net.InetSocketAddress;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * description: NettyServer date: 2021/6/4 11:54 下午
 *
 * @author: guizhenyu
 */
@Component
@Slf4j
public class NettyServer {

  @Value("${netty.port}")
  private Integer port;

  @Autowired
  private NettyServerHandlerInitializer nettyServerHandlerInitializer;


  private EventLoopGroup bossGroup = new NioEventLoopGroup();

  private EventLoopGroup workGroup = new NioEventLoopGroup();

  private Channel channel;


  @PostConstruct
  public void start() throws InterruptedException {
    ServerBootstrap serverBootstrap = new ServerBootstrap();

    serverBootstrap.group(bossGroup, bossGroup)
        .channel(NioServerSocketChannel.class)
        .localAddress(new InetSocketAddress(port))
        .option(ChannelOption.SO_BACKLOG, 1024)
        .childOption(ChannelOption.SO_KEEPALIVE, true)
        .childOption(ChannelOption.TCP_NODELAY, true)
        .childHandler(nettyServerHandlerInitializer);

    ChannelFuture future = serverBootstrap.bind().sync();

    if (future.isSuccess()){
      channel = future.channel();
      log.info("[start][Netty Server 启动在 {} 端口]", port);
    }
  }

  @PreDestroy
  public void shutDown(){
    if (!Objects.isNull(channel)){
      channel.close();
    }
    bossGroup.shutdownGracefully();
    workGroup.shutdownGracefully();
  }
}
