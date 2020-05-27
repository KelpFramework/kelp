package de.pxav.kelp.core.sound;

import de.pxav.kelp.core.version.KelpVersion;
import org.bukkit.Sound;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public enum KelpSound {

  LEVEL_UP(KelpVersion.MC_1_8_0),
  ITEM_BREAK(KelpVersion.MC_1_8_0)

  ;

  private KelpVersion since;

  KelpSound(KelpVersion since) {
    this.since = since;
  }

  public KelpVersion since() {
    return since;
  }

  public static Collection<KelpSound> aboveVersion(KelpVersion version) {
    Collection<KelpSound> output = new ArrayList<>();

    if (version == KelpVersion.highestVersion()) {
      return output;
    }

    for (KelpSound sound : KelpSound.values()) {
      if (sound.since() == version) continue;
      if (KelpVersion.higherVersion(version,  sound.since()) == sound.since()) {
        output.add(sound);
      }
    }

    return output;
  }

  public static Collection<KelpSound> belowVersion(KelpVersion version) {
    Collection<KelpSound> output = new ArrayList<>();

    if (version == KelpVersion.lowestVersion()) {
      return output;
    }

    for (KelpSound sound : KelpSound.values()) {
      if (sound.since() == version) continue;
      if (KelpVersion.lowerVersion(version,  sound.since()) == sound.since()) {
        output.add(sound);
      }
    }

    return output;
  }

  public static Collection<KelpSound> matchesVersion(KelpVersion version) {
    Collection<KelpSound> output = new ArrayList<>();

    for (KelpSound sound : KelpSound.values()) {
      if (sound.since() == version) {
        output.add(sound);
      }
    }

    return output;
  }

  public static Collection<KelpSound> includedIn(KelpVersion version) {
    Collection<KelpSound> output = new ArrayList<>(belowVersion(version));
    output.addAll(matchesVersion(version));
    return output;
  }

  @Override
  public String toString() {
    return super.toString();
  }

}
