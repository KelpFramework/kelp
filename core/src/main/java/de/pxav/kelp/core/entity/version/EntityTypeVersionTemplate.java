package de.pxav.kelp.core.entity.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * This version template handles the conversion between kelp and bukkit
 * entity types. It allows you to convert and create kelp entities and is
 * used by other methods in the background such as the static factories
 * in the entity classes.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class EntityTypeVersionTemplate {

  /**
   * Creates a new kelp entity instance with a specific type at a specific location.
   * This method won't spawn the entity, so that you can still do modifications
   * on it as needed.
   *
   * @param entityType  The type of the entity you want to spawn.
   * @param location    The location, where the entity should be spawned at later.
   * @return The new kelp entity instance.
   */
  public abstract KelpEntity<?> newKelpEntity(KelpEntityType entityType, Location location);

  /**
   * Takes any bukkit entity and converts it to a new kelp entity instance.
   *
   * This is used by the static methods in the entity classes such as
   * {@link de.pxav.kelp.core.entity.type.SheepEntity#from(Entity)}, so if you
   * are an application developer you should prefer those methods over this one.
   *
   * @param bukkitEntity The bukkit entity you want to convert.
   * @return The instance of the kelp entity equivalent to the given bukkit entity.
   */
  public abstract KelpEntity<?> getKelpEntity(Entity bukkitEntity);

}
