package de.pxav.kelp.core.reflect;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.inject.Singleton;
import io.github.classgraph.*;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

/**
 * This class is used to find classes with certain criteria.
 *
 * @see TypeCriterion
 * @author pxav
 */
@Singleton
public class TypeFinder {

  /**
   * Searches for all classes fulfilling the given criteria in the
   * given packages.
   *
   * @param packageNames  The names of the packages the algorithm should search for.
   *                      If you pass a package, which has subpackages, those will be
   *                      scanned as well.
   * @param typeCriteria  The criteria for the classes you want to find.
   * @return              A {@code Stream} containing the classes.
   */
  public Stream<Class<?>> filter(String[] packageNames, TypeCriterion... typeCriteria) {
    // validate given parameters
    Preconditions.checkNotNull(packageNames);
    Preconditions.checkNotNull(typeCriteria);

    Set<Class<?>> output = Sets.newHashSet();

    try (ScanResult scanResult = new ClassGraph()
            .enableAllInfo()
            .whitelistPackages(packageNames)
            .scan()) {

      ClassInfoList allClasses = scanResult.getAllClasses();

      // iterate all classes in the packages and check if they
      // math the criteria
      for (ClassInfo current : allClasses) {
        Class<?> c = current.loadClass();
        boolean allMatch = true;
        for (TypeCriterion typeCriterion : typeCriteria) {
          if (!typeCriterion.test(c)) {
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
