package de.pxav.kelp.core.player.hologram.line;

public class TextHologramLine implements HologramLine<String> {

  private String text;

  public static TextHologramLine create() {
    return new TextHologramLine();
  }

  public TextHologramLine text(String item) {
    this.text = item;
    return this;
  }

  @Override
  public String display() {
    return this.text;
  }

}
