package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.CreeperEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.core.world.util.ExplosionPower;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedMonster;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityCreeper;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftCreeper;

public class VersionedCreeper extends VersionedMonster<CreeperEntity> implements CreeperEntity {

  private EntityCreeper creeperHandle;

  public VersionedCreeper(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.creeperHandle = (EntityCreeper) entityHandle;
  }


  @Override
  public boolean isCharged() {
    return creeperHandle.isPowered();
  }

  @Override
  public CreeperEntity setCharged(boolean charged) {
    creeperHandle.setPowered(charged);
    return this;
  }

  @Override
  public CreeperEntity setMaxFuseTicks(int maxFuseTicks) {
    ReflectionUtil.setValue(creeperHandle, "maxFuseTicks", maxFuseTicks);
    return this;
  }

  @Override
  public int getMaxFuseTicks() {
    return (int) ReflectionUtil.getValue(creeperHandle, "maxFuseTicks");
  }

  @Override
  public CreeperEntity setFuseTicks(int fuseTicks) {
    ReflectionUtil.setValue(creeperHandle, "fuseTicks", fuseTicks);
    return this;
  }

  @Override
  public int getFuseTicks() {
    return (int) ReflectionUtil.getValue(creeperHandle, "fuseTicks");
  }

  @Override
  public CreeperEntity explode() {
    ReflectionUtil.invokeMethod(creeperHandle, "cr");
    return this;
  }

  @Override
  public CreeperEntity ignite() {
    creeperHandle.co();
    return this;
  }

  @Override
  public boolean isIgnited() {
    return creeperHandle.cn();
  }

  @Override
  public ExplosionPower getExplosionPower() {
    return ExplosionPower.custom((int) ReflectionUtil.getValue(creeperHandle, "explosionRadius"));
  }

  @Override
  public CreeperEntity setExplosionPower(ExplosionPower power) {
    ReflectionUtil.setValue(creeperHandle, "explosionRadius", (int) power.getPower());
    return this;
  }

  @Override
  public boolean createsFire() {
    return false;
  }

  @Override
  public CreeperEntity canCreateFire(boolean create) {
    return this;
  }

}
