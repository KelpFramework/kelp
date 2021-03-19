package de.pxav.kelp.testing.region;

import de.pxav.kelp.core.command.CreateCommand;
import de.pxav.kelp.core.command.ExecutorType;
import de.pxav.kelp.core.command.KelpCommand;
import de.pxav.kelp.core.player.KelpPlayer;

import javax.inject.Singleton;

@Singleton
@CreateCommand(name = "kregion", executorType = ExecutorType.PLAYER_ONLY)
public class KRegionCommand extends KelpCommand {

  @Override
  public void onCommandRegister() {

  }

  @Override
  public void onCommand(KelpPlayer player, String[] args) {
    if (args.length == 0) {
      player.sendPrefixedMessages("§8[§2Kelp§8] ",
        "§8§m----------------------------------",
        "§7",
        "§cWork in progress",
        "§7/kregion wand",
        "§7/kregion create <CUBOID|ELLIPSOID>",
        "§7/kregion edit",
        "",
        "§8§m----------------------------------");
    }
  }

}
