package de.pxav.kelp.core.inventory.metadata;

import org.apache.commons.lang.StringUtils;

/**
 * Represents a color that can be used for dyeing items such
 * as leather armor or represent colors inside minecraft in general.
 *
 * This class is a version independent alternative to bukkit's normal
 * {@link org.bukkit.Color} class.
 *
 * You can use the color presets such as {@link Color#CHAT_GOLD} or {@link Color#PRESET_OLIVE}
 * or create your own colors using rgb or hex values: {@link Color#fromRGB(int, int, int)}.
 *
 * You will need this color representation in classes like {@link LeatherArmorMetadata}.
 *
 * @author pxav
 */
public class Color {

  // all minecraft default chat colors in rgb notation
  public static final Color CHAT_DARK_RED = fromRGB(170, 0, 0);
  public static final Color CHAT_BLACK = fromRGB(0, 0, 0);
  public static final Color CHAT_DARK_BLUE = fromRGB(170, 0, 170);
  public static final Color CHAT_DARK_GREEN = fromRGB(0, 170, 0);
  public static final Color CHAT_DARK_AQUA = fromRGB(0, 170, 170);
  public static final Color CHAT_DARK_PURPLE = fromRGB(170, 0, 170);
  public static final Color CHAT_GOLD = fromRGB(255, 170, 0);
  public static final Color CHAT_GRAY = fromRGB(170, 170, 170);
  public static final Color CHAT_DARK_GRAY = fromRGB(85, 85, 85);
  public static final Color CHAT_BLUE = fromRGB(85, 85, 255);
  public static final Color CHAT_GREEN = fromRGB(85, 255, 85);
  public static final Color CHAT_AQUA = fromRGB(85, 255, 255);
  public static final Color CHAT_RED = fromRGB(255, 85, 85);
  public static final Color CHAT_LIGHT_PURPLE = fromRGB(255, 85, 255);
  public static final Color CHAT_YELLOW = fromRGB(255, 255, 85);
  public static final Color CHAT_WHITE = fromRGB(255, 255, 255);

  // some preset colors provided by the bukkit library.
  public static final Color PRESET_AQUA = fromRGB(0, 255, 255);
  public static final Color PRESET_BLACK = fromRGB(0, 0, 0);
  public static final Color PRESET_BLUE = fromRGB(0, 0, 255);
  public static final Color PRESET_WHITE = fromRGB(255, 255, 255);
  public static final Color PRESET_SILVER = fromRGB(192, 192, 192);
  public static final Color PRESET_GRAY = fromRGB(128, 128, 128);
  public static final Color PRESET_RED = fromRGB(255, 0, 0);
  public static final Color PRESET_MAROON = fromRGB(128, 0, 0);
  public static final Color PRESET_YELLOW = fromRGB(255, 255, 0);
  public static final Color PRESET_OLIVE = fromRGB(128, 128, 0);
  public static final Color PRESET_LIME = fromRGB(0, 255, 0);
  public static final Color PRESET_GREEN = fromRGB(0, 128, 0);
  public static final Color PRESET_TEAL = fromRGB(0, 128, 128);
  public static final Color PRESET_NAVY = fromRGB(0, 0, 128);
  public static final Color PRESET_FUCHSIA = fromRGB(255, 0, 255);
  public static final Color PRESET_PURPLE = fromRGB(128, 0, 128);
  public static final Color PRESET_ORANGE = fromRGB(255, 165, 0);

  // colors that are created when crafting a dye together with a dyeable item such as
  // leather armor or wool.
  public static final Color DYE_PINK = fromRGB(243, 139, 170);
  public static final Color DYE_LIME = fromRGB(128, 199, 31);
  public static final Color DYE_YELLOW = fromRGB(254, 216, 61);
  public static final Color DYE_LIGHT_BLUE = fromRGB(58, 179, 218);
  public static final Color DYE_MAGENTA = fromRGB(199, 78, 189);
  public static final Color DYE_ORANGE = fromRGB(249, 128, 29);
  public static final Color DYE_WHITE = fromRGB(249, 255, 254);
  public static final Color DYE_GRAY = fromRGB(71, 79, 82);
  public static final Color DYE_RED = fromRGB(176, 46, 38);
  public static final Color DYE_CACTUS = fromRGB(94, 124, 22);
  public static final Color DYE_BROWN = fromRGB(131, 84, 50);
  public static final Color DYE_BLUE = fromRGB(60, 68, 170);
  public static final Color DYE_PURPLE = fromRGB(137, 50, 184);
  public static final Color DYE_CYAN = fromRGB(22, 156, 156);
  public static final Color DYE_BLACK = fromRGB(29, 29, 33);

  /**
   * Creates a new color instance based on the given red, green and blue value.
   *
   * @param red     The amount of red in the desired color (from 0-255)
   * @param green   The amount of green in the desired color (from 0-255)
   * @param blue    The amount of blue in the desired color (from 0-255)
   * @return The new color instance based on the given values.
   */
  public static Color fromRGB(int red, int green, int blue) {
    return new Color(red, green, blue);
  }

