package com.guizhenyu.netty.clientdemo.messagehandler.chat;

import com.guizhenyu.netty.common.message.chat.ChatRedirectToUserRequest;
import com.guizhenyu.netty.common.dispatcher.MessageHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * description: ChatRedirectToUserRequestHandler date: 2021/6/4 4:18 下午
 *
 * @author: guizhenyu
 */
@Component
@Slf4j
public class ChatRedirectToUserRequestHandler implements MessageHandler<ChatRedirectToUserRequest> {

  @Override
  public void execute(Channel channel, ChatRedirectToUserRequest message) {
    log.info("[execute][收到消息：{}]", message);
  }

  @Override
  public String getType() {
    return ChatRedirectToUserRequest.TYPE;
  }
}
