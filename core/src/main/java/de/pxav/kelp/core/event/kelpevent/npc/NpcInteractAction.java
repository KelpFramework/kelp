package de.pxav.kelp.core.event.kelpevent.npc;

/**
 * Represents the action type of an interaction with an NPC.
 * This can be used when handling the {@link NpcInteractEvent}
 * for example, which is triggered when a {@link de.pxav.kelp.core.npc.KelpNpc}
 * is either right clicked or attacked.
 *
 * @author pxav
 */
public enum NpcInteractAction {

  /**
   * A player has right clicked the NPC.
   */
  RIGHT_CLICK,

  /**
   * A player has left clicked the NPC
   * which might indicate that they have tried
   * to attack the NPC.
   */
  LEFT_CLICK

}
