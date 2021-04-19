package de.pxav.kelp.core.entity.type;

import org.bukkit.entity.Husk;

public interface HuskEntity extends ZombieEntity {

  boolean isConverting();

  int getConversionTime();

  void setConversionTime(int conversionTime);

}
