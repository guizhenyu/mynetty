package com.guizhenyu.netty.serverdemo.messagehandler.auth;

import com.guizhenyu.netty.common.codec.Invocation;
import com.guizhenyu.netty.common.dispatcher.MessageHandler;
import com.guizhenyu.netty.common.message.auth.AuthRequest;
import com.guizhenyu.netty.common.message.auth.AuthResponse;
import com.guizhenyu.netty.serverdemo.server.NettyChannelManager;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * description: AuthRequestHandler date: 2021/6/4 7:54 下午
 *
 * @author: guizhenyu
 */
@Component
public class AuthRequestHandler implements MessageHandler<AuthRequest> {


  @Autowired
  private NettyChannelManager nettyChannelManager;

  @Override
  public void execute(Channel channel, AuthRequest authRequest) {
    // 如果未传递 accessToken
    if (StringUtils.isEmpty(authRequest.getAccessToken())){
      AuthResponse authResponse = new AuthResponse().setCode(1).setMessage("认证 accessToken 未传入");
      channel.writeAndFlush(new Invocation(AuthResponse.TYPE, authResponse));
      return;
    }

    //

    // 将用户和 Channel 绑定
    // 考虑到代码简化，我们先直接使用 accessToken 作为 User
    nettyChannelManager.addUser(channel, authRequest.getAccessToken());

    // 响应认证成功
    AuthResponse authResponse = new AuthResponse().setCode(0);
    channel.writeAndFlush(new Invocation(AuthResponse.TYPE, authResponse));
  }

  @Override
  public String getType() {
    return AuthRequest.TYPE;
  }
}
