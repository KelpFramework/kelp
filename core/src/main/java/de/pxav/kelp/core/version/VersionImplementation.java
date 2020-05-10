package de.pxav.kelp.core.version;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Each main class of an implementation module should
 * be annotated with this annotation so that the
 * {@code VersionBinderModule} can retrieve essential
 * information like the versions the implementation is
 * coded for.
 *
 * @author pxav
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface VersionImplementation {

  /**
   * @return All versions the implementation supports.
   */
  KelpVersion[] value();

  /**
   * @return All people who have written the implementation.
   */
  String[] authors() default {"Unknown"};

}
