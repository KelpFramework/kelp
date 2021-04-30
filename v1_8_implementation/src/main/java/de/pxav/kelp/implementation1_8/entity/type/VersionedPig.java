package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.PigEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedBreedableAnimal;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPig;
import org.bukkit.util.Vector;

public class VersionedPig extends VersionedBreedableAnimal<PigEntity> implements PigEntity {

  private CraftPig craftPig;

  public VersionedPig(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
  }

  @Override
  public Vector getVehicleVelocity() {
    return craftPig.getVelocity();
  }

  @Override
  public PigEntity setVehicleVelocity(Vector vehicleVelocity) {
    craftPig.setVelocity(vehicleVelocity);
    return this;
  }

  @Override
  public boolean hasSaddle() {
    return craftPig.hasSaddle();
  }

  @Override
  public PigEntity setSaddled(boolean saddled) {
    craftPig.setSaddle(saddled);
    return this;
  }

}
