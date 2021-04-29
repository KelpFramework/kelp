package de.pxav.kelp.core.reflect;

import com.google.common.base.Preconditions;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class ReflectionUtil {

  public static Object getValue(Object object, String fieldName) {
    try {
      Field field = object.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      return field.get(object);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void setValue(Object object, String fieldName, Object value) {
    try {
      Field field = object.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(object, value);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  // method name without "(" and ")"
  public static void invokeMethod(Object holdingObject, String methodName, Object... parameters) {
    Preconditions.checkNotNull(holdingObject);
    Preconditions.checkNotNull(methodName);
    try {

      Method method;

      if (parameters.length > 0) {
        Class<?>[] parameterTypes = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
          parameterTypes[i] = parameters[i].getClass();
        }
        method = holdingObject.getClass().getDeclaredMethod(methodName, parameterTypes);
      } else {
        method = holdingObject.getClass().getDeclaredMethod(methodName);
      }

      method.setAccessible(true);
      method.invoke(holdingObject);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  // method name without "(" and ")"
  public static void invokeStaticMethod(Class<?> holdingClass, String methodName, Object... parameters) {
    Preconditions.checkNotNull(holdingClass);
    Preconditions.checkNotNull(methodName);
    try {

      Method method;

      if (parameters.length > 0) {
        Class<?>[] parameterTypes = new Class[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
          parameterTypes[i] = parameters[i].getClass();
        }
        method = holdingClass.getDeclaredMethod(methodName, parameterTypes);
      } else {
        method = holdingClass.getDeclaredMethod(methodName);
      }

      method.setAccessible(true);
      method.invoke(null);
    } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

}
