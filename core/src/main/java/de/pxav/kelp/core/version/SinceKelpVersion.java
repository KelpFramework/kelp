package de.pxav.kelp.core.version;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This annotation is used for elements which are not available
 * for all minecraft/kelp versions.
 *
 * If you have a zombie for example and want to make it a
 * Drowned, this is only possible in 1.13+. To make sure the
 * method is not used by plugins made compatible for older
 * versions as well, such methods are annotated with
 * {@code @SinceKelpVersion(version)}.
 *
 * @author pxav
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface SinceKelpVersion {

  /**
   * The version since when the given element is available.
   * All versions below are not able to call this
   * element.
   *
   * @return The minimum version for the annotated element.
   */
  KelpVersion value();

}
