package de.pxav.kelp.sidebar;

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
public @interface CreateSidebar {

  String identifier();

  int switchInterval() default 100;

  // in millis
  int titleAnimationInterval() default 5;

  boolean async() default true;

}
