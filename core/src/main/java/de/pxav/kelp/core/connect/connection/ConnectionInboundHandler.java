package de.pxav.kelp.core.connect.connection;

import de.pxav.kelp.core.connect.packet.Packet;
import de.pxav.kelp.core.connect.packet.PacketOperator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Etrayed
 */
public class ConnectionInboundHandler extends SimpleChannelInboundHandler<Packet> {

  private final PacketOperator operator;

  ConnectionInboundHandler(PacketOperator operator) {
    this.operator = operator;
  }

  @Override
  protected void channelRead0(ChannelHandlerContext context, Packet packet) throws Exception {

  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

  }
}
