package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.DamageableEntity;
import de.pxav.kelp.implementation1_8.entity.VersionedEntity;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;

public class VersionedDamageable<T extends DamageableEntity<T>>
  extends VersionedEntity<T>
  implements DamageableEntity<T> {

  public VersionedDamageable(Entity entityHandle, KelpEntityType entityType, Location initialLocation) {
    super(entityHandle, entityType, initialLocation);
  }

  @Override
  public T damage(double damage) {
    return null;
  }

  @Override
  public T damage(double damage, KelpEntity<?> source) {
    return null;
  }

  @Override
  public double getAbsorptionAmount() {
    return 0;
  }

  @Override
  public T setAbsorptionAmount(int absorptionAmount) {
    return null;
  }

  @Override
  public int getHealth() {
    return 0;
  }

  @Override
  public int getMaxHealth() {
    return 0;
  }

  @Override
  public T setHealth(int health) {
    return null;
  }

}
