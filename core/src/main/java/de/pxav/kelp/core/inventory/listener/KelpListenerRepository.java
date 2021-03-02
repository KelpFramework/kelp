package de.pxav.kelp.core.inventory.listener;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.inject.Singleton;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

/**
 * This repository stores the listeners which are currently
 * active and can fire them when needed.
 *
 * Note that the ignition itself is not handled by this class,
 * but by the version modules, which check all clicked items
 * and can the {@link #fireListener(String, KelpClickEvent)} method
 * when a matching item was found.
 *
 * @author pxav
 */
@Singleton
public class KelpListenerRepository {

  // Used to store listeners that are independent from players
  public static final UUID GLOBAL_LISTENER_ID = UUID.nameUUIDFromBytes("KLR_GLOBAL".getBytes(StandardCharsets.UTF_8));

  // Stores which listener ids go with which player.
  // This allows to unregister listeners of a specific player
  // when they quit the server or their inventory in order to
  // save performance and memory.
  // player UUID -> list of their listener ids.
  private Multimap<UUID, String> listenerOwners = ArrayListMultimap.create();

  // Stores all listener ids with the listener interface to be
  // executed when the listener is fired.
  private Map<String, ClickListener> listeners = Maps.newHashMap();

  /**
   * Registers a new listener for the given player. This
   * method automatically generates a new id and returns
   * it at the end, which can be used to put it into
   * the item tag for example.
   *
   * @param playerFor   The player for whom the listener should be registered.
   * @param listener    The listener interface to be executed when the
   *                    listener is fired.
   * @return  The listener id.
   */
  public String registerListener(UUID playerFor, ClickListener listener) {
    String listenerId = this.newListenerId();
    listeners.put(listenerId, listener);
    listenerOwners.put(playerFor, listenerId);
    return listenerId;
  }

  /**
   * Fires a listener. This method should be called when a version module
   * has detected a click on an item
   *
   * @param listenerId  The id of the listener you want to fire.
   * @param event       Information about the event. This contains the
   *                    player who clicked, the item which was clicked,
   *                    the inventory where the item is stored, etc.
   */
  public void fireListener(String listenerId, KelpClickEvent event) {
    this.listeners.get(listenerId).onClick(event);
  }

  /**
   * Unregisters all listeners for a specific player. This
   * should be executed on every server or inventory quit
   * of the player in order to save memory and performance.
   *
   * @param playerFor The player for whom you want to unregister
   *                  all listeners.
   */
  public void unregisterListeners(UUID playerFor) {
    listenerOwners.get(playerFor).forEach(currentId -> listeners.remove(currentId));
    listenerOwners.removeAll(playerFor);
  }

  /**
   * Generates a new random listener id. This is done by
   * generating a UUID and taking the first 15 chars of it so
   * that it still fits into an item tag, which has a
   * limit of 16 chars.
   *
   * @return The listener id.
   */
  private String newListenerId() {
    return UUID.randomUUID().toString().substring(0, 14);
  }

}
