package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.IronGolemEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedGolem;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityIronGolem;
import org.bukkit.Location;

public class VersionedIronGolem extends VersionedGolem<IronGolemEntity> implements IronGolemEntity {

  private EntityIronGolem ironGolem;

  public VersionedIronGolem(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.ironGolem = (EntityIronGolem) entityHandle;
  }

  @Override
  public boolean isPlayerCreated() {
    return ironGolem.isPlayerCreated();
  }

  @Override
  public IronGolemEntity setPlayerCreated(boolean playerCreated) {
    ironGolem.setPlayerCreated(playerCreated);
    return this;
  }

}
