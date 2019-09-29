package de.pxav.kelp.configuration.type;

import de.pxav.kelp.configuration.ConfigurationAttribute;
import de.pxav.kelp.configuration.KelpConfiguration;

import java.util.Collection;

/**
 * This interface is the template for every
 * new configuration type you want to create.
 *
 * A configuration type is the method how a configuration is stored.
 * This can be for example .properties files, .yml files, ...
 *
 * Each format has a different way of how to store data so
 * you need an own implementation for each new
 * file format.
 *
 * @author pxav
 */
public interface ConfigurationType {

  /**
   * The appendix of a file name like '.json', '.properties', ...
   * @return The corresponding file extension.
   */
  String fileExtension();

  /**
   * This method stores all attributes given in the collection
   * into the file which corresponds to the configuration.
   *
   * @param attributes The attributes you want to save.
   * @param configurationClass The class of the configuration
   *                          (has to inherit from {@code KelpConfiguration})
   * @see KelpConfiguration
   */
  void saveAttributes(Collection<ConfigurationAttribute> attributes,
                      Class<? extends KelpConfiguration> configurationClass);

  /**
   * Reads the file and loads all entries into the configuration's cache
   * which is located in the corresponding class.
   *
   * @param configurationClass The class of your configuration.
   * @return A collection of the attributes that have been loaded.
   */
  Collection<ConfigurationAttribute> loadAttributes(Class<? extends KelpConfiguration> configurationClass);

}
