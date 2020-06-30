package de.pxav.kelp.core.inventory.listener;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class KelpListenerRepository {

  private Map<UUID, Collection<String>> listenerOwners = Maps.newHashMap();
  private Map<String, ClickListener> listeners = Maps.newHashMap();

  public String registerListener(ClickListener listener) {
    String listenerId = this.newListenerId();
    listeners.put(listenerId, listener);
    return listenerId;
  }

  public void fireListener(String listenerId, KelpClickEvent event) {
    this.listeners.get(listenerId).onClick(event);
  }

  public void unregisterListeners(UUID playerFor) {
    listenerOwners.get(playerFor).forEach(currentId -> listeners.remove(currentId));
    listenerOwners.remove(playerFor);
  }

  public String newListenerId() {
    return UUID.randomUUID().toString().substring(0, 14);
  }

}
