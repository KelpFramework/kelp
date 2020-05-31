package de.pxav.kelp.testing.packet;

import de.pxav.kelp.core.connect.connection.Connection;
import de.pxav.kelp.core.connect.packet.Packet;
import de.pxav.kelp.core.connect.packet.PacketOperator;
import de.pxav.kelp.core.connect.packet.PacketRegistry;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Etrayed
 */
public class DefaultPacketOperator implements PacketOperator {

  private final PacketRegistry packetRegistry;

  private final ScheduledExecutorService executorService;

  public DefaultPacketOperator() {
    this.packetRegistry = new PacketRegistry();
    this.executorService = Executors.newSingleThreadScheduledExecutor();

    packetRegistry.register(1, PingPacket.class);
  }

  @Override
  public PacketRegistry registry() {
    return packetRegistry;
  }

  @Override
  public void handleIncomingPacket(Connection connection, Packet packet) {
    if(packet instanceof PingPacket) {
      System.out.printf("[%s] Ping: %d%n", connection.isChildConnection() ? "Server" : "Client",
        System.currentTimeMillis() - ((PingPacket) packet).getTimestamp());

      executorService.schedule(() -> connection.write(new PingPacket()), 20, TimeUnit.SECONDS); // send a ping packet every 20 seconds
    }
  }

  @Override
  public void exceptionCaught(Connection connection, Throwable throwable) {
    System.out.printf("Encountered unknown exception from %s:%n", connection.getRemoteAddress());

    throwable.printStackTrace();
  }

  @Override
  public void onConnectionClose(Connection connection) {
    System.out.printf("Connection to %s was closed.%n", connection.getRemoteAddress());
  }
}
