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
import de.pxav.kelp.core.sound.KelpSound;

/**
 * @author Etrayed
 */
@Singleton
@CreateCommand(name = "sweetinventory", executorType = ExecutorType.PLAYER_ONLY)
public class SweetInventoryCommand extends KelpCommand {

  private AnimatedInventory inventory;

  @Inject
  public SweetInventoryCommand(KelpInventoryFactory factory) {
    inventory = factory.newAnimatedInventory();

    inventory.addWidget(ItemWidget.create().item(KelpItem.create().material(KelpMaterial.STONE)).addItemListener(event -> {
      event.getPlayer().sendMessage("You Clicked " + event.getClickedItem().getMaterial());
      event.getPlayer().playSound(KelpSound.LEVEL_UP);
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
