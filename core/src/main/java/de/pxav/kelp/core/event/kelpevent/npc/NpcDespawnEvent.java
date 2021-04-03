package de.pxav.kelp.core.event.kelpevent.npc;

import de.pxav.kelp.core.npc.KelpNpc;
import org.bukkit.event.HandlerList;

/**
 * This event is triggered when a {@link KelpNpc} is despawned.
 * A despawn can be triggered when the NPC is out of range for example
 * (caused by {@link de.pxav.kelp.core.npc.activity.AutoSpawnActivity})
 * or when it is completely removed from the server, which can be
 * checked with {@link #isRemove()}.
 *
 * When an NPC is just despawned, it is still registered in the NPC
 * cache and affected by NPC ticks, but it won't be visible for any player.
 *
 * @author pxav
 */
public class NpcDespawnEvent extends NpcEvent {

  private static final HandlerList handlers = new HandlerList();

  // whether the npc is completely removed from the server
  private boolean isRemove;

  public NpcDespawnEvent(KelpNpc npc, boolean isRemove) {
    super(npc);
    this.isRemove = isRemove;
  }

  /**
   * Checks whether the npc is completely removed from the server.
   * This would mean that it has been removed from the cache as well
   * as the tick system (no activities are executed on it anymore).
   *
   * @return {@code true} if the npc is completely removed, {@code false} if it was just a normal despawn
   */
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
