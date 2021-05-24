package de.pxav.kelp.core.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.util.KelpBlockFace;

public interface HangingEntity<T extends HangingEntity<?>> extends KelpEntity<T> {

  KelpBlockFace getAttachedFace();

  KelpBlock getAttachedBlock();

  T setFacingDirection(KelpBlockFace blockFace);

}
