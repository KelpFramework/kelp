package de.pxav.kelp.implementation1_8;

import com.google.inject.Singleton;
import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.application.NewKelpApplication;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.sidebar.CreateSidebar;
import de.pxav.kelp.core.sidebar.type.SimpleSidebar;
import de.pxav.kelp.core.version.KelpVersion;
import de.pxav.kelp.core.version.VersionImplementation;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
@VersionImplementation(value = {
  KelpVersion.MC_1_8_0,
  KelpVersion.MC_1_8_3,
  KelpVersion.MC_1_8_4,
  KelpVersion.MC_1_8_5,
  KelpVersion.MC_1_8_6,
  KelpVersion.MC_1_8_7,
  KelpVersion.MC_1_8_8
}, authors = {
  "pxav",
  "Etrayed"
})
public class KelpVersionImplementation extends KelpApplication {

  @Override
  public void onEnable() {
    getInstance(KelpLogger.class).log("Enabling v1.8 version module...");
  }

  @Override
  public void onDisable() {

  }

}
