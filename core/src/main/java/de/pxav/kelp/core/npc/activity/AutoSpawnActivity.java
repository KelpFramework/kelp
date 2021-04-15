package de.pxav.kelp.core.npc.activity;

import de.pxav.kelp.core.npc.KelpNpc;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.KelpLocation;

public class AutoSpawnActivity extends NpcActivity<AutoSpawnActivity> {

  private double distanceThreshold = 40;

  public static AutoSpawnActivity create() {
    return new AutoSpawnActivity();
  }

  public AutoSpawnActivity distanceThreshold(double distanceThreshold) {
    this.distanceThreshold = distanceThreshold;
    return this;
  }

  @Override
  public void onTick(KelpNpc kelpNpc) {
    KelpPlayer player = kelpNpc.getPlayer();
    KelpLocation npcLocation = kelpNpc.getLocation();

    if (player.getLocation().distanceSquared(npcLocation) <= (distanceThreshold * distanceThreshold)
      && !kelpNpc.isSpawned()) {
        kelpNpc.spawn();
    } else if (player.getLocation().distanceSquared(npcLocation) > (distanceThreshold * distanceThreshold)
      && kelpNpc.isSpawned()) {
        kelpNpc.deSpawn();
    }

  }

  @Override
  public boolean alwaysActive() {
    return true;
  }

}
