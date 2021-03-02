package de.pxav.kelp.testing.inventory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.command.CreateCommand;
import de.pxav.kelp.core.command.ExecutorType;
import de.pxav.kelp.core.command.KelpCommand;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.type.AnimatedInventory;
import de.pxav.kelp.core.inventory.type.KelpInventoryFactory;
import de.pxav.kelp.core.inventory.widget.ItemWidget;
import de.pxav.kelp.core.player.KelpPlayer;

/**
 * @author Etrayed
 */
@Singleton
@CreateCommand(name = "sweetinventory", executorType = ExecutorType.PLAYER_ONLY)
public class SweetInventoryCommand extends KelpCommand {

  private AnimatedInventory inventory;

  private int clickIdx;

  @Inject
  public SweetInventoryCommand(KelpInventoryFactory factory) {
    inventory = factory.newAnimatedInventory();

    KelpItem widgetItem = KelpItem.create().material(KelpMaterial.STONE).displayName("Hello World");

    inventory.addWidget(ItemWidget.create().item(widgetItem).addItemListener(event -> {
      String displayName = "Hello World";

      widgetItem.displayName(clickIdx == 0 ? " " : displayName.substring(0, clickIdx));

      event.getPlayer().updateKelpInventory();

      clickIdx++;

      if(clickIdx > displayName.length()) {
        clickIdx = 0;
      }
    }));
  }

  @Override
  public void onCommandRegister() {
    addAlias("sweetinv");
    addAlias("si");
  }

  @Override
  public void onCommand(KelpPlayer player, String[] args) {
    player.openInventory(inventory);
  }
}
