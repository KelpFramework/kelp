package de.pxav.kelp.testing;

import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.application.NewKelpApplication;
import de.pxav.kelp.core.logger.KelpLogger;

/**
 * This represents the main class for the testing application.
 * This allows kelp developers to test their applications and
 * include some example code snippets.
 *
 * @author pxav
 */
@NewKelpApplication(
        applicationName = "KelpTesting",
        version = "0.0.1",
        authors = {"pxav"},
        description = "Allows you to extensively test the kelp features"
)
public class KelpTesting extends KelpApplication {

  @Override
  public void onLoad() {
    getInstance(KelpLogger.class).log("Loading test application...");
  }

  @Override
  public void onEnable() {

  }

  @Override
  public void onDisable() {
    super.onDisable();
  }
}
