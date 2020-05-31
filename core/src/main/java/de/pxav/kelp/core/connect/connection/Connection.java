package de.pxav.kelp.core.connect.connection;

import de.pxav.kelp.core.connect.packet.Packet;
import de.pxav.kelp.core.connect.packet.PacketDecoder;
import de.pxav.kelp.core.connect.packet.PacketEncoder;
import de.pxav.kelp.core.connect.packet.PacketOperator;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.util.concurrent.Future;

import javax.crypto.Cipher;
import java.net.InetSocketAddress;

/**
 * @author Etrayed
 */
public class Connection {

  final ConnectionHolder holder;

  private final Bootstrap bootstrap;

  private final Cipher encrypter, decrypter;

  private final PacketOperator packetOperator;

  private Channel channel;

  public Connection(ConnectionHolder holder, ConnectionProperties properties) {
    this.holder = holder;
    this.bootstrap = properties.bootstrap;
    this.encrypter = properties.encrypter;
    this.decrypter = properties.decrypter;
    this.packetOperator = properties.packetOperator;
  }

  public Connection(ConnectionHolder holder, ConnectionProperties properties, Channel channel) {
    this(holder, properties);

    initChannel(channel);
  }

  public Future<Void> open() throws InterruptedException {
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

    channel.pipeline().addFirst("decrypter", new ConnectionDecrypter(decrypter));
    channel.pipeline().addAfter("decrypter", "decoder", new PacketDecoder(packetOperator.registry()));
    channel.pipeline().addLast("handler", new ConnectionInboundHandler(this));
    channel.pipeline().addBefore("encrypter", "encoder", new PacketEncoder(packetOperator.registry()));
    channel.pipeline().addLast("encrypter", new ConnectionEncrypter(encrypter));
  }

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
