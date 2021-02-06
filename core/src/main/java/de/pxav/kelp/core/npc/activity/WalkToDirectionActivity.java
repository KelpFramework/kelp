package de.pxav.kelp.core.npc.activity;

import de.pxav.kelp.core.npc.KelpNpc;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class WalkToDirectionActivity extends NpcActivity {

  private double distance;
  private Vector direction;
  private double index = .01;
  private Location startLocation;

  private Location target;

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
    System.out.println("Started. init Location set");
    startLocation = kelpNpc.getCurrentLocation();
  }

  @Override
  public void onTick(KelpNpc kelpNpc) {
    if (kelpNpc.isSneaking()) {
      index += .13;
    } else if (kelpNpc.isSprinting()) {
      index += .56;
    } else {
      index += .43;
    }

    System.out.println("index " + index);

    if (index >= distance) {
      finish();
      return;
    }

    System.out.println("loc0: x=" + kelpNpc.getCurrentLocation().getX() + " y="+kelpNpc.getCurrentLocation().getY() + " z=" + kelpNpc.getCurrentLocation().getZ());
    direction.multiply(index);
    Location newLocation = startLocation.clone().add(direction);
    System.out.println("loc1: x=" + newLocation.getX() + " y="+newLocation.getY() + " z=" + newLocation.getZ());

    Location previousLocation = kelpNpc.getCurrentLocation();
    System.out.println("loc2: x=" + previousLocation.getX() + " y="+previousLocation.getY() + " z=" + previousLocation.getZ());
    double x = newLocation.getX() - previousLocation.getX();
    double y = newLocation.getY() - previousLocation.getY();
    double z = newLocation.getZ() - previousLocation.getZ();
    System.out.println("new relative loc calculated: x=" + x + " y="+y + " z=" + z);
    kelpNpc.moveRelativeDistance(x, y, z, previousLocation.getYaw(), previousLocation.getPitch());
    kelpNpc.currentLocation(newLocation);

    direction.normalize();

  }

}
