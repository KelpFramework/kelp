package de.pxav.kelp.core.world.raycast;

import de.pxav.kelp.core.world.KelpLocation;

public interface RaycastHit {

  Object getCollider();

  double getHitDistance();

  KelpLocation getPoint();

}
