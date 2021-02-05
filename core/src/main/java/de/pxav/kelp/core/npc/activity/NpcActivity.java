package de.pxav.kelp.core.npc.activity;

import de.pxav.kelp.core.npc.KelpNpc;

public abstract class NpcActivity {

  private boolean finished;

  public void onSpawn(KelpNpc kelpNpc) {}

  public void onTick(KelpNpc kelpNpc) {}

  public void onRemove(KelpNpc kelpNpc) {}

  public boolean isFinished() {
    return finished;
  }

  public void finish() {
    this.finished = true;
  }

}
