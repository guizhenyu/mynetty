package com.guizhenyu.netty.clientdemo.messagehandler.chat;

import com.guizhenyu.netty.common.message.chat.ChatSendResponse;
import com.guizhenyu.netty.common.dispatcher.MessageHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * description: ChatSendResponseHandler date: 2021/6/4 4:20 下午
 *
 * @author: guizhenyu
 */
@Component
@Slf4j
public class ChatSendResponseHandler implements MessageHandler<ChatSendResponse> {

  @Override
  public void execute(Channel channel, ChatSendResponse message) {
    log.info("[execute][发送结果：{}]", message);
  }

  @Override
  public String getType() {
    return ChatSendResponse.TYPE;
  }
}
