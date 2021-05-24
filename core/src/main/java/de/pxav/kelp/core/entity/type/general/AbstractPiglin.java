package de.pxav.kelp.core.entity.type.general;

import org.bukkit.entity.PiglinAbstract;

public interface AbstractPiglin<T extends AbstractPiglin<?>> extends AgeableEntity<T>, MonsterEntity<T> {

  boolean isImmuneToZombification();

  void setImmuneToZombification(boolean immuneToZombification);

  int getConversionTime();

  void setConversionTime(int conversionTime);

  boolean isConverting();

}
