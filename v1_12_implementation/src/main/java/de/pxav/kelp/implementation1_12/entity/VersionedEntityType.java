package de.pxav.kelp.implementation1_12.entity;

import com.google.inject.Inject;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.type.DroppedItemEntity;
import de.pxav.kelp.core.entity.type.ElderGuardianEntity;
import de.pxav.kelp.core.entity.type.GuardianEntity;
import de.pxav.kelp.core.entity.type.ZombieEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.version.EntityVersionTemplate;
import de.pxav.kelp.core.entity.version.LivingEntityVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.item.KelpItemFactory;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_12_R1.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.*;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedEntityType extends EntityTypeVersionTemplate {

  private KelpItemFactory kelpItemFactory;
  private EntityVersionTemplate entityVersionTemplate;
  private LivingEntityVersionTemplate livingEntityVersionTemplate;

  @Inject
  public VersionedEntityType(KelpItemFactory kelpItemFactory, EntityVersionTemplate entityVersionTemplate, LivingEntityVersionTemplate livingEntityVersionTemplate) {
    this.kelpItemFactory = kelpItemFactory;
    this.entityVersionTemplate = entityVersionTemplate;
    this.livingEntityVersionTemplate = livingEntityVersionTemplate;
  }

  @Override
  public KelpEntity newKelpEntity(KelpEntityType entityType, Location location) {
    KelpEntity output = new KelpEntity(this.entityVersionTemplate);
    CraftWorld craftWorld = (CraftWorld) location.getWorld();
    Entity entity = null;
    output.minecraftEntity(null);
    switch (entityType) {
      case GUARDIAN:
        entity = craftWorld.createEntity(location, Guardian.class);
        output = new GuardianEntity();
        break;
      case ELDER_GUARDIAN:
        entity = craftWorld.createEntity(location, ElderGuardian.class);
        output = new ElderGuardianEntity();
        break;
      case ZOMBIE:
        entity = craftWorld.createEntity(location, Zombie.class);
        output = new ZombieEntity();
        break;
      case DROPPED_ITEM:
        entity = null;
        output = new DroppedItemEntity(entityVersionTemplate, null, 0, location, null);
        break;
    }

    if (entityType != KelpEntityType.DROPPED_ITEM) {
      output.entityId(entity.getId());
      output.entityType(entityType);
      output.minecraftEntity(entity);
      output.initialLocation(location);
      output.versionTemplate(this.entityVersionTemplate);
    }

    if (KelpEntityType.isLivingEntity(entityType)) {
      LivingEntity livingEntity = (CraftLivingEntity) entity.getBukkitEntity();
      ((LivingKelpEntity)output).bukkitLivingEntity(livingEntity);
      ((LivingKelpEntity)output).livingEntityVersionTemplate(livingEntityVersionTemplate);
    }

    return output;
  }

  @Override
  public KelpEntity getKelpEntity(org.bukkit.entity.Entity bukkitEntity) {

    if (bukkitEntity instanceof Item) {
      Item item = (Item) bukkitEntity;
      KelpItem kelpItem = kelpItemFactory.fromItemStack(item.getItemStack());
      return new DroppedItemEntity(this.entityVersionTemplate,
        ((CraftEntity)item).getHandle(),
        item.getEntityId(),
        item.getLocation(),
        kelpItem);
    }

    if (bukkitEntity instanceof Zombie) {
      Zombie zombie = (Zombie) bukkitEntity;
      LivingEntity livingEntity = (LivingEntity) bukkitEntity;
      return new ZombieEntity(this.entityVersionTemplate,
        this.livingEntityVersionTemplate,
        livingEntity,
        ((CraftEntity)zombie).getHandle(),
        zombie.getEntityId(),
        zombie.getLocation(),
        zombie.isBaby());
    }

    if (bukkitEntity instanceof Guardian) {
      Guardian guardian = (Guardian) bukkitEntity;
      if (guardian.isElder()) {
        return new ElderGuardianEntity(this.entityVersionTemplate, ((CraftEntity)guardian).getHandle(), guardian.getEntityId(), guardian.getLocation());
      } else {
        return new GuardianEntity(this.entityVersionTemplate, ((CraftEntity)guardian).getHandle(), guardian.getEntityId(), guardian.getLocation());
      }
    }

    return null;
  }

}
