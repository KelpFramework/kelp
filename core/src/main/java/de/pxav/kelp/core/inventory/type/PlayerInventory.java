package de.pxav.kelp.core.inventory.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.common.ConcurrentSetMultimap;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.version.PlayerInventoryVersionTemplate;
import de.pxav.kelp.core.inventory.widget.GroupedWidget;
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
public class PlayerInventory {

  // the version template used for player inventory manipulation
  private PlayerInventoryVersionTemplate versionTemplate;

  // the owner of this inventory
  private KelpPlayer player;

  // all widgets currently stored by all players on the server.
  // the uuid represents the player uuid who owns the widget.
  private static ConcurrentSetMultimap<UUID, SimpleWidget> simpleWidgets = ConcurrentSetMultimap.create();
  private static ConcurrentSetMultimap<UUID, GroupedWidget> groupedWidgets = ConcurrentSetMultimap.create();

  /**
   * Gets the inventory of a specific {@link KelpPlayer}.
   * Alternatively, you can use {@link KelpPlayer#getInventory()} to get
   * the inventory directly from the kelp player class.
   *
   * @param player The player you want to get the inventory of.
   * @return The {@link PlayerInventory} of the given player.
   */
  public static PlayerInventory of(KelpPlayer player) {
    return new PlayerInventory(
      player,
      KelpPlugin.getInjector().getInstance(PlayerInventoryVersionTemplate.class)
    );
  }

  private PlayerInventory(KelpPlayer player, PlayerInventoryVersionTemplate versionTemplate) {
    this.player = player;
    this.versionTemplate = versionTemplate;
  }

  /**
   * Gets all items stored in this player inventory. This includes
   * all slots (main storage, armor and hot bar).
   *
   * @return A set of all items that are currently in the player's inventory.
   */
  public Set<KelpItem> getItems() {
    return versionTemplate.getItems(player.getBukkitPlayer());
  }

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
  public KelpItem getItemAt(int slot) {
    return versionTemplate.getItemAt(player.getBukkitPlayer(), slot);
  }

  /**
   * Gets all items stored in the player's hotbar. The hotbar
   * are the first nine slots of a player's inventory (0-8) and it
   * is always visible at the screen, although the inventory is closed.
   *
   * @return A set of all items stored in the player's hotbar.
   */
  public Set<KelpItem> getHotBarItems() {
    return versionTemplate.getHotBarItems(player.getBukkitPlayer());
  }

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
  public PlayerInventory setHelmet(KelpItem helmet) {
    versionTemplate.setHelmet(player.getBukkitPlayer(), helmet);
    return this;
  }

  /**
   * Sets the chest plate of the player owning this inventory.
   * Unlike in the helmet method, you cannot pass other item materials
   * than chest plates here.
   *
   * @param chestPlate The chest plate item you want to set.
   * @return An instance of the current inventory for fluent builder design.
   */
  public PlayerInventory setChestPlate(KelpItem chestPlate) {
    versionTemplate.setChestPlate(player.getBukkitPlayer(), chestPlate);
    return this;
  }

  /**
   * Sets the leggings of the player owning this inventory.
   * Unlike in the helmet method, you cannot pass other item materials
   * than leggings here.
   *
   * @param leggings The leggings item you want to set.
   * @return An instance of the current inventory for fluent builder design.
   */
  public PlayerInventory setLeggings(KelpItem leggings) {
    versionTemplate.setLeggings(player.getBukkitPlayer(), leggings);
    return this;
  }

  /**
   * Sets the boots of the player owning this inventory.
   * Unlike in the helmet method, you cannot pass other item materials
   * than boots here.
   *
   * @param boots The chest plate item you want to set.
   * @return An instance of the current inventory for fluent builder design.
   */
  public PlayerInventory setBoots(KelpItem boots) {
    versionTemplate.setBoots(player.getBukkitPlayer(), boots);
    return this;
  }

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
  public KelpItem getHelmet() {
    return versionTemplate.getHelmet(player.getBukkitPlayer());
  }

