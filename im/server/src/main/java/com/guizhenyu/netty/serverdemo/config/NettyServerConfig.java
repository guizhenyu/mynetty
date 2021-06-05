package com.guizhenyu.netty.serverdemo.config;

import com.guizhenyu.netty.common.dispatcher.MessageDispatcher;
import com.guizhenyu.netty.common.dispatcher.MessageHandlerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: NettyServerConfig date: 2021/6/4 7:50 下午
 *
 * @author: guizhenyu
 */
@Configuration
public class NettyServerConfig {
  @Bean
  public MessageDispatcher messageDispatcher() {
    return new MessageDispatcher();
  }

  @Bean
  public MessageHandlerContainer messageHandlerContainer() {
    return new MessageHandlerContainer();
  }
}
