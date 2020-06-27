package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.inventory.item.KelpItem;
import org.bukkit.Location;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class DroppedItemEntity extends KelpEntity {

  private KelpItem item;

  public DroppedItemEntity() {}

  public DroppedItemEntity(Object entity, int entityId, Location location, KelpItem item) {
    super(entity, KelpEntityType.DROPPED_ITEM, location, entityId);
    this.item = item;
  }

  public KelpItem getItem() {
    return item;
  }

  public void setItem(KelpItem item) {
    this.item = item;
  }
}
