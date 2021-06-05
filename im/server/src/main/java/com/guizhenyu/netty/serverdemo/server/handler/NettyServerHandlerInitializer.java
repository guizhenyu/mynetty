package com.guizhenyu.netty.serverdemo.server.handler;

import com.guizhenyu.netty.common.codec.InvocationDecoder;
import com.guizhenyu.netty.common.codec.InvocationEncoder;
import com.guizhenyu.netty.common.dispatcher.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.timeout.ReadTimeoutHandler;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * description: NettyServerInitializer date: 2021/6/4 11:55 下午
 *
 * @author: guizhenyu
 */
@Component
public class NettyServerHandlerInitializer extends ChannelInitializer<Channel> {

  /**
   * 心跳间隔时间
   *
   */
  private static final Integer READ_TIMEOUT_SECONDS = 3 * 60;

  @Autowired
  private MessageDispatcher messageDispatcher;

  @Autowired
  private NettyServerHandler nettyServerHandler;

  @Override
  protected void initChannel(Channel ch) throws Exception {
    ChannelPipeline pipeline = ch.pipeline();

    pipeline
        .addLast(new ReadTimeoutHandler(READ_TIMEOUT_SECONDS, TimeUnit.SECONDS))
        .addLast(new InvocationEncoder())
        .addLast(new InvocationDecoder())
        .addLast(messageDispatcher)
        .addLast(nettyServerHandler);
  }
}
