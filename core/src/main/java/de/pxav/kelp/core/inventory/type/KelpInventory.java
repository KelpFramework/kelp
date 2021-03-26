package de.pxav.kelp.core.inventory.type;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.widget.GroupedWidget;
import de.pxav.kelp.core.inventory.widget.SimpleWidget;
import de.pxav.kelp.core.inventory.widget.Widget;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.inventory.Inventory;

import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public abstract class KelpInventory {

  protected int size;
  protected List<SimpleWidget> simpleWidgets = Lists.newArrayList();
  protected List<GroupedWidget> groupedWidgets = Lists.newArrayList();

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
      if (!current.isStateful()) {
        simpleWidgets.remove(current);
      }
    }

    for (GroupedWidget current : Lists.newArrayList(groupedWidgets)) {
      if (!current.isStateful()) {
        continue;
      }

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
