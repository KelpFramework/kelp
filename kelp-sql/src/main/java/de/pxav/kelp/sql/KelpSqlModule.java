package de.pxav.kelp.sql;

import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.application.NewKelpApplication;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@NewKelpApplication(applicationName = "KelpSQL",
  version = "0.0.5",
  authors = "pxav",
  description = "Adds basic support for sql databases and hibernate integration")
public class KelpSqlModule extends KelpApplication {

  @Override
  public void onEnable() {
    super.onEnable();
  }

  @Override
  public void onDisable() {
    super.onDisable();
  }

}
