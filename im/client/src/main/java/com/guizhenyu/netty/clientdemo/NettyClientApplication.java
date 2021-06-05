package com.guizhenyu.netty.clientdemo;

import com.guizhenyu.netty.common.dispatcher.MessageHandlerContainer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * description: NettyClientApplication date: 2021/6/4 3:01 下午
 *
 * @author: guizhenyu
 */
@SpringBootApplication
public class NettyClientApplication {

  public static void main(String[] args) {
    SpringApplication.run(NettyClientApplication.class, args);
  }

}
