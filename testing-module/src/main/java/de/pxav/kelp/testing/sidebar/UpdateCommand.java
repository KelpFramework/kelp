package de.pxav.kelp.testing.sidebar;

import de.pxav.kelp.core.command.CreateSubCommand;
import de.pxav.kelp.core.command.ExecutorType;
import de.pxav.kelp.core.command.KelpCommand;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.sidebar.type.AnimatedSidebar;
import de.pxav.kelp.core.sidebar.type.KelpSidebar;
import de.pxav.kelp.core.sidebar.type.SimpleSidebar;

@CreateSubCommand(name = "update",
  executorType = ExecutorType.PLAYER_ONLY,
  parentCommand = KSidebarCommand.class)
public class UpdateCommand extends KelpCommand {

  @Override
  public void onCommandRegister() {
    allowCustomParameters(true);
    argumentsStartFromZero(true);
  }

  @Override
  public void onCommand(KelpPlayer player, String[] args) {
    if (args.length == 0) {
      player.sendMessage("§cPlease specify an update action.");
      return;
    }

    KelpSidebar sidebar = player.getCurrentSidebar();

    if (sidebar == null) {
      player.sendMessage("§cThere is no sidebar to update.");
      return;
    }

    if (args[0].equalsIgnoreCase("lazy")) {
      if (sidebar instanceof AnimatedSidebar) {
        ((AnimatedSidebar)sidebar).lazyUpdate(player);
      }
      if (sidebar instanceof SimpleSidebar) {
        ((SimpleSidebar)sidebar).lazyUpdate(player);
      }
      player.sendMessage("§8[§2Kelp§8] §7A lazy update has been performed on your sidebar.");
    }

    if (args[0].equalsIgnoreCase("normal")) {
      sidebar.update(player);
      player.sendMessage("§8[§2Kelp§8] §7A normal update has been performed on your sidebar.");
    }

    if (args[0].equalsIgnoreCase("title")) {

      if (sidebar instanceof SimpleSidebar) {
        ((SimpleSidebar)sidebar).updateTitleOnly(player);
      } else {
        player.sendMessage("§cCannot update AnimatedSidebar's title.");
      }

      player.sendMessage("§8[§2Kelp§8] §7A title update has been performed on your sidebar.");
    }
  }

}
