package de.pxav.kelp.core.event.kelpevent.npc;

import de.pxav.kelp.core.npc.KelpNpc;
import org.bukkit.event.HandlerList;

public class NpcSpawnEvent extends NpcEvent {

  private static final HandlerList handlers = new HandlerList();
  private boolean initialSpawn;

  public NpcSpawnEvent(KelpNpc npc, boolean initialSpawn) {
    super(npc);
    this.initialSpawn = initialSpawn;
  }

  public boolean isInitialSpawn() {
    return initialSpawn;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

}
