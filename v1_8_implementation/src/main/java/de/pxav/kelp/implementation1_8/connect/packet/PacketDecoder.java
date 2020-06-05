package de.pxav.kelp.implementation1_8.connect.packet;

import com.google.common.base.Preconditions;
import de.pxav.kelp.core.connect.KelpBuffer;
import de.pxav.kelp.core.connect.packet.IPacketDecoder;
import de.pxav.kelp.core.connect.packet.Packet;
import de.pxav.kelp.core.connect.packet.PacketRegistry;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import javax.crypto.Cipher;
import java.util.List;

/**
 * @author Etrayed
 */
public class PacketDecoder extends ByteToMessageDecoder implements IPacketDecoder {

  private final PacketRegistry registry;

  private final Cipher decrypter;

  public PacketDecoder(PacketRegistry registry, Cipher decrypter) {
    this.registry = registry;
    this.decrypter = decrypter;
  }

  @Override
  protected void decode(ChannelHandlerContext context, ByteBuf byteBuf, List<Object> list) throws Exception {
    if(decrypter != null) {
      byte[] allBytes = new byte[byteBuf.readableBytes()];

      byteBuf.readBytes(allBytes);

      byteBuf = Unpooled.wrappedBuffer(decrypter.doFinal(allBytes));
    }

    KelpBuffer buffer = new KelpBuffer(byteBuf);
    int packetId = buffer.readVarInt();

    Preconditions.checkArgument(registry.isRegistered(packetId), String.format("received unregistered packetId(%d)", packetId));

    Packet packet = registry.getPacketClass(packetId).getConstructor().newInstance();

    packet.take(new KelpBuffer(buffer.delegate()));

    list.add(packet);
  }
}
