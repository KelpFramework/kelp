package de.pxav.kelp.core.inventory.metadata;

public class Color {

  public static Color fromRGB(int red, int green, int blue) {
    return new Color(red, green, blue);
  }

  public static final Color CHAT_DARK_RED = fromRGB(170, 0, 0);
  public static final Color CHAT_LIGHT_RED = fromRGB(170, 0, 0);
  public static final Color CHAT_YELLOW = fromRGB(170, 0, 0);
  public static final Color CHAT_GOLD = fromRGB(170, 0, 0);

  public static final Color AQUA = fromRGB(0, 255, 255);
  public static final Color BLACK = fromRGB(0, 0, 0);
  public static final Color BLUE = fromRGB(0, 0, 255);
  public static final Color WHITE = fromRGB(255, 255, 255);
  public static final Color SILVER = fromRGB(192, 192, 192);
  public static final Color GRAY = fromRGB(128, 128, 128);
  public static final Color RED = fromRGB(255, 0, 0);
  public static final Color MAROON = fromRGB(128, 0, 0);
  public static final Color YELLOW = fromRGB(255, 255, 0);
  public static final Color OLIVE = fromRGB(128, 128, 0);
  public static final Color LIME = fromRGB(0, 255, 0);
  public static final Color GREEN = fromRGB(0, 128, 0);
  public static final Color TEAL = fromRGB(0, 128, 128);
  public static final Color NAVY = fromRGB(0, 0, 128);
  public static final Color FUCHSIA = fromRGB(255, 0, 255);
  public static final Color PURPLE = fromRGB(128, 0, 128);
  public static final Color ORANGE = fromRGB(255, 165, 0);

  public static Color fromBukkit(org.bukkit.Color color) {
    return new Color(color.getRed(), color.getGreen(), color.getBlue());
  }

  private int red;
  private int green;
  private int blue;

  private Color(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  public void setRed(int red) {
    this.red = red;
  }

  public void setGreen(int green) {
    this.green = green;
  }

  public void setBlue(int blue) {
    this.blue = blue;
  }

  public org.bukkit.Color getBukkitColor() {
    return org.bukkit.Color.fromRGB(red, green, blue);
  }

}
