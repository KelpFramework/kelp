package de.pxav.kelp.core.player.message.image;

public enum ImageChar {

  BLOCK('\u2588'),
  DARK_SHADE('\u2593'),
  MEDIUM_SHADE('\u2592'),
  LIGHT_SHADE('\u2591'),
  TRANSPARENT(' ');

  private final char c;

  ImageChar(char c) {
    this.c = c;
  }

  public char getChar() {
    return c;
  }

}