  /**
   * Gets the chest plate the player is currently wearing.
   *
   * If the player has no chest plate, {@code null} will be returned.
   *
   * @return The item representing the player's chest plate.
   */
  public KelpItem getChestPlate() {
    return versionTemplate.getChestPlate(player.getBukkitPlayer());
  }

  /**
   * Gets the leggings the player is currently wearing.
   *
   * If the player has no leggings, {@code null} will be returned.
   *
   * @return The item representing the player's chest plate.
   */
  public KelpItem getLeggings() {
    return versionTemplate.getLeggings(player.getBukkitPlayer());
  }

  /**
   * Gets the boots the player is currently wearing.
   *
   * If the player has no boots, {@code null} will be returned.
   *
   * @return The item representing the player's chest plate.
   */
  public KelpItem getBoots() {
    return versionTemplate.getBoots(player.getBukkitPlayer());
  }

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
  public PlayerInventory setItem(int slot, KelpItem item) {
    versionTemplate.setItem(player.getBukkitPlayer(), slot, item);
    return this;
  }

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
  public PlayerInventory addItem(KelpItem item) {
    versionTemplate.addItem(player.getBukkitPlayer(), item);
    return this;
  }

  /**
   * Gets the item that is currently stored in the player's
   * main hand (the player's right hand).
   *
   * If the player has no item in hand, {@code null} will be returned.
   *
   * @return The item the player is holding right now.
   */
  public KelpItem getItemInHand() {
    return versionTemplate.getItemInHand(player.getBukkitPlayer());
  }

  /**
   * Sets the item in the player's main hand (the player's right hand).
   *
   * Unlike the off hand, the main hand can be used in all server versions.
   *
   * @param item The item to set in the player's main hand.
   * @return An instance of the current inventory for fluent builder design.
   */
  public PlayerInventory setItemInHand(KelpItem item) {
    versionTemplate.setItemInHand(player.getBukkitPlayer(), item);
    return this;
  }

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
  public KelpItem getItemInOffHand() {
    return versionTemplate.getItemInOffHand(player.getBukkitPlayer());
  }

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
  public PlayerInventory setItemInOffHand(KelpItem item) {
    versionTemplate.setItemInOffHand(player.getBukkitPlayer(), item);
    return this;
  }

  /**
   * Adds a new {@link SimpleWidget} to the player's inventory.
   * This method does not immediately render the widget, but only
   * adds it to the cache. To make the widget appear, call
   * {@link #updateWidgets()} first.
   *
   * @param simpleWidget The simple widget you want to add to the inventory.
   * @return An instance of the current inventory for fluent builder design.
   */
  public PlayerInventory addWidget(SimpleWidget simpleWidget) {
    simpleWidgets.put(player.getUUID(), simpleWidget);
    return this;
  }

  /**
   * Adds a new {@link GroupedWidget} to the player's inventory.
   * This method does not immediately render the widget, but only
   * adds it to the cache. To make the widget appear, call
   * {@link #updateWidgets()} first.
   *
   * @param groupedWidget The grouped widget you want to add to the inventory.
   * @return An instance of the current inventory for fluent builder design.
   */
  public PlayerInventory addWidget(GroupedWidget groupedWidget) {
    groupedWidgets.put(player.getUUID(), groupedWidget);
    return this;
  }

  /**
   * Removes all {@link SimpleWidget simple widgets} of a certain type.
   * This method removes the widget from the cache as well as the inventory,
   * so that no items are there anymore.
   *
   * @param widgetClass The class of the widget type you want to remove.
   *                    If you pass {@code ToggleableWidget.class} here,
   *                    all {@link de.pxav.kelp.core.inventory.widget.ToggleableWidget ToggleableWidgets}
   *                    will be removed from the inventory.
   * @return An instance of the current inventory for fluent builder design.
   */
  public PlayerInventory removeSimpleWidget(Class<? extends SimpleWidget> widgetClass) {
    simpleWidgets.get(player.getUUID()).forEach(widget -> {
      if (widgetClass.getName().equalsIgnoreCase(widget.getClass().getName())) {
        removeWidget(widget);
      }
    });
    return this;
  }

