package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.MobileEntity;

public interface PhantomEntity extends MobileEntity<PhantomEntity> {

  int getPhantomSize();

  PhantomEntity setPhantomSize(int phantomSize);

}
