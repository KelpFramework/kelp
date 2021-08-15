package de.pxav.kelp.testing.packet;

import com.google.inject.Inject;
import de.pxav.kelp.core.connect.connection.Connection;
import de.pxav.kelp.core.connect.packet.Packet;
import de.pxav.kelp.core.connect.packet.PacketOperator;
import de.pxav.kelp.core.connect.packet.PacketRegistry;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.testing.KelpTesting;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author Etrayed
 */
public class DefaultPacketOperator implements PacketOperator {

  private final PacketRegistry packetRegistry;

  private final ScheduledExecutorService executorService;

  @Inject
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
      KelpLogger.of(KelpTesting.class).fine(String.format("[KelpConnect] %s -> Ping: %dms", connection.isChildConnection() ? "Server" : "Client",
        System.currentTimeMillis() - ((PingPacket) packet).getTimestamp()));

      executorService.schedule(() -> connection.write(new PingPacket()), 20, TimeUnit.SECONDS); // send a ping packet every 20 seconds
    }
  }

  @Override
  public void exceptionCaught(Connection connection, Throwable throwable) {
    KelpLogger.of(KelpTesting.class).severe(String.format("[KelpConnect] Encountered unknown exception from %s:", connection.getRemoteAddress()));

    throwable.printStackTrace();
  }

  @Override
  public void onConnectionClose(Connection connection) {
    KelpLogger.of(KelpTesting.class).fine(String.format("[KelpConnect] Connection to %s was closed.", connection.getRemoteAddress()));;
  }
}
