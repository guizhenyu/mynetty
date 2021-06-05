package com.guizhenyu.netty.clientdemo.messagehandler.auth;

import com.guizhenyu.netty.common.message.auth.AuthResponse;
import com.guizhenyu.netty.common.dispatcher.MessageHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * description: AuthResponseHandler date: 2021/6/4 4:13 下午
 *
 * @author: guizhenyu
 */
@Component
@Slf4j
public class AuthResponseHandler implements MessageHandler<AuthResponse> {

  @Override
  public void execute(Channel channel, AuthResponse message) {
    log.info("[execute][认证结果：{}]", message);
  }

  @Override
  public String getType() {
    return AuthResponse.TYPE;
  }
}
