package de.pxav.kelp.implementation1_12.player;

import com.google.inject.Inject;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import de.pxav.kelp.core.player.PlayerChatVisibility;
import de.pxav.kelp.implementation1_12.packet.GlobalPacketListener;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.NoSuchElementException;

/**
 * A class description goes here.
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

  @EventHandler
  public void handlePlayerLogin(PlayerLoginEvent event) {
    kelpPlayerRepository.playerEntityObject(event.getPlayer().getUniqueId(),
      ((CraftEntity)event.getPlayer()).getHandle());
    KelpPlayer kelpPlayer = kelpPlayerRepository.newKelpPlayer(event.getPlayer());
    kelpPlayerRepository.addOrUpdatePlayer(kelpPlayer.getUUID(), kelpPlayer);
  }

  @EventHandler
  public void handlePlayerQuit(PlayerQuitEvent event) {
    kelpPlayerRepository.removeKelpPlayer(event.getPlayer().getUniqueId());
  }

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
      } catch (NoSuchElementException ignore) {
        // if the player has quit during the reload, immediately remove it
        // from the cache again.
        kelpPlayerRepository.removeKelpPlayer(current.getUniqueId());
      }
    });
  }

}
