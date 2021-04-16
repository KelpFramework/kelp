package de.pxav.kelp.core.inventory.listener;

import com.google.inject.Inject;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * This listener controls the inventory state of every player.
 * Inventory state means whether a player has opened or closed
 * his inventory. When a player closes it, he has to be removed from
 * the cache to avoid problems and lags on long running servers.
 *
 * @author pxav
 */
public class PlayerInventoryStateListener {

  private KelpPlayerRepository playerRepository;

  @Inject
  public PlayerInventoryStateListener(KelpPlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  /**
   * This method is triggered when any player closes their inventory.
   *
   * @param event The event to listen for.
   */
  @EventHandler
  public void handlePlayerInventoryClose(InventoryCloseEvent event) {
    KelpPlayer kelpPlayer = KelpPlayer.from(event.getPlayer().getUniqueId());

    if (kelpPlayer == null) {
      return;
    }

    kelpPlayer.closeInventory();
  }

}
