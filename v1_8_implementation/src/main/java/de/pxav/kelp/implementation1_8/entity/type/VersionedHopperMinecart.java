package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.HopperMinecart;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedAbstractMinecart;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityMinecartHopper;
import org.bukkit.Location;

public class VersionedHopperMinecart extends VersionedAbstractMinecart<HopperMinecart> implements HopperMinecart {

  private EntityMinecartHopper minecartHandle;

  public VersionedHopperMinecart(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.minecartHandle = (EntityMinecartHopper) entityHandle;
  }

  @Override
  public boolean isHopperEnabled() {
    return minecartHandle.y();
  }

  @Override
  public HopperMinecart setHopperEnabled(boolean enabled) {
    minecartHandle.i(enabled);
    return this;
  }

}
