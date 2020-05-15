package de.pxav.kelp.core.application;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.inject.Injector;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import de.pxav.kelp.core.common.KelpFileUtils;
import de.pxav.kelp.core.listener.EventRegistration;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.annotation.Annotation;
import javassist.bytecode.annotation.ArrayMemberValue;
import javassist.bytecode.annotation.MemberValue;
import javassist.bytecode.annotation.StringMemberValue;
import org.bukkit.entity.Skeleton;

/**
 * This repository class is used to load and manage
 * the different kelp applications.
 *
 * @author pxav
 */
@Singleton
public final class KelpApplicationRepository {

  private final Map<String, KelpInformation> pluginsToLoad = Maps.newHashMap();
  private final Map<String, KelpApplication> pluginsToEnable = Maps.newHashMap();
  private final Map<String, KelpApplication> enabledPlugins = Maps.newHashMap();
  private final Map<String, KelpInformation> pluginInfo = Maps.newHashMap();
  private final Map<KelpInformation, Class<? extends KelpApplication>>
          pluginsToEnableAfterLoadInClassPath = Maps.newHashMap();

  private final Injector injector;
  private final KelpFileUtils kelpFileUtils;

  @Inject
  public KelpApplicationRepository(Injector injector, KelpFileUtils kelpFileUtils) {
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
  public KelpApplicationRepository detectGamePlugins(File kelpApplicationDirectory) {
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
                        System.out.println("Couldn't load module from file " + file.getName());
                        return null;
                      }
                    })
            .filter(Objects::nonNull)
            .forEach(
                    moduleInfo -> {
                      KelpInformation kelpInformation = checkPlugin(moduleInfo.getJarFile());
                      pluginInfo.put(kelpInformation.getApplicationName(), kelpInformation);
                      if (kelpInformation == null) {
                        System.out.println("Can't load module from file " + moduleInfo.getFile().getName());
                        return;
                      }

                      if (!enabledPlugins.containsKey(kelpInformation.getApplicationName())) {
                        kelpInformation.file(moduleInfo.getFile());
                        pluginsToLoad.put(kelpInformation.getApplicationName(), kelpInformation);
                      }
                    });
    return this;
  }

  /**
   *
   * @return
   */
  public KelpApplicationRepository load() {
    Map<KelpInformation, Boolean> moduleStatuses2 = Maps.newHashMap();
    for (Map.Entry<String, KelpInformation> entry : pluginsToLoad.entrySet()) {
      KelpInformation module = entry.getValue();

      if (!loadPluginsInClassPath(moduleStatuses2, new Stack<>(), module)) {
        System.out.println("Failed to enable " + entry.getKey());
        continue;
      }

      System.out.println("Successfully loaded " + entry.getKey());
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
   * @see KelpInformation
   */
  private KelpInformation checkPlugin(JarFile jarFile) {
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

          String descriptionName =
                  ((StringMemberValue) descriptionAnnotation.getMemberValue("applicationName")).getValue();
          StringMemberValue descriptionVersion =
                  ((StringMemberValue) descriptionAnnotation.getMemberValue("version"));
          ArrayMemberValue descriptionDepends =
                  ((ArrayMemberValue) descriptionAnnotation.getMemberValue("hardDependencies"));
          ArrayMemberValue descriptionSoftDepends =
                  ((ArrayMemberValue) descriptionAnnotation.getMemberValue("softDependencies"));

          KelpInformation kelpInformation = new KelpInformation();
          kelpInformation
                  .applicationName(descriptionName)
                  .version(descriptionVersion == null ? "1.0.0" : descriptionVersion.getValue())
                  .hardDependencies(
                          (descriptionDepends == null ? Sets.newHashSet() : newHashSet(descriptionDepends)))
                  .softDependencies(
                          (descriptionSoftDepends == null
                                  ? Sets.newHashSet()
                                  : newHashSet(descriptionSoftDepends)));
          kelpInformation.main(classFile.getName());
          return kelpInformation;
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
   * @param dependStack
   * @param module
   * @return
   */
  private boolean loadPluginsInClassPath(
          Map<KelpInformation, Boolean> moduleStatuses,
          Stack<KelpInformation> dependStack,
          KelpInformation module) {
    if (moduleStatuses.containsKey(module)) return moduleStatuses.get(module);

    Set<String> dependencies = Sets.newHashSet();
    dependencies.addAll(module.getHardDependencies());
    dependencies.addAll(module.getSoftDependencies());

    boolean status = true;

    for (String dependName : dependencies) {
      KelpInformation depend =
              pluginsToLoad.containsKey(dependName)
                      ? pluginsToLoad.get(dependName)
                      : (enabledPlugins.containsKey(dependName))
                      ? enabledPlugins.get(dependName).getInformation()
                      : null;
      Boolean dependStatus = (depend != null) ? moduleStatuses.get(depend) : Boolean.FALSE;

      if (dependStatus == null) {
        if (dependStack.contains(depend)) {
          StringBuilder dependencyGraph = new StringBuilder();

          for (KelpInformation element : dependStack) {
            dependencyGraph.append(element.getApplicationName()).append(" -> ");
          }

          dependencyGraph.append(module.getApplicationName()).append(" -> ").append(dependName);
          System.out.println("Circular dependency detected: " + dependencyGraph);
          status = false;
        } else {
          dependStack.push(module);
          dependStatus = this.loadPluginsInClassPath(moduleStatuses, dependStack, depend);
          dependStack.pop();
        }
      }

      if (Boolean.FALSE.equals(dependStatus) && !module.getSoftDependencies().contains(dependName)) {
        System.out.println(dependName + " (required by " + module.getApplicationName() + ") is unavailable");
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
        this.pluginsToEnableAfterLoadInClassPath.put(module, main);
      } catch (Throwable t) {
        System.out.println("Can't load module " + module.getApplicationName() + ":");
        t.printStackTrace();
      }
    }

    moduleStatuses.put(module, status);
    return status;
  }

  public void enableAfterClassPathLoad() {
    this.pluginsToEnableAfterLoadInClassPath.forEach(
            (description, clazz) -> {
              KelpApplication moduleInstance = injector.getInstance(clazz);
              moduleInstance.init(description, injector);

              pluginsToEnable.put(description.getApplicationName(), moduleInstance);
              System.out.println(
                      "Loading gameplugin "
                              + description.getApplicationName()
                              + " with version "
                              + description.getVersion());
              moduleInstance.onLoad();
    });
  }

  public void enablePlugins() {
    for (Map.Entry<String, KelpApplication> namePluginEntry : pluginsToEnable.entrySet()) {
      try {
        System.out.println(
                "Enabling gameplugin "
                        + namePluginEntry.getValue().getInformation().getApplicationName()
                        + " with version "
                        + namePluginEntry.getValue().getInformation().getVersion());
        injector
                .getInstance(EventRegistration.class)
                .initialize(namePluginEntry.getValue().getClass().getPackage().getName());
        namePluginEntry.getValue().onEnable();

        enabledPlugins.put(namePluginEntry.getKey(), namePluginEntry.getValue());
      } catch (Throwable t) {
        System.out.println(
                "Exception encountered when loading gameplugin "
                        + namePluginEntry.getValue().getInformation().getApplicationName()
                        + ":");
        t.printStackTrace();
        disablePlugin(namePluginEntry.getValue());
      }
    }

    pluginsToEnable.clear();
  }

  public void enable() {
    enableAfterClassPathLoad();
    pluginsToLoad.clear();
  }

  /**
   * @return  A collection of all main classes of all kelp applications
   *          as {@code Class<? extends KelpApplication>}
   * @see     KelpApplication
   */
  public Collection<Class<? extends KelpApplication>> getAllPluginClasses() {
    return pluginsToEnableAfterLoadInClassPath.values();
  }

  private void disablePlugin(KelpApplication application) {
    if (enabledPlugins.containsKey(application.getInformation().getApplicationName())) return;

    Lists.newArrayList(enabledPlugins.values())
            .forEach(
                    currentApplication -> {
                      if (!currentApplication.equals(application)
                              && currentApplication
                              .getInformation()
                              .getHardDependencies()
                              .contains(application.getInformation().getApplicationName())) {
                        disablePlugin(currentApplication);
                      }

                      try {
                        application.onDisable();
                      } catch (Exception e) {
                        System.out.println("Error while disabling " + application.getInformation().getApplicationName());
                        e.printStackTrace();
                      }

                      enabledPlugins.remove(application.getInformation().getApplicationName());
                    });

    for (KelpApplication currentApplication : Lists.newArrayList(enabledPlugins.values())) {
      if (!currentApplication.equals(application)
              && currentApplication.getInformation().getHardDependencies().contains(application.getInformation().getApplicationName())) {
        disablePlugin(currentApplication);
      }
    }

    System.out.println("Disabling module " + application.getInformation().getApplicationName());

    try {
      application.onDisable();
    } catch (Exception e) {
      System.out.println("Error while disabling " + application.getInformation().getApplicationName());
      e.printStackTrace();
    }

    enabledPlugins.remove(application.getInformation().getApplicationName());
  }

  public void disablePlugins() {
    enabledPlugins.values().forEach(this::disablePlugin);
  }

  public Collection<KelpApplication> getEnabledPlugins() {
    return Lists.newArrayList(this.enabledPlugins.values());
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
