package de.pxav.kelp.core.connect;

import com.google.inject.Singleton;
import de.pxav.kelp.core.connect.connection.Connection;
import de.pxav.kelp.core.connect.connection.ConnectionHolder;
import de.pxav.kelp.core.connect.connection.ConnectionProperties;

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

  public void closeAllConnections() {
    connectionHolder.closeAll();
  }
}
