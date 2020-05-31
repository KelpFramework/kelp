package de.pxav.kelp.core.connect.packet;

import de.pxav.kelp.core.connect.connection.Connection;

/**
 * @author Etrayed
 */
public interface PacketOperator {

  PacketRegistry registry();

  void handleIncomingPacket(Connection connection, Packet packet);

  void exceptionCaught(Connection connection, Throwable throwable);

  void onConnectionClose(Connection connection);
}
