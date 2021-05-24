package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.HorseEntity;
import de.pxav.kelp.core.entity.util.HorseColor;
import de.pxav.kelp.core.entity.util.HorseStyle;
import de.pxav.kelp.core.entity.version.EntityConstantsVersionTemplate;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.version.InventoryVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedAbstractHorse;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHorse;
import org.bukkit.entity.Horse;

public class VersionedHorse extends VersionedAbstractHorse<HorseEntity> implements HorseEntity {

  private CraftHorse craftHorse;
  private EntityConstantsVersionTemplate entityConstantsVersionTemplate;

  public VersionedHorse(Entity entityHandle, KelpEntityType entityType, Location initialLocation,
                        EntityTypeVersionTemplate entityTypeVersionTemplate,
                        InventoryVersionTemplate inventoryVersionTemplate,
                        EntityConstantsVersionTemplate entityConstantsVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate, inventoryVersionTemplate);
    this.craftHorse = (CraftHorse) entityHandle.getBukkitEntity();
    this.entityConstantsVersionTemplate = entityConstantsVersionTemplate;
  }

  @Override
  public HorseColor getHorseColor() {
    return entityConstantsVersionTemplate.getHorseColor(craftHorse.getColor().name());
  }

  @Override
  public HorseStyle getHorseStyle() {
    return entityConstantsVersionTemplate.getHorseStyle(craftHorse.getStyle().name());
  }

  @Override
  public HorseEntity setHorseColor(HorseColor color) {
    craftHorse.setColor(Horse.Color.valueOf(entityConstantsVersionTemplate.getHorseColor(color)));
    return this;
  }

  @Override
  public HorseEntity setHorseStyle(HorseStyle style) {
    craftHorse.setStyle(Horse.Style.valueOf(entityConstantsVersionTemplate.getHorseStyle(style)));
    return this;
  }

}
