package de.pxav.kelp.core.configuration;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import de.pxav.kelp.core.reflect.TypeCriterion;
import de.pxav.kelp.core.reflect.TypeFinder;
import de.pxav.kelp.core.configuration.type.ConfigurationType;

import java.io.File;

/**
 * This repository class allows you to load
 * and manage all configurations created by plugins
 * or the Kelp framework itself.
 *
 * @author pxav
 */
@Singleton
public class ConfigurationRepository {

  private Injector injector;
  private TypeFinder typeFinder;

  @Inject
  public ConfigurationRepository(Injector injector, TypeFinder typeFinder) {
    this.injector = injector;
    this.typeFinder = typeFinder;
  }

  /**
   * @param configurationClass The class of your configuration
   *                           which extends from {@code KelpConfiguration}
   * @return The file in which the requested configuration will be saved in.
   */
  public File fileOf(Class<? extends KelpConfiguration> configurationClass) {
    Configuration annotation = configurationClass.getAnnotation(Configuration.class);
    return new File(
            annotation.path(),
            annotation.name() + injector.getInstance(annotation.type()).fileExtension()
    );
  }

  /**
   * Iterates all classes in the given packages
   * and checks if these are extending from {@code KelpConfiguration}
   * and annotated with the {@code Configuration} annotation.
   * If this is {@code true} it will load the files for this configuration
   * and copy the data into the cache so that it becomes available for use.
   *
   * @param packages The packages in which the method should search for.
   *                 If you pass a package all child-packages will be
   *                 iterated automatically as well.
   * @see KelpConfiguration
   * @see Configuration
   */
  public void loadAll(String... packages) {
    System.out.println("loading config files....");
    this.typeFinder
            .filter(
                    packages,
                    TypeCriterion.subclassOf(KelpConfiguration.class),
                    TypeCriterion.annotatedWith(Configuration.class))
            .forEach(
                    configurationClass -> {
                      System.out.println("loading config file " + configurationClass);
                      Configuration annotation = configurationClass.getAnnotation(Configuration.class);
                      ConfigurationType type = injector.getInstance(annotation.type());
                      KelpConfiguration config = (KelpConfiguration) injector.getInstance(configurationClass);

                      type.loadAttributes(config.getClass());
                      System.out.println(
                              "Successfully loaded configuration "
                                      + annotation.name()
                                      + type.fileExtension());
                    });
  }

}
