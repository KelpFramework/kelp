package de.pxav.kelp.implementation1_8.player;

import com.google.inject.Inject;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
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

  @Inject
  public PlayerCreationListener(KelpPlayerRepository kelpPlayerRepository) {
    this.kelpPlayerRepository = kelpPlayerRepository;
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
      kelpPlayerRepository.addOrUpdatePlayer(kelpPlayer.getUUID(), kelpPlayer);
    });
  }

}
