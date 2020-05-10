package de.pxav.kelp.core.application;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * If you write an application based on Kelp you have to
 * annotate your main class with this annotation in order
 * to pass essential information like the name or version.
 *
 * This is needed, because Kelp works without external files
 * for the loading process unlike the {@code plugin.yml} of
 * normal spigot plugins for example.
 *
 * To complete your main class you have to furthermore inherit
 * from {@code KelpApplication} which provides methods like
 * {@code #onEnable}
 *
 * @author pxav
 * @see KelpApplication
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface NewKelpApplication {

  /**
   * @return The name of your plugin / application.
   *         This has no effect on the plugin itself, but
   *         it will be used as display name for the plugin
   *         in for example the console.
   */
  String applicationName();

  /**
   * @return The version string of your plugin.
   *         Is used to display which version of your plugin
   *         is currently running on the server.
   */
  String version() default "1.0.0";

  /**
   * @return A list of people who have contributed
   *         to the plugin including the original author
   *         itself.
   */
  String[] authors();

  /**
   * @return A brief but expressive description of your plugin.
   */
  String description() default "none";

  /**
   * A collection of the names of the plugins your
   * own plugins needs in order to run.
   *
   * This does only include plugins which are definitely
   * needed, so your plugin cannot work without them.
   *
   * See {@code #softDependencies} if your plugin could
   * also run without them.
   *
   * @return A list of the hard dependencies of the plugin.
   */
  String[] hardDependencies() default {};

  /**
   * A collection of the names of the plugins your
   * own plugins needs in order to run.
   *
   * This does only include plugins which are optionally
   * required and your plugin could also run without them.
   *
   * See {@code #hardDependencies} if your plugin cannot
   * work without them.
   *
   * @return A list of soft dependencies of your plugin.
   */
  String[] softDependencies() default {};

}
