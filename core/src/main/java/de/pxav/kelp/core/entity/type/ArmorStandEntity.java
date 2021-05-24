package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.type.SimpleEntityEquipment;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.util.EulerAngle;

public interface ArmorStandEntity extends KelpEntity<ArmorStandEntity> {

  /**
   * Creates a new entity of this type at the given location.
   *
   * While this creates a new instance, it won't actually spawn the entity.
   * You can first do some modifications on it and then call the
   * {@link KelpEntity#spawn()} method.
   *
   * If you don't want to create a new entity, but just a new kelp
   * entity instance based of an existing bukkit entity, you can use
   * {@link #from(Entity)} instead.
   *
   * @param location The location, where the entity should be spawned later.
   * @return A new instance of a sheep entity.
   */
  static ArmorStandEntity create(KelpLocation location) {
    return (ArmorStandEntity) KelpPlugin.getInjector().getInstance(EntityTypeVersionTemplate.class)
      .newKelpEntity(getEntityType(), location.getBukkitLocation());
  }

  /**
   * Takes a bukkit entity and converts it to a kelp entity of the same type.
   *
   * This can be used if you are for example handling an event that returns a bukkit entity,
   * but you want to use a kelp entity for your operations. You can also use
   * the more general method provided by {@link de.pxav.kelp.core.entity.KelpEntity the
   * kelp entity base class}: {@link de.pxav.kelp.core.entity.KelpEntity#from(Entity)},
   * but this way you don't have to cast your entity to the specific type
   * manually.
   *
   * @param entity The entity you want to convert.
   * @return
   */
  static ArmorStandEntity from(Entity entity) {
    return (ArmorStandEntity) KelpPlugin.getInjector().getInstance(EntityTypeVersionTemplate.class)
      .getKelpEntity(entity);
  }

  static KelpEntityType getEntityType() {
    return KelpEntityType.ARMOR_STAND;
  }

  @Override
  default KelpEntityType getType() {
    return getEntityType();
  }
  
  EulerAngle getBodyPose();

  ArmorStandEntity setBodyPose(EulerAngle bodyPose);
  
  EulerAngle getLeftArmPose();

  ArmorStandEntity setLeftArmPose(EulerAngle leftArmPose);
  
  EulerAngle getRightArmPose();

  ArmorStandEntity setRightArmPose(EulerAngle rightArmPose);
  
  EulerAngle getLeftLegPose();

  ArmorStandEntity setLeftLegPose(EulerAngle leftLegPose);

  EulerAngle getRightLegPose();

  ArmorStandEntity setRightLegPose(EulerAngle rightLegPose);

  EulerAngle getHeadPose();

  ArmorStandEntity setHeadPose(EulerAngle headPose);

  boolean hasBasePlate();

  ArmorStandEntity setBasePlate(boolean basePlate);

  boolean isVisible();

  ArmorStandEntity setVisible(boolean visible);

  boolean hasArms();

  ArmorStandEntity setArms(boolean arms);

  boolean isSmall();

  ArmorStandEntity setSmall(boolean small);

  boolean isMarker();

  ArmorStandEntity setMarker(boolean marker);

  SimpleEntityEquipment getEquipment();

//  void addEquipmentLock(EquipmentSlot var1, ArmorStand.LockType var2);
//
//  void removeEquipmentLock(EquipmentSlot var1, ArmorStand.LockType var2);
//
//  boolean hasEquipmentLock(EquipmentSlot var1, ArmorStand.LockType var2);

}
