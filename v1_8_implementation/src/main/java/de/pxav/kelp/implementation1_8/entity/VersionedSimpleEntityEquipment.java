package de.pxav.kelp.implementation1_8.entity;

import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.type.SimpleEntityEquipment;
import org.bukkit.entity.LivingEntity;

public class VersionedSimpleEntityEquipment implements SimpleEntityEquipment {

  private LivingEntity bukkitEntity;

  public VersionedSimpleEntityEquipment(LivingEntity bukkitEntity) {
    this.bukkitEntity = bukkitEntity;
  }

  @Override
  public SimpleEntityEquipment setHelmet(KelpItem helmet) {
    bukkitEntity.getEquipment().setHelmet(helmet.getItemStack());
    return this;
  }

  @Override
  public SimpleEntityEquipment setChestPlate(KelpItem chestPlate) {
    bukkitEntity.getEquipment().setHelmet(chestPlate.getItemStack());
    return this;
  }

  @Override
  public SimpleEntityEquipment setLeggings(KelpItem leggings) {
    bukkitEntity.getEquipment().setHelmet(leggings.getItemStack());
    return this;
  }

  @Override
  public SimpleEntityEquipment setBoots(KelpItem boots) {
    bukkitEntity.getEquipment().setHelmet(boots.getItemStack());
    return this;
  }

  @Override
  public KelpItem getHelmet() {
    return KelpItem.from(bukkitEntity.getEquipment().getHelmet());
  }

  @Override
  public KelpItem getChestPlate() {
    return KelpItem.from(bukkitEntity.getEquipment().getChestplate());
  }

  @Override
  public KelpItem getLeggings() {
    return KelpItem.from(bukkitEntity.getEquipment().getLeggings());
  }

  @Override
  public KelpItem getBoots() {
    return KelpItem.from(bukkitEntity.getEquipment().getBoots());
  }

  @Override
  public KelpItem getItemInHand() {
    return KelpItem.from(bukkitEntity.getEquipment().getItemInHand());
  }

  @Override
  public SimpleEntityEquipment setItemInHand(KelpItem item) {
    bukkitEntity.getEquipment().setHelmet(item.getItemStack());
    return this;
  }

  @Override
  public KelpItem getItemInOffHand() {
    return null;
  }

  @Override
  public SimpleEntityEquipment setItemInOffHand(KelpItem item) {
    return this;
  }

}
