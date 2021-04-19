package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.ExplosiveEntity;
import org.bukkit.util.Vector;

public interface ThrownFireballEntity extends ExplosiveEntity<ThrownFireballEntity> {

  Vector getDirection();

  ThrownFireballEntity setDirection(Vector vector);

}
