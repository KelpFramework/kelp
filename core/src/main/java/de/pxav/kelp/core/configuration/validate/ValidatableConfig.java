package de.pxav.kelp.core.configuration.validate;

import de.pxav.kelp.core.configuration.ConfigurationValue;
import de.pxav.kelp.core.configuration.KelpConfiguration;
import de.pxav.kelp.core.configuration.ValidatableValue;

import java.util.Map;

public abstract class ValidatableConfig extends KelpConfiguration {

  public abstract void setValidators();

  protected void addValidator(String key, IntegerValidator... integerValidators) {
    ConfigurationValue configurationValue = configValues.get(key);
    ValidatableValue validatableValue = ValidatableValue.from(configurationValue, integerValidators);

    configValues.put(key, validatableValue);
  }

  public void validate() throws ConfigValidationException {
    for (Map.Entry<String, ConfigurationValue> entry : configValues.entrySet()) {
      String key = entry.getKey();
      ConfigurationValue configValue = entry.getValue();

      if (!(configValue instanceof ValidatableValue)) {
        return;
      }

      ValidatableValue validatableValue = (ValidatableValue) configValue;
      for (ConfigValidator validator : validatableValue.validators()) {
        String result = validator.validate(configValue.value());
        if (result != null) {
          throw new ConfigValidationException(key, result);
        }
      }

    }
  }

}
