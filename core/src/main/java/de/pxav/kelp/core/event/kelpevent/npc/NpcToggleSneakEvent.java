package de.pxav.kelp.core.event.kelpevent.npc;

import de.pxav.kelp.core.npc.KelpNpc;
import org.bukkit.event.HandlerList;

/**
 * This event is called after an NPC toggled its sneak state.
 *
 * @author pxav
 */
public class NpcToggleSneakEvent extends NpcEvent {

  private static final HandlerList handlers = new HandlerList();

  public NpcToggleSneakEvent(KelpNpc npc) {
    super(npc);
  }

  /**
   * Checks whether the NPC has sneaked before it changed their sneak state/
   * before the event was triggered.
   *
   * @return {@code true} if the NPC has sneaked before.
   */
  public boolean hasSneakedBefore() {
    return !npc.isSneaking();
  }

  /**
   * Checks if the NPC is sneaking now/after the event has been triggered.
   * This is equivalent to calling {@link KelpNpc#isSneaking()}
   *
   * @return {@code true} if the NPC is sneaking now
   */
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
