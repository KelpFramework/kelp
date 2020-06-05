package de.pxav.kelp.core.connect.server;

import de.pxav.kelp.core.connect.connection.ConnectionProperties;

import java.net.InetSocketAddress;

/**
 * @author Etrayed
 */
@FunctionalInterface
public interface ConnectionPropertiesFactory {

  ConnectionProperties createProperties(Server server, InetSocketAddress remoteAddress);
}
