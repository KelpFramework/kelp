package de.pxav.kelp.core.connect.server;

import de.pxav.kelp.core.connect.packet.PacketOperator;
import de.pxav.kelp.core.connect.util.NativeTransportUtil;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.AttributeKey;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * @author Etrayed
 */
public final class ServerProperties {

  final ServerBootstrap bootstrap;

  final PacketOperator packetOperator;

  final ConnectionPropertiesFactory connectionPropertiesFactory;

  public ServerProperties(int port, PacketOperator packetOperator, ConnectionPropertiesFactory propertiesFactory) {
    this.bootstrap = new ServerBootstrap().localAddress(port).channel(NioServerSocketChannel.class);
    this.packetOperator = packetOperator;
    this.connectionPropertiesFactory = propertiesFactory;
  }

  public ServerProperties withTransport(Class<? extends ServerSocketChannel> socketChannelClass) {
    this.bootstrap.channel(socketChannelClass);

    return this;
  }

  public ServerProperties withEventLoopGroup(EventLoopGroup eventLoopGroup) {
    this.bootstrap.group(eventLoopGroup);

    return this;
  }

  public ServerProperties withEventLoopGroups(EventLoopGroup parentGroup, EventLoopGroup childGroup) {
    this.bootstrap.group(parentGroup, childGroup);

    return this;
  }

  public ServerProperties useNativeTransport() {
    return withTransport(NativeTransportUtil.NATIVE_SERVER_SOCKET_CHANNEL_CLASS);
  }

  public ServerProperties useNativeEventLoopGroup() {
    return withEventLoopGroup(NativeTransportUtil.acquireEventLoopGroup());
  }

  public ServerProperties useNativeEventLoopGroup(int nThreads) {
    return withEventLoopGroup(NativeTransportUtil.acquireEventLoopGroup(nThreads));
  }

  public ServerProperties useNativeEventLoopGroup(int nThreads, Executor executor) {
    return withEventLoopGroup(NativeTransportUtil.acquireEventLoopGroup(nThreads, executor));
  }

  public ServerProperties useNativeEventLoopGroup(int nThreads, ThreadFactory factory) {
    return withEventLoopGroup(NativeTransportUtil.acquireEventLoopGroup(nThreads, factory));
  }

  public <T> ServerProperties withAttribute(AttributeKey<T> key, T value) {
    this.bootstrap.attr(key, value);

    return this;
  }

  public <T> ServerProperties withChildAttribute(AttributeKey<T> key, T value) {
    this.bootstrap.childAttr(key, value);

    return this;
  }

  public <T> ServerProperties option(ChannelOption<T> option, T value) {
    this.bootstrap.option(option, value);

    return this;
  }

  public <T> ServerProperties childOption(ChannelOption<T> option, T value) {
    this.bootstrap.childOption(option, value);

    return this;
  }
}
