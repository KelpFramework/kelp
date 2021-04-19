package de.pxav.kelp.core.entity.type.general;

public interface AgeableEntity<T extends AgeableEntity<?>> extends MobileEntity<T> {

  boolean isAgeLocked();

  T setAgeLocked(boolean ageLocked);

  default T makeBaby() {
    setBaby(true);
    return (T) this;
  }

  T setBaby(boolean baby);

  default T setAdult(boolean adult) {
    setBaby(!adult);
    return (T) this;
  }

  default T makeAdult() {
    setAdult(true);
    return (T) this;
  }

  default boolean isAdult() {
    return !isBaby();
  }

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
