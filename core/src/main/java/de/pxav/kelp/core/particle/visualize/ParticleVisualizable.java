package de.pxav.kelp.core.particle.visualize;

import de.pxav.kelp.core.player.KelpPlayer;

public interface ParticleVisualizable {

  void visualize(KelpPlayer player, ParticleVisualizerProfile visualizerProfile);

  default void visualize(KelpPlayer player) {
    visualize(player, new DefaultParticleVisualizerTheme());
  }

}
