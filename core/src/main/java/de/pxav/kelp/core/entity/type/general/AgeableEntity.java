package de.pxav.kelp.core.entity.type.general;

public interface AgeableEntity<T extends AgeableEntity<?>> {

  T makeBaby();

  T setBaby(boolean baby);

  T setAdult(boolean adult);

  T makeAdult();

  boolean isAdult();

  boolean isBaby();

  T setAge(int age);

  default T toggleMaxAge() {
    if (isBaby()) {
      makeAdult();
    } else {
      makeBaby();
    }
    return (T) this;
  }

}
