package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AbstractVillager;
import de.pxav.kelp.core.entity.util.VillagerProfession;
import de.pxav.kelp.core.entity.util.VillagerType;

public interface VillagerEntity extends AbstractVillager<VillagerEntity> {

  VillagerProfession getProfession();

  VillagerEntity setProfession(VillagerProfession profession);

  VillagerType getVillagerType();

  VillagerEntity setVillagerType(VillagerType villagerType);

  int getVillagerExperience();

  int getVillagerLevel();

  VillagerEntity setVillagerLevel(int villagerLevel);

  VillagerEntity setVillagerExperience();

}
