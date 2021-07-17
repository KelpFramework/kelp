package de.pxav.kelp.core.configuration.internal;

import de.pxav.kelp.core.configuration.validate.IntegerValidator;
import de.pxav.kelp.core.configuration.validate.ValidatableConfig;

import javax.inject.Singleton;
import java.io.File;

@Singleton
public class KelpDefaultConfiguration extends ValidatableConfig {

  /**
   * @return Final destination of the config file
   */
  @Override
  protected File configFile() {
    return new File("plugins//Kelp", "kelp-config.yml");
  }

  /**
   * @return The location of the config blueprint file
   *         (including documentation and default values)
   */
  @Override
  protected String resourceTemplatePath() {
    return "core/kelp-config.yml";
  }

  /**
   * Register all validators that have to pass before the
   * config can be properly used. Checked on every enable.
   */
  @Override
  public void setValidators() {

    // tick rate must be positive and low enough
    // to avoid stuttering animations
    addValidator(KelpConfigValue.NPC_TICK_RATE,
      IntegerValidator.nonNegative(),
      IntegerValidator.failIfGreaterThan(1000));

    // there are only 3 options, so fail if any index
    // out of that range is chosen
    addValidator(KelpConfigValue.CONFIG_PATCH_MODE,
      IntegerValidator.nonNegative(),
      IntegerValidator.failIfGreaterThan(2));

  }

}
