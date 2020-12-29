package de.pxav.kelp.implementation1_8;

import com.google.inject.Singleton;
import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.event.listener.EventHandlerRegistration;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.version.KelpVersion;
import de.pxav.kelp.core.version.VersionImplementation;
import de.pxav.kelp.implementation1_8.packet.GlobalPacketListener;
import de.pxav.kelp.implementation1_8.player.BossBarLocationUpdater;
import de.pxav.kelp.implementation1_8.player.PlayerCreationListener;
import org.bukkit.Bukkit;

/**
 * This is the main class of the 1.8 implementation module
 * for kelp.
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

    // registers all event handlers, which are relevant for handling prompts or boss bars, etc.
    getInstance(EventHandlerRegistration.class).initialize("de.pxav.kelp.implementation1_8");

    // initialize default settings and KelpPlayer instances for all players currently online
    // this is relevant for server reloads.
    getInstance(PlayerCreationListener.class).createOnStartup();
  }

  @Override
  public void onDisable() {
    // Disable packet listeners to avoid listener overflow.
    Bukkit.getOnlinePlayers().forEach(current -> {
      getInstance(BossBarLocationUpdater.class).remove(current.getUniqueId());
      getInstance(GlobalPacketListener.class).removePacketListener(current);
    });
  }

}
