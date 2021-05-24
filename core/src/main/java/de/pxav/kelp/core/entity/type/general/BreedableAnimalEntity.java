package de.pxav.kelp.core.entity.type.general;

public interface BreedableAnimalEntity<T extends BreedableAnimalEntity<?>> extends AnimalEntity<T> {

  boolean canBreed();

  T setBreedable(boolean breedable);

}
