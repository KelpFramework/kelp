package de.pxav.kelp.implementation1_12;

import com.google.inject.Singleton;
import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.event.listener.EventHandlerRegistration;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.version.KelpVersion;
import de.pxav.kelp.core.version.VersionImplementation;
import de.pxav.kelp.implementation1_12.packet.GlobalPacketListener;
import de.pxav.kelp.implementation1_12.player.PlayerCreationListener;
import org.bukkit.Bukkit;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
@VersionImplementation(value = {
  KelpVersion.MC_1_12_0,
  KelpVersion.MC_1_12_1,
  KelpVersion.MC_1_12_2
}, authors = {
  "pxav",
  "Etrayed",
  "DSeeLPYT"
})
public class KelpVersionImplementation extends KelpApplication {

  @Override
  public void onEnable() {
    getInstance(KelpLogger.class).log("Enabling v1.12 version module...");
    getInstance(EventHandlerRegistration.class).initialize("de.pxav.kelp.implementation1_12");
    getInstance(PlayerCreationListener.class).createOnStartup();
  }

  @Override
  public void onDisable() {
    Bukkit.getOnlinePlayers().forEach(current -> {
      getInstance(GlobalPacketListener.class).removePacketListener(current);
    });
  }

}
