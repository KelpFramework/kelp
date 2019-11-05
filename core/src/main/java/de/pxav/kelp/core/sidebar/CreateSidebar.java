package de.pxav.kelp.core.sidebar;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark methods
 * which provide a sidebar that can be used
 * and opened.
 *
 * @author pxav
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CreateSidebar {

  /**
   * Each sidebar must have a unique identifier.
   * This identifier is used to for example
   * open the sidebar, etc.
   *
   * @return The unique identifier string.
   */
  String identifier();

  int switchInterval() default 100;

  /**
   * If your sidebar title is animated
   * you should specify an interval in which
   * the sidebar title should be updated.
   * The default value is 1000 milliseconds.
   *
   * @return The interval in milliseconds.
   */
  int titleAnimationInterval() default 1000;

  /**
   * Should your sidebar be handled asynchronously?
   * This only applies for general updates, the title updates
   * (if your sidebar is animated) are always asynchronous.
   *
   * @return {@code true} if sidebar updates should happen async.
   */
  boolean async() default true;

  /**
   * This attribute describes whether the scoreboard
   * should be displayed automatically when a player
   * joins the server.
   * @return {@code true} if it should be the default scoreboard.
   */
  boolean setOnJoin() default false;

}
