package de.pxav.kelp.core.event.kelpevent;

import de.pxav.kelp.core.event.kelpevent.npc.NpcInteractEvent;

/**
 * Represents the action type of an interaction with an entity.
 * This can be used when handling the {@link NpcInteractEvent}
 * for example, which is triggered when a {@link de.pxav.kelp.core.npc.KelpNpc}
 * is either right clicked or attacked.
 *
 * @author pxav
 */
public enum EntityInteractAction {

  /**
   * A player has right clicked the entity.
   */
  RIGHT_CLICK,

  /**
   * A player has left clicked the entity
   * which might indicate that they have tried
   * to attack the entity.
   */
  LEFT_CLICK

}
