package de.pxav.kelp.core.connect.util;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueServerSocketChannel;
import io.netty.channel.kqueue.KQueueSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Arrays;

/**
 * @author Etrayed
 */
public class NativeTransportUtil {

  public static final Class<? extends SocketChannel> NATIVE_SOCKET_CHANNEL_CLASS;

  public static final Class<? extends ServerSocketChannel> NATIVE_SERVER_SOCKET_CHANNEL_CLASS;

  private static final Class<? extends EventLoopGroup> NATIVE_EVENT_LOOP_GROUP_CLASS;

  public static EventLoopGroup acquireEventLoopGroup(Object... args) {
    try {
      return NATIVE_EVENT_LOOP_GROUP_CLASS.getConstructor(Arrays.stream(args).map(Object::getClass).toArray(Class[]::new))
        .newInstance(args);
    } catch (ReflectiveOperationException e) {
      e.printStackTrace();
    }

    return new NioEventLoopGroup();
  }

  static {
    if(Epoll.isAvailable()) {
      NATIVE_SOCKET_CHANNEL_CLASS = EpollSocketChannel.class;
      NATIVE_SERVER_SOCKET_CHANNEL_CLASS = EpollServerSocketChannel.class;
      NATIVE_EVENT_LOOP_GROUP_CLASS = EpollEventLoopGroup.class;
    } else if(KQueue.isAvailable()) {
      NATIVE_SOCKET_CHANNEL_CLASS = KQueueSocketChannel.class;
      NATIVE_SERVER_SOCKET_CHANNEL_CLASS = KQueueServerSocketChannel.class;
      NATIVE_EVENT_LOOP_GROUP_CLASS = KQueueEventLoopGroup.class;
    } else {
      NATIVE_SOCKET_CHANNEL_CLASS = NioSocketChannel.class;
      NATIVE_SERVER_SOCKET_CHANNEL_CLASS = NioServerSocketChannel.class;
      NATIVE_EVENT_LOOP_GROUP_CLASS = NioEventLoopGroup.class;
    }
  }
}
