package de.pxav.kelp.core.configuration;

import de.pxav.kelp.core.configuration.validate.ConfigValidator;

public interface ValidatableValue extends ConfigurationValue {

  static ValidatableValue from(ConfigurationValue configurationValue, ConfigValidator... validators) {
    return new ValidatableValue() {
      @Override
      public ConfigValidator[] validators() {
        return validators;
      }

      @Override
      public Object value() {
        return configurationValue.value();
      }
    };
  }

  ConfigValidator[] validators();

}
