package de.pxav.kelp.core.inventory;

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
public @interface CreateInventory {

  String identifier();

  boolean async() default true;

}
