package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntity;

public interface AnimalEntity<T extends AnimalEntity<?>> extends MobileEntity<T> {

    boolean isInLove();

    T setInLove(boolean inLove);

    KelpEntity<?> getBreeder();

    T setBreeder(KelpEntity<?> breeder);

    int getLoveModeTicks();

    T setLoveModeTicks(int loveModeTicks);

}
