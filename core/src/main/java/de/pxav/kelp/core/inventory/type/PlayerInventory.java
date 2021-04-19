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
   * Gets all items stored in this player inventory. This includes
   * all slots (main storage, armor and hot bar).
   *
   * @return A set of all items that are currently in the player's inventory.
   */
  Set<KelpItem> getItems();

  /**
   * Gets an item at a specific slot. The slot count starts at the
   * first hotbar slot from 0-8 and then goes on from the uppermost
   * line of the main storage.
   *
   * If there is no item at the given slot, {@code null} will be returned.
   *
   * @param slot The slot of the item you want to get.
   * @return The item stored at the given slot in the player inventory.
   */
  KelpItem getItemAt(int slot);

  /**
   * Gets all items stored in the player's hotbar. The hotbar
   * are the first nine slots of a player's inventory (0-8) and it
   * is always visible at the screen, although the inventory is closed.
   *
   * @return A set of all items stored in the player's hotbar.
   */
  Set<KelpItem> getHotBarItems();

  /**
   * Sets the helmet the player is wearing to the given item.
   * Specifically for helmets, you can not only use the normal armor items
   * such as leather helmet, gold helmet, etc. but also blocks, heads or banners
   * to set them as a player head.
   *
   * @param helmet The item of the helmet you want to set. This can be
   *               a normal armor helmet or any banner, head or even some blocks.
   * @return An instance of the current inventory for fluent builder design.
   */
  PlayerInventory setHelmet(KelpItem helmet);

  /**
   * Sets the chest plate of the player owning this inventory.
   * Unlike in the helmet method, you cannot pass other item materials
   * than chest plates here.
   *
   * @param chestPlate The chest plate item you want to set.
   * @return An instance of the current inventory for fluent builder design.
   */
  PlayerInventory setChestPlate(KelpItem chestPlate);

  /**
   * Sets the leggings of the player owning this inventory.
   * Unlike in the helmet method, you cannot pass other item materials
   * than leggings here.
   *
   * @param leggings The leggings item you want to set.
   * @return An instance of the current inventory for fluent builder design.
   */
  PlayerInventory setLeggings(KelpItem leggings);

  /**
   * Sets the boots of the player owning this inventory.
   * Unlike in the helmet method, you cannot pass other item materials
   * than boots here.
   *
   * @param boots The chest plate item you want to set.
   * @return An instance of the current inventory for fluent builder design.
   */
  PlayerInventory setBoots(KelpItem boots);

  /**
   * Gets the helmet the player is currently wearing.
   * Unlike the other armor parts, the helmet can also be of
   * materials other than normal helmets, but also banners and some
   * blocks.
   *
   * If the player has no helmet, {@code null} will be returned.
   *
   * @return The item representing the player's helmet.
   */
  KelpItem getHelmet();

  /**
   * Gets the chest plate the player is currently wearing.
   *
   * If the player has no chest plate, {@code null} will be returned.
   *
   * @return The item representing the player's chest plate.
   */
  KelpItem getChestPlate();

  /**
   * Gets the leggings the player is currently wearing.
   *
   * If the player has no leggings, {@code null} will be returned.
   *
   * @return The item representing the player's chest plate.
   */
  KelpItem getLeggings();

  /**
   * Gets the boots the player is currently wearing.
   *
   * If the player has no boots, {@code null} will be returned.
   *
   * @return The item representing the player's chest plate.
   */
  KelpItem getBoots();

  /**
   * Stores an item in the player inventory at the given slot location.
   * If there already is an item present at the given location, this
   * item will be overwritten. If you want to avoid this and rather
   * search for free inventory space use {@link #addItem(KelpItem)} instead.
   *
   * @param slot The slot to store the item at.
   * @param item The item to store at the given slot.
   * @return An instance of the current inventory for fluent builder design.
   */
  PlayerInventory setItem(int slot, KelpItem item);

  /**
   * Adds a new item to the player's inventory.
   * This method won't overwrite any existing items, but try to find free
   * space in the inventory. If there is already a {@link KelpItem} with
   * the same data (same display name, item description, material, tags, etc.)
   * and this item has not yet exceeded its maximum stack size, it will add
   * the item to an existing stack.
   *
   * @param item The item to add.
   * @return An instance of the current inventory for fluent builder design.
   */
  PlayerInventory addItem(KelpItem item);

  /**
   * Gets the item that is currently stored in the player's
   * main hand (the player's right hand).
   *
   * If the player has no item in hand, {@code null} will be returned.
   *
   * @return The item the player is holding right now.
   */
  KelpItem getItemInHand();

  /**
   * Sets the item in the player's main hand (the player's right hand).
   *
   * Unlike the off hand, the main hand can be used in all server versions.
   *
   * @param item The item to set in the player's main hand.
   * @return An instance of the current inventory for fluent builder design.
   */
  PlayerInventory setItemInHand(KelpItem item);

  /**
   * Gets the item that is currently in the player's off hand
   * (the player's left hand).
   *
   * Please note that off hands were introduced in {code MC 1.9} and are
   * therefore not available on 1.8 servers. But this method won't throw
   * an error if you do it anyways.
   *
   * If the player has no item in his off hand, {@code null} will be returned.
   *
   * @return The item that is stored in the player's off hand right now.
   */
  KelpItem getItemInOffHand();

  /**
   * Sets the item in the player's off hand (the player's left hand).
   *
   * Please note that off hands were introduced in {code MC 1.9} and are
   * therefore not available on 1.8 servers. But this method won't throw
   * an error if you do it anyways.
   *
   * @param item The item to set in the player's off hand.
   * @return An instance of the current inventory for fluent builder design.
   */
  PlayerInventory setItemInOffHand(KelpItem item);

  /**
   * Gets the player owning this inventory.
   *
   * @return The player owning this inventory.
   */
  KelpPlayer getPlayer();

}
