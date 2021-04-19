package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.LivingKelpEntity;

public interface EvokerFangEntity extends KelpEntity<EvokerFangEntity> {

  LivingKelpEntity<?> getFangOwner();

  EvokerFangEntity setFangOwner(LivingKelpEntity<?> fangOwner);

}
