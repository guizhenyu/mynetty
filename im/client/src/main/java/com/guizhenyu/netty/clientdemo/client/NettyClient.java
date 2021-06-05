package com.guizhenyu.netty.clientdemo.client;

import com.guizhenyu.netty.clientdemo.client.handler.NettyClientHandlerInitializer;
import com.guizhenyu.netty.common.codec.Invocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import java.util.concurrent.TimeUnit;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * description: NettyClient date: 2021/6/4 4:50 下午
 *
 * @author: guizhenyu
 */

@Component
@Slf4j
public class NettyClient {

  private static final Integer RECONNECT_SECONDS = 20;

  @Value("${netty.server.host}")
  private String serverHost;
  @Value("${netty.server.port}")
  private Integer serverPort;

  @Autowired
  private NettyClientHandlerInitializer nettyClientHandlerInitializer;


  /**
   * 线程组，用于客户端对服务端的链接、数据读写
   */
  private EventLoopGroup eventGroup = new NioEventLoopGroup();

  /**
   * Netty Client Channel
   */
  private volatile Channel channel;


  /**
   * 启动 netty client
   * @throws InterruptedException
   */
  @PostConstruct
  public void start() throws InterruptedException{
    // 创建 Bootstrap 对象
    Bootstrap bootstrap = new Bootstrap();

    // 设置 Bootstrap 的各种属性。
    bootstrap.group(eventGroup) // 设置一个 EventLoopGroup 对象
        .channel(NioSocketChannel.class)  // 指定 Channel 为客户端 NioSocketChannel
        .remoteAddress(serverHost, serverPort) // 指定链接服务器的地址
        .option(ChannelOption.SO_KEEPALIVE, true) // TCP Keepalive 机制，实现 TCP 层级的心跳保活功能
        .option(ChannelOption.TCP_NODELAY, true) // 允许较小的数据包的发送，降低延迟
        .handler(nettyClientHandlerInitializer);

    // 连接服务器，并异步等待成功， 即启动客户端

    bootstrap.connect().addListener(new ChannelFutureListener() {
      @Override
      public void operationComplete(ChannelFuture future) throws Exception {
        if (! future.isSuccess()){
          log.error("[start][Netty Client 连接服务器({}:{}) 失败]", serverHost, serverPort);
          reconnect();
          return;
        }

        // 连接成功
        channel = future.channel();
        log.info("[start][Netty Client 连接服务器({}:{}) 成功]", serverHost, serverPort);
      }

    });

  }

  public void reconnect() {

    eventGroup.schedule(new Runnable() {
      @Override
      public void run() {
        log.info("[reconnect][开始重连]");
        try {
          start();
        } catch (InterruptedException e) {
          log.error("[reconnect][重连失败]", e);
        }
      }
    }, RECONNECT_SECONDS, TimeUnit.SECONDS);

    log.info("[reconnect][{} 秒后将发起重连]", RECONNECT_SECONDS);
  }


  /**
   *  关闭netty server
   */
  @PreDestroy
  public void shutdown(){

    if (null != channel){
      channel.close();
    }

    // 优雅关闭一个 EventLoopGroup 对象
    eventGroup.shutdownGracefully();

  }

  /**
   * 发送消息
   *
   * @param invocation
   */
  public void send(Invocation invocation){
    if (null == channel){
      log.error("[send][连接不存在]");
      return;
    }

    if (!channel.isActive()){
      log.error("[send][连接({})未激活]", channel.id());
      return;
    }

    // 发送消息
    channel.writeAndFlush(invocation);
    log.info("[send][连接({})激活]", channel.id());
  }
}
