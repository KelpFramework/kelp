package de.pxav.kelp.core.inventory.type;

import de.pxav.kelp.core.animation.TextAnimation;
import de.pxav.kelp.core.inventory.widget.InventoryWidget;
import org.bukkit.inventory.Inventory;

import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class SimpleInventory extends KelpInventory {

  private TextAnimation title;
  private List<InventoryWidget> widgets;

  public SimpleInventory() {

  }

  public SimpleInventory title(TextAnimation textAnimation) {
    this.title = textAnimation;
    return this;
  }

  public SimpleInventory addWidget(InventoryWidget widget) {
    this.widgets.add(widget);
    return this;
  }

  @Override
  public Inventory render() {

    return null;
  }

  @Override
  public void update(Inventory toUpdate) {

  }

}
