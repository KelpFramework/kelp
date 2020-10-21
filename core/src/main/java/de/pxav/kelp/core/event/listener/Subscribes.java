package de.pxav.kelp.core.event.listener;

import org.bukkit.event.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark methods which should be
 * executed on a specific event. You can either listen to only
 * one or multiple events.
 *
 * A useful example would be to subscribe to the join and quit event
 * and update the new player count in the scoreboard for all players
 * then.
 *
 * @author pxav
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribes {

  /**
   * The classes of the events you want to listen for.
   *
   * @return The event classes.
   */
  Class<? extends Event>[] value();

  /**
   * Enable whether Kelp should try to automatically inject
   * common parameters such as a player for {@code PlayerEvents}.
   * WARNING: This is still in experimental stage and is not safe.
   * Please do not yet rely on the parameters passed by Kelp.
   *
   * @return whether parameters should be injected automatically.
   */
  boolean injectParameters() default true;

}
