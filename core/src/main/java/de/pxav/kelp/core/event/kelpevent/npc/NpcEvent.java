package de.pxav.kelp.core.event.kelpevent.npc;

import de.pxav.kelp.core.npc.KelpNpc;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.event.Event;

/**
 * Represents an event that is related to a {@link KelpNpc}.
 *
 * @author pxav
 */
public abstract class NpcEvent extends Event {

  // the npc handled by this event
  protected KelpNpc npc;

  public NpcEvent(KelpNpc npc) {
    this.npc = npc;
  }

  /**
   * Gets the {@link KelpNpc NPC} that is handled by this event.
   *
   * @return The npc that is handled by this event.
   */
  public final KelpNpc getNpc() {
    return this.npc;
  }

  /**
   * Gets the owner of the NPC, the player who can see the NPC
   * or is associated with it in the server cache.
   *
   * @return the owning {@link KelpPlayer player} of the NPC.
   */
  public final KelpPlayer getPlayer() {
    return npc.getPlayer();
  }

}
