package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.AgeableEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityAgeable;
import org.bukkit.Location;

public class VersionedAgeable<T extends AgeableEntity<T>> extends VersionedMobileEntity<T> implements AgeableEntity<T> {

  private EntityAgeable ageableHandle;

  public VersionedAgeable(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.ageableHandle = (EntityAgeable) entityHandle;
  }

  @Override
  public boolean isAgeLocked() {
    return ageableHandle.ageLocked;
  }

  @Override
  public T setAgeLocked(boolean ageLocked) {
    ageableHandle.ageLocked = ageLocked;
    return (T) this;
  }

  @Override
  public T setBaby(boolean baby) {
    if (baby) {
      if (this.isAdult()) {
        this.setAge(-24000);
      }
    } else {
      if (!this.isAdult()) {
        this.setAge(0);
      }
    }
    return (T) this;
  }

  @Override
  public boolean isBaby() {
    return ageableHandle.isBaby();
  }

  @Override
  public T setAge(int age) {
    ageableHandle.setAge(age);
    return (T) this;
  }

}
