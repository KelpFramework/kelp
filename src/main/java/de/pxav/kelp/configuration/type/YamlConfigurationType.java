package de.pxav.kelp.configuration.type;

import de.pxav.kelp.configuration.ConfigurationAttribute;
import de.pxav.kelp.configuration.KelpConfiguration;

import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class YamlConfigurationType implements ConfigurationType {


  @Override
  public String fileExtension() {
    return null;
  }

  @Override
  public void saveAttributes(Collection<ConfigurationAttribute> attributes, Class<? extends KelpConfiguration> configurationClass) {

  }

  @Override
  public Collection<ConfigurationAttribute> loadAttributes(Class<? extends KelpConfiguration> configurationClass) {
    return null;
  }
}
