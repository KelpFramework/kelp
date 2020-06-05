package de.pxav.kelp.core.connect;

import com.google.inject.Inject;
import de.pxav.kelp.core.connect.connection.Connection;
import de.pxav.kelp.core.connect.connection.ConnectionHolder;
import de.pxav.kelp.core.connect.connection.ConnectionProperties;
import de.pxav.kelp.core.connect.server.Server;
import de.pxav.kelp.core.connect.server.ServerProperties;

import java.util.Collections;
import java.util.List;

/**
 * @author Etrayed
 */
public class KelpConnect {

  private final KelpConnectVersionTemplate versionTemplate;

  private final ConnectionHolder connectionHolder;

  @Inject
  public KelpConnect(KelpConnectVersionTemplate versionTemplate) {
    this.versionTemplate = versionTemplate;
    this.connectionHolder = new ConnectionHolder();
  }

  public Connection createConnection(ConnectionProperties properties) {
    return new Connection(versionTemplate, connectionHolder, properties);
  }

  public Server createServer(ServerProperties properties) {
    return new Server(versionTemplate, properties);
  }

  public List<Connection> getRegisteredConnections() {
    return Collections.unmodifiableList(connectionHolder.getRegisteredConnections());
  }

  public boolean isConnectionRegistered(Connection connection) {
    return connectionHolder.isRegistered(connection);
  }

  public void closeAllConnections() {
    connectionHolder.closeAll();
  }
}
