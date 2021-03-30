package de.pxav.kelp.core.inventory.metadata;

/**
 * Represents the item-specific metadata of any leather armor item
 * (no matter if that is a helmet, chestplate, leggings or boots).
 *
 * It allows you to set the color of the armor represented by
 * {@link Color} class of kelp.
 *
 * @author pxav
 */
public class LeatherArmorMetadata implements ItemMetadata {

  public static LeatherArmorMetadata create() {
    return new LeatherArmorMetadata();
  }

  // the color of the leather armor
  private Color color;

  /**
   * Sets the color of the given leather armor item.
   *
   * @param color The new color of the leather armor item.
   * @return An instance of the current metadata for fluent builder design.
   */
  public LeatherArmorMetadata setColor(Color color) {
    this.color = color;
    return this;
  }

  /**
   * Gets the color that is currently applied to the leather armor.
   *
   * @return The current color of the leather armor.
   */
  public Color getColor() {
    return color;
  }

}
