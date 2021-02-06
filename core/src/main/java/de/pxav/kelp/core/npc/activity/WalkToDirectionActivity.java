package de.pxav.kelp.core.npc.activity;

import de.pxav.kelp.core.npc.KelpNpc;
import de.pxav.kelp.core.npc.MovementSpeed;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class WalkToDirectionActivity extends NpcActivity {

  private double distance;
  private Vector direction;
  private double index = .01;
  private Location startLocation;

  public static WalkToDirectionActivity create() {
    return new WalkToDirectionActivity();
  }

  public WalkToDirectionActivity distance(double distance) {
    this.distance = distance;
    return this;
  }

  public WalkToDirectionActivity direction(Vector direction) {
    this.direction = direction;
    return this;
  }

  @Override
  public void onStart(KelpNpc kelpNpc) {
    startLocation = kelpNpc.getCurrentLocation();
  }

  @Override
  public void onTick(KelpNpc kelpNpc) {
    index += MovementSpeed.getNpcSpeed(kelpNpc);

    if (index >= distance) {
      finish();
      return;
    }

    direction.multiply(index);
    Location newLocation = startLocation.clone().add(direction);

    Location previousLocation = kelpNpc.getCurrentLocation();
    double x = newLocation.getX() - previousLocation.getX();
    double y = newLocation.getY() - previousLocation.getY();
    double z = newLocation.getZ() - previousLocation.getZ();
    kelpNpc.moveRelativeDistance(x, y, z, previousLocation.getYaw(), previousLocation.getPitch());
    kelpNpc.currentLocation(newLocation);

    direction.normalize();
  }

}
