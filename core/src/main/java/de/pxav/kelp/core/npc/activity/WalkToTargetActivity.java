package de.pxav.kelp.core.npc.activity;

import de.pxav.kelp.core.npc.KelpNpc;
import de.pxav.kelp.core.npc.MovementSpeed;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class WalkToTargetActivity extends NpcActivity<WalkToTargetActivity> {

  private Vector direction;
  private double index = .01;
  private Location startLocation;
  private Location target;

  private boolean lastTick = false;

  public static WalkToTargetActivity create() {
    return new WalkToTargetActivity();
  }

  public WalkToTargetActivity target(Location target) {
    this.target = target;
    return this;
  }

  @Override
  public void onStart(KelpNpc kelpNpc) {
    this.startLocation = kelpNpc.getCurrentLocation();
    this.direction = target.clone().toVector().subtract(startLocation.toVector());
  }

  @Override
  public void onTick(KelpNpc kelpNpc) {
    if (lastTick) {
      kelpNpc.moveTo(target);
      kelpNpc.lookTo(target.clone().add(direction));
      finish();
      return;
    }

    direction.multiply(index);
    Location newLocation = startLocation.clone().add(direction);

    kelpNpc.moveTo(newLocation);
    kelpNpc.lookTo(target);

    direction.normalize();

    if (kelpNpc.getCurrentLocation().distance(target) < 1) {
      lastTick = true;
    }

    index += MovementSpeed.getNpcSpeed(kelpNpc);

  }

}
