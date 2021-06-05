package com.guizhenyu.netty.serverdemo.server.handler;

import com.guizhenyu.netty.serverdemo.server.NettyChannelManager;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * description: NettyServerHandler date: 2021/6/4 11:49 下午
 *
 * @author: guizhenyu
 */
@Component
@Sharable
@Slf4j
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

  @Autowired
  private NettyChannelManager nettyChannelManager;


  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    nettyChannelManager.add(ctx.channel());
  }

  @Override
  public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    nettyChannelManager.remove(ctx.channel());
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    log.error("[exceptionCaught][连接({}) 发生异常]", ctx.channel().id(), cause);
    ctx.channel().close();
  }
}
