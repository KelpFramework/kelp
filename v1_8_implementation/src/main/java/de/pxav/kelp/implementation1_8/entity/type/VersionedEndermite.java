package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.EndermiteEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedMonster;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityEndermite;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEndermite;

public class VersionedEndermite extends VersionedMonster<EndermiteEntity> implements EndermiteEntity {

  EntityEndermite endermiteHandle;

  public VersionedEndermite(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.endermiteHandle = (EntityEndermite) entityHandle;
  }

  @Override
  public boolean isPlayerSpawned() {
    return endermiteHandle.n();
  }

  @Override
  public EndermiteEntity setPlayerSpawned(boolean playerSpawned) {
    endermiteHandle.a(playerSpawned);
    return this;
  }

}
