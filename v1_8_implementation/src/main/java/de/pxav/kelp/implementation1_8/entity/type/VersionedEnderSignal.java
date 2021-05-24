package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.EnderSignalEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.implementation1_8.entity.VersionedEntity;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityEnderSignal;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEnderSignal;

public class VersionedEnderSignal extends VersionedEntity<EnderSignalEntity> implements EnderSignalEntity {

  private CraftEnderSignal craftEnderSignal;
  private EntityEnderSignal signalHandle;

  public VersionedEnderSignal(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
  }

  // todo: maybe the target location can be implemented using the a, b, and c field of EntityEnderSignal class?
  // todo: check if they represent the target location
  @Override
  public KelpLocation getTargetLocation() {
    return null;
  }

  @Override
  public EnderSignalEntity setTargetLocation(KelpLocation var1) {
    return this;
  }

  @Override
  public boolean getDropItem() {
    return (boolean) ReflectionUtil.getValue(signalHandle, "e");
  }

  @Override
  public EnderSignalEntity setDropItem(boolean dropItem) {
    ReflectionUtil.setValue(signalHandle, "e", dropItem);
    return this;
  }

  @Override
  public KelpItem getItem() {
    return null;
  }

  @Override
  public EnderSignalEntity setItem(KelpItem item) {
    return this;
  }

  @Override
  public int getDespawnTimer() {
    return 0;
  }

  @Override
  public EnderSignalEntity setDespawnTimer(int despawnTimer) {
    return this;
  }
}
