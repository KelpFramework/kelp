package de.pxav.kelp.core.reflect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.inject.Singleton;
import io.github.classgraph.*;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 *
 *
 * @author pxav
 */
@Singleton
public class TypeFinder {

  public Stream<Class<?>> filter(String[] packageNames, TypeCriterion... typeCriteria) {
    Preconditions.checkNotNull(packageNames);
    Preconditions.checkNotNull(typeCriteria);

    Set<Class<?>> output = Sets.newHashSet();

    try (ScanResult scanResult = new ClassGraph()
            .enableAllInfo()
            .whitelistPackages(packageNames)
            .scan()) {

      ClassInfoList allClasses = scanResult.getAllClasses();

      for (ClassInfo current : allClasses) {
        Class<?> c = current.loadClass();
        boolean allMatch = true;
        for (TypeCriterion typeCriterion : typeCriteria) {
          if (!typeCriterion.matches(c)) {
            allMatch = false;
            break;
          }
        }
        if (allMatch) {
          output.add(c);
        }
      }
    }

    return output.stream().filter(Objects::nonNull);
  }

}
