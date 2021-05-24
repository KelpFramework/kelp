package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.DroppedItemEntity;
import de.pxav.kelp.core.entity.util.ItemDropType;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.implementation1_8.entity.VersionedEntity;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItem;

public class VersionedItem extends VersionedEntity<DroppedItemEntity> implements DroppedItemEntity {

  private CraftItem craftItem;
  private ItemDropType itemDropType;

  public VersionedItem(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.craftItem = (CraftItem) entityHandle.getBukkitEntity();
  }

  @Override
  public KelpItem getItem() {
    return KelpItem.from(craftItem.getItemStack());
  }

  @Override
  public void setItem(KelpItem item) {
    craftItem.setItemStack(item.getItemStack());
  }

  @Override
  public ItemDropType getItemDropType() {
    return itemDropType;
  }

  @Override
  public void setItemDropType(ItemDropType itemDropType) {
    this.itemDropType = itemDropType;
  }

}
