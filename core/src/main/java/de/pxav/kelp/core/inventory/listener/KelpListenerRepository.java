package de.pxav.kelp.core.inventory.listener;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.inject.Singleton;

import java.util.Map;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class KelpListenerRepository {

  private Multimap<UUID, String> listenerOwners = ArrayListMultimap.create();
  private Map<String, ClickListener> listeners = Maps.newHashMap();

  public String registerListener(UUID playerFor, ClickListener listener) {
    String listenerId = this.newListenerId();
    listeners.put(listenerId, listener);
    listenerOwners.put(playerFor, listenerId);
    return listenerId;
  }

  public void fireListener(String listenerId, KelpClickEvent event) {
    this.listeners.get(listenerId).onClick(event);
  }

  public void unregisterListeners(UUID playerFor) {
    listenerOwners.get(playerFor).forEach(currentId -> listeners.remove(currentId));
    listenerOwners.removeAll(playerFor);
  }

  public String newListenerId() {
    return UUID.randomUUID().toString().substring(0, 14);
  }

}
