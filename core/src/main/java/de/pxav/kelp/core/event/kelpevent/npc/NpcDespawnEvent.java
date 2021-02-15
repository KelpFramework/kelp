package de.pxav.kelp.core.event.kelpevent.npc;

import de.pxav.kelp.core.npc.KelpNpc;
import org.bukkit.event.HandlerList;

public class NpcDespawnEvent extends NpcEvent {

  private static final HandlerList handlers = new HandlerList();
  private boolean isRemove;

  public NpcDespawnEvent(KelpNpc npc, boolean isRemove) {
    super(npc);
    this.isRemove = isRemove;
  }

  public boolean isRemove() {
    return isRemove;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

}
