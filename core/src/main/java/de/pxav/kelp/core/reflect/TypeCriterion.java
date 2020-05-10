package de.pxav.kelp.core.reflect;

import java.lang.annotation.Annotation;

/**
 * This class contains all criteria you can use for searching/filtering
 * a class.
 *
 * @see TypeFinder
 * @author pxav
 */
public interface TypeCriterion {

  /**
   * Returns {@code true} if the given criteria are fulfilled
   * by the given class.
   *
   * @param c The class you want to check
   * @return  {@code true} if the criteria matches the class
   */
  boolean matches(Class<?> c);

  /**
   * Checks if the class is annotated with a certain
   * annotation.
   *
   * @param annotation  The annotation the class should have.
   * @return            The criterion, so that you can append further
   *                    criteria.
   */
  static TypeCriterion annotatedWith(Class<? extends Annotation> annotation) {
    return c -> c.isAnnotationPresent(annotation);
  }

  /**
   * Checks whether the given class is a subclass of a certain
   * parent class. This is true when for example a class
   * implements an interface or extends from a normal or abstract class.
   *
   * @param parentClass The parent class, the subclass is extending from.
   * @return            The criterion, so that you can append further
   *                    criteria.
   */
  static TypeCriterion subclassOf(Class<?> parentClass) {
    return c -> !parentClass.equals(c) && parentClass.isAssignableFrom(c);
  }

  /**
   * Checks if the class is located in a certain package.
   *
   * @param packageName The package the class should be located in.
   * @return            The criterion, so that you can append further
   *                    criteria.
   */
  static TypeCriterion locatedIn(String packageName) {
    return c -> c.getPackage().getName().equals(packageName);
  }

}
