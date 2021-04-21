package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.ZombieVillagerEntity;
import de.pxav.kelp.core.entity.util.VillagerProfession;
import de.pxav.kelp.core.entity.util.VillagerType;
import de.pxav.kelp.core.entity.version.EntityConstantsVersionTemplate;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityZombie;
import org.bukkit.Location;

public class VersionedZombieVillager extends VersionedZombie implements ZombieVillagerEntity {

  private EntityConstantsVersionTemplate entityConstantsVersionTemplate;
  private EntityZombie zombieHandle;
  private ReflectionUtil reflectionUtil;

  public VersionedZombieVillager(Entity entityHandle,
                                 KelpEntityType entityType,
                                 Location initialLocation,
                                 EntityTypeVersionTemplate entityTypeVersionTemplate,
                                 EntityConstantsVersionTemplate entityConstantsVersionTemplate,
                                 ReflectionUtil reflectionUtil) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.entityConstantsVersionTemplate = entityConstantsVersionTemplate;
    this.zombieHandle = (EntityZombie) entityHandle;
    this.reflectionUtil = reflectionUtil;
    zombieHandle.setVillager(true);
  }

  @Override
  public ZombieVillagerEntity setConversionTime(int conversionTime) {
    reflectionUtil.setValue(zombieHandle, "bn", conversionTime);
    return this;
  }

  @Override
  public ZombieVillagerEntity setVillagerType(VillagerType villagerType) {
    return this;
  }

  @Override
  public ZombieVillagerEntity setVillagerProfession(VillagerProfession villagerProfession) {
    return this;
  }

  @Override
  public int getConversionTime() {
    return (int) reflectionUtil.getValue(zombieHandle, "bn");
  }

  @Override
  public VillagerType getVillagerType() {
    return VillagerType.NONE;
  }

  // zombie villagers have no profession in 1.8
  @Override
  public VillagerProfession getVillagerProfession() {
    return VillagerProfession.NONE;
  }

}
