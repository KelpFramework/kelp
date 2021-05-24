package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.VillagerEntity;
import de.pxav.kelp.core.entity.util.VillagerProfession;
import de.pxav.kelp.core.entity.util.VillagerType;
import de.pxav.kelp.core.entity.version.EntityConstantsVersionTemplate;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.version.InventoryVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedAbstractVillager;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;
import org.bukkit.entity.Villager;

public class VersionedVillager extends VersionedAbstractVillager<VillagerEntity> implements VillagerEntity {

  private CraftVillager craftVillager;
  private EntityConstantsVersionTemplate entityConstantsVersionTemplate;

  public VersionedVillager(Entity entityHandle,
                           KelpEntityType entityType,
                           Location initialLocation,
                           EntityTypeVersionTemplate entityTypeVersionTemplate,
                           InventoryVersionTemplate inventoryVersionTemplate,
                           EntityConstantsVersionTemplate entityConstantsVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate, inventoryVersionTemplate);
    this.entityConstantsVersionTemplate = entityConstantsVersionTemplate;
    this.craftVillager = (CraftVillager) entityHandle.getBukkitEntity();
  }

  @Override
  public VillagerProfession getProfession() {
    return entityConstantsVersionTemplate.getVillagerProfession(craftVillager.getProfession().name());
  }

  @Override
  public VillagerEntity setProfession(VillagerProfession profession) {
    craftVillager.setProfession(Villager.Profession.valueOf(entityConstantsVersionTemplate.getVillagerProfession(profession)));
    return this;
  }

  // villagers have no types in 1.8

  @Override
  public VillagerType getVillagerType() {
    return VillagerType.NONE;
  }

  @Override
  public VillagerEntity setVillagerType(VillagerType villagerType) {
    return this;
  }


  // not available in 1.8

  @Override
  public int getVillagerExperience() {
    return 0;
  }

  @Override
  public int getVillagerLevel() {
    return 0;
  }

  @Override
  public VillagerEntity setVillagerLevel(int villagerLevel) {
    return this;
  }

  @Override
  public VillagerEntity setVillagerExperience() {
    return this;
  }

}
