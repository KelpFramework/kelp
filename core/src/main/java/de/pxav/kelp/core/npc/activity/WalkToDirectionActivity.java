package de.pxav.kelp.core.npc.activity;

import de.pxav.kelp.core.npc.KelpNpc;
import de.pxav.kelp.core.npc.MovementSpeed;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class WalkToDirectionActivity extends NpcActivity<WalkToDirectionActivity> {

  private double distance;
  private Vector direction;
  private double index = .01;
  private KelpLocation startLocation;
  private boolean lookToTarget = true;

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

  public WalkToDirectionActivity lookToTarget(boolean lookToTarget) {
    this.lookToTarget = lookToTarget;
    return this;
  }

  @Override
  public void onStart(KelpNpc kelpNpc) {
    startLocation = kelpNpc.getLocation();
  }

  @Override
  public void onTick(KelpNpc kelpNpc) {
    index += MovementSpeed.getNpcSpeed(kelpNpc);

    if (index >= distance) {
      finish();
      return;
    }

    direction.multiply(index);
    KelpLocation newLocation = startLocation.clone().add(direction);

    kelpNpc.moveTo(newLocation);
    if (this.lookToTarget) {
      kelpNpc.lookTo(newLocation.clone().add(direction));
    }

    direction.normalize();
  }

}
