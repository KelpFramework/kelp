package de.pxav.kelp.core.inventory.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import org.bukkit.entity.Player;

import java.util.Set;

/**
 * This version template is responsible for handling
 * all the version specific stuff for manipulating the personal
 * inventory of a {@link de.pxav.kelp.core.player.KelpPlayer}.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class StorageInventoryVersionTemplate {

  /**
   * Gets all items stored in this inventory. This includes
   * all slots (main storage, armor and hot bar).
   *
   * @param player The player to get the items of.
   * @return A set of all items that are currently in the player's inventory.
   */
  public abstract Set<KelpItem> getItems(Player player);

  /**
   * Gets all items stored in the player's hotbar. The hotbar
   * are the first nine slots of a player's inventory (0-8) and it
   * is always visible at the screen, although the inventory is closed.
   *
   * @param player The player to get the hotbar items of
   * @return A set of all items stored in the player's hotbar.
   */
  public abstract Set<KelpItem> getHotBarItems(Player player);

  /**
   * Gets an item at a specific slot. The slot count starts at the
   * first hotbar slot from 0-8 and then goes on from the uppermost
   * line of the main storage.
   *
   * If there is no item at the given slot, {@code null} will be returned.
   *
   * @param player  The player to get the item of.
   * @param slot    The slot of the item you want to get.
   * @return The item stored at the given slot in the player inventory.
   */
  public abstract KelpItem getItemAt(Player player, int slot);

  /**
   * Gets the helmet the player is currently wearing.
   * Unlike the other armor parts, the helmet can also be of
   * materials other than normal helmets, but also banners and some
   * blocks.
   *
   * If the player has no helmet, {@code null} will be returned.
   *
   * @param player The player to get the helmet of.
   * @return The item representing the player's helmet.
   */
  public abstract KelpItem getHelmet(Player player);

  /**
   * Gets the chest plate the player is currently wearing.
   *
   * If the player has no chest plate, {@code null} will be returned.
   *
   * @param player The player to get the chest plate of.
   * @return The item representing the player's chest plate.
   */
  public abstract KelpItem getChestPlate(Player player);

  /**
   * Gets the leggings the player is currently wearing.
   *
   * If the player has no leggings, {@code null} will be returned.
   *
   * @param player The player to get the leggings of.
   * @return The item representing the player's chest plate.
   */
  public abstract KelpItem getLeggings(Player player);

  /**
   * Gets the boots the player is currently wearing.
   *
   * If the player has no boots, {@code null} will be returned.
   *
   * @param player The player to get the boots of.
   * @return The item representing the player's chest plate.
   */
  public abstract KelpItem getBoots(Player player);

  /**
   * Sets the helmet the player is wearing to the given item.
   * Specifically for helmets, you can not only use the normal armor items
   * such as leather helmet, gold helmet, etc. but also blocks, heads or banners
   * to set them as a player head.
   *
   * @param player  The player to set the helmet of.
   * @param item    The item of the helmet you want to set. This can be
   *                a normal armor helmet or any banner, head or even some blocks.
   */
  public abstract void setHelmet(Player player, KelpItem item);

  /**
   * Sets the chest plate of the player owning this inventory.
   * Unlike in the helmet method, you cannot pass other item materials
   * than chest plates here.
   *
   * @param player   The player to set the chest plate of.
   * @param item     The chest plate item you want to set.
   */
  public abstract void setChestPlate(Player player, KelpItem item);

  /**
   * Sets the leggings of the player owning this inventory.
   * Unlike in the helmet method, you cannot pass other item materials
   * than leggings here.
   *
   * @param player   The player to set the leggings of.
   * @param item     The leggings item you want to set.
   */
  public abstract void setLeggings(Player player, KelpItem item);

  /**
   * Sets the boots of the player owning this inventory.
   * Unlike in the helmet method, you cannot pass other item materials
   * than boots here.
   *
   * @param player  The player to set the boots of.
   * @param item    The chest plate item you want to set.
   */
  public abstract void setBoots(Player player, KelpItem item);

  /**
   * Adds a new item to the player's inventory.
   * This method won't overwrite any existing items, but try to find free
   * space in the inventory. If there is already a {@link KelpItem} with
   * the same data (same display name, item description, material, tags, etc.)
   * and this item has not yet exceeded its maximum stack size, it will add
   * the item to an existing stack.
   *
   * @param player  The player to add the item to.
   * @param item    The item to add.
   */
  public abstract void addItem(Player player, KelpItem item);

  /**
   * Stores an item in the player inventory at the given slot location.
   * If there already is an item present at the given location, this
   * item will be overwritten. If you want to avoid this and rather
   * search for free inventory space use {@link #addItem(Player, KelpItem)} instead.
   *
   * @param player  The player to set the item to.
   * @param slot    The slot to store the item at.
   * @param item    The item to store at the given slot.
   */
  public abstract void setItem(Player player, int slot, KelpItem item);

  /**
   * Gets the item that is currently stored in the player's
   * main hand (the player's right hand).
   *
   * If the player has no item in hand, {@code null} will be returned.
   *
   * @param player  The player to get the main hand item of.
   * @return The item the player is holding right now.
   */
  public abstract KelpItem getItemInHand(Player player);

  /**
   * Sets the item in the player's main hand (the player's right hand).
   *
   * Unlike the off hand, the main hand can be used in all server versions.
   *
   * @param player The player to set the main hand item of.
   * @param item The item to set in the player's main hand.
   */
  public abstract void setItemInHand(Player player, KelpItem item);

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
   * @param player The player to get the off hand item of.
   * @return The item that is stored in the player's off hand right now.
   */
  public abstract KelpItem getItemInOffHand(Player player);

  /**
   * Sets the item in the player's off hand (the player's left hand).
   *
   * Please note that off hands were introduced in {code MC 1.9} and are
   * therefore not available on 1.8 servers. But this method won't throw
   * an error if you do it anyways.
   *
   * @param player  The player to set the off hand item of.
   * @param item    The item to set in the player's off hand.
   */
  public abstract void setItemInOffHand(Player player, KelpItem item);

}
