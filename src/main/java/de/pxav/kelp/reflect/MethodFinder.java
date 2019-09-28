package de.pxav.kelp.reflect;

import com.google.common.reflect.ClassPath;
import com.google.inject.Inject;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class MethodFinder {

  private ClassPath classPath;

  @Inject
  public MethodFinder(ClassLoader classLoader) {
    try {
      this.classPath = ClassPath.from(classLoader);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Stream<Method> filter(String[] packageNames, MethodCriterion... methodCriteria) {
    return (packageNames.length == 0
            ? this.classPath.getAllClasses()
            : Arrays.stream(packageNames)
            .flatMap(
                    packageName -> this.classPath.getTopLevelClassesRecursive(packageName).stream())
            .collect(Collectors.toList()))
            .stream()
            .map(
                    classInfo -> {
                      try {
                        return classInfo.load();
                      } catch (Throwable ignored) {
                      }
                      return null;
                    })
            .filter(Objects::nonNull)
            .flatMap(clazz -> Arrays.stream(clazz.getDeclaredMethods()))
            .filter(
                    method ->
                            Arrays.stream(methodCriteria)
                                    .allMatch(methodFilter -> methodFilter.matches(method)));
  }

}
