package com.guizhenyu.netty.clientdemo.config;

import com.guizhenyu.netty.common.dispatcher.MessageDispatcher;
import com.guizhenyu.netty.common.dispatcher.MessageHandlerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * description: NettyClientConfig date: 2021/6/4 4:45 下午
 *
 * @author: guizhenyu
 */
@Configuration
public class NettyClientConfig {

  @Bean
  public MessageDispatcher messageDispatcher(){
    return new MessageDispatcher();
  }

  @Bean
  public MessageHandlerContainer messageHandlerContainer(){
    return new MessageHandlerContainer();
  }

}
