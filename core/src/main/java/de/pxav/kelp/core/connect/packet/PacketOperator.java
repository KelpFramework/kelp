package de.pxav.kelp.core.connect.packet;

/**
 * @author Etrayed
 */
public interface PacketOperator {

  PacketRegistry registry();

  void handleIncomingPacket(Packet packet);

  void exceptionCaught(Throwable throwable);

  void onConnectionClose();
}
