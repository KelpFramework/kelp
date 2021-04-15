package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.type.general.MobileEntity;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.implementation1_8.entity.VersionedLivingEntity;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;

public class VersionedMobileEntity<T extends MobileEntity<T>>
  extends VersionedLivingEntity<T>
  implements MobileEntity<T> {

  public VersionedMobileEntity(Entity entityHandle,
                               KelpEntityType entityType,
                               Location initialLocation) {
    super(entityHandle, entityType, initialLocation);
  }

  @Override
  public T setTarget(LivingKelpEntity<?> target) {
    return null;
  }

  @Override
  public LivingKelpEntity<?> getTarget() {
    return null;
  }

  @Override
  public boolean isAware() {
    return false;
  }

  @Override
  public boolean setAware(boolean aware) {
    return false;
  }

  @Override
  public KelpLocation getEyeLocation() {
    return null;
  }

}
