package com.guizhenyu.netty.common.message.auth;

import com.guizhenyu.netty.common.dispatcher.Message;

/**
 * description: AuthRequest date: 2021/6/4 3:53 下午
 * 用户认证请求
 * @author: guizhenyu
 */
public class AuthRequest implements Message {

  public static final String  TYPE = "AUTH_REQUEST";


  /**
   * 认证 Token
   */
  private String accessToken;

  public AuthRequest setAccessToken(String accessToken) {
    this.accessToken = accessToken;
    return this;
  }


  public String getAccessToken() {
    return accessToken;
  }

  @Override
  public String toString() {
    return "AuthRequest{" +
        "accessToken='" + accessToken + '\'' +
        '}';
  }
}
