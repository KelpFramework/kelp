package de.pxav.kelp.core.configuration.internal;

import com.google.inject.Singleton;
import de.pxav.kelp.core.configuration.Configuration;
import de.pxav.kelp.core.configuration.ConfigurationAttribute;
import de.pxav.kelp.core.configuration.KelpConfiguration;
import de.pxav.kelp.core.configuration.type.PropertiesConfigurationType;

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
