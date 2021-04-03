package de.pxav.kelp.testing.inventory;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.command.CreateCommand;
import de.pxav.kelp.core.command.ExecutorType;
import de.pxav.kelp.core.command.KelpCommand;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.type.SimpleInventory;
import de.pxav.kelp.core.inventory.util.SlotArea;
import de.pxav.kelp.core.inventory.widget.PlaceholderWidget;
import de.pxav.kelp.core.inventory.widget.StatefulItemWidget;
import de.pxav.kelp.core.player.KelpPlayer;

/**
 * @author Etrayed
 */
@Singleton
@CreateCommand(name = "sweetinventory", executorType = ExecutorType.PLAYER_ONLY)
public class SweetInventoryCommand extends KelpCommand {

  private SimpleInventory inventory;

  private int clickIndex;

  @Inject
  public SweetInventoryCommand() {
    inventory = SimpleInventory.create().size(27);

    KelpItem widgetItem = KelpItem.create()
      .material(KelpMaterial.STONE)
      .displayName("Hello World")
      .slot(10);

    inventory.addWidget(PlaceholderWidget.create()
      .addSlots(SlotArea.outerBorder(27)));

    widgetItem.addGlobalListener(event -> {
      String displayName = "Hello World";

      widgetItem.displayName(clickIndex == 0 ? " " : displayName.substring(0, clickIndex));

      event.getPlayer().updateKelpInventory();

      clickIndex++;

      if(clickIndex > displayName.length()) {
        clickIndex = 0;
      }
    });

    inventory.addWidget(StatefulItemWidget.create().item(() -> widgetItem));

  }

  @Override
  public void onCommandRegister() {
    addAlias("sweetinv");
  }

  @Override
  public void onCommand(KelpPlayer player, String[] args) {
    player.openInventory(inventory);
  }

}
