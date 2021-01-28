package de.pxav.kelp.testing.sidebar;

import de.pxav.kelp.core.command.CreateCommand;
import de.pxav.kelp.core.command.ExecutorType;
import de.pxav.kelp.core.command.KelpCommand;
import de.pxav.kelp.core.player.KelpPlayer;

import javax.inject.Singleton;

@Singleton
@CreateCommand(
  name = "ksidebar",
  executorType = ExecutorType.PLAYER_ONLY
)
public class KSidebarCommand extends KelpCommand {

  @Override
  public void onCommandRegister() {
    noPlayerMessage("§cYou have to be a player to execute this command");
    permission("kelp.test.sidebar");
  }

  @Override
  public void onCommand(KelpPlayer player, String[] args) {
    player.sendPrefixedMessages("§8[§2Kelp§8]",
      " §8§m-------------------------",
        " §7/ksidebar show <ExampleId>",
        " §7/ksidebar update <normal|lazy|title>",
        " §7/ksidebar remove",
        " §7",
        " §7Example IDs§8:",
        " §a1 §7Simple, static",
        " §a2 §7Simple, updatable",
        " §a3 §7Animated, updatable",
        "",
        " §8§m-------------------------"
    );
  }

}
