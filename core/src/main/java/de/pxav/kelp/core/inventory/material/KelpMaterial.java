package de.pxav.kelp.core.inventory.material;

import de.pxav.kelp.core.version.KelpVersion;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public enum KelpMaterial {

  STONE(0, KelpVersion.MC_1_8_0),
  DIRT(0, KelpVersion.MC_1_8_0),
  APPLE(0, KelpVersion.MC_1_8_0),

  WHITE_WOOL(0, KelpVersion.MC_1_8_0),
  ORANGE_WOOL(1, KelpVersion.MC_1_8_0)
  ;

  private KelpVersion since;
  private int bukkitSubId;

  KelpMaterial(int bukkitSubId, KelpVersion since) {
    this.since = since;
    this.bukkitSubId = bukkitSubId;
  }

  public KelpVersion since() {
    return since;
  }

  public int bukkitSubId() {
    return bukkitSubId;
  }

  public static Collection<KelpMaterial> aboveVersion(KelpVersion version) {
    Collection<KelpMaterial> output = new ArrayList<>();

    if (version == KelpVersion.highestVersion()) {
      return output;
    }

    for (KelpMaterial material : KelpMaterial.values()) {
      if (material.since() == version) continue;
      if (KelpVersion.higherVersion(version,  material.since()) == material.since()) {
        output.add(material);
      }
    }

    return output;
  }

  public static Collection<KelpMaterial> belowVersion(KelpVersion version) {
    Collection<KelpMaterial> output = new ArrayList<>();

    if (version == KelpVersion.lowestVersion()) {
      return output;
    }

    for (KelpMaterial material : KelpMaterial.values()) {
      if (material.since() == version) continue;
      if (KelpVersion.lowerVersion(version,  material.since()) == material.since()) {
        output.add(material);
      }
    }

    return output;
  }

  public static Collection<KelpMaterial> matchesVersion(KelpVersion version) {
    Collection<KelpMaterial> output = new ArrayList<>();

    for (KelpMaterial material : KelpMaterial.values()) {
      if (material.since() == version) {
        output.add(material);
      }
    }

    return output;
  }

  public static Collection<KelpMaterial> includedIn(KelpVersion version) {
    Collection<KelpMaterial> output = new ArrayList<>(belowVersion(version));
    output.addAll(matchesVersion(version));
    return output;
  }

  @Override
  public String toString() {
    return super.toString() + ":" + bukkitSubId;
  }
}
