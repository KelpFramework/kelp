package de.pxav.kelp.core.connect.connection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Etrayed
 */
public class ConnectionHolder {

  private final List<Connection> registeredConnections;

  public ConnectionHolder() {
    this.registeredConnections = new ArrayList<>();
  }

  public void register(Connection connection) {
    synchronized (registeredConnections) {
      registeredConnections.add(connection);
    }
  }

  public boolean isRegistered(Connection connection) {
    return registeredConnections.contains(connection);
  }

  public List<Connection> getRegisteredConnections() {
    return registeredConnections;
  }

  public void unregister(Connection connection) {
    synchronized (registeredConnections) {
      registeredConnections.remove(connection);
    }
  }

  public void closeAll() {
    synchronized (registeredConnections) {
      registeredConnections.forEach(Connection::close);
      registeredConnections.clear();
    }
  }
}
