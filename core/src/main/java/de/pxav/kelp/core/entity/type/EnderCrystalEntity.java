package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.world.KelpLocation;

public interface EnderCrystalEntity extends KelpEntity<EnderCrystalEntity> {

  boolean isShowingBottom();

  void setShowingBottom(boolean showingBottom);

  KelpLocation getBeamTarget();

  void setBeamTarget(KelpLocation beamTarget);

}
