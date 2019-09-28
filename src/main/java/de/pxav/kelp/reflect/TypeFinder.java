package de.pxav.kelp.reflect;

import com.google.common.base.Preconditions;
import com.google.common.reflect.ClassPath;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.KelpPlugin;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class TypeFinder {

  private ClassPath classPath;

  @Inject
  public TypeFinder() {
    try {
      this.classPath = ClassPath.from(KelpPlugin.class.getClassLoader());
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public Stream<? extends Class<?>> filter(String[] packageNames, TypeCriterion... typeCriteria) {
    Preconditions.checkNotNull(packageNames);
    Preconditions.checkNotNull(typeCriteria);
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
                      } catch (Throwable ignore) {}
                      return null;
                    })
            .filter(Objects::nonNull)
            .filter(
                    clazz ->
                            Arrays.stream(typeCriteria).allMatch(typeFilter -> typeFilter.matches(clazz)));
  }

}
