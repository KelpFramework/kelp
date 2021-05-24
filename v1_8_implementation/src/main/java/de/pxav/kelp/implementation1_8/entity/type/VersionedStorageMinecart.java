package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.StorageMinecart;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.type.StorageInventory;
import de.pxav.kelp.core.inventory.version.InventoryVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedAbstractMinecart;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftMinecartChest;

public class VersionedStorageMinecart extends VersionedAbstractMinecart<StorageMinecart> implements StorageMinecart {

  private CraftMinecartChest craftMinecart;
  private InventoryVersionTemplate inventoryVersionTemplate;

  public VersionedStorageMinecart(Entity entityHandle,
                                  KelpEntityType entityType,
                                  Location initialLocation,
                                  EntityTypeVersionTemplate entityTypeVersionTemplate,
                                  InventoryVersionTemplate inventoryVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    craftMinecart = (CraftMinecartChest) entityHandle.getBukkitEntity();
    this.inventoryVersionTemplate = inventoryVersionTemplate;
  }

  @Override
  public StorageInventory<?> getInventory() {
    return inventoryVersionTemplate.getStorageInventory(craftMinecart.getInventory());
  }

}
