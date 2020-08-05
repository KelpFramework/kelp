package de.pxav.kelp.core.particle.type;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.inject.Singleton;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class ParticleTypeRepository {

  private BiMap<ParticleType, String> particles = HashBiMap.create();

  /**
   * Gets the bukkit material matching the given kelp material as a string.
   * Sub IDs are not supported by this method.
   *
   * @param particleType The kelp material you want to get
   *                     the bukkit material of.
   * @return The bukkit material string.
   */
  public String getParticleType(ParticleType particleType) {
    return particles.get(particleType);
  }

  /**
   * Gets the bukkit material matching the given kelp material as a string.
   * Sub IDs are not supported by this method.
   *
   * @param particleTypeName The kelp material name/string you want to get
   *                         the bukkit material of.
   * @return The bukkit material string.
   */
  public String getParticleType(String particleTypeName) {
    ParticleType particleType = ParticleType.valueOf(particleTypeName);
    return this.getParticleType(particleType);
  }

  /**
   * Gets the kelp material matching the given bukkit material name.
   * This method does not support sub IDs.
   *
   * @param bukkitParticle The name of the bukkit material you want
   *                       to get the kelp material of.
   * @return The final kelp material.
   */
  public ParticleType getKelpParticleType(String bukkitParticle) {
    return particles.inverse().get(bukkitParticle);
  }

  /**
   * Adds a new material to the map. Sub ids can be added by simply
   * appending a {@code :<subId>} at the end of the bukkit materal string.
   *
   * @param kelpParticleType    The kelp material you want to add a link for.
   * @param bukkitParticle  The string of the bukkit material which should
   *                        be linked to the kelp material (including sub ids
   *                        with {@code :<subId} at the end of the string).
   */
  public void addParticleType(ParticleType kelpParticleType, String bukkitParticle) {
    this.particles.put(kelpParticleType, bukkitParticle);
  }

  /**
   * Removes a material from the version map.
   *
   * @param particleType The material you want to remove.
   */
  public void removeParticleType(ParticleType particleType) {
    this.particles.remove(particleType);
  }

}
