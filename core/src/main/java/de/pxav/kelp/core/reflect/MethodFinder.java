package de.pxav.kelp.core.reflect;

import com.google.common.collect.Sets;
import io.github.classgraph.*;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * This class can be used to search for certain methods using
 * certain criteria. An example of how this could look like:
 *
 * {@code filter(new String[] {"abc.def", "de.pxav.kelp.core"},
 * MethodCriterion.annotatedWith(annotation));
 * }
 *
 * @see MethodCriterion
 * @author pxav
 */
public class MethodFinder {

  /**
   * Searches for all methods fulfilling the given criteria in the
   * given packages.
   *
   * @param packageNames    The names of the packages the algorithm should search for.
   *                        If you pass a package, which has subpackages, those will be
   *                        scanned as well.
   * @param methodCriteria  The criteria for the methods you want to find.
   * @return                A {@code Stream} containing the methods.
   */
  public Stream<Method> filter(String[] packageNames, MethodCriterion... methodCriteria) {

    Set<Method> output = Sets.newHashSet();

    // scanning for all classes in the packages and then
    // iterate all their methods
    try (ScanResult scanResult = new ClassGraph()
            .enableAllInfo()
            .whitelistPackages(packageNames)
            .scan()) {

      ClassInfoList allClasses = scanResult.getAllClasses();

      for (ClassInfo current : allClasses) {
        MethodInfoList methodInfos = current.getMethodInfo();
        for (MethodInfo methodInfo : methodInfos) {
          Method method;

          try {
            method = methodInfo.loadClassAndGetMethod();
          } catch (Exception e) {
            continue;
          }

          boolean allMatch = true;
          for (MethodCriterion methodCriterion : methodCriteria) {
            if (!methodCriterion.matches(method)) {
              allMatch = false;
              break;
            }
          }

          if (allMatch) {
            output.add(method);
          }
        }
      }
    }

    return output.stream().filter(Objects::nonNull);
  }

}
