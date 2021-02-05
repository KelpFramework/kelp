package de.pxav.kelp.core.npc.activity;

import de.pxav.kelp.core.npc.KelpNpc;
import de.pxav.kelp.core.player.KelpPlayer;

public class SneakingActivity extends NpcActivity {

  private KelpPlayer toImitate;

  public static SneakingActivity create() {
    return new SneakingActivity();
  }

  public SneakingActivity imitatedPlayer(KelpPlayer toImitate) {
    this.toImitate = toImitate;
    return this;
  }

  @Override
  public void onTick(KelpNpc npc) {
    // un-sneak if necessary.
    if (npc.isSneaking() && !toImitate.isSneaking()) {
      npc.unSneak();
      npc.refresh(toImitate);

    // sneak if necessary.
    } else if(!npc.isSneaking() && toImitate.isSneaking()) {
      npc.sneak();
      npc.refresh(toImitate);
    }
  }

}
