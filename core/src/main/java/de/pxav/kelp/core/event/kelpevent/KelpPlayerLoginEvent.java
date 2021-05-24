package de.pxav.kelp.core.event.kelpevent;

import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
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
 * @author pxav
 */
public class KelpPlayerLoginEvent extends KelpPlayerEvent {

  // list of all event handlers listening for this event
  private static final HandlerList handlers = new HandlerList();

  private String hostname;
  private PlayerLoginEvent.Result result;
  private String message;

  public KelpPlayerLoginEvent(KelpPlayer who, String hostname, PlayerLoginEvent.Result result, String message) {
    super(who);
    this.hostname = hostname;
    this.result = result;
    this.message = message;
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
