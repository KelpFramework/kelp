package de.pxav.kelp.testing.region;

import de.pxav.kelp.core.command.CreateSubCommand;
import de.pxav.kelp.core.command.ExecutorType;
import de.pxav.kelp.core.command.KelpCommand;
import de.pxav.kelp.core.player.KelpPlayer;

import javax.inject.Singleton;

@Singleton
@CreateSubCommand(name = "create", executorType = ExecutorType.PLAYER_ONLY, parentCommand = KRegionCommand.class)
public class CreateRegionCommand extends KelpCommand {

  @Override
  public void onCommandRegister() {
    argumentsStartFromZero(true);
    allowCustomParameters(true);
  }

  @Override
  public void onCommand(KelpPlayer player, String[] args) {
    if (args.length == 0) {
      player.sendMessage("§8[§2§8] §7Please select either §aCUBOID §7or §aELLIPSOID");
    }
  }

}
