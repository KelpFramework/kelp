package de.pxav.kelp.core.application.inject;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.inject.AbstractModule;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.application.KelpApplicationRepository;
import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.version.KelpVersion;
import de.pxav.kelp.core.version.VersionImplementation;
import de.pxav.kelp.core.version.Versioned;
import io.github.classgraph.*;
import javassist.bytecode.AnnotationsAttribute;
import javassist.bytecode.ClassFile;
import javassist.bytecode.annotation.*;
import org.bukkit.Bukkit;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
import java.util.function.Consumer;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * This class is used to build the Guice module,
 * which binds the
 *
 * @author pxav
 */
public final class VersionBinderModule extends AbstractModule {

  private static Class<? extends KelpApplication> mainClass;

  // the plugin instance under which the plugin is running
  private final KelpPlugin plugin;

  // the folder containing the jar files with version
  // implementations.
  private final File folder;

  public VersionBinderModule(KelpPlugin plugin, File folder) {
    this.plugin = plugin;
    this.folder = folder;
  }

  /**
   * This method configures the Guice module
   * by binding the template classes to their implementation classes.
   */
  @Override
  protected void configure() {
    KelpVersion version = KelpVersion.withBukkitVersion(this.plugin.getServer().getBukkitVersion());
    Collection<Class> templateClasses = this.detectVersionTemplates("de.pxav.kelp.core");

    // create the folder if it does not exist
    if (!this.folder.exists()) {
      folder.mkdirs();
    }

    File[] files = folder.listFiles();
    Preconditions.checkNotNull(files);
    if (files.length <= 0) return;

    List<Class<?>> implementationClasses = Lists.newArrayList();

    // iterate all jar files in the given directory
    // and add the needed classes to the final collection (implementationClasses)
    Arrays.stream(files)
            .filter(file -> file.isFile() && file.getName().endsWith(".jar"))
            .forEach(current -> {
              try {
                Collection<Class<?>> implementations = implementationsOf(current.getPath(), version, (newMainClass) -> {
                  mainClass = newMainClass;
                });
                if (!implementations.isEmpty()) {
                  implementationClasses.addAll(implementations);
                }
              } catch (IOException
                      | ClassNotFoundException
                      | NoSuchMethodException
                      | IllegalAccessException
                      | InvocationTargetException e) {
                e.printStackTrace();
              }
            });

    // iterate all implementation classes and check to
    // which template they belong.
    for (Class<?> current : implementationClasses) {
      for (Class templateClass : templateClasses) {
        if (current.getSuperclass().getName().equalsIgnoreCase(templateClass.getName())) {

          // finally bind the implementation to the corresponding template
          // to the guice injector.
          Class g = current.asSubclass(templateClass);
          bind(templateClass).to(g);

        }
      }
    }
  }

  /**
   * This method searches all classes in the given packages
   * which are annotated with {@code KelpVersionTemplate}.
   *
   * These classes are considered to be version templates for
   * kelp, because they have abstract methods which do version-
   * specific stuff like packet management.
   *
   * To increase maintainability you don't have to always
   * register your newly created template classes here to bind
   * them properly to the Guice injector, but you just have
   * to annotate them and Kelp will automatically bind the
   * implementation class to it.
   *
   * @return A collection
   * @see KelpVersionTemplate
   */
  private Collection<Class> detectVersionTemplates(String... packages) {
    KelpVersion serverVersion = KelpVersion.withBukkitVersion(Bukkit.getBukkitVersion());
    Collection<Class> output = Lists.newArrayList();

    try (ScanResult scanResult =
                 new ClassGraph()
                         .enableAnnotationInfo()
                         .whitelistPackages(packages)
                         .scan()) {

      ClassInfoList allClasses = scanResult.getAllClasses();

      for (ClassInfo current : allClasses) {
        AnnotationInfoList annotations = current.getAnnotationInfo();
        for (AnnotationInfo annotationInfo : annotations) {
          if (annotationInfo.getName().equalsIgnoreCase(KelpVersionTemplate.class.getName())) {
            output.add(current.loadClass());
          }

          // next to normal version templates, search for potion templates that - unlike general
          // version templates - don't have to be implemented in every server version. On an 1.8
          // server, the Levitation effect has to be implemented, while on an 1.14 server not.
          if (annotationInfo.getName().equalsIgnoreCase(MinecraftPotion.class.getName())) {

            // the annotation only has a single parameter: The version since when it existed.
            for (AnnotationParameterValue parameter : annotationInfo.getParameterValues()) {
              // fetch the version since when the potion exists in the game
              AnnotationEnumValue enumValue = (AnnotationEnumValue) parameter.getValue();
              KelpVersion potionVersion = KelpVersion.valueOf(enumValue.getValueName());

              // check if the current server version is lower than the version since when the
              // potion existed, because in that case it has to be implemented and therefore
              // be added to the template list.
              if (potionVersion.isHigherThan(serverVersion)) {
                output.add(current.loadClass());
              }
            }
          }

        }
      }

    }
    return output;
  }

