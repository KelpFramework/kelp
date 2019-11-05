package de.pxav.kelp.core.application;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface KelpPlugin {

  String applicationName();

  String version() default "1.0";

  String[] authors();

  String description() default "none";

  String[] hardDependencies() default {};

  String[] softDependencies() default {};

}
