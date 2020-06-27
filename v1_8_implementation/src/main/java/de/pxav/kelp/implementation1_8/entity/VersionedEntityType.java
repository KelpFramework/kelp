package de.pxav.kelp.implementation1_8.entity;

import com.google.inject.Inject;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.type.DroppedItemEntity;
import de.pxav.kelp.core.entity.type.ElderGuardianEntity;
import de.pxav.kelp.core.entity.type.GuardianEntity;
import de.pxav.kelp.core.entity.type.ZombieEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.item.KelpItemFactory;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityGuardian;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.Guardian;
import org.bukkit.entity.Item;
import org.bukkit.entity.Zombie;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedEntityType extends EntityTypeVersionTemplate {

  private KelpItemFactory kelpItemFactory;

  @Inject
  public VersionedEntityType(KelpItemFactory kelpItemFactory) {
    this.kelpItemFactory = kelpItemFactory;
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
      case DROPPED_ITEM:
        output = new DroppedItemEntity();
        entity = craftWorld.createEntity(location, Item.class);
        break;
    }

    output.entityId(entity.getId());
    output.entityType(entityType);
    output.currentLocation(location);
    output.bukkitEntity(entity);

    return output;
  }

  @Override
  public KelpEntity getKelpEntity(org.bukkit.entity.Entity bukkitEntity) {

    if (bukkitEntity instanceof Item) {
      Item item = (Item) bukkitEntity;
      KelpItem kelpItem = kelpItemFactory.fromItemStack(item.getItemStack());
      return new DroppedItemEntity(((CraftEntity)item).getHandle(), item.getEntityId(), item.getLocation(), kelpItem);
    }

    if (bukkitEntity instanceof Zombie) {
      Zombie zombie = (Zombie) bukkitEntity;
      return new ZombieEntity(((CraftEntity)zombie).getHandle(),
        zombie.getEntityId(),
        zombie.getLocation(),
        zombie.isBaby());
    }

    if (bukkitEntity instanceof Guardian) {
      Guardian guardian = (Guardian) bukkitEntity;
      if (guardian.isElder()) {
        return new ElderGuardianEntity(((CraftEntity)guardian).getHandle(), guardian.getEntityId(), guardian.getLocation());
      } else {
        return new GuardianEntity(((CraftEntity)guardian).getHandle(), guardian.getEntityId(), guardian.getLocation());
      }
    }

    return null;
  }

}
