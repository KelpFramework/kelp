package de.pxav.kelp.testing.npc;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import de.pxav.kelp.core.command.CreateCommand;
import de.pxav.kelp.core.command.ExecutorType;
import de.pxav.kelp.core.command.KelpCommand;
import de.pxav.kelp.core.npc.KelpNpc;
import de.pxav.kelp.core.player.KelpPlayer;

import javax.inject.Singleton;
import java.util.UUID;

@Singleton
@CreateCommand(
  name = "knpc",
  executorType = ExecutorType.PLAYER_ONLY)
public class NpcCommand extends KelpCommand {

  private static Multimap<UUID, KelpNpc> playerNpcs = HashMultimap.create();

  @Override
  public void onCommandRegister() {
    permission("kelp.test.npc");
    noPlayerMessage("§cYou have to be a player to use this command");
  }

  @Override
  public void onCommand(KelpPlayer player, String[] args) {
    if (args.length == 0) {
      player.sendPrefixedMessages("§8[§2Kelp§8] ",
        "§8§m----------------------------------",
        "§7",
        "§7/knpc spawn",
        "§7/knpc removeAll",
        "",
        "§8§m----------------------------------");
    }
  }

  public static Multimap<UUID, KelpNpc> getPlayerNpcs() {
    return playerNpcs;
  }

}
