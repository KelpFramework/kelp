package de.pxav.kelp.core.event.listener;

import de.pxav.kelp.core.event.kelpevent.KelpPlayerEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.function.Predicate;

public class EventCriteria {

  public static <T extends KelpPlayerEvent> Predicate<T> playerHasPermission(String permission) {
    return event -> event.getPlayer().hasPermission(permission);
  }

  public static <T extends KelpPlayerEvent> Predicate<T> playerName(String name) {
    return event -> event.getPlayer().getName().equalsIgnoreCase(name);
  }

  public static <T extends KelpPlayerEvent> Predicate<T> playerIsOperator() {
    return event -> event.getPlayer().isOperator();
  }

  public static <T extends AsyncPlayerChatEvent> Predicate<T> messageStartsWith(String prefix) {
    return event -> event.getMessage().startsWith(prefix);
  }

  public static <T extends PlayerCommandPreprocessEvent> Predicate<T> commandStartsWith(String prefix) {
    return event -> event.getMessage().startsWith(prefix);
  }

}
