package org.willow.rpc;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import java.nio.channels.Channel;
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
    workerGroup = NettyEventLoopFactory.eventLoopGroup(
        getUrl().getPositiveParameter(IO_THREADS_KEY, Constants.DEFAULT_IO_THREADS),
        "NettyServerWorker");

    final NettyServerHandler nettyServerHandler = new NettyServerHandler(getUrl(), this);
    channels = nettyServerHandler.getChannels();

    bootstrap.group(bossGroup, workerGroup)
        .channel(NettyEventLoopFactory.serverSocketChannelClass())
        .option(ChannelOption.SO_REUSEADDR, Boolean.TRUE)
        .childOption(ChannelOption.TCP_NODELAY, Boolean.TRUE)
        .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            // FIXME: should we use getTimeout()?
            int idleTimeout = UrlUtils.getIdleTimeout(getUrl());
            NettyCodecAdapter adapter = new NettyCodecAdapter(getCodec(), getUrl(), NettyServer.this);
            if (getUrl().getParameter(SSL_ENABLED_KEY, false)) {
              ch.pipeline().addLast("negotiation",
                  SslHandlerInitializer.sslServerHandler(getUrl(), nettyServerHandler));
            }
            ch.pipeline()
                .addLast("decoder", adapter.getDecoder())
                .addLast("encoder", adapter.getEncoder())
                .addLast("server-idle-handler", new IdleStateHandler(0, 0, idleTimeout, MILLISECONDS))
                .addLast("handler", nettyServerHandler);
          }
        });
    // bind
    ChannelFuture channelFuture = bootstrap.bind(getBindAddress());
    channelFuture.syncUninterruptibly();
    channel = channelFuture.channel();

  }
}
