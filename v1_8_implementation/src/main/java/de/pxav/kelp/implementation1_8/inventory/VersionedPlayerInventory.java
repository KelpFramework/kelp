package de.pxav.kelp.implementation1_8.inventory;

import de.pxav.kelp.core.common.ConcurrentSetMultimap;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.type.KelpInventory;
import de.pxav.kelp.core.inventory.type.PlayerInventory;
import de.pxav.kelp.core.inventory.version.StorageInventoryVersionTemplate;
import de.pxav.kelp.core.inventory.widget.GroupedWidget;
import de.pxav.kelp.core.inventory.widget.SimplePagination;
import de.pxav.kelp.core.inventory.widget.SimpleWidget;
import de.pxav.kelp.core.player.KelpPlayer;

import java.util.UUID;

public class VersionedPlayerInventory extends VersionedStorageInventory<PlayerInventory> implements PlayerInventory {

  // the version template used for player inventory manipulation
  private StorageInventoryVersionTemplate versionTemplate;

  // the owner of this inventory
  private KelpPlayer player;

  // all widgets currently stored by all players on the server.
  // the uuid represents the player uuid who owns the widget.
  private static ConcurrentSetMultimap<UUID, SimpleWidget> simpleWidgets = ConcurrentSetMultimap.create();
  private static ConcurrentSetMultimap<UUID, GroupedWidget> groupedWidgets = ConcurrentSetMultimap.create();

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
   *                    all {@link SimplePagination Paginations}
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

      remove(current.getCoveredSlot());

      KelpItem item = current.render();

      // if items are not explicitly stated as interactable
      // cancel interactions by default
      if (!item.hasTagKey("interactionAllowed")) {
        item.cancelInteractions();
      }

      setItem(item);
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

}
