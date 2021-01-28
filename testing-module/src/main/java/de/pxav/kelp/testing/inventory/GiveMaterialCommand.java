package de.pxav.kelp.testing.inventory;

import com.google.inject.Inject;
import de.pxav.kelp.core.command.CreateSubCommand;
import de.pxav.kelp.core.command.ExecutorType;
import de.pxav.kelp.core.command.KelpCommand;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.item.KelpItemFactory;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.sound.KelpSound;
import de.pxav.kelp.testing.config.DebugCommandConfig;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@CreateSubCommand(name = "give", executorType = ExecutorType.PLAYER_ONLY, parentCommand = KMaterialCommand.class)
public class GiveMaterialCommand extends KelpCommand {

  private DebugCommandConfig config;
  private KelpItemFactory itemFactory;

  @Inject
  public GiveMaterialCommand(DebugCommandConfig config, KelpItemFactory itemFactory) {
    this.config = config;
    this.itemFactory = itemFactory;
  }

  @Override
  public void onCommandRegister() {
    argumentsStartFromZero(true);
    allowCustomParameters(true);
  }

  @Override
  public void onCommand(KelpPlayer player, String[] args) {
    String type = args[0];
    String rawMaterial = args[1];

    if (type.equalsIgnoreCase("kelp")) {
      KelpMaterial kelpMaterial = KelpMaterial.valueOf(rawMaterial);
      KelpItem item = itemFactory.newKelpItem().material(kelpMaterial);

      player.getBukkitPlayer().getInventory().addItem(item.getItemStack());
      player.playSound(KelpSound.LEVEL_UP, 10, 10);
      player.sendMessage(config.getStringValue("msg.givenKelpMaterial").replace("%material%", kelpMaterial.toString()));
      return;
    }

    if (type.equalsIgnoreCase("bukkit")) {
      Material material = Material.valueOf(rawMaterial);
      KelpItem item = itemFactory.fromItemStack(new ItemStack(material));

      player.getBukkitPlayer().getInventory().addItem(item.getItemStack());
      player.playSound(KelpSound.LEVEL_UP, 10, 10);
      player.sendMessage(config.getStringValue("msg.givenBukkitMaterial").replace("%material%", material.toString()));
    }

  }

}
