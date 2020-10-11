package de.pxav.kelp.core.listener;

import org.bukkit.event.Event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Subscribes {

  Class<? extends Event>[] value();
  boolean injectParameters() default true;

}
