package de.pxav.kelp.core.connect.connection;

import de.pxav.kelp.core.connect.packet.PacketOperator;
import de.pxav.kelp.core.connect.util.NativeTransportUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import javax.crypto.Cipher;
import java.net.InetSocketAddress;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadFactory;

/**
 * @author Etrayed
 */
public final class ConnectionProperties {

  final Bootstrap bootstrap;

  final PacketOperator packetOperator;

  Cipher encrypter, decrypter;

  public ConnectionProperties(InetSocketAddress remoteAddress, PacketOperator packetOperator) {
    this.bootstrap = new Bootstrap().remoteAddress(remoteAddress).channel(NioSocketChannel.class);
    this.packetOperator = packetOperator;
  }

  public ConnectionProperties withEncrypter(Cipher encrypter) {
    this.encrypter = encrypter;

    return this;
  }

  public ConnectionProperties withDecrypter(Cipher decrypter) {
    this.decrypter = decrypter;

    return this;
  }

  public ConnectionProperties withTransport(Class<? extends SocketChannel> socketChannelClass) {
    this.bootstrap.channel(socketChannelClass);

    return this;
  }

  public ConnectionProperties withEventLoopGroup(EventLoopGroup eventLoopGroup) {
    this.bootstrap.group(eventLoopGroup);

    return this;
  }

  public ConnectionProperties useNativeTransport() {
    return withTransport(NativeTransportUtil.NATIVE_SOCKET_CHANNEL_CLASS);
  }

  public ConnectionProperties useNativeEventLoopGroup() {
    return withEventLoopGroup(NativeTransportUtil.acquireEventLoopGroup());
  }

  public ConnectionProperties useNativeEventLoopGroup(int nThreads) {
    return withEventLoopGroup(NativeTransportUtil.acquireEventLoopGroup(nThreads));
  }

  public ConnectionProperties useNativeEventLoopGroup(int nThreads, Executor executor) {
    return withEventLoopGroup(NativeTransportUtil.acquireEventLoopGroup(nThreads, executor));
  }

  public ConnectionProperties useNativeEventLoopGroup(int nThreads, ThreadFactory factory) {
    return withEventLoopGroup(NativeTransportUtil.acquireEventLoopGroup(nThreads, factory));
  }

  public <T> ConnectionProperties withAttribute(AttributeKey<T> key, T value) {
    bootstrap.attr(key, value);

    return this;
  }

  public <T> ConnectionProperties option(ChannelOption<T> option, T value) {
    bootstrap.option(option, value);

    return this;
  }
}
