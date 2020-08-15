package de.pxav.kelp.core.particle.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.particle.type.ParticleType;
import de.pxav.kelp.core.player.KelpPlayer;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class ParticleVersionTemplate {

  public abstract void spawnParticle(KelpPlayer player,
                                     ParticleType particleType,
                                     boolean longDistance,
                                     double x,
                                     double y,
                                     double z,
                                     float offsetX,
                                     float offsetY,
                                     float offsetZ,
                                     float particleData,
                                     int count,
                                     Object data);

}
