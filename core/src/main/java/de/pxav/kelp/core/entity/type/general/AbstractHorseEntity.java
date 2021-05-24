package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.inventory.type.AbstractHorseInventory;

public interface AbstractHorseEntity<T extends AbstractHorseEntity<?>> extends TameableAnimal<T> {

  int getDomestication();

  AbstractHorseInventory<?> getInventory();

  double getJumpStrength();

  int getMaximumDomestication();

  T setDomestication(int domesticationLevel);

  T setJumpStrength(double jumpStrength);

  T setMaximumDomestication(int maximumDomestication);

}
