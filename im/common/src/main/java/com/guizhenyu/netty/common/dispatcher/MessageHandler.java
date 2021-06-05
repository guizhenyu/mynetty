package com.guizhenyu.netty.common.dispatcher;

import io.netty.channel.Channel;

/**
 * description: MessageHandler date: 2021/6/3 9:02 下午
 *
 * @author: guizhenyu
 */
public interface MessageHandler <T extends Message>{

  /**
   * 执行处理消息
   *
   * @param channel
   * @param message
   */
  void execute(Channel channel, T message);


  /**
   *
   *  @return 消息类型，即每个 Message 实现类上的 TYPE 静态字段
   */
  String getType();
}
