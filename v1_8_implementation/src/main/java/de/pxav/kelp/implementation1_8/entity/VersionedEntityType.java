package de.pxav.kelp.implementation1_8.entity;

import com.google.inject.Inject;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.version.EntityVersionTemplate;
import de.pxav.kelp.core.entity.version.LivingEntityVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.core.version.Versioned;
import de.pxav.kelp.implementation1_8.entity.type.VersionedSheep;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.Sheep;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedEntityType extends EntityTypeVersionTemplate {

  private EntityVersionTemplate entityVersionTemplate;
  private LivingEntityVersionTemplate livingEntityVersionTemplate;
  private ReflectionUtil reflectionUtil;

  @Inject
  public VersionedEntityType(EntityVersionTemplate entityVersionTemplate,
                             LivingEntityVersionTemplate livingEntityVersionTemplate,
                             ReflectionUtil reflectionUtil) {
    this.entityVersionTemplate = entityVersionTemplate;
    this.livingEntityVersionTemplate = livingEntityVersionTemplate;
    this.reflectionUtil = reflectionUtil;
  }

  @Override
  public KelpEntity<?> newKelpEntity(KelpEntityType entityType, Location location) {
    KelpEntity<?> output = null;
    Entity entity = null; // net.minecraft entity, not a bukkit one
    CraftWorld craftWorld = (CraftWorld) location.getWorld();

    switch (entityType) {
//      case GUARDIAN:
//        entity = craftWorld.createEntity(location, Guardian.class);
//        output = new VersionedGuardian(entity, KelpEntityType.GUARDIAN, location, entity.getId(), entityVersionTemplate, livingEntityVersionTemplate);
//        break;
//      case ELDER_GUARDIAN:
//        entity = craftWorld.createEntity(location, Guardian.class);
//        CraftGuardian guardian = (CraftGuardian) entity.getBukkitEntity();
//        guardian.setElder(true);
//        output = new VersionedElderGuardian(entity, KelpEntityType.ELDER_GUARDIAN, location, entity.getId(), entityVersionTemplate, livingEntityVersionTemplate);
//        break;
//      case ZOMBIE:
//        entity = craftWorld.createEntity(location, Zombie.class);
//        output = new VersionedZombie(entity, entityType, location, entity.getId(), entityVersionTemplate, livingEntityVersionTemplate);
//        break;
//      case DROPPED_ITEM:
//        entity = null;
//        break;
      case SHEEP:
        entity = craftWorld.createEntity(location, Sheep.class);
        output = new VersionedSheep(entity, KelpEntityType.SHEEP, location, this, reflectionUtil);
    }

//    if (entityType != KelpEntityType.DROPPED_ITEM) {
//      output.entityId(entity.getId());
//      output.entityType(entityType);
//      output.minecraftEntity(entity);
//      output.initialLocation(location);
//      output.versionTemplate(this.entityVersionTemplate);
//    }


//    if (KelpEntityType.isLivingEntity(entityType)) {
//      LivingEntity livingEntity = (CraftLivingEntity) entity.getBukkitEntity();
//      ((LivingKelpEntity)output).bukkitLivingEntity(livingEntity);
//      ((LivingKelpEntity)output).livingEntityVersionTemplate(livingEntityVersionTemplate);
//    }

    return output;
  }

  @Override
  public KelpEntity<?> getKelpEntity(org.bukkit.entity.Entity bukkitEntity) {

//    if (bukkitEntity instanceof Item) {
//      Item item = (Item) bukkitEntity;
//      KelpItem kelpItem = KelpItem.from(item.getItemStack());
//      return new DroppedItemEntity(this.entityVersionTemplate,
//        ((CraftEntity)item).getHandle(),
//        item.getEntityId(),
//        item.getLocation(),
//        kelpItem);
//    }
//
//    if (bukkitEntity instanceof Zombie) {
//      Zombie zombie = (Zombie) bukkitEntity;
//      LivingEntity livingEntity = (LivingEntity) bukkitEntity;
//      return new ZombieEntity(this.entityVersionTemplate,
//        this.livingEntityVersionTemplate,
//        livingEntity,
//        ((CraftEntity)zombie).getHandle(),
//        zombie.getEntityId(),
//        zombie.getLocation(),
//        zombie.isBaby());
//    }
//
//    if (bukkitEntity instanceof Guardian) {
//      Guardian guardian = (Guardian) bukkitEntity;
//      if (guardian.isElder()) {
//        return new ElderGuardianEntity(this.entityVersionTemplate, ((CraftEntity)guardian).getHandle(), guardian.getEntityId(), guardian.getLocation());
//      } else {
//        return new GuardianEntity(this.entityVersionTemplate, ((CraftEntity)guardian).getHandle(), guardian.getEntityId(), guardian.getLocation());
//      }
//    }



    return null;
  }

}
