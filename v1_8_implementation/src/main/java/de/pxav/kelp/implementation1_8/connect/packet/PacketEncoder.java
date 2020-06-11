package de.pxav.kelp.implementation1_8.connect.packet;

import com.google.common.base.Preconditions;
import de.pxav.kelp.core.connect.KelpBuffer;
import de.pxav.kelp.core.connect.packet.IPacketEncoder;
import de.pxav.kelp.core.connect.packet.Packet;
import de.pxav.kelp.core.connect.packet.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import javax.crypto.Cipher;

/**
 * @author Etrayed
 */
public class PacketEncoder extends MessageToByteEncoder<Packet> implements IPacketEncoder {

  private final PacketRegistry registry;

  private final Cipher encrypter;

  public PacketEncoder(PacketRegistry registry, Cipher encrypter) {
    this.registry = registry;
    this.encrypter = encrypter;
  }

  @Override
  protected void encode(ChannelHandlerContext context, Packet packet, ByteBuf output) throws Exception {
    Preconditions.checkArgument(registry.isRegistered(packet.getClass()), "could not encode unregistered packet: "
      + packet.getClass().getCanonicalName());

    ByteBuf byteBuf = Unpooled.buffer();
    KelpBuffer buffer = new KelpBuffer(byteBuf);
    int packetId = registry.getId(packet.getClass());

    buffer.writeVarInt(packetId);

    packet.store(buffer);

    if(encrypter != null) {
      encrypter.doFinal(byteBuf.nioBuffer(), output.nioBuffer());
    } else {
      output.writeBytes(byteBuf);
    }
  }
}
