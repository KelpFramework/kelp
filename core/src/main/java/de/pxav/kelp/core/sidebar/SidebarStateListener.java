package de.pxav.kelp.core.sidebar;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * This class handles the states of sidebars.
 * It gives default sidebars on every player join
 * and removes any type of sidebar on every player
 * quit.
 *
 * This avoids useless calculation for the server.
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

  /**
   * This event is triggered when a player joins the server.
   * In this case it gives the player the default sidebar,
   * if there has been defined one (with 'setOnJoin' to true
   * in the {@code @CreateSidebar} annotation).
   *
   * @param event Instance of the current event.
   */
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent event) {
    Player player = event.getPlayer();

    // if a scoreboard has 'setOnJoin' set to true, it will be shown to the player
    if (!sidebarRepository.getDefaultScoreboard().equalsIgnoreCase("NONE")) {
      sidebarRepository.openSidebar(sidebarRepository.getDefaultScoreboard(), player);
    }

  }

  /**
   * This event is triggered when a player quits the server.
   * In this case it removes the sidebar for every player so
   * that the server does not do useless updates on their
   * sidebar.
   *
   * @param event Instance of the current event.
   */
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    sidebarRepository.removeSidebar(player);
  }

}
