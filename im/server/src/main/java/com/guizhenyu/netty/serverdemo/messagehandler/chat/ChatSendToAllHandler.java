package com.guizhenyu.netty.serverdemo.messagehandler.chat;

import com.guizhenyu.netty.common.codec.Invocation;
import com.guizhenyu.netty.common.dispatcher.MessageHandler;
import com.guizhenyu.netty.common.message.chat.ChatRedirectToUserRequest;
import com.guizhenyu.netty.common.message.chat.ChatSendResponse;
import com.guizhenyu.netty.common.message.chat.ChatSendToAllRequest;
import com.guizhenyu.netty.serverdemo.server.NettyChannelManager;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * description: ChatSendToAllHandler date: 2021/6/4 11:22 下午
 *
 * @author: guizhenyu
 */
@Component
public class ChatSendToAllHandler implements MessageHandler<ChatSendToAllRequest> {

  @Autowired
  private NettyChannelManager nettyChannelManager;

  @Override
  public void execute(Channel channel, ChatSendToAllRequest message) {
    // 这里简化，直接成功
    ChatSendResponse chatSendResponse = new ChatSendResponse().setMsgId(message.getMsgId()).setCode(0);
    channel.writeAndFlush(new Invocation(ChatSendResponse.TYPE, chatSendResponse));

    // 创建转发的消息，并广播发送
    ChatRedirectToUserRequest chatRedirectToUserRequest = new ChatRedirectToUserRequest()
        .setMsgId(message.getMsgId())
        .setContent(message.getContent());

    nettyChannelManager.sendAll(new Invocation(ChatRedirectToUserRequest.TYPE, chatRedirectToUserRequest));
  }

  @Override
  public String getType() {
    return ChatSendToAllRequest.TYPE;
  }
}
