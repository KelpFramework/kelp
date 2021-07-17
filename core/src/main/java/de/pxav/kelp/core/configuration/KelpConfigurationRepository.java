package de.pxav.kelp.core.configuration;

import com.google.inject.Injector;
import de.pxav.kelp.core.configuration.validate.ConfigValidationException;
import de.pxav.kelp.core.configuration.validate.ValidatableConfig;
import org.bukkit.plugin.java.JavaPlugin;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class KelpConfigurationRepository {

  private JavaPlugin kelpPlugin;
  private Injector injector;

  @Inject
  public KelpConfigurationRepository(JavaPlugin kelpPlugin, Injector injector) {
    this.kelpPlugin = kelpPlugin;
    this.injector = injector;
  }

  public void registerConfig(Class<? extends KelpConfiguration> configurationClass) {
    KelpConfiguration configInstance = injector.getInstance(configurationClass);

    configInstance.load();

    if (configurationClass.getSuperclass().equals(ValidatableConfig.class)) {
      ValidatableConfig validatableConfig = (ValidatableConfig) configInstance;
      try {
        validatableConfig.setValidators();
        validatableConfig.validate();
      } catch (ConfigValidationException e) {
        e.logValidationError();
      }
    }
  }

}
