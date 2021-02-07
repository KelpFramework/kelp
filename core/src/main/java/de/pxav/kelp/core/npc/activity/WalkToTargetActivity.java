package de.pxav.kelp.core.npc.activity;

import de.pxav.kelp.core.npc.KelpNpc;
import de.pxav.kelp.core.npc.MovementSpeed;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class WalkToTargetActivity extends NpcActivity {

  private Vector direction;
  private double index = .01;
  private Location startLocation;

  private Location target;

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
    this.direction = target.clone().subtract(startLocation).toVector();
  }

  @Override
  public void onTick(KelpNpc kelpNpc) {
    index += MovementSpeed.getNpcSpeed(kelpNpc);

    direction.multiply(index);
    Location newLocation = startLocation.clone().add(direction);

    Location previousLocation = kelpNpc.getCurrentLocation();
    double x = newLocation.getX() - previousLocation.getX();
    double y = newLocation.getY() - previousLocation.getY();
    double z = newLocation.getZ() - previousLocation.getZ();
    kelpNpc.moveRelativeDistance(x, y, z, previousLocation.getYaw(), previousLocation.getPitch());
    kelpNpc.currentLocation(newLocation);
    kelpNpc.lookTo(target);

    if (kelpNpc.getCurrentLocation().distance(target) < 1) {
      finish();
      return;
    }

    direction.normalize();

  }

}
