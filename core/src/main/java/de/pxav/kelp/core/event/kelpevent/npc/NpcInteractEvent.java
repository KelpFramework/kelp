package de.pxav.kelp.core.event.kelpevent.npc;

import de.pxav.kelp.core.npc.KelpNpc;
import org.bukkit.event.HandlerList;

/**
 * This event is called when a {@link de.pxav.kelp.core.player.KelpPlayer} interacts
 * with a {@link KelpNpc} by either right or left clicking/attacking it.
 *
 * @author pxav
 */
public class NpcInteractEvent extends NpcEvent {

  private static final HandlerList handlers = new HandlerList();

  // the action performed on the NPC.
  private NpcInteractAction action;

  public NpcInteractEvent(KelpNpc npc, NpcInteractAction action) {
    super(npc);
    this.action = action;
  }

  /**
   * Gets the action performed on the NPC. This way you can
   * determine whether the player has attacked/left clicked
   * or right clicked the NPC.
   *
   * @return The type/action of the interaction.
   */
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
