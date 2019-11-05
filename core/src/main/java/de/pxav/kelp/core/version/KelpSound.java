package de.pxav.kelp.core.version;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public enum  KelpSound {

  BLOCK_ANVIL_LAND(KelpVersion.MC_1_8_0);

  private KelpVersion since;

  KelpSound(KelpVersion since) {
    this.since = since;
  }

  public KelpVersion since() {
    return since;
  }

}
