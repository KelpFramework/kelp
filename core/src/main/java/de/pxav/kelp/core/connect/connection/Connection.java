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

  private final InetSocketAddress remoteAddress;

  private final Cipher encrypter, decrypter;

  private final PacketOperator packetOperator;

  private Channel channel;

  public Connection(InetSocketAddress remoteAddress, Cipher encrypter, Cipher decrypter, PacketOperator packetOperator) {
    this.remoteAddress = remoteAddress;
    this.encrypter = encrypter;
    this.decrypter = decrypter;
    this.packetOperator = packetOperator;
  }

  public Future<Void> open(Bootstrap bootstrap) throws InterruptedException {
    return bootstrap.handler(new ChannelInitializer<Channel>() {

      @Override
      protected void initChannel(Channel channel) throws Exception {
        Connection.this.channel = channel;

        channel.pipeline().addFirst("decrypter", new ConnectionDecrypter(decrypter));
        channel.pipeline().addAfter("decrypter", "decoder", new PacketDecoder(packetOperator.registry()));
        channel.pipeline().addLast("handler", new ConnectionInboundHandler(packetOperator));
        channel.pipeline().addBefore("encrypter", "encoder", new PacketEncoder(packetOperator.registry()));
        channel.pipeline().addLast("encrypter", new ConnectionEncrypter(encrypter));
      }
    }).connect(remoteAddress).sync();
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
    return remoteAddress;
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
