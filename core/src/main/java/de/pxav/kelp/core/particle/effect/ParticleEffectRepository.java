package de.pxav.kelp.core.particle.effect;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class ParticleEffectRepository {

  private ConcurrentHashMap<Collection<KelpPlayer>, ScheduledExecutorService> effectTimers;
  private KelpPlayerRepository playerRepository;
  private ExecutorService executorService;

  @Inject
  public ParticleEffectRepository(KelpPlayerRepository playerRepository) {
    this.effectTimers = new ConcurrentHashMap<>();
    this.playerRepository = playerRepository;
    this.executorService = Executors.newCachedThreadPool();
  }

  public void addTimer(Collection<KelpPlayer> players, ScheduledExecutorService executorService) {
    this.effectTimers.put(players, executorService);
  }

  public void stopAllTimers() {
    effectTimers.forEach((effect, timer) -> timer.shutdownNow());
  }

  @EventHandler
  public void handlePlayerQuit(PlayerQuitEvent event) {
    executorService.execute(() -> {
      KelpPlayer player = playerRepository.getKelpPlayer(event.getPlayer());

      new ConcurrentHashMap<>(effectTimers).forEach((players, timer) -> {

        if (players.isEmpty() || (players.size() == 1 && players.contains(player))) {
          timer.shutdownNow();
          effectTimers.remove(players);
          return;
        }

        if (players.contains(player)) {
          players.remove(player);
           effectTimers.put(players, timer);
        }

      });
    });
  }

}