  /**
   * Converts a bukkit color into a kelp color using its rgb values.
   *
   * @param color The bukkit color you want to convert.
   * @return The kelp color instance equivalent to the bukkit color.
   */
  public static Color fromBukkit(org.bukkit.Color color) {
    return new Color(color.getRed(), color.getGreen(), color.getBlue());
  }

  /**
   * Builds an array of all default colors available in this
   * class. This includes (in order):
   * - Dye colors (DYE_)
   * - Chat colors (CHAT_)
   * - Preset colors (PRESET_)
   *
   * @return An array of all default colors in this class.
   */
  public static Color[] colorValues() {
    return new Color[] {
      DYE_PINK,
      DYE_LIME,
      DYE_YELLOW,
      DYE_LIGHT_BLUE,
      DYE_MAGENTA,
      DYE_ORANGE,
      DYE_WHITE,
      DYE_GRAY,
      DYE_RED,
      DYE_CACTUS,
      DYE_BROWN,
      DYE_BLUE,
      DYE_PURPLE,
      DYE_CYAN,
      DYE_BLACK,

      CHAT_DARK_RED,
      CHAT_BLACK,
      CHAT_DARK_BLUE,
      CHAT_DARK_GREEN,
      CHAT_DARK_AQUA,
      CHAT_DARK_PURPLE,
      CHAT_GOLD,
      CHAT_GRAY,
      CHAT_DARK_GRAY,
      CHAT_BLUE,
      CHAT_GREEN,
      CHAT_AQUA,
      CHAT_RED,
      CHAT_LIGHT_PURPLE,
      CHAT_YELLOW,
      CHAT_WHITE,

      PRESET_ORANGE,
      PRESET_PURPLE,
      PRESET_FUCHSIA,
      PRESET_NAVY,
      PRESET_TEAL,
      PRESET_GREEN,
      PRESET_LIME,
      PRESET_OLIVE,
      PRESET_YELLOW,
      PRESET_MAROON,
      PRESET_RED,
      PRESET_GRAY,
      PRESET_SILVER,
      PRESET_WHITE,
      PRESET_BLUE,
      PRESET_BLACK,
      PRESET_AQUA,
    };
  }

  // RGB color values
  private int red;
  private int green;
  private int blue;

  private Color(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Sets the amount of red in this color.
   *
   * @param red The amount of red in this color.
   *            The value may range from 0 to 255.
   * @return The instance of the current color for fluent builder design.
   */
  public Color setRed(int red) {
    this.red = red;
    return this;
  }

  /**
   * Gets the amount of red in this color (0-255)
   *
   * @return The amount of red in this color.
   */
  public int getRed() {
    return red;
  }

  /**
   * Sets the amount of green in this color.
   *
   * @param green The amount of green in this color.
   *              The value may range from 0 to 255.
   * @return The instance of the current color for fluent builder design.
   */
  public Color setGreen(int green) {
    this.green = green;
    return this;
  }

  /**
   * Gets the amount of green in this color (0-255)
   *
   * @return The amount of green in this color.
   */
  public int getGreen() {
    return green;
  }

  /**
   * Sets the amount of blue in this color.
   *
   * @param blue The amount of blue in this color.
   *             The value may range from 0 to 255.
   * @return The instance of the current color for fluent builder design.
   */
  public Color setBlue(int blue) {
    this.blue = blue;
    return this;
  }

  /**
   * Gets the amount of blue in this color (0-255)
   *
   * @return The amount of blue in this color.
   */
  public int getBlue() {
    return blue;
  }

  /**
   * Converts the color values from RGB to a HEX string. This means it converts
   * each color value for red, green and blue into their hex number, which is
   * two characters long. Those characters are appended in the RGB order resulting
   * in the six-chars long hex format of the current color. This method does not
   * add a {@code #} in front of each color.
   *
   * @return The HEX notation of the current color.
   */
  public String asHex() {
    return StringUtils.leftPad(Integer.toHexString(red), 2, '0') +
      StringUtils.leftPad(Integer.toHexString(green), 2, '0') +
      StringUtils.leftPad(Integer.toHexString(blue), 2, '0').toUpperCase();
  }

  /**
   * Generates an array containing the RGB color values in their
   * regular order: [red, green, blue]
   *
   * @return The color values in RGB order.
   */
  public int[] asRGB() {
    return new int[] {
      this.red,
      this.green,
      this.blue
    };
  }

  /**
   * Generates an array containing the color values in the format:
   * [blue, green, red]
   *
   * @return The raw color values composing the current color in BGR format.
   */
  public int[] asBGR() {
    return new int[] {
      this.blue,
      this.green,
      this.red
    };
  }

  /**
   * Converts this color to the equivalent bukkit color.
   *
   * @return The bukkit color equivalent to this color.
   */
  public org.bukkit.Color getBukkitColor() {
    return org.bukkit.Color.fromRGB(red, green, blue);
  }

}
