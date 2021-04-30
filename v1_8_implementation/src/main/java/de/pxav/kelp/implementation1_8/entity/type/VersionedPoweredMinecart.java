package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.PoweredMinecart;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedAbstractMinecart;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityMinecartFurnace;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftMinecartFurnace;

public class VersionedPoweredMinecart extends VersionedAbstractMinecart<PoweredMinecart> implements PoweredMinecart {

  private EntityMinecartFurnace furnaceMinecart;

  public VersionedPoweredMinecart(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.furnaceMinecart = (EntityMinecartFurnace) entityHandle;
  }

  @Override
  public int getFuelTicks() {
    return (int) ReflectionUtil.getValue(furnaceMinecart, "c");
  }

  @Override
  public PoweredMinecart setFuelTicks(int fuelTicks) {
    ReflectionUtil.setValue(furnaceMinecart, "c", fuelTicks);
    return this;
  }

}
