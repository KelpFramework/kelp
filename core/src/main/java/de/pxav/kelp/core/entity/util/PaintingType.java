package de.pxav.kelp.core.entity.util;

import java.util.concurrent.ThreadLocalRandom;

public enum PaintingType {

  KEBAB(1, 1),
  AZTEC(1, 1),
  ALBANIAN( 1, 1),
  AZTEC2( 1, 1),
  BOMB( 1, 1),
  PLANT( 1, 1),
  WASTELAND( 1, 1),
  POOL(2, 1),
  COURBET( 2, 1),
  SEA(2, 1),
  SUNSET(2, 1),
  CREEBET(2, 1),
  WANDERER(1, 2),
  GRAHAM(1, 2),
  MATCH(2, 2),
  BUST(2, 2),
  STAGE(2, 2),
  VOID(2, 2),
  SKULL_AND_ROSES(2, 2),
  WITHER(2, 2),
  FIGHTERS(4, 2),
  POINTER(4, 4),
  PIGSCENE(4, 4),
  BURNING_SKULL( 4, 4),
  SKELETON(4, 3),
  DONKEY_KONG(4, 3)

  ;

  private int width;
  private int height;

  PaintingType(int width, int height) {
    this.width = width;
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public int getHeight() {
    return height;
  }

  public static PaintingType random() {
    return values()[ThreadLocalRandom.current().nextInt(values().length)];
  }

  public static PaintingType random(int width, int height) {
    PaintingType output = random();
    while (output.width != width || output.height != height) {
      output = random();
    }
    return output;
  }

}
