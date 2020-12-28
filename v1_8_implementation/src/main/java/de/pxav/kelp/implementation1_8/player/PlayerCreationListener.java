package de.pxav.kelp.implementation1_8.player;

import com.google.inject.Inject;
import de.pxav.kelp.core.event.kelpevent.KelpPlayerLoginEvent;
import de.pxav.kelp.core.event.kelpevent.KelpPlayerUpdateSettingsEvent;
import de.pxav.kelp.core.event.kelpevent.SettingsUpdateStage;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import de.pxav.kelp.core.player.PlayerChatVisibility;
import de.pxav.kelp.implementation1_8.packet.GlobalPacketListener;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.NoSuchElementException;

/**
 * This class is responsible for keeping player instances over
 * the server lifetime. When the kelp plugin instance is disabled
 * (by a reload for example), then
 *
 * @author pxav
 */
public class PlayerCreationListener {

  private KelpPlayerRepository kelpPlayerRepository;
  private GlobalPacketListener globalPacketListener;

  @Inject
  public PlayerCreationListener(KelpPlayerRepository kelpPlayerRepository, GlobalPacketListener globalPacketListener) {
    this.kelpPlayerRepository = kelpPlayerRepository;
    this.globalPacketListener = globalPacketListener;
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
    kelpPlayerRepository.playerEntityObject(event.getPlayer().getUniqueId(),
      ((CraftEntity)event.getPlayer()).getHandle());
    KelpPlayer kelpPlayer = kelpPlayerRepository.newKelpPlayer(event.getPlayer());
    kelpPlayerRepository.addOrUpdatePlayer(kelpPlayer.getUUID(), kelpPlayer);
    Bukkit.getPluginManager().callEvent(new KelpPlayerLoginEvent(
      event.getPlayer(),
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
    Bukkit.getOnlinePlayers().forEach(current -> {
      // catches NoSuchElementException which occurs when a player quits
      // the server during a reload, because the server does not recognize
      // the quit event at first. So the exception is caught and the player
      // is removed from the cache again.
      try {
        KelpPlayer kelpPlayer = kelpPlayerRepository.newKelpPlayer(current);
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
  }

}
