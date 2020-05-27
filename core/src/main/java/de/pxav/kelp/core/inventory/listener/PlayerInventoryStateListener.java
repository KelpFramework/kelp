package de.pxav.kelp.core.inventory.listener;

import com.google.inject.Inject;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.PlayerFactory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class PlayerInventoryStateListener {

  private PlayerFactory playerFactory;

  @Inject
  public PlayerInventoryStateListener(PlayerFactory playerFactory) {
    this.playerFactory = playerFactory;
  }

  @EventHandler
  public void handlePlayerInventoryClose(InventoryCloseEvent event) {
    KelpPlayer kelpPlayer = playerFactory.newKelpPlayer((Player) event.getPlayer());
    kelpPlayer.closeInventory();

  }

}
