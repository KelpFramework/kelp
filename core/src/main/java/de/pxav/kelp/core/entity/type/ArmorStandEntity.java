package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.type.SimpleEntityEquipment;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.entity.ArmorStand;
import org.bukkit.util.EulerAngle;

public interface ArmorStandEntity extends KelpEntity<ArmorStandEntity> {

  static ArmorStandEntity create(KelpLocation location) {
    return (ArmorStandEntity) KelpPlugin.getInjector().getInstance(EntityTypeVersionTemplate.class)
      .newKelpEntity(KelpEntityType.ARMOR_STAND, location.getBukkitLocation());
  }

  static ArmorStandEntity from(ArmorStand armorStand) {
    return (ArmorStandEntity) KelpPlugin.getInjector().getInstance(EntityTypeVersionTemplate.class)
      .getKelpEntity(armorStand);
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
