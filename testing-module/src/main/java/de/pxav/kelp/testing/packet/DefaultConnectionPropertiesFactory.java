package de.pxav.kelp.testing.packet;

import de.pxav.kelp.core.connect.connection.ConnectionProperties;
import de.pxav.kelp.core.connect.packet.PacketOperator;
import de.pxav.kelp.core.connect.server.ConnectionPropertiesFactory;
import de.pxav.kelp.core.connect.server.Server;

import java.net.InetSocketAddress;

/**
 * @author Etrayed
 */
public class DefaultConnectionPropertiesFactory implements ConnectionPropertiesFactory {

  private final PacketOperator packetOperator;

  public DefaultConnectionPropertiesFactory(PacketOperator packetOperator) {
    this.packetOperator = packetOperator;
  }

  @Override
  public ConnectionProperties createProperties(Server server, InetSocketAddress remoteAddress) {
    return new ConnectionProperties(remoteAddress, packetOperator);
  }
}
