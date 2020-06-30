package de.pxav.kelp.implementation1_8.player;

import com.google.inject.Inject;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import de.pxav.kelp.core.player.PlayerChatVisibility;
import de.pxav.kelp.implementation1_8.packet.GlobalPacketListener;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

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

  public void createOnStartup() {
    Bukkit.getOnlinePlayers().forEach(current -> {
      KelpPlayer kelpPlayer = kelpPlayerRepository.newKelpPlayer(current);
      kelpPlayer.setClientViewDistanceInternally(Bukkit.getViewDistance());
      kelpPlayer.setClientLanguageInternally("en_US");
      kelpPlayer.setPlayerChatVisibilityInternally(PlayerChatVisibility.SHOW_ALL_MESSAGES);
      kelpPlayer.setPlayerChatColorEnabledInternally(true);
      kelpPlayerRepository.addOrUpdatePlayer(kelpPlayer.getUUID(), kelpPlayer);
      globalPacketListener.injectPacketListener(current);
    });
  }

}
