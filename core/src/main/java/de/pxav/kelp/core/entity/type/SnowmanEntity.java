package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.GolemEntity;

public interface SnowmanEntity extends GolemEntity<SnowmanEntity> {

  boolean hasPumpkin();

  SnowmanEntity setPumpkinHead(boolean pumpkinHead);

}
