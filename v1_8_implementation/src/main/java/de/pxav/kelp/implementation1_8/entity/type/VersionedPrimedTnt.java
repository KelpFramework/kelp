package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.type.PrimedTntEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.core.world.util.ExplosionPower;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedExplosiveEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import net.minecraft.server.v1_8_R3.EntityTNTPrimed;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftLivingEntity;

public class VersionedPrimedTnt extends VersionedExplosiveEntity<PrimedTntEntity> implements PrimedTntEntity {

  private EntityTNTPrimed primedTnt;

  public VersionedPrimedTnt(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.primedTnt = (EntityTNTPrimed) entityHandle;
  }

  @Override
  public int getFuseTicks() {
    return primedTnt.fuseTicks;
  }

  @Override
  public PrimedTntEntity setFuseTicks(int fuseTicks) {
    primedTnt.fuseTicks = fuseTicks;
    return this;
  }

  @Override
  public KelpEntity<?> getSource() {
    return entityTypeVersionTemplate.getKelpEntity(primedTnt.getSource().getBukkitEntity());
  }

  @Override
  public PrimedTntEntity setSource(KelpEntity<?> sourceEntity) {
    if (!(sourceEntity instanceof LivingKelpEntity)) {
      //todo log!
      return this;
    }
    ReflectionUtil.setValue(primedTnt, "source", (EntityLiving) ((CraftLivingEntity)(sourceEntity.getBukkitEntity())).getHandle());
    return this;
  }

  @Override
  public ExplosionPower getExplosionPower() {
    return ExplosionPower.custom(primedTnt.yield);
  }

  @Override
  public PrimedTntEntity setExplosionPower(ExplosionPower power) {
    primedTnt.yield = power.getPower();
    return this;
  }

  @Override
  public boolean createsFire() {
    return primedTnt.isIncendiary;
  }

  @Override
  public PrimedTntEntity canCreateFire(boolean create) {
    primedTnt.isIncendiary = create;
    return this;
  }

}
