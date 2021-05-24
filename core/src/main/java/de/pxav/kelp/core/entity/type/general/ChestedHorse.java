package de.pxav.kelp.core.entity.type.general;

public interface ChestedHorse<T extends ChestedHorse<?>> extends AbstractHorseEntity<T> {

  boolean isCarryingChest();

  T setCarryingChest(boolean carryingChest);

}
