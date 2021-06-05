package com.guizhenyu.netty.common.message.chat;

import com.guizhenyu.netty.common.dispatcher.Message;

/**
 * description: ChatRedirectToUserRequest date: 2021/6/4 4:02 下午
 *  转发消息给一个用户的 Message
 * @author: guizhenyu
 */
public class ChatRedirectToUserRequest implements Message {

  public static final String TYPE = "CHAT_REDIRECT_TO_USER_REQUEST";

  /**
   * 消息编号
   */
  private String msgId;

  /**
   * 内容
   */
  private String content;

  public String getMsgId() {
    return msgId;
  }

  public ChatRedirectToUserRequest setMsgId(String msgId) {
    this.msgId = msgId;
    return this;
  }

  public String getContent() {
    return content;
  }

  public ChatRedirectToUserRequest setContent(String content) {
    this.content = content;
    return this;
  }

  @Override
  public String toString() {
    return "ChatRedirectToUserRequest{" +
        "msgId='" + msgId + '\'' +
        ", content='" + content + '\'' +
        '}';
  }
}
