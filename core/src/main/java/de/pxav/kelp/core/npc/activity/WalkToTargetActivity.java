package de.pxav.kelp.core.npc.activity;

import de.pxav.kelp.core.npc.KelpNpc;
import de.pxav.kelp.core.npc.MovementSpeed;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.util.Vector3;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class WalkToTargetActivity extends NpcActivity<WalkToTargetActivity> {

  private Vector3 direction;
  private double index = .01;
  private KelpLocation startLocation;
  private KelpLocation target;
  private boolean lookToTarget = true;

  private boolean lastTick = false;

  public static WalkToTargetActivity create() {
    return new WalkToTargetActivity();
  }

  public WalkToTargetActivity target(KelpLocation target) {
    this.target = target;
    return this;
  }

  public WalkToTargetActivity lookToTarget(boolean lookToTarget) {
    this.lookToTarget = lookToTarget;
    return this;
  }

  @Override
  public void onStart(KelpNpc kelpNpc) {
    this.startLocation = kelpNpc.getLocation();
    this.direction = target.clone().toVector().subtract(startLocation.toVector());
  }

  @Override
  public void onTick(KelpNpc kelpNpc) {
    if (lastTick) {
      kelpNpc.moveTo(target);
      if (lookToTarget) {
        kelpNpc.lookTo(target.clone().add(direction));
      }
      finish();
      return;
    }

    direction.multiply(index);
    KelpLocation newLocation = startLocation.clone().add(direction);

    kelpNpc.moveTo(newLocation);
    if (this.lookToTarget) {
      kelpNpc.lookTo(target);
    }

    direction.normalize();

    if (kelpNpc.getLocation().distance(target) < 1) {
      lastTick = true;
    }

    index += MovementSpeed.getNpcSpeed(kelpNpc);

  }

}
