package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.entity.version.EntityVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import org.bukkit.Location;
import org.bukkit.entity.Item;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class DroppedItemEntity extends KelpEntity {

  private KelpItem item;
  private ItemDropType itemDropType;

  public static DroppedItemEntity from(Item item) {
    return (DroppedItemEntity) KelpPlugin.getInjector().getInstance(EntityTypeVersionTemplate.class).getKelpEntity(item);
  }

  public DroppedItemEntity() {}

  public DroppedItemEntity(EntityVersionTemplate entityVersionTemplate, Object entity, int entityId, Location location, KelpItem item) {
    super(entity, KelpEntityType.DROPPED_ITEM, location, entityId, entityVersionTemplate);
    this.item = item;
  }

  public KelpItem getItem() {
    return item;
  }

  public void setItem(KelpItem item) {
    this.item = item;
  }

  public ItemDropType getItemDropType() {
    return itemDropType;
  }

  public void setItemDropType(ItemDropType itemDropType) {
    this.itemDropType = itemDropType;
  }

}
