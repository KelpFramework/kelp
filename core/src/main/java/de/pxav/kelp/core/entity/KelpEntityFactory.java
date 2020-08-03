package de.pxav.kelp.core.entity;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import org.bukkit.Location;
import org.bukkit.entity.Entity;

/**
 * This factory class allows you to create new {@code KelpEntities}
 *
 * @author pxav
 */
@Singleton
public class KelpEntityFactory {

  private EntityTypeVersionTemplate typeVersionTemplate;

  @Inject
  public KelpEntityFactory(EntityTypeVersionTemplate typeVersionTemplate) {
    this.typeVersionTemplate = typeVersionTemplate;
  }

  /**
   * Creates a new {@code KelpEntity} instance based on the given entity type.
   *
   * @param entityType  The type of the entity you want to spawn.
   * @param location    The initial location of the entity. This also contains the
   *                    world where it is added to later.
   * @return The new {@code KelpEntity}
   * @see KelpEntity
   * @see KelpEntityType
   */
  public KelpEntity newKelpEntity(KelpEntityType entityType, Location location) {
    return typeVersionTemplate.newKelpEntity(entityType, location);
  }

  /**
   * Converts the given bukkit entity to a {@code KelpEntity}.
   *
   * @param bukkitEntity The instance of the bukkit entity you want to convert.
   * @return The {@code KelpEntity} equivalent to the bukkit
   */
  public KelpEntity getKelpEntity(Entity bukkitEntity) {
    return typeVersionTemplate.getKelpEntity(bukkitEntity);
  }

}
