package de.pxav.kelp.implementation1_8.player;

import com.google.inject.Inject;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.event.kelpevent.KelpPlayerLoginEvent;
import de.pxav.kelp.core.event.kelpevent.KelpPlayerUpdateSettingsEvent;
import de.pxav.kelp.core.event.kelpevent.SettingsUpdateStage;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.particle.version.ParticleVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import de.pxav.kelp.core.player.PlayerChatVisibility;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.core.sound.SoundRepository;
import de.pxav.kelp.implementation1_8.packet.GlobalPacketListener;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.NoSuchElementException;

/**
 * This class is responsible for keeping player instances over
 * the server lifetime. When the kelp plugin instance is disabled
 * (by a reload for example), then {@link #createOnStartup()} can be
 * called to restore all player instances and continue working safely
 * with them.
 *
 * @author pxav
 */
public class PlayerCreationListener {

  private KelpPlayerRepository kelpPlayerRepository;
  private GlobalPacketListener globalPacketListener;
  private EntityTypeVersionTemplate entityTypeVersionTemplate;
  private SoundRepository soundRepository;
  private BossBarLocationUpdater bossBarLocationUpdater;
  private JavaPlugin javaPlugin;
  private ReflectionUtil reflectionUtil;
  private ParticleVersionTemplate particleVersionTemplate;
  private KelpLogger logger;

  @Inject
  public PlayerCreationListener(KelpPlayerRepository kelpPlayerRepository,
                                GlobalPacketListener globalPacketListener,
                                KelpLogger logger,
                                JavaPlugin javaPlugin,
                                SoundRepository soundRepository,
                                EntityTypeVersionTemplate entityTypeVersionTemplate,
                                ReflectionUtil reflectionUtil,
                                BossBarLocationUpdater bossBarLocationUpdater,
                                ParticleVersionTemplate particleVersionTemplate) {
    this.kelpPlayerRepository = kelpPlayerRepository;
    this.globalPacketListener = globalPacketListener;
    this.logger = logger;
    this.javaPlugin = javaPlugin;
    this.reflectionUtil = reflectionUtil;
    this.particleVersionTemplate = particleVersionTemplate;
    this.bossBarLocationUpdater = bossBarLocationUpdater;
    this.soundRepository = soundRepository;
    this.entityTypeVersionTemplate = entityTypeVersionTemplate;
  }

  /**
   * Creates the player in the {@link KelpPlayerRepository} so that it can
   * be queried by other plugins. After the player has been added, the
   * {@link KelpPlayerLoginEvent} is called. This event should be used by kelp
   * applications instead of the {@link PlayerLoginEvent} as it is not save
   * whether the {@code KelpPlayer} has already been initialized.
   *
   * @param event The event to listen for.
   */
  @EventHandler
  public void handlePlayerLogin(PlayerLoginEvent event) {
    Entity playerHandle = ((CraftPlayer)event.getPlayer()).getHandle();
    kelpPlayerRepository.playerEntityObject(event.getPlayer().getUniqueId(), playerHandle);
    KelpPlayer kelpPlayer = new VersionedKelpPlayer(
      playerHandle,
      KelpEntityType.PLAYER,
      event.getPlayer().getLocation(),
      entityTypeVersionTemplate,
      reflectionUtil,
      logger,
      bossBarLocationUpdater,
      soundRepository,
      particleVersionTemplate,
      javaPlugin);
    kelpPlayerRepository.addOrUpdatePlayer(kelpPlayer.getUUID(), kelpPlayer);
    Bukkit.getPluginManager().callEvent(new KelpPlayerLoginEvent(
      kelpPlayer,
      event.getHostname(),
      event.getResult(),
      event.getKickMessage()));
  }

  /**
   * Deletes the player from the cache as soon as they quit in
   * order to save server performance.
   *
   * @param event The event to listen for.
   */
  @EventHandler
  public void handlePlayerQuit(PlayerQuitEvent event) {
    kelpPlayerRepository.removeKelpPlayer(event.getPlayer().getUniqueId());
  }

  /**
   * Iterates through all players that are currently online and
   * adds them to the cache. This is important as the cache is cleared
   * after every reload and querying information about those players
   * would lead to {@code null pointers}.
   */
  public void createOnStartup() {
    logger.log(LogLevel.DEBUG, "[VERSION-1.8] Restoring player instances...");
    Bukkit.getOnlinePlayers().forEach(current -> {
      // catches NoSuchElementException which occurs when a player quits
      // the server during a reload, because the server does not recognize
      // the quit event at first. So the exception is caught and the player
      // is removed from the cache again.
      try {
        Entity playerHandle = ((CraftPlayer)current).getHandle();
        KelpPlayer kelpPlayer = new VersionedKelpPlayer(
          playerHandle,
          KelpEntityType.PLAYER,
          current.getLocation(),
          entityTypeVersionTemplate,
          reflectionUtil,
          logger,
          bossBarLocationUpdater,
          soundRepository,
          particleVersionTemplate,
          javaPlugin);
        kelpPlayer.setClientViewDistanceInternally(Bukkit.getViewDistance());
        kelpPlayer.setClientLanguageInternally("en_US");
        kelpPlayer.setPlayerChatVisibilityInternally(PlayerChatVisibility.SHOW_ALL_MESSAGES);
        kelpPlayer.setPlayerChatColorEnabledInternally(true);
        globalPacketListener.injectPacketListener(current);
        kelpPlayerRepository.addOrUpdatePlayer(kelpPlayer.getUUID(), kelpPlayer);
        Bukkit.getPluginManager().callEvent(new KelpPlayerUpdateSettingsEvent(kelpPlayer,
          SettingsUpdateStage.PLUGIN_STARTUP,
          "en_US",
          Bukkit.getViewDistance(),
          PlayerChatVisibility.SHOW_ALL_MESSAGES,
          true));
      } catch (NoSuchElementException ignore) {
        // if the player has quit during the reload, immediately remove it
        // from the cache again.
        kelpPlayerRepository.removeKelpPlayer(current.getUniqueId());
      }
    });
    logger.log(LogLevel.DEBUG, "[VERSION-1.8] All players have been successfully created internally.");
  }

}
