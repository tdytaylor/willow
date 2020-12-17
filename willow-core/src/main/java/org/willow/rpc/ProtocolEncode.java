package org.willow.rpc;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class ProtocolEncode extends MessageToByteEncoder {

  @Override
  protected void encode(ChannelHandlerContext channelHandlerContext, Object o, ByteBuf byteBuf)
      throws Exception {
    ByteBuf buffer = channelHandlerContext.alloc().buffer();
    buffer.writeByte((byte) 64);
  }
}
