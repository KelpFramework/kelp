package de.pxav.kelp.implementation1_8.entity;

import com.google.inject.Inject;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.type.ElderGuardianEntity;
import de.pxav.kelp.core.entity.type.GuardianEntity;
import de.pxav.kelp.core.entity.type.ZombieEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityGuardian;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Zombie;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedEntityType extends EntityTypeVersionTemplate {

  private KelpLogger logger;

  @Inject
  public VersionedEntityType(KelpLogger logger) {
    this.logger = logger;
  }

  @Override
  public KelpEntity newKelpEntity(KelpEntityType entityType, Location location) {
    KelpEntity output = new KelpEntity();
    CraftWorld craftWorld = (CraftWorld) location.getWorld();
    Entity entity = null;

    switch (entityType) {
      case GUARDIAN:
        output = new GuardianEntity();
        entity = craftWorld.createEntity(location, Guardian.class);
        break;
      case ELDER_GUARDIAN:
        output = new ElderGuardianEntity();
        entity = craftWorld.createEntity(location, Guardian.class);
        ((EntityGuardian) entity).setElder(true);
        break;
      case ZOMBIE:
        output = new ZombieEntity();
        entity = craftWorld.createEntity(location, Zombie.class);
        break;
    }

    output.entityId(entity.getId());
    output.entityType(entityType);
    output.currentLocation(location);
    output.bukkitEntity(entity);

    return output;
  }

}
