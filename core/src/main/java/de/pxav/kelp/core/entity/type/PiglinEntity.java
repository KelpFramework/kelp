package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.AbstractPiglin;

public interface PiglinEntity extends AbstractPiglin<PiglinEntity> {

  boolean isAbleToHunt();

  PiglinEntity setAbleToHunt(boolean ableToHunt);

}
