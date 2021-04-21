package de.pxav.kelp.core.inventory.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.common.ConcurrentSetMultimap;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.version.StorageInventoryVersionTemplate;
import de.pxav.kelp.core.inventory.widget.GroupedWidget;
import de.pxav.kelp.core.inventory.widget.SimplePagination;
import de.pxav.kelp.core.inventory.widget.SimpleWidget;
import de.pxav.kelp.core.player.KelpPlayer;

import java.util.Set;
import java.util.UUID;

/**
 * Represents the personal inventory of a {@link KelpPlayer} which
 * is independent from any additionally opened GUI.
 *
 * With this class you can manipulate the contents of a player's inventory
 * by adding/setting items or adding normal widgets into it. PlayerInventories
 * have full support for widgets you would normally only use in {@link KelpInventory kelp inventories}.
 *
 * You can get the inventory of a player by using its static factory {@link #of(KelpPlayer)}
 * or you can use the method provided by the {@code KelpPlayer} class: {@link KelpPlayer#getInventory()}.
 *
 * @author pxav
 */
public interface PlayerInventory extends StorageInventory<PlayerInventory> {

  /**
   * Gets all items stored in the player's hotbar. The hotbar
   * are the first nine slots of a player's inventory (0-8) and it
   * is always visible at the screen, although the inventory is closed.
   *
   * @return A set of all items stored in the player's hotbar.
   */
  Set<KelpItem> getHotBarItems();



  /**
   * Gets the player owning this inventory.
   *
   * @return The player owning this inventory.
   */
  KelpPlayer getPlayer();

}
