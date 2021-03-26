package de.pxav.kelp.core.inventory;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.event.kelpevent.KelpInventoryCloseEvent;
import de.pxav.kelp.core.event.kelpevent.KelpInventoryOpenEvent;
import de.pxav.kelp.core.event.kelpevent.KelpInventoryUpdateEvent;
import de.pxav.kelp.core.inventory.listener.KelpListenerRepository;
import de.pxav.kelp.core.inventory.material.MaterialVersionTemplate;
import de.pxav.kelp.core.inventory.type.AnimatedInventory;
import de.pxav.kelp.core.inventory.type.KelpInventory;
import de.pxav.kelp.core.inventory.type.PlayerInventory;
import de.pxav.kelp.core.inventory.type.SimpleInventory;
import de.pxav.kelp.core.inventory.widget.Pagination;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.reflect.MethodFinder;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.UUID;

/**
 * This repository class stores and handles all kelp
 * inventories. You can open, close and update kelp inventories
 * from here. Some of the methods are also callable
 * from the {@link KelpPlayer} class directly, if you are working
 * with players.
 *
 * @author pxav
 */
@Singleton
public class KelpInventoryRepository {

  private MaterialVersionTemplate materialVersionTemplate;
  private MethodFinder methodFinder;
  private KelpListenerRepository kelpListenerRepository;

  private Map<UUID, KelpInventory<?>> playerInventories = Maps.newHashMap();
  private Map<UUID, Map<Pagination, Integer>> playerPages = Maps.newHashMap();
  private Map<UUID, AnimatedInventory> playerAnimations = Maps.newHashMap();

  @Inject
  public KelpInventoryRepository(MaterialVersionTemplate materialVersionTemplate,
                                 MethodFinder methodFinder,
                                 KelpListenerRepository kelpListenerRepository) {
    this.materialVersionTemplate = materialVersionTemplate;
    this.methodFinder = methodFinder;
    this.kelpListenerRepository = kelpListenerRepository;
  }

  /**
   * Maps all kelp materials with the version specific bukkit materials.
   */
  public void loadMaterials() {
    this.materialVersionTemplate.defineDefaults();
  }

  @EventHandler
  public void handlePlayerQuit(PlayerQuitEvent event) {
    KelpPlayer player = KelpPlayer.from(event.getPlayer());

    player.getInventory().removeAllWidgets();
  }

  /**
   * Opens a kelp inventory to the given player. This also adds the
   * player to all required lists.
   *
   * @param inventory The inventory you want to show to the player.
   * @param player    The player who should see the inventory.
   */
  public void openInventory(KelpInventory<?> inventory, KelpPlayer player) {
    Inventory renderedInventory = inventory.render(player);
    player.getBukkitPlayer().openInventory(renderedInventory);
    boolean animated = false;

    if (inventory instanceof AnimatedInventory) {
      animated = true;
      AnimatedInventory animatedInventory = (AnimatedInventory) inventory;
      animatedInventory.scheduleUpdater(player.getBukkitPlayer());
      playerAnimations.put(player.getUUID(), animatedInventory);
    }

    Bukkit.getPluginManager().callEvent(new KelpInventoryOpenEvent(player, inventory, animated));
    playerInventories.put(player.getUUID(), inventory);
  }

  /**
   * This method should be executed when a player closes their
   * kelp inventory as it removes the player from the cache and
   * stops all schedulers.
   *
   * @param player The player you want to remove from the cache/whose inventory
   *               you want to close.
   */
  public void closeInventory(KelpPlayer player) {
    KelpInventory<?> inventory = this.playerInventories.get(player.getUUID());

    if (inventory == null) {
      return;
    }

    // if his inventory was animated remember this for the event
    boolean animated = false;
    if (inventory instanceof AnimatedInventory) {
      animated = true;
    }

    inventory.onClose();

    Bukkit.getPluginManager().callEvent(new KelpInventoryCloseEvent(player, inventory, animated));
    this.playerInventories.remove(player.getUUID());
    this.playerAnimations.remove(player.getUUID());
    this.kelpListenerRepository.unregisterListeners(player.getUUID());
  }

  /**
   * Updates the entire kelp inventory of a player.
   * This includes all widgets, but not the normal
   * inventory of the player.
   *
   * @param player The player whose inventory you want to update.
   */
  public void updateInventory(KelpPlayer player) {
    KelpInventory<?> kelpInventory = playerInventories.get(player.getUUID());

    if (kelpInventory == null) {
      PlayerInventory playerInventory = PlayerInventory.of(player);
      playerInventory.updateWidgets();
      return;
    }

    kelpInventory.update(player);
    Bukkit.getPluginManager().callEvent(new KelpInventoryUpdateEvent(player, kelpInventory));
  }

  /**
   * Updates the title of the player's current kelp inventory if this
   * inventory is of type {@link SimpleInventory}. A simple inventory has
   * an updatable title in form of a {@link java.util.function.Supplier<String>}, while
   * the title of an {@link AnimatedInventory} is managed by itself, so calling this method
   * on an animated inventory won't have any effect, but it won't throw an error either.
   *
   * @param player The player you want to update the inventory title of.
   */
  public void updateInventoryTitle(KelpPlayer player) {
    KelpInventory<?> kelpInventory = playerInventories.get(player.getUUID());

    if (kelpInventory instanceof SimpleInventory) {
      SimpleInventory simpleInventory = (SimpleInventory) kelpInventory;
      simpleInventory.updateTitleOnly(player);
    }

  }

  /**
   * Checks whether the given {@link KelpPlayer player} currently has a
   * {@link KelpInventory} opened. Having opened a normal player inventory
   * (using {@code E} key on the key board) does not count for this
   * method.
   *
   * @param player The player you want to check.
   * @return {@code true} of the given player has a kelp inventory opened.
   */
  public boolean hasInventory(KelpPlayer player) {
    return playerInventories.containsKey(player.getUUID());
  }

  public Map<UUID, Map<Pagination, Integer>> getPlayerPages() {
    return playerPages;
  }

}
