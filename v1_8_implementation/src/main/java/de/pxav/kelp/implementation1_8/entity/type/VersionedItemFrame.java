package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.ItemFrameEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.entity.version.EntityVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.implementation1_8.entity.FixedItemFrameListener;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedHangingEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityItemFrame;
import org.bukkit.Location;
import org.bukkit.Rotation;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHorse;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftItemFrame;
import org.bukkit.entity.Item;

public class VersionedItemFrame extends VersionedHangingEntity<ItemFrameEntity> implements ItemFrameEntity {

  private CraftItemFrame craftItemFrame;
  private EntityItemFrame itemFrameHandle;
  private FixedItemFrameListener fixedItemFrame;

  public VersionedItemFrame(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate, FixedItemFrameListener fixedItemFrame) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.craftItemFrame = (CraftItemFrame) entityHandle.getBukkitEntity();
    this.itemFrameHandle = (EntityItemFrame) entityHandle;
    this.fixedItemFrame = fixedItemFrame;
  }

  @Override
  public Rotation getItemRotation() {
    return craftItemFrame.getRotation();
  }

  @Override
  public ItemFrameEntity setItemRotation(Rotation rotation) {
    craftItemFrame.setRotation(rotation);
    return this;
  }

  // not natively available before 1.16
  @Override
  public ItemFrameEntity setFixed(boolean fixed) {
    if (fixed) {
      this.fixedItemFrame.fixItemFrame(this);
    } else {
      this.fixedItemFrame.unfixItemFrame(this);
    }
    return this;
  }

  @Override
  public boolean isFixed() {
    return this.fixedItemFrame.isItemFrameFixed(this);
  }

  @Override
  public ItemFrameEntity setVisible(boolean visible) {
    return this;
  }

  // not available before 1.16
  @Override
  public boolean isVisible() {
    return true;
  }

  @Override
  public KelpItem getItem() {
    return KelpItem.from(craftItemFrame.getItem());
  }

  @Override
  public ItemFrameEntity setItem(KelpItem item) {
    craftItemFrame.setItem(item.getItemStack());
    return this;
  }

  @Override
  public ItemFrameEntity setItemAndPlayPlacementSound(KelpItem item) {
    //todo placement sound not available before 1.9.
    //maybe simply add a block placement sound here?
    return this;
  }

  @Override
  public double getItemDropChance() {
    return (double) ((float) ReflectionUtil.getValue(itemFrameHandle, "c")) ;
  }

  @Override
  public ItemFrameEntity setItemDropChance(double itemDropChance) {
    ReflectionUtil.setValue(itemFrameHandle, "c", (float) itemDropChance);
    return this;
  }

}
