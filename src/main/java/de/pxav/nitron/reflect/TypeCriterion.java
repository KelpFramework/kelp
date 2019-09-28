package de.pxav.nitron.reflect;

import java.lang.annotation.Annotation;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public interface TypeCriterion {

  boolean matches(Class<?> c);

  static TypeCriterion annotatedWith(Class<? extends Annotation> annotation) {
    return c -> c.isAnnotationPresent(annotation);
  }

  static TypeCriterion subclassOf(Class<?> parentClass) {
    return c -> !parentClass.equals(c) && parentClass.isAssignableFrom(c);
  }

  static TypeCriterion locatedIn(String packageName) {
    return c -> c.getPackage().getName().equals(packageName);
  }

}
