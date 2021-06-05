package com.guizhenyu.netty.common.message.auth;

import com.guizhenyu.netty.common.dispatcher.Message;

/**
 * description: AuthResponse date: 2021/6/4 3:58 下午
 * 用户认证响应
 * @author: guizhenyu
 */
public class AuthResponse implements Message {

  public static final String TYPE = "AUTH_RESPONSE";

  /**
   * 响应状态码
   */
  private Integer code;

  /**
   * 响应提示
   */
  private String message;

  public Integer getCode() {
    return code;
  }

  public AuthResponse setCode(Integer code) {
    this.code = code;
    return this;
  }

  public String getMessage() {
    return message;
  }

  public AuthResponse setMessage(String message) {
    this.message = message;
    return this;
  }

  @Override
  public String toString() {
    return "AuthResponse{" +
        "code=" + code +
        ", message='" + message + '\'' +
        '}';
  }
}
