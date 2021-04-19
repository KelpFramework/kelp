package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.type.general.AnimalEntity;
import de.pxav.kelp.core.entity.util.FoxType;

public interface FoxEntity extends AnimalEntity<FoxEntity> {

  FoxType getFoxType();

  void setFoxType(FoxType foxType);

  boolean isCrouching();

  void setCrouching(boolean crouching);

  void setSleeping(boolean sleeping);

  KelpEntity<?> getFirstTrustedEntity();

  void setFirstTrustedEntity(KelpEntity<?> trusted);

  KelpEntity<?> getSecondTrustedEntity();

  void setSecondTrustedEntity(KelpEntity<?> trusted);

}
