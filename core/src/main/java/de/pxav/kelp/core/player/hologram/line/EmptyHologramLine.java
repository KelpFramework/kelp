package de.pxav.kelp.core.player.hologram.line;

public class EmptyHologramLine implements HologramLine<Void> {

  public static EmptyHologramLine create() {
    return new EmptyHologramLine();
  }

  @Override
  public Void display() {
    return null;
  }

}
