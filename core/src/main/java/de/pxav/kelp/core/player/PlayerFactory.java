package de.pxav.kelp.core.player;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.sidebar.SidebarRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class PlayerFactory {

  private PlayerVersionTemplate playerVersionTemplate;
  private SidebarRepository sidebarRepository;
  private KelpLogger logger;

  @Inject
  public PlayerFactory(PlayerVersionTemplate playerVersionTemplate,
                       KelpLogger logger,
                       SidebarRepository sidebarRepository) {
    this.playerVersionTemplate = playerVersionTemplate;
    this.logger = logger;
    this.sidebarRepository = sidebarRepository;
  }

  public KelpPlayer newKelpPlayer(Player bukkitPlayer) {
    return new KelpPlayer(bukkitPlayer, playerVersionTemplate, sidebarRepository);
  }

  public KelpPlayer newKelpPlayer(UUID uuid) {
    Player bukkitPlayer = Bukkit.getPlayer(uuid);
    if (bukkitPlayer == null) {
      logger.log(LogLevel.WARNING, "Given player UUID for getting a new KelpPlayer failed." +
              "This player is not online, returning null!");
      return null;
    }
    return new KelpPlayer(bukkitPlayer, playerVersionTemplate, sidebarRepository);
  }

}
