package de.pxav.kelp.core.reflect;

import com.google.common.base.Preconditions;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This util class is used for simple reflection operations
 * especially used for NMS stuff. In lots of NMS classes, important
 * fields are {@code private} and can therefore only be accessed with
 * reflection. Private methods can also be invoked depending on the
 * use-case.
 *
 * Generally, this class is mainly used by Kelp internally in
 * the version implementations and should not encourage you to do NMS
 * operations from an application or the core module, although it might
 * work for now. Calling version specific methods is considered bad
 * practice in those areas of Kelp.
 *
 * Furthermore, please only use reflection if you know what you are
 * doing. Fields and methods are usually private by purpose (safety
 * reasons). Calling them might cause damage during runtime or might
 * produce unexpected behaviour.
 *
 * @author pxav
 */
public class ReflectionUtil {

  /**
   * Gets the value from a field with the given name of the given object.
   *
   * @param object      The object containing the given field.
   * @param fieldName   The name of the field inside the object.
   * @return The value held by the given field.
   */
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

  /**
   * Sets the value of the given field to the given value.
   *
   * @param object      The object containing the field you want to manipulate.
   * @param fieldName   The name of the field you want to manipulate.
   * @param value       The value you want to set the field to.
   */
  public static void setValue(Object object, String fieldName, Object value) {
    try {
      Field field = object.getClass().getDeclaredField(fieldName);
      field.setAccessible(true);
      field.set(object, value);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }
  }

  /**
   * Invokes the given instance method. This means the given method
   * may not be a static method, but has to be contained by an object.
   *
   * If you want to call a static method, use {@link #invokeStaticMethod(Class, String, Object...)}
   * instead.
   *
   * @param holdingObject   The object containing the method to call.
   * @param methodName      The name of the method to call without parenthesis ('(' and ')').
   * @param parameters      Optionally pass the required parameters here.
   */
  public static void invokeMethod(Object holdingObject, String methodName, Object... parameters) {
    Preconditions.checkNotNull(holdingObject);
    Preconditions.checkNotNull(methodName);
    try {

      Method method;

      if (parameters.length > 0) {
        // automatically fetch type of the given parameter objects.
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

  /**
   * Invokes the given static method. This means the method may
   * not be bound to an instance, but has to be accessible statically.
   *
   * If you want to call an instance method, use {@link #invokeMethod(Object, String, Object...)}
   * instead.
   *
   * @param holdingClass    The class containing the method to call.
   * @param methodName      The name of the method to call without parenthesis ('(' and ')').
   * @param parameters      Optionally pass the required parameters here.
   */
  public static void invokeStaticMethod(Class<?> holdingClass, String methodName, Object... parameters) {
    Preconditions.checkNotNull(holdingClass);
    Preconditions.checkNotNull(methodName);
    try {

      Method method;

      if (parameters.length > 0) {
        // automatically fetch type of the given parameter objects.
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
