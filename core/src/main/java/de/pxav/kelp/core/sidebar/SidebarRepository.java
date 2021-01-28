package de.pxav.kelp.core.sidebar;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import de.pxav.kelp.core.animation.TextAnimation;
import de.pxav.kelp.core.event.kelpevent.sidebar.KelpSidebarRemoveEvent;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import de.pxav.kelp.core.scheduler.KelpSchedulerRepository;
import de.pxav.kelp.core.scheduler.type.SchedulerFactory;
import de.pxav.kelp.core.sidebar.type.AnimatedSidebar;
import de.pxav.kelp.core.sidebar.version.SidebarUpdaterVersionTemplate;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class SidebarRepository {

  private ConcurrentHashMap<UUID, Integer> animationStates;
  private ConcurrentHashMap<String, Set<UUID>> clusters;
  private ConcurrentHashMap<String, UUID> clusterTasks;
  private ConcurrentHashMap<UUID, UUID> playerTasks;
  private ConcurrentHashMap<UUID, TextAnimation> animations;

  private KelpSchedulerRepository schedulerRepository;
  private SchedulerFactory schedulerFactory;
  private SidebarUpdaterVersionTemplate updaterVersionTemplate;
  private KelpPlayerRepository playerRepository;

  @Inject
  public SidebarRepository(KelpSchedulerRepository schedulerRepository,
                           SchedulerFactory schedulerFactory,
                           SidebarUpdaterVersionTemplate updaterVersionTemplate,
                           KelpPlayerRepository playerRepository) {
    this.animationStates = new ConcurrentHashMap<>();
    this.clusters = new ConcurrentHashMap<>();
    this.clusterTasks = new ConcurrentHashMap<>();
    this.playerTasks = new ConcurrentHashMap<>();
    this.animations = new ConcurrentHashMap<>();
    this.schedulerFactory = schedulerFactory;
    this.schedulerRepository = schedulerRepository;
    this.updaterVersionTemplate = updaterVersionTemplate;
    this.playerRepository = playerRepository;
  }

  public void addAnimatedSidebar(AnimatedSidebar sidebar, KelpPlayer player) {

    animationStates.put(player.getUUID(), 0);
    animations.put(player.getUUID(), sidebar.getTitle());

    // add player to cluster or create a new cluster if needed
    if (sidebar.getClusterId() != null) {
      if (!clusters.containsKey(sidebar.getClusterId())) {
        this.addCluster(sidebar.getClusterId(), sidebar.getTitleAnimationInterval());
      }
      this.addPlayerToCluster(sidebar.getClusterId(), player);
    } else {
      // if the sidebar does not have clusters, an individual scheduler
      // has to be set up
      UUID task = schedulerFactory.newRepeatingScheduler()
        .async()
        .every(sidebar.getTitleAnimationInterval())
        .milliseconds()
        .run(taskId -> {
          String updateTo = incrementAnimationState(player.getUUID());
          updaterVersionTemplate.updateTitleOnly(updateTo, player);
        });
      playerTasks.put(player.getUUID(), task);
    }

  }

  public void removeAnimatedSidebar(KelpPlayer player) {
    if (playerTasks.containsKey(player.getUUID())) {
      UUID task = playerTasks.get(player.getUUID());
      schedulerRepository.interruptScheduler(task);
      this.playerTasks.remove(player.getUUID());
    } else {
      Maps.newHashMap(this.clusters).forEach((clusterId, playerSet)
        -> playerSet.stream()
        .filter(uuid -> player.getUUID() == uuid)
        .findFirst()
        .ifPresent(uuid -> {
          playerSet.remove(uuid);
          if (playerSet.isEmpty()) {
            stopCluster(clusterId);
            return;
          }
          clusters.put(clusterId, playerSet);
        }));
    }
    this.animationStates.remove(player.getUUID());
    this.animations.remove(player.getUUID());
  }

  public void stopAllClusters() {
    Maps.newHashMap(this.clusters).forEach((clusterId, playerSet)
      -> stopCluster(clusterId));
  }

  @EventHandler
  public void handleClusterRemove(PlayerQuitEvent event) {
    KelpPlayer player = playerRepository.getKelpPlayer(event.getPlayer());
    if (animationStates.containsKey(player.getUUID())) {
      removeAnimatedSidebar(player);
    }
  }

  @EventHandler
  public void handleSidebarRemove(KelpSidebarRemoveEvent event) {
    // stops all schedulers and removes the player from the list when
    // their sidebar is removed.
    this.removeAnimatedSidebar(event.getPlayer());
  }

  private void addPlayerToCluster(String clusterId, KelpPlayer player) {
    Set<UUID> players = clusters.get(clusterId);
    players.add(player.getUUID());
    clusters.put(clusterId, players);
  }

  private void addCluster(String clusterId, int interval) {
    clusters.put(clusterId, Sets.newHashSet());
    UUID task = schedulerFactory.newRepeatingScheduler()
      .async()
      .every(interval)
      .milliseconds()
      .run(taskId -> clusters.get(clusterId).forEach(current -> {
        String updateTo = incrementAnimationState(current);
        updaterVersionTemplate.updateTitleOnly(updateTo, playerRepository.getKelpPlayer(current));
      }));
    clusterTasks.put(clusterId, task);
  }

  private String incrementAnimationState(UUID uuid) {
    List<String> states = animations.get(uuid).states();
    int current = animationStates.get(uuid);
    int max = states.size();

    current += 1;
    if (current == max) {
      current = 0;
    }

    animationStates.put(uuid, current);
    return states.get(current);
  }

  private void stopCluster(String clusterId) {
    if (this.clusterTasks.get(clusterId) == null) {
      return;
    }
    schedulerRepository.interruptScheduler(clusterTasks.get(clusterId));
    clusterTasks.remove(clusterId);
    clusters.remove(clusterId);
  }

}
