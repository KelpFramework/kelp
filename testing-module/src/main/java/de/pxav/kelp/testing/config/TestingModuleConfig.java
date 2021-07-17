package de.pxav.kelp.testing.config;

import de.pxav.kelp.core.configuration.KelpConfiguration;
import de.pxav.kelp.core.configuration.validate.IntegerValidator;
import de.pxav.kelp.core.configuration.validate.ValidatableConfig;

import java.io.File;

public class TestingModuleConfig extends ValidatableConfig {

  @Override
  protected File configFile() {
    return new File("kelp_plugins//TestingModule", "config.yml");
  }

  @Override
  protected String resourceTemplatePath() {
    return "testing_module/config.yml";
  }

  @Override
  public void setValidators() {
    addValidator("attr", IntegerValidator.nonNegative());
  }

}
