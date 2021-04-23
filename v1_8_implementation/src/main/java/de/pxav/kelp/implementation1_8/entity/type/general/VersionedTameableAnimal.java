package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.TameableAnimal;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityTameableAnimal;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftTameableAnimal;

public class VersionedTameableAnimal<T extends TameableAnimal<T>> extends VersionedAnimalEntity<T> implements TameableAnimal<T> {

  private EntityTameableAnimal tameableHandle;

  public VersionedTameableAnimal(Entity entityHandle,
                                 KelpEntityType entityType,
                                 Location initialLocation,
                                 EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.tameableHandle = (EntityTameableAnimal) entityHandle;
  }

  @Override
  public T setTamed(boolean tamed) {
    tameableHandle.setTamed(tamed);
    return (T) this;
  }

  @Override
  public boolean isTamed() {
    return tameableHandle.isTamed();
  }

  @Override
  public KelpEntity<?> getOwner() {
    return entityTypeVersionTemplate.getKelpEntity(tameableHandle.getOwner().getBukkitEntity());
  }

  @Override
  public T setOwner(KelpEntity<?> owner) {
    tameableHandle.setOwnerUUID(owner.getUUID().toString());
    return (T) this;
  }

}
