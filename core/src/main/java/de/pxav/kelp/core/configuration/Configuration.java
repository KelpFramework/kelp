package de.pxav.kelp.core.configuration;

import de.pxav.kelp.core.configuration.type.ConfigurationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation is used to mark a class which
 * should be a configuration class. It's used to pass
 * essential data like the name and path of your file.
 *
 * Furthermore you can select the storage type of your configuration
 * with this annotation. This means the file format (like Yaml,
 * Java Properties, etc.).
 *
 * To make a class a configuration class you have to inherit
 * from {@code KelpConfiguration} as well.
 *
 * @author pxav
 * @see KelpConfiguration
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {

  /**
   * @return The path of the file in which the configuration should be saved.
   */
  String path();

  /**
   * @return The name of the file in which the configuration should be saved.
   */
  String name();

  /**
   * This property defines which method you want to use to
   * save your configuration file. You can pass any class
   * that implements {@code ConfigurationType} here.
   *
   * @return The method/type in which you want to save your configuration.
   * @see ConfigurationType
   */
  Class<? extends ConfigurationType> type();

}
