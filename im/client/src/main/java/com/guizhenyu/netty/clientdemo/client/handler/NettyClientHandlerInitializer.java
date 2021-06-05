package com.guizhenyu.netty.clientdemo.client.handler;

import com.guizhenyu.netty.common.codec.InvocationDecoder;
import com.guizhenyu.netty.common.codec.InvocationEncoder;
import com.guizhenyu.netty.common.dispatcher.MessageDispatcher;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.handler.timeout.ReadTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * description: NettyClientHandlerInitializer date: 2021/6/4 4:51 下午
 *
 * @author: guizhenyu
 */
@Component
@Slf4j
public class NettyClientHandlerInitializer extends ChannelInitializer<Channel> {

  /**
   * 心跳超时时间
   */
  private static final Integer READ_TIMEOUT_SECONDS = 60;

  @Autowired
  private MessageDispatcher messageDispatcher;

  @Autowired
  private NettyClientHandler nettyClientHandler;

  @Override
  protected void initChannel(Channel ch) throws Exception {
    ch.pipeline()
        .addLast(new IdleStateHandler(READ_TIMEOUT_SECONDS, 0, 0))
        .addLast(new ReadTimeoutHandler(3 * READ_TIMEOUT_SECONDS))
        .addLast(new InvocationEncoder())
        .addLast(new InvocationDecoder())
        .addLast(messageDispatcher)
        .addLast(nettyClientHandler);
  }
}
