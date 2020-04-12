package de.pxav.kelp.core.reflect;

import com.google.common.collect.Sets;
import io.github.classgraph.*;

import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class MethodFinder {

  public Stream<Method> filter(String[] packageNames, MethodCriterion... methodCriteria) {

    Set<Method> output = Sets.newHashSet();

    try (ScanResult scanResult = new ClassGraph()
            .enableAllInfo()
            .whitelistPackages(packageNames)
            .scan()) {

      ClassInfoList allClasses = scanResult.getAllClasses();

      for (ClassInfo current : allClasses) {
        MethodInfoList methodInfos = current.getMethodInfo();
        for (MethodInfo methodInfo : methodInfos) {
          Method method = methodInfo.loadClassAndGetMethod();
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
