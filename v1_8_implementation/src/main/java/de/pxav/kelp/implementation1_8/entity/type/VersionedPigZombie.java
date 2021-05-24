package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.PigZombie;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPigZombie;
import org.bukkit.Location;

public class VersionedPigZombie extends VersionedZombie implements PigZombie {

  private EntityPigZombie pigZombie;

  public VersionedPigZombie(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.pigZombie = (EntityPigZombie) entityHandle;
  }

  @Override
  public int getAnger() {
    return pigZombie.angerLevel;
  }

  @Override
  public void setAnger(int anger) {
    pigZombie.angerLevel = anger;
  }

  @Override
  public void setAngry(boolean angry) {
    if (angry && pigZombie.angerLevel < 1) {
      pigZombie.angerLevel = 1;
    } else if (!angry && pigZombie.angerLevel > 0) {
      pigZombie.angerLevel = 0;
    }
  }

  @Override
  public boolean isAngry() {
    return pigZombie.angerLevel > 0;
  }

  @Override
  public boolean isConverting() {
    return false;
  }

  @Override
  public int getConversionTime() {
    return 0;
  }

  @Override
  public void setConversionTime(int conversionTime) {

  }

}
