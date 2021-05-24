package de.pxav.kelp.core.entity.util;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.version.EntityConstantsVersionTemplate;

public enum VillagerType {

  DESERT,
  JUNGLE,
  PLAINS,
  SAVANNA,
  SNOW,
  SWAMP,
  TAIGA,
  NONE;

  public static VillagerType from(String bukkitType) {
    return KelpPlugin.getInjector().getInstance(EntityConstantsVersionTemplate.class).getVillagerType(bukkitType);
  }

  public static String toBukkitType(VillagerType type) {
    return KelpPlugin.getInjector().getInstance(EntityConstantsVersionTemplate.class).getVillagerType(type);
  }

  public String toBukkitType() {
    return toBukkitType(this);
  }

}
