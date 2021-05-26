package de.pxav.kelp.core.application;

import com.google.common.base.Preconditions;
import com.google.common.collect.*;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import de.pxav.kelp.core.common.KelpFileUtils;
import de.pxav.kelp.core.event.listener.EventHandlerRegistration;
import de.pxav.kelp.core.logger.KelpLogger;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * This repository class is used to load and manage
 * the different kelp applications.
 *
 * @author pxav
 */
@Singleton
public final class KelpApplicationRepository {

  private final Map<String, KelpApplicationMeta> appsToLoad = Maps.newHashMap();
  private final Map<String, KelpApplication> appsToEnable = Maps.newHashMap();
  private final Map<String, KelpApplication> enabledApps = Maps.newHashMap();
  private final Map<String, KelpApplicationMeta> appMeta = Maps.newHashMap();
  private final BiMap<KelpApplicationMeta, Class<? extends KelpApplication>>
          classPathApps = HashBiMap.create();

  private final Injector injector;
  private final KelpFileUtils kelpFileUtils;

  @Inject
  public KelpApplicationRepository(Injector injector,
                                   KelpFileUtils kelpFileUtils) {
    this.injector = injector;
    this.kelpFileUtils = kelpFileUtils;
  }

  /**
   * Iterates all {@code .jar} files in a certain directory and
   * checks whether they are valid kelp applications.
   * If so they will be added to the list so that they can
   * be loaded later.
   *
   * @param kelpApplicationDirectory The directory whose files should be iterated.
   * @return The {@code KelpApplicationRepository} object.
   */
  public KelpApplicationRepository detectKelpApplications(File kelpApplicationDirectory) {
    Preconditions.checkNotNull(kelpApplicationDirectory);
    kelpFileUtils.createFolderIfNotExists(kelpApplicationDirectory);

    // get a list of all files in the directory.
    File[] filesToLoad = kelpApplicationDirectory.listFiles();
    if (filesToLoad != null && filesToLoad.length <= 0) return this;
    Preconditions.checkNotNull(filesToLoad);

    Arrays.stream(filesToLoad)
            .filter(file -> file.isFile() && file.getName().endsWith(".jar"))
            .map(
                    file -> {
                      try {
                        return new TemporaryApplicationInfo(file, new JarFile(file));
                      } catch (IOException e) {
                        KelpLogger.of(KelpApplication.class).severe("Cannot load application from file " + file.getName());
                        return null;
                      }
                    })
            .filter(Objects::nonNull)
            .forEach(
                    moduleInfo -> {
                      KelpApplicationMeta applicationMeta = validateApplication(moduleInfo.getJarFile());
                      appMeta.put(applicationMeta.getApplicationName(), applicationMeta);
                      if (applicationMeta == null) {
                        KelpLogger.of(KelpApplication.class).severe("Cannot load application from file " + moduleInfo.getFile().getName());
                        return;
                      }

                      if (!enabledApps.containsKey(applicationMeta.getApplicationName())) {
                        applicationMeta.file(moduleInfo.getFile());
                        appsToLoad.put(applicationMeta.getApplicationName(), applicationMeta);
                      }
                    });
    return this;
  }

  /**
   *
   * @return
   */
  public KelpApplicationRepository load() {
    Map<KelpApplicationMeta, Boolean> moduleStatuses2 = Maps.newHashMap();
    for (Map.Entry<String, KelpApplicationMeta> entry : appsToLoad.entrySet()) {
      KelpApplicationMeta module = entry.getValue();

      if (!bindToClassPath(moduleStatuses2, new Stack<>(), module)) {
        KelpLogger.of(KelpApplication.class).severe("[APP] Failed to enable " + entry.getKey());
        continue;
      }

      KelpLogger.of(KelpApplication.class).info("[APP] Successfully loaded " + entry.getKey());
    }
    return this;
  }

