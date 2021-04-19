package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.EulerAngle;

public interface ArmorStandEntity extends KelpEntity<ArmorStandEntity> {

  ItemStack getItemInHand();

  void setItemInHand(ItemStack var1);

  ItemStack getBoots();
  
  void setBoots(ItemStack var1);
  
  ItemStack getLeggings();
  
  void setLeggings(ItemStack var1);
  
  ItemStack getChestplate();
  
  void setChestplate( ItemStack var1);
  
  ItemStack getHelmet();
  
  void setHelmet(ItemStack var1);
  
  EulerAngle getBodyPose();

  void setBodyPose(EulerAngle var1);
  
  EulerAngle getLeftArmPose();

  void setLeftArmPose(EulerAngle var1);
  
  EulerAngle getRightArmPose();

  void setRightArmPose(EulerAngle var1);
  
  EulerAngle getLeftLegPose();

  void setLeftLegPose(EulerAngle var1);

  EulerAngle getRightLegPose();

  void setRightLegPose(EulerAngle var1);

  EulerAngle getHeadPose();

  void setHeadPose(EulerAngle var1);

  boolean hasBasePlate();

  void setBasePlate(boolean var1);

  boolean isVisible();

  void setVisible(boolean var1);

  boolean hasArms();

  void setArms(boolean var1);

  boolean isSmall();

  void setSmall(boolean var1);

  boolean isMarker();

  void setMarker(boolean var1);

  void addEquipmentLock(EquipmentSlot var1, ArmorStand.LockType var2);

  void removeEquipmentLock(EquipmentSlot var1, ArmorStand.LockType var2);

  boolean hasEquipmentLock(EquipmentSlot var1, ArmorStand.LockType var2);

  enum LockType {
    ADDING_OR_CHANGING,
    REMOVING_OR_CHANGING,
    ADDING;

    LockType() {
    }
  }

}
