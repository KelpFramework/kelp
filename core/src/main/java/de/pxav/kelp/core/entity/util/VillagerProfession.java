package de.pxav.kelp.core.entity.util;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.version.EntityConstantsVersionTemplate;

public enum VillagerProfession {

  NONE,
  ARMORER,
  BUTCHER,
  CARTOGRAPHER,
  CLERIC,
  FARMER,
  FISHERMAN,
  FLETCHER,
  LEATHER_WORKER,
  LIBRARIAN,
  MASON,
  NITWIT,
  SHEPHERD,
  TOOL_SMITH,
  WEAPON_SMITH;

  public static VillagerProfession from(String bukkitProfession) {
    return KelpPlugin.getInjector().getInstance(EntityConstantsVersionTemplate.class).getVillagerProfession(bukkitProfession);
  }

  public static String toBukkitProfession(VillagerProfession profession) {
    return KelpPlugin.getInjector().getInstance(EntityConstantsVersionTemplate.class).getVillagerProfession(profession);
  }

  public String toBukkitProfession() {
    return toBukkitProfession(this);
  }

}
