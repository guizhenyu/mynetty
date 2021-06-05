package com.guizhenyu.netty.common.codec;

import com.alibaba.fastjson.JSON;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
/**
 * description: InvocationDecoder date: 2021/6/3 8:33 下午
 *  解码器
 * @author: guizhenyu
 */


@Slf4j
public class InvocationDecoder extends ByteToMessageDecoder {

  @Override
  protected void decode(ChannelHandlerContext ctx, ByteBuf in,
      List<Object> out) {
    // 标记当前读取位置
    in.markReaderIndex();
    // 判断是否能够读取 length 长度
    // TODO：为什么是 4
    if(in.readableBytes() <= 4){
      return;
    }

    // 读取长度
    int length = in.readInt();
    if (length < 0){
      throw new CorruptedFrameException("negative length: " + length);
    }

    // 如果 message 不可读， 则退回到原读位置
    if(in.readableBytes() < length){
      in.resetReaderIndex();
      return;
    }

    // 读取内容
    byte[] content = new byte[length];
    in.readBytes(content);

    // 解析成 Invocation
    Invocation invocation = JSON.parseObject(content, Invocation.class);
    out.add(invocation);

    log.info("[decode][连接({}) 解析到一条消息({})]", ctx.channel().id(), invocation.toString());


  }
}
