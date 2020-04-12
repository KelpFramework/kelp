package de.pxav.kelp.core.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * A class description goes here.
 *
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
