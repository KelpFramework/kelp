package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.util.ItemDropType;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import org.bukkit.entity.Item;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public interface DroppedItemEntity extends KelpEntity<DroppedItemEntity> {

  static DroppedItemEntity from(Item item) {
    return (DroppedItemEntity) KelpPlugin.getInjector().getInstance(EntityTypeVersionTemplate.class).getKelpEntity(item);
  }

  KelpItem getItem();

  void setItem(KelpItem item);

  ItemDropType getItemDropType();

  void setItemDropType(ItemDropType itemDropType);

}
