package de.pxav.kelp.configuration.internal;

import com.google.inject.Singleton;
import de.pxav.kelp.configuration.Configuration;
import de.pxav.kelp.configuration.ConfigurationAttribute;
import de.pxav.kelp.configuration.KelpConfiguration;
import de.pxav.kelp.configuration.type.PropertiesConfigurationType;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
@Configuration(name = "config",
        path = "plugins//Kelp",
        type = PropertiesConfigurationType.class
)
public final class KelpDefaultConfiguration extends KelpConfiguration {

  @Override
  public void defineDefaults() {
    defaultValues.add(new ConfigurationAttribute(developmentMode(), false));
  }

  public String developmentMode() {
    return "development-mode";
  }

}
