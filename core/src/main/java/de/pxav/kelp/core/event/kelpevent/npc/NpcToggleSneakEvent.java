package de.pxav.kelp.core.event.kelpevent.npc;

import de.pxav.kelp.core.npc.KelpNpc;
import org.bukkit.event.HandlerList;

// called after sneak action
public class NpcToggleSneakEvent extends NpcEvent {

  private static final HandlerList handlers = new HandlerList();
  private boolean initialSpawn;

  public NpcToggleSneakEvent(KelpNpc npc) {
    super(npc);
  }

  public boolean hasSneakedBefore() {
    return !npc.isSneaking();
  }

  public boolean isSneakingNow() {
    return npc.isSneaking();
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

}
