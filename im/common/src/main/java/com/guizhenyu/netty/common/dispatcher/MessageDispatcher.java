package com.guizhenyu.netty.common.dispatcher;

import com.alibaba.fastjson.JSON;
import com.guizhenyu.netty.common.codec.Invocation;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * description: MessageDispatcher date: 2021/6/3 9:04 下午
 *
 * @author: guizhenyu
 */

@Sharable
public class MessageDispatcher extends SimpleChannelInboundHandler<Invocation> {

  @Autowired
  private MessageHandlerContainer messageHandlerContainer;

  private final ExecutorService executor = Executors.newFixedThreadPool(200);

  @Override
  protected void channelRead0(ChannelHandlerContext ctx, Invocation msg) throws Exception {
    // 获得 type 对应的 MessageHandler 处理器
    MessageHandler messageHandler = messageHandlerContainer.getMessageHandler(msg.getType());

    // 获得 MessageHandler 处理器的消息类
    Class<? extends Message> messageClass = MessageHandlerContainer.getMessageClass(messageHandler);

    // 解析消息体
    Message message = JSON.parseObject(msg.getMessage(), messageClass);

    // 执行逻辑
    executor.submit(new Runnable() {
      @Override
      public void run() {
        messageHandler.execute(ctx.channel(), message);
      }
    });
  }
}
