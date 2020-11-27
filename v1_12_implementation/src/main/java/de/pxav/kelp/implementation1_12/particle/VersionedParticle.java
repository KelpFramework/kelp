package de.pxav.kelp.implementation1_12.particle;

import com.google.inject.Inject;
import de.pxav.kelp.core.particle.type.ParticleType;
import de.pxav.kelp.core.particle.type.ParticleTypeRepository;
import de.pxav.kelp.core.particle.version.ParticleVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_12_R1.EnumParticle;
import net.minecraft.server.v1_12_R1.PacketPlayOutWorldParticles;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedParticle extends ParticleVersionTemplate {

  private ParticleTypeRepository particleTypeRepository;

  @Inject
  public VersionedParticle(ParticleTypeRepository particleTypeRepository) {
    this.particleTypeRepository = particleTypeRepository;
  }

  @Override
  public void spawnParticle(KelpPlayer player,
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
                            Object generalData) {
    EnumParticle enumParticle = EnumParticle.valueOf(particleTypeRepository.getParticleType(particleType));
    PacketPlayOutWorldParticles packet;

    if (generalData instanceof int[]) {
      packet = new PacketPlayOutWorldParticles(enumParticle,
        false,
        (float) x,
        (float) y,
        (float) z,
        offsetX,
        offsetY,
        offsetZ,
        particleData,
        count,
        (int[]) generalData);
    } else {
      packet = new PacketPlayOutWorldParticles(enumParticle,
        false,
        (float) x,
        (float) y,
        (float) z,
        offsetX,
        offsetY,
        offsetZ,
        particleData,
        count);
    }

    ((CraftPlayer)player.getBukkitPlayer()).getHandle().playerConnection.sendPacket(packet);

  }

}
