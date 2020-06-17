package de.pxav.kelp.core.inventory.listener;

import com.google.inject.Inject;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class PlayerInventoryStateListener {

  private KelpPlayerRepository playerRepository;

  @Inject
  public PlayerInventoryStateListener(KelpPlayerRepository playerRepository) {
    this.playerRepository = playerRepository;
  }

  @EventHandler
  public void handlePlayerInventoryClose(InventoryCloseEvent event) {
    KelpPlayer kelpPlayer = playerRepository.getKelpPlayer((Player) event.getPlayer());
    kelpPlayer.closeInventory();
  }

}
