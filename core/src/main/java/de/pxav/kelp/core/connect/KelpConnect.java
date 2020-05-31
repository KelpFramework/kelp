package de.pxav.kelp.core.connect;

import com.google.inject.Singleton;
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
@Singleton
public class KelpConnect {

  private final ConnectionHolder connectionHolder;

  public KelpConnect() {
    this.connectionHolder = new ConnectionHolder();
  }

  public Connection createConnection(ConnectionProperties properties) {
    return new Connection(connectionHolder, properties);
  }

  public Server createServer(ServerProperties properties) {
    return new Server(properties);
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
