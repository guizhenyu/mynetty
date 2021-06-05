package com.guizhenyu.netty.serverdemo.messagehandler.heartbeat;

import com.guizhenyu.netty.common.codec.Invocation;
import com.guizhenyu.netty.common.dispatcher.MessageHandler;
import com.guizhenyu.netty.common.message.heartbeat.HeartbeatRequest;
import com.guizhenyu.netty.common.message.heartbeat.HeartbeatResponse;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * description: HeartbeatRequestHandler date: 2021/6/4 11:45 下午
 *
 * @author: guizhenyu
 */
@Component
@Slf4j
public class HeartbeatRequestHandler implements MessageHandler<HeartbeatRequest> {

  @Override
  public void execute(Channel channel, HeartbeatRequest message) {
    log.info("[execute][收到连接({}) 的心跳请求]", channel.id());

    HeartbeatResponse heartbeatResponse = new HeartbeatResponse();
    channel.writeAndFlush(new Invocation(HeartbeatResponse.TYPE, heartbeatResponse));

  }

  @Override
  public String getType() {
    return HeartbeatRequest.TYPE;
  }
}
