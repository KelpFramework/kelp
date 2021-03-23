package de.pxav.kelp.core.inventory.type;

import com.google.common.collect.Lists;
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

  public abstract void update(KelpPlayer toUpdate);

  public final void removeWidgetsAndClose() {
    onClose();
    simpleWidgets.forEach(Widget::onRemove);
    groupedWidgets.forEach(Widget::onRemove);
  }

  public void onClose() {}

}
