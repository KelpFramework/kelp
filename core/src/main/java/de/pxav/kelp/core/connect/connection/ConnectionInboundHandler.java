package de.pxav.kelp.core.connect.connection;

import de.pxav.kelp.core.connect.packet.Packet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Etrayed
 */
public class ConnectionInboundHandler extends SimpleChannelInboundHandler<Packet> {

  private final Connection connection;

  ConnectionInboundHandler(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    connection.holder.unregister(connection);

    connection.getPacketOperator().onConnectionClose(connection);
  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    connection.holder.register(connection);
  }

  @Override
  protected void channelRead0(ChannelHandlerContext context, Packet packet) throws Exception {
    connection.getPacketOperator().handleIncomingPacket(connection, packet);
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    connection.getPacketOperator().exceptionCaught(connection, cause);
  }
}
