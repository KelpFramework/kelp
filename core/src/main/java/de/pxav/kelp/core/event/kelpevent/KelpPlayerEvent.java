package de.pxav.kelp.core.event.kelpevent;

import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.event.Event;

/**
 * Represents a specific type of event. It is basically a replacement
 * class for bukkit's normal {@link org.bukkit.event.player.PlayerEvent}, but
 * does not hold a normal {@link org.bukkit.entity.Player} instance. Instead
 * the {@code #getPlayer()} method returns a {@link KelpPlayer} to offer better
 * integration of custom Kelp events into your plugins. You don't need to
 * manually fetch the player anymore with the {@link de.pxav.kelp.core.player.KelpPlayerRepository}
 * but can directly retrieve it from the event.
 *
 * @author pxav
 */
public abstract class KelpPlayerEvent extends Event {

  protected KelpPlayer player;

  public KelpPlayerEvent(KelpPlayer who) {
    this.player = who;
  }

  /**
   * Gets the player who has triggered the event or
   * the player who should be handled by this event.
   *
   * @return The player of this event.
   */
  public final KelpPlayer getPlayer() {
    return this.player;
  }

}
