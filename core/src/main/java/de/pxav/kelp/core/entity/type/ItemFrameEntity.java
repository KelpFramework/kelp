package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.HangingEntity;
import de.pxav.kelp.core.inventory.item.KelpItem;
import org.bukkit.Rotation;
import org.bukkit.entity.ItemFrame;

// hanging, attachable
public interface ItemFrameEntity extends HangingEntity<ItemFrameEntity> {

  Rotation getItemRotation();

  ItemFrameEntity setItemRotation(Rotation rotation);

  ItemFrameEntity setFixed(boolean fixed);

  boolean isFixed();

  ItemFrameEntity setVisible(boolean visible);

  boolean isVisible();

  KelpItem getItem();

  ItemFrameEntity setItem(KelpItem item);

  ItemFrameEntity setItemAndPlayPlacementSound(KelpItem item);

  double getItemDropChance();

  ItemFrameEntity setItemDropChance(double itemDropChance);

}
