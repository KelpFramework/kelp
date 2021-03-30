package de.pxav.kelp.core.event.kelpevent.npc;

import de.pxav.kelp.core.npc.KelpNpc;
import org.bukkit.event.HandlerList;

/**
 * This event is triggered when an NPC is spawned to a {@link de.pxav.kelp.core.world.KelpWorld}.
 * It can either be triggered when an NPC spawns for the first time or when
 * it spawns again after a {@link NpcDespawnEvent}. This can be queried
 * using {@link #isInitialSpawn()}.
 *
 * @author pxav
 */
public class NpcSpawnEvent extends NpcEvent {

  private static final HandlerList handlers = new HandlerList();
  private boolean initialSpawn;

  public NpcSpawnEvent(KelpNpc npc, boolean initialSpawn) {
    super(npc);
    this.initialSpawn = initialSpawn;
  }

  /**
   * Checks whether the NPC has spawned for the first time
   * or if it just spawned again after it has been despawned.
   *
   * @return {@code true} if the npc spawns for the first time, {@code false} if it spawned after a respawn.
   */
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
