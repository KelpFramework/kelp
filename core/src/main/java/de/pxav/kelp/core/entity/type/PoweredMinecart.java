package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.MinecartEntity;

public interface PoweredMinecart extends MinecartEntity<PoweredMinecart> {

  int getFuelTicks();

  PoweredMinecart setFuelTicks(int fuelTicks);

}
