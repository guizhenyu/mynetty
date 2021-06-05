package com.guizhenyu.netty.serverdemo.server;

import com.guizhenyu.netty.common.codec.Invocation;
import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.util.AttributeKey;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 客户端 Channel 管理器。提供两种功能：
 * 1. 客户端 Channel 的管理
 * 2. 向客户端 Channel 发送消息
 * @author apple_gui
 */
@Component
@Slf4j
public class NettyChannelManager {

  /**
   * {@link Channel#attr(AttributeKey)} 属性中，表示 Channel 对应的用户
   */
  private static final AttributeKey<String> CHANNEL_ATTR_KEY_USER = AttributeKey.newInstance("user");

  /**
   * Channel 映射  连接
   */
  private ConcurrentHashMap<ChannelId, Channel> channels = new ConcurrentHashMap<>();

  /**
   * 用户和channel的映射
   *
   * 通过它，可以获取用户对应的channel, 这样，我们可以向指定用户发送信息
   *
   */
  private ConcurrentHashMap<String, Channel> userChannels = new ConcurrentHashMap<>();


  /**
   * 添加 Channel 到 {@link #channels} 中
   *
   * @param channel
   */
  public void add(Channel channel){
    channels.put(channel.id(), channel);
    log.info("[add][一个连接({})加入]", channel.id());
  }

  /**
   * 添加指定用户到 {@link #userChannels} 中
   *
   * @param channel
   * @param user
   */
  public void addUser(Channel channel, String user){
    Channel existChannel = channels.get(channel.id());
    if (Objects.isNull(existChannel)){
      log.error("[addUser][连接({}) 不存在]", channel.id());
      return;
    }
    //设置属性
    channel.attr(CHANNEL_ATTR_KEY_USER).set(user);
    userChannels.put(user,channel);
  }

  /**
   * 将 Channel 从 {@link #channels} 和 {@link #userChannels} 中移除
   *
   * @param channel
   */
  public void remove(Channel channel){
    channels.remove(channel.id());

    if (channel.hasAttr(CHANNEL_ATTR_KEY_USER)){
      userChannels.remove(channel.attr(CHANNEL_ATTR_KEY_USER).get());
    }
    log.info("[remove][一个连接({})离开]", channel.id());
  }

  /**
   * 向指定用户发送信息
   *
   * @param user
   * @param invocation
   */
  public void send(String user, Invocation invocation){
    // 获得用户对应的 channel
    Channel channel = userChannels.get(user);

    if(Objects.isNull(channel)){
      log.error("[send][连接不存在]");
      return;
    }

    if(!channel.isActive()){
      log.error("[send][连接({})未激活", channel.id());
      return;
    }

    channel.writeAndFlush(invocation);
  }

  /**
   * 向所有用户发信息
   *
   * @param invocation
   */
  public void sendAll(Invocation invocation){
    for (Channel channel : channels.values()){
      if(!channel.isActive()){
        log.error("[send][连接({})未激活", channel.id());
        break;
      }
      channel.writeAndFlush(invocation);
    }
  }
}
