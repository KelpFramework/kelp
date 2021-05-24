package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.ThrownFireballEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.world.util.ExplosionPower;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedExplosiveEntity;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFireball;
import org.bukkit.util.Vector;

public class VersionedThrownFireball extends VersionedExplosiveEntity<ThrownFireballEntity> implements ThrownFireballEntity {

  private CraftFireball craftFireball;

  public VersionedThrownFireball(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.craftFireball = (CraftFireball) entityHandle.getBukkitEntity();
  }

  @Override
  public Vector getDirection() {
    return craftFireball.getDirection();
  }

  @Override
  public ThrownFireballEntity setDirection(Vector vector) {
    craftFireball.setDirection(vector);
    return this;
  }

  @Override
  public ExplosionPower getExplosionPower() {
    return ExplosionPower.custom(craftFireball.getYield());
  }

  @Override
  public ThrownFireballEntity setExplosionPower(ExplosionPower power) {
    craftFireball.setYield(power.getPower());
    return this;
  }

  @Override
  public boolean createsFire() {
    return craftFireball.isIncendiary();
  }

  @Override
  public ThrownFireballEntity canCreateFire(boolean create) {
    craftFireball.setIsIncendiary(create);
    return this;
  }

}
