package de.pxav.kelp.core.inventory.metadata;

public class LeatherArmorMetadata implements ItemMetadata {

  public static LeatherArmorMetadata create() {
    return new LeatherArmorMetadata();
  }

  private Color color;

  public LeatherArmorMetadata setColor(Color color) {
    this.color = color;
    return this;
  }

  public Color getColor() {
    return color;
  }

}
