package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.ArmorStandEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.type.SimpleEntityEquipment;
import de.pxav.kelp.implementation1_8.entity.VersionedEntity;
import de.pxav.kelp.implementation1_8.entity.VersionedSimpleEntityEquipment;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityArmorStand;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftArmorStand;
import org.bukkit.util.EulerAngle;

public class VersionedArmorStand extends VersionedEntity<ArmorStandEntity> implements ArmorStandEntity {

  CraftArmorStand craftArmorStand;
  EntityArmorStand armorStandHandle;

  public VersionedArmorStand(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.craftArmorStand = (CraftArmorStand) entityHandle.getBukkitEntity();
    this.armorStandHandle = (EntityArmorStand) entityHandle;
  }

  @Override
  public EulerAngle getBodyPose() {
    return craftArmorStand.getBodyPose();
  }

  @Override
  public ArmorStandEntity setBodyPose(EulerAngle bodyPose) {
    craftArmorStand.setBodyPose(bodyPose);
    return this;
  }

  @Override
  public EulerAngle getLeftArmPose() {
    return craftArmorStand.getLeftArmPose();
  }

  @Override
  public ArmorStandEntity setLeftArmPose(EulerAngle leftArmPose) {
    craftArmorStand.setLeftArmPose(leftArmPose);
    return this;
  }

  @Override
  public EulerAngle getRightArmPose() {
    return craftArmorStand.getRightArmPose();
  }

  @Override
  public ArmorStandEntity setRightArmPose(EulerAngle rightArmPose) {
    craftArmorStand.setRightArmPose(rightArmPose);
    return this;
  }

  @Override
  public EulerAngle getLeftLegPose() {
    return craftArmorStand.getLeftLegPose();
  }

  @Override
  public ArmorStandEntity setLeftLegPose(EulerAngle leftLegPose) {
    craftArmorStand.setLeftLegPose(leftLegPose);
    return this;
  }

  @Override
  public EulerAngle getRightLegPose() {
    return craftArmorStand.getRightLegPose();
  }

  @Override
  public ArmorStandEntity setRightLegPose(EulerAngle rightLegPose) {
    craftArmorStand.setRightLegPose(rightLegPose);
    return this;
  }

  @Override
  public EulerAngle getHeadPose() {
    return craftArmorStand.getHeadPose();
  }

  @Override
  public ArmorStandEntity setHeadPose(EulerAngle headPose) {
    craftArmorStand.setHeadPose(headPose);
    return this;
  }

  @Override
  public boolean hasBasePlate() {
    return armorStandHandle.hasBasePlate();
  }

  @Override
  public ArmorStandEntity setBasePlate(boolean basePlate) {
    armorStandHandle.setBasePlate(basePlate);
    return this;
  }

  @Override
  public boolean isVisible() {
    return craftArmorStand.isVisible();
  }

  @Override
  public ArmorStandEntity setVisible(boolean visible) {
    craftArmorStand.setVisible(visible);
    return this;
  }

  @Override
  public boolean hasArms() {
    return armorStandHandle.hasArms();
  }

  @Override
  public ArmorStandEntity setArms(boolean arms) {
    armorStandHandle.setArms(arms);
    return this;
  }

  @Override
  public boolean isSmall() {
    return armorStandHandle.isSmall();
  }

  @Override
  public ArmorStandEntity setSmall(boolean small) {
    armorStandHandle.setSmall(small);
    return this;
  }

  @Override
  public boolean isMarker() {
    return craftArmorStand.isMarker();
  }

  @Override
  public ArmorStandEntity setMarker(boolean marker) {
    craftArmorStand.setMarker(marker);
    return this;
  }

  @Override
  public SimpleEntityEquipment getEquipment() {
    return new VersionedSimpleEntityEquipment(craftArmorStand);
  }
}
