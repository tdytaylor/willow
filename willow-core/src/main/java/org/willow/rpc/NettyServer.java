package org.willow.rpc;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tdytaylor
 * <p>
 * NettyServer.
 */
public class NettyServer {

  private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

  /**
   * the cache for alive worker channel.
   */
  private Map<String, Channel> channels;

  /**
   * the boss channel that receive connections and dispatch these to worker channel.
   */
  private Channel channel;

  private ServerBootstrap bootstrap;

  private EventLoopGroup bossGroup;
  private EventLoopGroup workerGroup;

  protected void doOpen() throws Throwable {
    bootstrap = new ServerBootstrap();

    bossGroup = NettyEventLoopFactory.eventLoopGroup(1, "NettyServerBoss");
    workerGroup = NettyEventLoopFactory
        .eventLoopGroup(Runtime.getRuntime().availableProcessors() * 2,
            "NettyServerWorker");

    bootstrap.group(bossGroup, workerGroup)
        .channel(NettyEventLoopFactory.serverSocketChannelClass())
        .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
        .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline();
          }
        });
    // bind
    ChannelFuture channelFuture = bootstrap.bind(9999);
    channelFuture.syncUninterruptibly();
    channel = channelFuture.channel();

  }
}
