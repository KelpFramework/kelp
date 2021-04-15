package de.pxav.kelp.implementation1_8.entity;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedDamageable;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityLiving;
import org.bukkit.Location;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class VersionedLivingEntity<T extends LivingKelpEntity<T>>
  extends VersionedDamageable<T>
  implements LivingKelpEntity<T> {

  private EntityLiving entityHandle;

  public VersionedLivingEntity(Entity entityHandle,
                               KelpEntityType entityType,
                               Location initialLocation) {
    super(entityHandle, entityType, initialLocation);
    this.entityHandle = (EntityLiving) entityHandle;
  }

  @Override
  public KelpLocation getEyeLocation() {
    return null;
  }

}
