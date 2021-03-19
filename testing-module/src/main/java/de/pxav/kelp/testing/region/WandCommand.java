package de.pxav.kelp.testing.region;

import de.pxav.kelp.core.command.CreateSubCommand;
import de.pxav.kelp.core.command.ExecutorType;
import de.pxav.kelp.core.command.KelpCommand;
import de.pxav.kelp.core.player.KelpPlayer;

import javax.inject.Singleton;

@Singleton
@CreateSubCommand(name = "wand", executorType = ExecutorType.PLAYER_ONLY, parentCommand = KRegionCommand.class)
public class WandCommand extends KelpCommand {

  @Override
  public void onCommandRegister() {
    super.onCommandRegister();
  }

  @Override
  public void onCommand(KelpPlayer player, String[] args) {}

}
