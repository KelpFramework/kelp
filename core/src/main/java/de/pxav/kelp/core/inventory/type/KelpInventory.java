package de.pxav.kelp.core.inventory.type;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.version.InventoryVersionTemplate;
import de.pxav.kelp.core.inventory.widget.GroupedWidget;
import de.pxav.kelp.core.inventory.widget.SimpleWidget;
import de.pxav.kelp.core.inventory.widget.Widget;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;

import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public abstract class KelpInventory<T extends KelpInventory<?>> {

  protected int size;
  protected List<SimpleWidget> simpleWidgets = Lists.newArrayList();
  protected List<GroupedWidget> groupedWidgets = Lists.newArrayList();

  protected InventoryVersionTemplate inventoryVersionTemplate;

  public KelpInventory(InventoryVersionTemplate inventoryVersionTemplate) {
    this.inventoryVersionTemplate = inventoryVersionTemplate;
    this.size = 54;
  }

  public T size(int size) {
    this.size = size;
    return (T) this;
  }

  public T rows(int rows) {
    this.size = rows * 9;
    return (T) this;
  }

  public T addWidget(SimpleWidget widget) {
    this.simpleWidgets.add(widget);
    return (T) this;
  }

  public T addWidget(GroupedWidget widget) {
    this.groupedWidgets.add(widget);
    return (T) this;
  }

  public abstract Inventory render(KelpPlayer player);

  public void update(KelpPlayer toUpdate) {
    Inventory playerInventory = toUpdate.getBukkitPlayer().getOpenInventory().getTopInventory();
    playerInventory.clear();

    for (SimpleWidget current : Lists.newArrayList(simpleWidgets)) {
      KelpItem item = current.render();
      if (!item.hasTagKey("interactionAllowed")) {
        item.cancelInteractions();
      }
      playerInventory.setItem(item.getSlot(), item.getItemStack());
    }

    for (GroupedWidget current : Lists.newArrayList(groupedWidgets)) {
      current.render(toUpdate).forEach(item -> {
        if (!item.hasTagKey("interactionAllowed")) {
          item.cancelInteractions();
        }
        playerInventory.setItem(item.getSlot(), item.getItemStack());
      });
    }

    toUpdate.getBukkitPlayer().updateInventory();
  }

  public final void removeWidgetsAndClose() {
    onClose();
    simpleWidgets.forEach(Widget::onRemove);
    groupedWidgets.forEach(Widget::onRemove);
  }

  public void onClose() {}
}
