package de.pxav.kelp.implementation1_8.entity;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.version.EntityVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.event.entity.CreatureSpawnEvent;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedEntity extends EntityVersionTemplate {

  @Override
  public void spawnEntity(KelpEntity entity) {
    CraftWorld craftWorld = (CraftWorld) entity.getCurrentLocation().getWorld();
    ((Entity) entity.getBukkitEntity()).setPositionRotation(entity.getCurrentLocation().getX(), entity.getCurrentLocation().getY(), entity.getCurrentLocation().getZ(), entity.getCurrentLocation().getYaw(), entity.getCurrentLocation().getPitch());
    craftWorld.addEntity((Entity) entity.getBukkitEntity(), CreatureSpawnEvent.SpawnReason.CUSTOM);
    System.out.println("to spawn " + (Entity) entity.getBukkitEntity());
    System.out.println("entity spawned");
  }

}
