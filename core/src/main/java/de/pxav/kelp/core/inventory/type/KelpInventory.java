package de.pxav.kelp.core.inventory.type;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.version.InventoryVersionTemplate;
import de.pxav.kelp.core.inventory.widget.GroupedWidget;
import de.pxav.kelp.core.inventory.widget.SimpleWidget;
import de.pxav.kelp.core.inventory.widget.Widget;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.Material;
import org.bukkit.block.Dispenser;
import org.bukkit.inventory.Inventory;

import java.util.List;

/**
 * Represents the basis for every GUI inventory you can build
 * with Kelp.
 *
 * @param <T> The specific inventory type you are creating, e. g. {@link AnimatedInventory}
 *          or {@link SimpleInventory}
 * @author pxav
 */
public abstract class KelpInventory<T extends KelpInventory<?>> {

  // the amount of slots covered by this inventory
  protected int size;

  // all widgets contained by this inventory
  protected List<SimpleWidget> simpleWidgets = Lists.newArrayList();
  protected List<GroupedWidget> groupedWidgets = Lists.newArrayList();

  protected InventoryVersionTemplate inventoryVersionTemplate;

  public KelpInventory(InventoryVersionTemplate inventoryVersionTemplate) {
    this.inventoryVersionTemplate = inventoryVersionTemplate;
    this.size = 54;
  }

  /**
   * Sets the size of the inventory. The size determines how many
   * rows the inventory has. Please note that kelp inventories can
   * only be of type chest and therefore have a maximum size of 54.
   * All numbers must be a multiple of 9, e. g. 9, 18, 27, ...
   * No other values are allowed and will cause exceptions.
   *
   * @param size The size of the inventory in full slots.
   *             So if the inventory should have 3 rows, pass 27 here.
   * @return The instance of the current inventory for fluent builder design.
   */
  public T size(int size) {
    this.size = size;
    return (T) this;
  }

  /**
   * Sets the size of the inventory. The size determines how many rows an
   * inventory has. Unlike in {@link #size(int)}, you don't have to calculate
   * the full size (slot amount), but can simply say how many lines you want.
   * This number is then multiplied by 9 to fetch the amount of slots the
   * inventory should have. Using this method can make your code more readable.
   *
   * @param rows The amount of rows your inventory should have.
   * @return The instance of the current inventory for fluent builder design.
   */
  public T rows(int rows) {
    this.size = rows * 9;
    return (T) this;
  }

  /**
   * Adds a new {@link SimpleWidget} to the inventory. Please note that before
   * this widget becomes visible, you have to either {@link #render(KelpPlayer) render}
   * or {@link #update(KelpPlayer) update} the inventory first.
   *
   * @param widget The widget you want to add to the inventory.
   * @return The instance of the current inventory for fluent builder design.
   */
  public T addWidget(SimpleWidget widget) {
    this.simpleWidgets.add(widget);
    return (T) this;
  }

  /**
   * Adds a new {@link GroupedWidget} to the inventory. Please note that before
   * this widget becomes visible, you have to either {@link #render(KelpPlayer) render}
   * or {@link #update(KelpPlayer) update} the inventory first.
   *
   * @param widget The widget you want to add to the inventory.
   * @return The instance of the current inventory for fluent builder design.
   */
  public T addWidget(GroupedWidget widget) {
    this.groupedWidgets.add(widget);
    return (T) this;
  }

  /**
   * Renders the widget to the given player. This method should
   * only be called once in the inventory lifetime as it creates the
   * inventory for the first time. Once this bukkit inventory has
   * been created, {@link #update(KelpPlayer)} should be used to update
   * the contents of the inventory.
   *
   * Usually, this method shouldn't be used by an application developer
   * as this method is already called by kelp-internal handlers
   * when opening a player inventory using {@link KelpPlayer#openInventory(KelpInventory)}
   * for example.
   *
   * @param player The player to open the inventory to.
   * @return The bukkit inventory instance containing the contents of this inventory.
   */
  public abstract Inventory render(KelpPlayer player);

  /**
   * Updates all widgets contained by this inventory by clearing
   * all contents and then adding them back widget by widget.
   *
   * @param toUpdate The player to update the widgets of.
   */
  public void update(KelpPlayer toUpdate) {
    Inventory playerInventory = toUpdate.getBukkitPlayer().getOpenInventory().getTopInventory();

    // remove old widget artifacts
    playerInventory.clear();

    for (SimpleWidget current : Lists.newArrayList(simpleWidgets)) {
      KelpItem item = current.render();

      // if interactions with the item are not explicitly allowed
      // cancel them by default
      if (!item.hasTagKey("interactionAllowed")) {
        item.cancelInteractions();
      }

      playerInventory.setItem(item.getSlot(), item.getItemStack());
    }

    for (GroupedWidget current : Lists.newArrayList(groupedWidgets)) {
      current.render(toUpdate).forEach(item -> {

        // if interactions with the item are not explicitly allowed
        // cancel them by default
        if (!item.hasTagKey("interactionAllowed")) {
          item.cancelInteractions();
        }

        playerInventory.setItem(item.getSlot(), item.getItemStack());
      });
    }

    toUpdate.getBukkitPlayer().updateInventory();
  }

  /**
   * Executes the {@link #onClose()} method which stops
   * the schedulers and then removes all widgets from the inventory
   * cache.
   */
  public final void removeWidgetsAndClose() {
    onClose();
    simpleWidgets.forEach(Widget::onRemove);
    groupedWidgets.forEach(Widget::onRemove);
  }

  /**
   * This method is triggered when the inventory is closed
   * either manually by the player or when another inventory
   * is opened on top of this inventory. You can optionally
   * override this method to stop schedulers for example.
   */
  public void onClose() {}

}
