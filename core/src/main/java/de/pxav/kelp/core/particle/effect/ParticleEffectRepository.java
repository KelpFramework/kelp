package de.pxav.kelp.core.particle.effect;

import com.google.common.collect.Maps;
import com.google.inject.Singleton;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class ParticleEffectRepository {

  private ConcurrentHashMap<Collection<KelpPlayer>, ScheduledExecutorService> effectTimers;

  public ParticleEffectRepository() {
    this.effectTimers = new ConcurrentHashMap<>();
  }

  public void addTimer(Collection<KelpPlayer> players, ScheduledExecutorService executorService) {
  }

  public void stopAllTimers() {
    effectTimers.forEach((effect, timer) -> timer.shutdownNow());
  }

  @EventHandler
  public void handlePlayerQuit(PlayerQuitEvent event) {

  }

}
