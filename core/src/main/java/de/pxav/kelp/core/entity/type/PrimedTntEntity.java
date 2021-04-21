package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.type.general.ExplosiveEntity;
import org.bukkit.entity.TNTPrimed;

// explosive
public interface PrimedTntEntity extends ExplosiveEntity<PrimedTntEntity> {

  int getFuseTicks();

  PrimedTntEntity setFuseTicks(int fuseTicks);

  KelpEntity<?> getSource();

  PrimedTntEntity setSource(KelpEntity<?> sourceEntity);

}
