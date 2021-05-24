package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.FireworkEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.metadata.FireworkMetadata;
import de.pxav.kelp.core.inventory.metadata.ItemMetadataVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedProjectile;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftFirework;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

import java.util.concurrent.ThreadLocalRandom;

public class VersionedFirework extends VersionedProjectile<FireworkEntity> implements FireworkEntity {

  CraftFirework craftFirework;
  private ItemMetadataVersionTemplate itemMetadataVersionTemplate;

  public VersionedFirework(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate, ItemMetadataVersionTemplate itemMetadataVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.craftFirework = (CraftFirework) entityHandle.getBukkitEntity();
    this.itemMetadataVersionTemplate = itemMetadataVersionTemplate;
  }

  @Override
  public FireworkEntity detonate() {
    craftFirework.detonate();
    return this;
  }

  @Override
  public FireworkMetadata getFireworkMetadata() {
    return (FireworkMetadata) itemMetadataVersionTemplate.getMetadata(craftFirework.getFireworkMeta());
  }

  @Override
  public FireworkEntity setFireworkMetadata(FireworkMetadata fireworkMetadata) {
    CraftItemStack itemStack = (CraftItemStack) ReflectionUtil.getValue(craftFirework, "item");
    fireworkMetadata.applyTo(itemStack);
    ReflectionUtil.setValue(craftFirework, "item", itemStack);
    craftFirework.getHandle().expectedLifespan = 10 * (1 + fireworkMetadata.getHeight()) + ThreadLocalRandom.current().nextInt(6) + ThreadLocalRandom.current().nextInt(7);
    craftFirework.getHandle().getDataWatcher().update(8);
    return null;
  }

  @Override
  public boolean isShotAtAngle() {
    return false;
  }

  @Override
  public FireworkEntity setShotAtAngle(boolean shotAtAngle) {
    return this;
  }
}
