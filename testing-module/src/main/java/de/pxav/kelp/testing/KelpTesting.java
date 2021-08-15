package de.pxav.kelp.testing;

import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.application.NewKelpApplication;
import de.pxav.kelp.core.command.KelpCommandRepository;
import de.pxav.kelp.core.configuration.ConfigurationRepository;
import de.pxav.kelp.core.connect.KelpConnect;
import de.pxav.kelp.core.connect.connection.Connection;
import de.pxav.kelp.core.connect.connection.ConnectionProperties;
import de.pxav.kelp.core.connect.server.Server;
import de.pxav.kelp.core.connect.server.ServerProperties;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.testing.packet.DefaultConnectionPropertiesFactory;
import de.pxav.kelp.testing.packet.DefaultPacketOperator;
import de.pxav.kelp.testing.packet.PingPacket;

import java.net.InetSocketAddress;
import java.util.logging.Level;

/**
 * This represents the main class for the testing application.
 * This allows kelp developers to test their applications and
 * include some example code snippets.
 *
 * @author pxav
 */
@NewKelpApplication(
        applicationName = "KelpTesting",
        version = "0.0.1",
        authors = {"pxav"},
        description = "Allows you to extensively test the kelp features"
)
public class KelpTesting extends KelpApplication {

  private Server server;

  private Connection client;

  @Override
  public void onLoad() {
    KelpLogger.of(KelpTesting.class).info("Loading test application...");
  }

  @Override
  public void onEnable() {
    KelpLogger.of(KelpTesting.class).info("THIS IS A TEST INFO MESSAGE!!!!!!!!!!!!");
    KelpLogger.of(KelpTesting.class).severe("THIS IS A TEST INFO MESSAGE!!!!!!!!!!!!");
    getInstance(ConfigurationRepository.class).loadAll("de.pxav.kelp.testing");
    getInstance(KelpCommandRepository.class).loadCommands("de.pxav.kelp.testing");
    setupConnect();
  }

  private void setupConnect() {
    DefaultPacketOperator packetOperator = getInstance(DefaultPacketOperator.class);

    this.server = getInstance(KelpConnect.class).createServer(new ServerProperties(25576, packetOperator,
      new DefaultConnectionPropertiesFactory(packetOperator)).useNativeEventLoopGroup().useNativeTransport()); // create a server instance

    try {
      server.bind().sync(); // bind the server

      KelpLogger.of(KelpTesting.class).fine("[KelpConnect] Server bound");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    this.client = getInstance(KelpConnect.class).createConnection(new ConnectionProperties(new InetSocketAddress(25576),
      packetOperator).useNativeEventLoopGroup().useNativeTransport()); // create a connection using the native transport

    try {
      client.connect().sync(); // connect client

      KelpLogger.of(KelpTesting.class).fine("[KelpConnect] Client connected");
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

    client.write(new PingPacket());

  }

  @Override
  public void onDisable() {
    getInstance(KelpConnect.class).closeAllConnections();

    server.close();
  }
}
