package de.pxav.kelp.core.event.kelpevent;

import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 * This event is triggered on any player login. It is recommended to use this
 * event instead of the normal {@link PlayerLoginEvent} as this event is used
 * to handle the handshake between the client and server.
 *
 * If a kelp application would try to convert the {@link Player} instance into a
 * {@link KelpPlayer} instance, there would be a {@link NullPointerException} as
 * the player has not been registered yet. So the login event handler that creates
 * the {@link KelpPlayer} instances calls this event after it has finished so that
 * it is safe to use the kelp player object.
 *
 * All methods (except {@link #getKelpPlayer()}) are the same as those from the normal
 * bukkit event, so you can look for more detailed information there.
 *
 * @author pxav
 */
public class KelpPlayerLoginEvent extends PlayerEvent {

  // list of all event handlers listening for this event
  private static final HandlerList handlers = new HandlerList();

  private String hostname;
  private PlayerLoginEvent.Result result;
  private String message;
  private KelpPlayer kelpPlayer;

  public KelpPlayerLoginEvent(Player who, KelpPlayer kelpPlayer, String hostname, PlayerLoginEvent.Result result, String message) {
    super(who);
    this.hostname = hostname;
    this.result = result;
    this.message = message;
    this.kelpPlayer = kelpPlayer;
  }

  /**
   * @return The {@link KelpPlayer} instance of the player who logged in.
   */
  public KelpPlayer getKelpPlayer() {
    return kelpPlayer;
  }

  public String getHostname() {
    return hostname;
  }

  public PlayerLoginEvent.Result getResult() {
    return result;
  }

  public String getMessage() {
    return message;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

}
