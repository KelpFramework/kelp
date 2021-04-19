package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.util.VillagerProfession;
import de.pxav.kelp.core.entity.util.VillagerType;

// sub type of zombie
public interface ZombieVillagerEntity extends ZombieEntity {

  ZombieVillagerEntity setConversionTime(int conversionTime);

  ZombieVillagerEntity setVillagerType(VillagerType villagerType);

  ZombieVillagerEntity setVillagerProfession(VillagerProfession villagerProfession);

  int getConversionTime();

  VillagerType getVillagerType();

  VillagerProfession getVillagerProfession();

}