  /**
   * Check whether the given {@code .jar} file contains a
   * valid kelp application.
   *
   * If so the classes will be analyzed and information about
   * the application will be collected and saved as a {@code KelpInformation}
   * object.
   *
   * @param jarFile The {@code .jar} file you want to search in.
   * @return        The final information object.
   *                {@code null} if the {@code jar} file is no valid kelp application.
   * @see KelpApplicationMeta
   */
  private KelpApplicationMeta validateApplication(JarFile jarFile) {
    Enumeration<JarEntry> jarEntries = jarFile.entries();

    while (jarEntries.hasMoreElements()) {
      JarEntry jarEntry = jarEntries.nextElement();

      if (jarEntry != null && jarEntry.getName().endsWith(".class")) {
        try {
          ClassFile classFile =
                  new ClassFile(new DataInputStream(jarFile.getInputStream(jarEntry)));
          if (!classFile.getSuperclass().equals(KelpApplication.class.getName())) continue;

          Annotation descriptionAnnotation = Arrays.stream(
                          ((AnnotationsAttribute)
                                  classFile.getAttribute(AnnotationsAttribute.visibleTag))
                                  .getAnnotations())
                          .filter(annotation -> annotation.getTypeName().equals(NewKelpApplication.class.getName()))
                          .findFirst()
                          .orElse(null);

          if (descriptionAnnotation == null) continue;

          String applicationName =
                  ((StringMemberValue) descriptionAnnotation.getMemberValue("applicationName")).getValue();
          StringMemberValue version =
                  ((StringMemberValue) descriptionAnnotation.getMemberValue("version"));
          ArrayMemberValue hardDependencies =
                  ((ArrayMemberValue) descriptionAnnotation.getMemberValue("hardDependencies"));
          ArrayMemberValue softDependencies =
                  ((ArrayMemberValue) descriptionAnnotation.getMemberValue("softDependencies"));

          KelpApplicationMeta applicationMeta = new KelpApplicationMeta();
          applicationMeta
                  .applicationName(applicationName)
                  .version(version == null ? "1.0.0" : version.getValue())
                  .hardDependencies(
                          (hardDependencies == null ? Sets.newHashSet() : newHashSet(hardDependencies)))
                  .softDependencies(
                          (softDependencies == null
                                  ? Sets.newHashSet()
                                  : newHashSet(softDependencies)));
          applicationMeta.main(classFile.getName());
          return applicationMeta;
        } catch (IOException ignored) {
          break;
        }
      }
    }
    return null;
  }

  /**
   * Loads the given plugins into the {@code classpath} of
   * the main plugin.
   *
   * @param moduleStatuses
   * @param dependencyStack
   * @param module
   * @return
   */
  private boolean bindToClassPath(
          Map<KelpApplicationMeta, Boolean> moduleStatuses,
          Stack<KelpApplicationMeta> dependencyStack,
          KelpApplicationMeta module) {
    if (moduleStatuses.containsKey(module)) return moduleStatuses.get(module);

    Set<String> dependencies = Sets.newHashSet();
    dependencies.addAll(module.getHardDependencies());
    dependencies.addAll(module.getSoftDependencies());

    boolean status = true;

    for (String dependName : dependencies) {
      KelpApplicationMeta depend =
              appsToLoad.containsKey(dependName)
                      ? appsToLoad.get(dependName)
                      : (enabledApps.containsKey(dependName))
                      ? enabledApps.get(dependName).getInformation()
                      : null;
      Boolean dependStatus = (depend != null) ? moduleStatuses.get(depend) : false;

      if (dependStatus == null) {
        if (dependencyStack.contains(depend)) {
          StringBuilder dependencyGraph = new StringBuilder();

          for (KelpApplicationMeta element : dependencyStack) {
            dependencyGraph.append(element.getApplicationName()).append(" -> ");
          }

          dependencyGraph.append(module.getApplicationName()).append(" -> ").append(dependName);
          KelpLogger.of(KelpApplication.class).severe("[APP] Circular dependency detected for "
                  + module.getApplicationName()
                  + ": " + dependencyGraph);
          status = false;
        } else {
          dependencyStack.push(module);
          dependStatus = this.bindToClassPath(moduleStatuses, dependencyStack, depend);
          dependencyStack.pop();
        }
      }

      if (!Boolean.FALSE.equals(dependStatus) && !module.getSoftDependencies().contains(dependName)) {
        KelpLogger.of(KelpApplication.class).warning("[APP] Dependency " + dependName + " (required by " + module.getApplicationName() + ") is unavailable");
        status = false;
      }

      if (!status) {
        break;
      }
    }

    if (status) {
      try {
        URLClassLoader loader = (URLClassLoader) KelpApplicationRepository.class.getClassLoader();
        Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
        addURL.setAccessible(true);
        addURL.invoke(loader, module.getFile().toURI().toURL());
        Class<? extends KelpApplication> main =
                (Class<? extends KelpApplication>) loader.loadClass(module.getMain());
        this.classPathApps.put(module, main);
        KelpLogger.registerLogger(main, module.getApplicationName());
      } catch (Throwable t) {
        KelpLogger.of(KelpApplication.class).severe("[APP] Cannot load module " + module.getApplicationName() + ". Check stack trace for more information:");
        t.printStackTrace();
      }
    }

    moduleStatuses.put(module, status);
    return status;
  }

