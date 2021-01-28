package de.pxav.kelp.testing.sidebar;

import de.pxav.kelp.core.command.CreateSubCommand;
import de.pxav.kelp.core.command.ExecutorType;
import de.pxav.kelp.core.command.KelpCommand;
import de.pxav.kelp.core.player.KelpPlayer;

@CreateSubCommand(name = "remove",
  executorType = ExecutorType.PLAYER_ONLY,
  parentCommand = KSidebarCommand.class
)
public class RemoveCommand extends KelpCommand {

  @Override
  public void onCommandRegister() {
    allowCustomParameters(false);
  }

  @Override
  public void onCommand(KelpPlayer player, String[] args) {
    player.removeSidebar();
    player.sendMessage("§8[§2Kelp§8] §7Your sidebar has been removed.");
  }

}
