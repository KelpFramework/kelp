package de.pxav.kelp.core.sound;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class SoundRepository {

  private BiMap<KelpSound, String> sounds = HashBiMap.create();



  public String getSound(KelpSound kelpSound) {
    return sounds.get(kelpSound);
  }

  public String getSound(String kelpSound) {
    return sounds.get(KelpSound.valueOf(kelpSound));
  }

  public KelpSound getKelpSound(String bukkitSound) {
    return sounds.inverse().get(bukkitSound);
  }

  public void addSound(KelpSound kelpSound, String bukkitSound) {
    this.sounds.put(kelpSound, bukkitSound);
  }

  public void removeSound(KelpSound kelpSound) {
    this.sounds.remove(kelpSound);
  }

}
