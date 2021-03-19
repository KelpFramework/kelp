package de.pxav.kelp.testing.npc;

import de.pxav.kelp.core.command.CreateSubCommand;
import de.pxav.kelp.core.command.ExecutorType;
import de.pxav.kelp.core.command.KelpCommand;
import de.pxav.kelp.core.command.KelpConsoleSender;
import de.pxav.kelp.core.npc.KelpNpc;

@CreateSubCommand(name = "removeAll",
  executorType = ExecutorType.PLAYER_AND_CONSOLE,
  parentCommand = NpcCommand.class)
public class NpcRemoveAllCommand extends KelpCommand {

  @Override
  public void onCommandRegister() {
    delegatePlayerToConsole(true);
    inheritFromMainCommand(true);
  }

  @Override
  public void onCommand(KelpConsoleSender consoleSender, String[] args) {
    NpcCommand.getPlayerNpcs().keySet().forEach(uuid
      -> NpcCommand.getPlayerNpcs().get(uuid).forEach(KelpNpc::remove));
    NpcCommand.getPlayerNpcs().clear();
    consoleSender.sendMessage("§8[§2Kelp§8] §7All NPCs have been removed successfully.");
  }

}
