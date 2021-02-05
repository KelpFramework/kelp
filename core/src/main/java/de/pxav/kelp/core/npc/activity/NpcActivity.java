package de.pxav.kelp.core.npc.activity;

import de.pxav.kelp.core.npc.KelpNpc;

public abstract class NpcActivity {

  private boolean finished = false;
  protected boolean started = false;

  public void onSpawn(KelpNpc kelpNpc) {}

  public void onStart(KelpNpc kelpNpc) {}

  public void onTick(KelpNpc kelpNpc) {}

  public void onRemove(KelpNpc kelpNpc) {}

  public boolean isFinished() {
    return finished;
  }

  public void finish() {
    this.finished = true;
  }

  public boolean hasStarted() {
    return started;
  }

  public void start(KelpNpc kelpNpc) {
    this.started = true;
    this.onStart(kelpNpc);
  }

}
