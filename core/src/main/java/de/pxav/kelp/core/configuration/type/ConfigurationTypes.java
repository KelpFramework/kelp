package de.pxav.kelp.core.configuration.type;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public enum ConfigurationTypes {

  JSON(PropertiesConfigurationType.class);

  private Class<? extends ConfigurationType> implementation;

  ConfigurationTypes(Class<? extends ConfigurationType> implementation) {
    this.implementation = implementation;
  }

  public Class<? extends ConfigurationType> type() {
    return implementation;
  }

}
