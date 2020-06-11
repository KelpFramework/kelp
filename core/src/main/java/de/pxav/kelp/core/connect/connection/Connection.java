package de.pxav.kelp.core.connect.connection;

import de.pxav.kelp.core.connect.KelpConnectVersionTemplate;
import de.pxav.kelp.core.connect.packet.Packet;
import de.pxav.kelp.core.connect.packet.PacketOperator;
import de.pxav.kelp.core.connect.server.Server;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.util.concurrent.Future;

import javax.crypto.Cipher;
import java.io.Closeable;
import java.net.InetSocketAddress;

/**
 * @author Etrayed
 */
public class Connection implements Closeable {

  private final KelpConnectVersionTemplate versionTemplate;

  final ConnectionHolder holder;

  private final Bootstrap bootstrap;

  private final Cipher encrypter, decrypter;

  private final PacketOperator packetOperator;

  private Channel channel;

  private Server parent;

  public Connection(KelpConnectVersionTemplate versionTemplate, ConnectionHolder holder, ConnectionProperties properties) {
    this.versionTemplate = versionTemplate;
    this.holder = holder;
    this.bootstrap = properties.bootstrap;
    this.encrypter = properties.encrypter;
    this.decrypter = properties.decrypter;
    this.packetOperator = properties.packetOperator;
  }

  public Connection(KelpConnectVersionTemplate versionTemplate, ConnectionHolder holder, ConnectionProperties properties,
                    Server parent, Channel channel) {
    this(versionTemplate, holder, properties);

    this.parent = parent;

    initChannel(channel);
  }

  public Future<Void> connect() {
    if(isReady()) {
      return null;
    }

    return bootstrap.handler(new ChannelInitializer<Channel>() {

      @Override
      protected void initChannel(Channel channel) throws Exception {
        Connection.this.initChannel(channel);
      }
    }).connect();
  }

  private void initChannel(Channel channel) {
    this.channel = channel;

    channel.pipeline().addFirst("decoder", (ChannelHandler) versionTemplate.newPacketDecoder(packetOperator.registry(), decrypter));
    channel.pipeline().addAfter("decoder", "handler", new ConnectionInboundHandler(this));
    channel.pipeline().addLast("encoder", (ChannelHandler) versionTemplate.newPacketEncoder(packetOperator.registry(), encrypter));
  }

  @Override
  public void close() {
    channel.close();
  }

  public void write(Packet packet) {
    channel.writeAndFlush(packet);
  }

  public boolean isReady() {
    return channel != null && channel.isOpen();
  }

  public boolean isClosed() {
    return channel == null || !channel.isOpen();
  }

  public boolean isChildConnection() {
    return parent != null;
  }

  public Server getParent() {
    return parent;
  }

  public InetSocketAddress getRemoteAddress() {
    return channel != null ? (InetSocketAddress) channel.remoteAddress() : null;
  }

  public String getEncryptionAlgorithm() {
    return encrypter == null ? "None" : encrypter.getAlgorithm();
  }

  public String getDecryptionAlgorithm() {
    return decrypter == null ? "None" : decrypter.getAlgorithm();
  }

  public PacketOperator getPacketOperator() {
    return packetOperator;
  }
}
