package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.BreedableAnimalEntity;

public interface HoglinEntity extends BreedableAnimalEntity<HoglinEntity> {

  boolean isImmuneToZombification();

  void setImmuneToZombification(boolean immuneToZombification);

  boolean isHuntable();

  void setHuntable(boolean huntable);

  int getConversionTime();

  void setConversionTime(int conversionTime);

  boolean isConverting();

}
