package de.pxav.kelp.sidebar;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class SidebarStateListener {

  private SidebarRepository sidebarRepository;

  @Inject
  public SidebarStateListener(SidebarRepository sidebarRepository) {
    this.sidebarRepository = sidebarRepository;
  }

  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    if (!sidebarRepository.getDefaultScoreboard().equalsIgnoreCase("NONE")) {
      sidebarRepository.openSidebar(sidebarRepository.getDefaultScoreboard(), player);
    }

  }

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
  }

}
