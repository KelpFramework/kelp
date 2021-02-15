package de.pxav.kelp.core.event.kelpevent.npc;

import de.pxav.kelp.core.npc.KelpNpc;
import org.bukkit.event.HandlerList;

public class NpcInteractEvent extends NpcEvent {

  private static final HandlerList handlers = new HandlerList();
  private NpcInteractAction action;

  public NpcInteractEvent(KelpNpc npc, NpcInteractAction action) {
    super(npc);
    this.action = action;
  }

  public NpcInteractAction getAction() {
    return action;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

}
