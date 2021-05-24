package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.AbstractVillager;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.type.StorageInventory;
import de.pxav.kelp.core.inventory.version.InventoryVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftVillager;

public class VersionedAbstractVillager<T extends AbstractVillager<T>> extends VersionedHumanEntity<T> implements AbstractVillager<T> {

  private InventoryVersionTemplate inventoryVersionTemplate;
  private CraftVillager craftVillager;

  public VersionedAbstractVillager(Entity entityHandle,
                                   KelpEntityType entityType,
                                   Location initialLocation,
                                   EntityTypeVersionTemplate entityTypeVersionTemplate,
                                   InventoryVersionTemplate inventoryVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.inventoryVersionTemplate = inventoryVersionTemplate;
    this.craftVillager = (CraftVillager) entityHandle.getBukkitEntity();
  }

  @Override
  public StorageInventory<?> getStorageInventory() {
    return inventoryVersionTemplate.getStorageInventory(craftVillager.getInventory());
  }

}
