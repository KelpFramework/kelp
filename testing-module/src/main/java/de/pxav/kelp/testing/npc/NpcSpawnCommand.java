package de.pxav.kelp.testing.npc;



import de.pxav.kelp.core.command.CreateSubCommand;
import de.pxav.kelp.core.command.ExecutorType;
import de.pxav.kelp.core.command.KelpCommand;
import de.pxav.kelp.core.event.kelpevent.npc.NpcInteractAction;
import de.pxav.kelp.core.npc.KelpNpc;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.scheduler.type.DelayedScheduler;
import de.pxav.kelp.testing.npc.gui.NpcGui;

import javax.inject.Inject;

@CreateSubCommand(
  name = "spawn",
  executorType = ExecutorType.PLAYER_ONLY,
  parentCommand = NpcCommand.class)
public class NpcSpawnCommand extends KelpCommand {

  @Inject private NpcGui gui;

  @Override
  public void onCommandRegister() {
    inheritFromMainCommand(true);
  }

  @Override
  public void onCommand(KelpPlayer player, String[] args) {
    KelpNpc npc = KelpNpc.create();

    npc.location(player.getLocation());
    npc.player(player);
    npc.customName("§2Kelp Demo NPC");
    npc.showCustomName();
    npc.onInteract(event -> {
      if (event.getAction() == NpcInteractAction.RIGHT_CLICK) {
        DelayedScheduler.create().withDelayOf(50).milliseconds().run(taskId -> {
          gui.open(npc);
        });

        return;
      }

      // todo if npc is attackable, play damage animation
    });

    npc.spawn();
    npc.lookTo(player.getLocation());

    NpcCommand.getPlayerNpcs().put(player.getUUID(), npc);
    player.sendMessage("§8[§2Kelp§8] §7An NPC has been spawned at your location.");
    player.sendMessage("§8[§2Kelp§8] §7Right click it to change some settings.");
  }

}
