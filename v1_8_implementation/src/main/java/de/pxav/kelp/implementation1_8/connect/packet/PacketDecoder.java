package de.pxav.kelp.implementation1_8.connect.packet;

import com.google.common.base.Preconditions;
import de.pxav.kelp.core.connect.KelpBuffer;
import de.pxav.kelp.core.connect.packet.IPacketDecoder;
import de.pxav.kelp.core.connect.packet.Packet;
import de.pxav.kelp.core.connect.packet.PacketRegistry;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author Etrayed
 */
public class PacketDecoder extends MessageToMessageDecoder<KelpBuffer> implements IPacketDecoder {

  private final PacketRegistry registry;

  public PacketDecoder(PacketRegistry registry) {
    this.registry = registry;
  }

  @Override
  protected void decode(ChannelHandlerContext context, KelpBuffer buffer, List<Object> list) throws Exception {
    int packetId = buffer.readVarInt();

    Preconditions.checkArgument(registry.isRegistered(packetId), String.format("received unregistered packetId(%d)", packetId));

    Packet packet = registry.getPacketClass(packetId).getConstructor().newInstance();

    packet.take(new KelpBuffer(buffer.delegate()));

    list.add(packet);
  }
}