  public void enableAfterClassPathLoad() {
    this.classPathApps.forEach(
            (description, clazz) -> {
              KelpApplication moduleInstance = injector.getInstance(clazz);
              moduleInstance.init(description, injector);

              appsToEnable.put(description.getApplicationName(), moduleInstance);
              KelpLogger.of(KelpApplication.class).info("[APP] Loading application "
                              + description.getApplicationName()
                              + " with version "
                              + description.getVersion());
              moduleInstance.onLoad();
    });
  }

  public void enableApplications() {
    KelpLogger.of(KelpApplication.class).info("[APP] Enabling all registered KelpApplications...");
    for (Map.Entry<String, KelpApplication> namePluginEntry : appsToEnable.entrySet()) {
      try {
        KelpLogger.of(KelpApplication.class).info(
                "[APP] Enabling application "
                        + namePluginEntry.getValue().getInformation().getApplicationName()
                        + " with version "
                        + namePluginEntry.getValue().getInformation().getVersion());
        injector
                .getInstance(EventHandlerRegistration.class)
                .initialize(namePluginEntry.getValue().getClass().getPackage().getName());
        namePluginEntry.getValue().onEnable();

        enabledApps.put(namePluginEntry.getKey(), namePluginEntry.getValue());
      } catch (Throwable t) {
        KelpLogger.of(KelpApplication.class).severe(
                "[APP] Exception encountered while loading application "
                        + namePluginEntry.getValue().getInformation().getApplicationName()
                        + ":");
        t.printStackTrace();
        disableApplication(namePluginEntry.getValue());
      }
    }

    appsToEnable.clear();
  }

  public void callOnLoad() {
    enableAfterClassPathLoad();
    appsToLoad.clear();
  }

  /**
   * @return  A collection of all main classes of all kelp applications
   *          as {@code Class<? extends KelpApplication>}
   * @see     KelpApplication
   */
  public Collection<Class<? extends KelpApplication>> getAllApplicationClasses() {
    return classPathApps.values();
  }

  private void disableApplication(KelpApplication application) {
    if (!enabledApps.containsKey(application.getInformation().getApplicationName())) {
      return;
    }

    getEnabledApps().stream().filter(kelpApplication -> !kelpApplication.equals(application)
      && kelpApplication.getInformation().getHardDependencies().contains(application.getInformation()
      .getApplicationName())).forEach(this::disableApplication);

    KelpLogger.of(KelpApplication.class).info("[APP] Disabling application " + application.getInformation().getApplicationName());

    try {
      application.onDisable();
    } catch (Exception e) {
      KelpLogger.of(KelpApplication.class).severe("[APP] Error while disabling " + application.getInformation().getApplicationName());
      e.printStackTrace();
    }

    enabledApps.remove(application.getInformation().getApplicationName());
  }

  public void disableApplications() {
    KelpLogger.of(KelpApplication.class).info("[APP] Disabling all KelpApplications...");
    getEnabledApps().forEach(this::disableApplication);
    KelpLogger.of(KelpApplication.class).info("[APP] All KelpApplications have been successfully disabled.");
  }

  public Collection<KelpApplication> getEnabledApps() {
    return Lists.newArrayList(this.enabledApps.values());
  }

  /**
   * Takes an {@code ArrayMemberValue} and converts
   * it to a set which contains all its elements.
   *
   * @param depend The input array
   * @return A {@code Set} containing all elements of the given array.
   */
  private Set<String> newHashSet(ArrayMemberValue depend) {
    MemberValue[] values = depend.getValue();
    String[] depends = new String[values.length];

    for (int i = 0; i < values.length; i++) {
      depends[i] = ((StringMemberValue) values[i]).getValue();
    }

    return newHashSet(depends);
  }

  /**
   * Takes an array of strings and converts it
   * to a {@code Set} of all elements of the array.
   *
   * @param depends The string array you want to convert.
   * @return The final {@code Set}.
   */
  private Set<String> newHashSet(String[] depends) {
    Set<String> set = new HashSet<>();
    Collections.addAll(set, depends);
    return set;
  }

  public BiMap<KelpApplicationMeta, Class<? extends KelpApplication>> getClassPathApps() {
    return classPathApps;
  }

  /**
   * This class is a temporary application object
   * which holds basic information like the {@code jar}
   * file and the raw file.
   *
   * @author pxav
   */
  protected static class TemporaryApplicationInfo {
    private final File file;
    private final JarFile jarFile;

    TemporaryApplicationInfo(File file, JarFile jarFile) {
      this.file = file;
      this.jarFile = jarFile;
    }

    File getFile() {
      return file;
    }

    JarFile getJarFile() {
      return jarFile;
    }
  }

}