  /**
   * Removes all {@link GroupedWidget grouped widgets} of a certain type.
   * This method removes the widget from the cache as well as the inventory,
   * so that no items are there anymore.
   *
   * @param widgetClass The class of the widget type you want to remove.
   *                    If you pass {@code Pagination.class} here,
   *                    all {@link de.pxav.kelp.core.inventory.widget.Pagination Paginations}
   *                    will be removed from the inventory.
   * @return An instance of the current inventory for fluent builder design.
   */
  public PlayerInventory removeGroupedWidget(Class<? extends GroupedWidget> widgetClass) {
    groupedWidgets.get(player.getUUID()).forEach(widget -> {
      if (widgetClass.getName().equalsIgnoreCase(widget.getClass().getName())) {
        removeWidget(widget);
      }
    });
    return this;
  }

  /**
   * Removes a specific {@link SimpleWidget} from the inventory.
   * This method removes the widget from the cache as well as
   * the inventory itself, so there won't be any items rendered by
   * this widget anymore.
   *
   * @param widget The object of the widget you want to remove.
   * @return An instance of the current inventory for fluent builder design.
   */
  public PlayerInventory removeWidget(SimpleWidget widget) {
    player.getBukkitPlayer().getInventory().clear(widget.getCoveredSlot());
    widget.onRemove();
    simpleWidgets.remove(player.getUUID(), widget);
    return this;
  }

  /**
   * Removes a specific {@link GroupedWidget} from the inventory.
   * This method removes the widget from the cache as well as
   * the inventory itself, so there won't be any items rendered by
   * this widget anymore.
   *
   * @param widget The object of the widget you want to remove.
   * @return An instance of the current inventory for fluent builder design.
   */
  public PlayerInventory removeWidget(GroupedWidget widget) {
    widget.getCoveredSlots().forEach(slot -> player.getBukkitPlayer().getInventory().clear(slot));
    widget.onRemove();
    groupedWidgets.remove(player.getUUID(), widget);
    return this;
  }

  /**
   * Removes all widgets from the player's inventory.
   * This will not only remove them from the cache, but also from
   * the visible inventory itself.
   *
   * @return An instance of the current inventory for fluent builder design.
   */
  public PlayerInventory removeAllWidgets() {
    simpleWidgets.getOrEmpty(player.getUUID()).forEach(this::removeWidget);
    groupedWidgets.getOrEmpty(player.getUUID()).forEach(this::removeWidget);
    return this;
  }

  /**
   * Updates all widgets inside this player inventory.
   *
   * This is also equivalent to the {@link KelpInventory#render(KelpPlayer) render method} you
   * already know from KelpInventories, so call this method even if you
   * have just put widgets into the inventory for the first time.
   *
   * @return An instance of the current inventory for fluent builder design.
   */
  public PlayerInventory updateWidgets() {
    for (SimpleWidget current : simpleWidgets.getOrEmpty(player.getUUID())) {
      if (!current.isStateful()) {
        continue;
      }

      player.getBukkitPlayer().getInventory().clear(current.getCoveredSlot());

      KelpItem item = current.render();

      // if items are not explicitly stated as interactable
      // cancel interactions by default
      if (!item.hasTagKey("interactionAllowed")) {
        item.cancelInteractions();
      }

      setItem(item.getSlot(), item);
    }

    for (GroupedWidget current : groupedWidgets.getOrEmpty(player.getUUID())) {
      if (!current.isStateful()) {
        continue;
      }

      for (Integer slot : current.getCoveredSlots()) {
        player.getBukkitPlayer().getInventory().clear(slot);
      }

      current.render(player).forEach(item -> {

        // if items are not explicitly stated as interactable
        // cancel interactions by default
        if (!item.hasTagKey("interactionAllowed")) {
          item.cancelInteractions();
        }

        setItem(item.getSlot(), item);
      });
    }

    return this;
  }

  /**
   * Gets the player owning this inventory.
   *
   * @return The player owning this inventory.
   */
  public KelpPlayer getPlayer() {
    return player;
  }

}