  /**
   * This method opens the {@code .jar} file at the given path and fetches
   * the classes which represent an implementation of a version template.
   *
   * Furthermore this method adds the classes to the classpath of this
   * application so that they can be used in the code properly for
   * for example getting superclasses, casting, ...
   * But this only happens when the classes match the required version.
   *
   * @param path      The path of the {@code .jar} file
   *                  you want to get the implementation classes of.
   * @param required  The required version of the implementations. Inside the method
   *                  there is a check which compares your required version with the
   *                  one of the implementation. If the versions match, you will get
   *                  the desired classes, otherwise it will return an empty list.
   * @return          If your desired version matches the version provided by the file,
   *                  a collection of all the classes which are an implementation of
   *                  a version template.
   *                  If the versions don't match you will get back an empty list.
   * @see KelpVersion
   */
  private Collection<Class<?>> implementationsOf(String path,
                                                 KelpVersion required,
                                                 Consumer<Class<? extends KelpApplication>> mainClassConsumer)
    throws IOException,
          ClassNotFoundException,
          NoSuchMethodException,
          InvocationTargetException,
          IllegalAccessException {
    Collection<Class<?>> output = Lists.newArrayList();
    JarFile jarFile = new JarFile(path);
    Enumeration<JarEntry> jarEntries = jarFile.entries();

    URL[] urls = { new URL("jar:file:" + path + "!/") };

    // build up the class loader based on the new jar file and the current one.
    // Including the current classloader is important, because it ensures that there
    // will be no errors loading classes depending on classes from the core module.
    URLClassLoader classLoader = URLClassLoader.newInstance(urls, this.getClass().getClassLoader());

    // iterate all jar entries
    while (jarEntries.hasMoreElements()) {
      JarEntry jarEntry = jarEntries.nextElement();
      if(jarEntry == null
              || jarEntry.isDirectory()
              || !jarEntry.getName().endsWith(".class")) {
        continue;
      }

      ClassFile classFile =
              new ClassFile(new DataInputStream(jarFile.getInputStream(jarEntry)));

      AnnotationsAttribute annotationsAttribute = (AnnotationsAttribute) classFile.getAttribute(AnnotationsAttribute.visibleTag);

      // if the class does not have any annotations, it is no class handling any
      // version-specific stuff, so we can continue.
      if (annotationsAttribute == null || annotationsAttribute.getAnnotations() == null) {
        continue;
      }

      // classes with this annotation are main classes of a version implementation module.
      // So they do not handle version-specific stuff directly.
      Annotation mainClassAnnotation = Arrays.stream(annotationsAttribute.getAnnotations())
              .filter(annotation -> annotation.getTypeName().equals(VersionImplementation.class.getName()))
              .findFirst()
              .orElse(null);

      // classes with this annotations are classes extending a version template,
      // so they directly handle version-specific stuff.
      Annotation versionedAnnotation = Arrays.stream(annotationsAttribute.getAnnotations())
              .filter(annotation -> annotation.getTypeName().equals(Versioned.class.getName()))
              .findFirst()
              .orElse(null);

      // if the class handles version-specific stuff, it has to be loaded
      // to the main class path.
      if (versionedAnnotation != null) {

        int ignoreClass = ".class".length();
        String className = jarEntry.getName().substring(0, jarEntry.getName().length() - ignoreClass);
        className = className.replace('/', '.');

        Class implementationClass = classLoader.loadClass(className);

        Class<?> outputClass = this.loadToClassPath(path, implementationClass);
        output.add(outputClass);
        continue;
      }

      // check if the current implementation module matches the version
      // requirements, otherwise stop the process.
      if (mainClassAnnotation != null && classFile.getSuperclass().equalsIgnoreCase(KelpApplication.class.getName())) {
        ArrayMemberValue versionArray = (ArrayMemberValue) mainClassAnnotation.getMemberValue("value");
        MemberValue[] memberValues = versionArray.getValue();

        Collection<KelpVersion> versions = Lists.newArrayList();
        for (MemberValue memberValue : memberValues) {
          EnumMemberValue enumMemberValue = (EnumMemberValue) memberValue;
          versions.add(KelpVersion.valueOf(enumMemberValue.getValue()));
        }

        if (!versions.contains(required)) {
          return Lists.newArrayList();
        }

        int ignoreClass = ".class".length();
        String className = jarEntry.getName().substring(0, jarEntry.getName().length() - ignoreClass);
        className = className.replace('/', '.');
        Class mainClass = classLoader.loadClass(className);
        this.loadToClassPath(path, mainClass);
        mainClassConsumer.accept(mainClass);

      }

    }
    return output;
  }

  /**
   * Loads the given class into the classpath of
   * the current application in order to work with it
   * later in the project.
   *
   * @param path    The path of the JarFile which contains the given class.
   * @param toLoad  The class you want to load to the classpath.
   * @throws NoSuchMethodException This exception is thrown when a given method does not exist.
   */
  private Class<?> loadToClassPath(String path, Class toLoad) throws NoSuchMethodException,
          MalformedURLException,
          InvocationTargetException,
          IllegalAccessException,
          ClassNotFoundException {
    URLClassLoader loader = (URLClassLoader) KelpApplicationRepository.class.getClassLoader();
    Method addURL = URLClassLoader.class.getDeclaredMethod("addURL", URL.class);
    addURL.setAccessible(true);
    addURL.invoke(loader, new File(path).toURI().toURL());
    return loader.loadClass(toLoad.getName());
  }

  public static Class<? extends KelpApplication> getMainClass() {
    return mainClass;
  }

}
