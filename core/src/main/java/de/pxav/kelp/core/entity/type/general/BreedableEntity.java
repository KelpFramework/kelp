package de.pxav.kelp.core.entity.type.general;

public interface BreedableEntity<T extends BreedableEntity<?>> extends MobileEntity<T> {

  boolean canBreed();

  T setBreedable(boolean breedable);

}
