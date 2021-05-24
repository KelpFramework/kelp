package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.ChestedHorse;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.version.InventoryVersionTemplate;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityHorse;
import org.bukkit.Location;

public class VersionedChestedHorse<T extends ChestedHorse<T>> extends VersionedAbstractHorse<T> implements ChestedHorse<T> {

  private EntityHorse horseHandle;

  public VersionedChestedHorse(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate, InventoryVersionTemplate inventoryVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate, inventoryVersionTemplate);
    this.horseHandle = (EntityHorse) entityHandle;
    this.horseHandle.setHasChest(true);
  }

  @Override
  public boolean isCarryingChest() {
    return horseHandle.hasChest();
  }

  @Override
  public T setCarryingChest(boolean carryingChest) {
    horseHandle.setHasChest(carryingChest);
    return (T) this;
  }

}
