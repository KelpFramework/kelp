package de.pxav.kelp.core.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * This class contains all criteria you can use for searching/filtering
 * a method using the {@code MethodFinder}.
 *
 * @see MethodFinder
 * @author pxav
 */
public interface MethodCriterion {

  boolean matches(Method method);

  static MethodCriterion annotatedWith(Class<? extends Annotation> annotation) {
    return method -> method.isAnnotationPresent(annotation);
  }

  static MethodCriterion hasParameters(int parameterAmount) {
    return method -> method.getParameters().length == parameterAmount;
  }

  static MethodCriterion locatedIn(Class<?> clazz) {
    return method -> method
            .getDeclaringClass()
            .equals(clazz);
  }

  static MethodCriterion locatedIn(String packageName) {
    return method -> method
            .getDeclaringClass()
            .getPackage()
            .getName()
            .equals(packageName);
  }

}
