package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.WolfEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedTameableAnimal;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftWolf;

public class VersionedWolf extends VersionedTameableAnimal<WolfEntity> implements WolfEntity {

  private CraftWolf craftWolf;

  public VersionedWolf(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
  }

  @Override
  public boolean isAngry() {
    return craftWolf.isAngry();
  }

  @Override
  public WolfEntity setAngry(boolean angry) {
    craftWolf.setAngry(angry);
    return this;
  }

  @Override
  public Color getCollarDyeColor() {
    return Color.fromBukkit(craftWolf.getCollarColor().getColor());
  }

  @Override
  public WolfEntity setCollarDyeColor(Color color) {
    craftWolf.setCollarColor(DyeColor.getByColor(color.getBukkitColor()));
    return this;
  }

}
