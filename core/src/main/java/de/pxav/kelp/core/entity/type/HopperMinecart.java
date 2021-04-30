package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.MinecartEntity;

public interface HopperMinecart extends MinecartEntity<HopperMinecart> {

  boolean isHopperEnabled();

  HopperMinecart setHopperEnabled(boolean enabled);

}
