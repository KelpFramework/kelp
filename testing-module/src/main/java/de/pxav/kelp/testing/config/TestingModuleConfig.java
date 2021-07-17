package de.pxav.kelp.testing.config;

import de.pxav.kelp.core.configuration.validate.IntegerValidator;
import de.pxav.kelp.core.configuration.validate.ValidatableConfig;

import javax.inject.Singleton;
import java.io.File;

@Singleton
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
    System.out.println("setting validators...");
    addValidator("attr", IntegerValidator.nonNegative());
  }

}
