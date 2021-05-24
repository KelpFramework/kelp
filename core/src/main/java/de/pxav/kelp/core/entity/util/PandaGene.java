package de.pxav.kelp.core.entity.util;

public enum PandaGene {

  AGGRESSIVE(true),
  BROWN(false),
  LAZY(true),
  NORMAL(true),
  PLAYFUL(true),
  WEAK(false),
  WORRIED(true);

  private boolean isDominant;

  PandaGene(boolean isDominant) {
    this.isDominant = isDominant;
  }

  public boolean isDominant() {
    return isDominant;
  }

  public boolean isRecessive() {
    return !isDominant;
  }

}
