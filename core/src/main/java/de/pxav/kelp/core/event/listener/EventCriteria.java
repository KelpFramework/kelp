package de.pxav.kelp.core.event.listener;

import de.pxav.kelp.core.event.kelpevent.KelpPlayerEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.function.Predicate;

/**
 * Contains basic pre-defined criteria for the dynamic event library.
 * Instead of creating a predicate yourself every time, you can use the
 * shortcuts from this class. Example:
 *
 * {@code .criterion({@link EventCriteria#playerHasPermission(String)})}
 *
 * @author pxav
 */
public class EventCriteria {

  /**
   * Checks whether the event player has the given permission. This method uses the normal
   * {@link de.pxav.kelp.core.player.KelpPlayer#hasPermission(String)} check so it
   * does not matter from which permission plugin the permission comes from.
   *
   * @param permission  The permission you want to check.
   * @param <T> The type of event you are using. Must be a subtype of {@link KelpPlayerEvent}.
   * @return The final predicate representing the criterion.
   */
  public static <T extends KelpPlayerEvent> Predicate<T> playerHasPermission(String permission) {
    return event -> event.getPlayer().hasPermission(permission);
  }

  /**
   * Checks whether the event player has a specific name.
   *
   * @param name The name you want to check the player for.
   * @param <T> The type of event you are using. Must be a subtype of {@link KelpPlayerEvent}.
   * @return The final predicate representing the criterion.
   */
  public static <T extends KelpPlayerEvent> Predicate<T> playerName(String name) {
    return event -> event.getPlayer().getName().equalsIgnoreCase(name);
  }

  /**
   * Checks if the player of the event is a server operator. This
   * does not check for {@code '*'-Permissions}, but only if the player
   * has an entry in the server's {@code ops.json}
   *
   * @param <T> The type of event you are using. Must be a subtype of {@link KelpPlayerEvent}.
   * @return The final predicate representing the criterion.
   */
  public static <T extends KelpPlayerEvent> Predicate<T> playerIsOperator() {
    return event -> event.getPlayer().isOperator();
  }

  /**
   * Checks whether the message of an {@link AsyncPlayerChatEvent} starts with a given
   * prefix. If you are doing chat commands for example, this might become useful to
   * check if the message is a command you want to check for.
   *
   * @param prefix The prefix the message should have.
   * @param <T> The type of event you are using. Must be {@link AsyncPlayerChatEvent}.
   * @return The final predicate representing the criterion.
   */
  public static <T extends AsyncPlayerChatEvent> Predicate<T> messageStartsWith(String prefix) {
    return event -> event.getMessage().startsWith(prefix);
  }

  /**
   * Checks if the command passed by a {@link PlayerCommandPreprocessEvent} starts with a given
   * prefix. This is useful if you want to handle sub commands via this event and you want to
   * check if the player is using a command that you handle ({@code /yourCommand})
   *
   * @param prefix The prefix the command message should have.
   * @param <T> The type of event you are using. Must be {@link PlayerCommandPreprocessEvent}.
   * @return The final predicate representing the criterion.
   */
  public static <T extends PlayerCommandPreprocessEvent> Predicate<T> commandStartsWith(String prefix) {
    return event -> event.getMessage().startsWith(prefix);
  }

}
