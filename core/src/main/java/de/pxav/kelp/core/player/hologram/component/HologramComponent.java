package de.pxav.kelp.core.player.hologram.component;

public abstract class HologramComponent<T extends HologramComponent<T>> {

  protected int index = -1;

  public T index(int index) {
    this.index = index;
    return (T) this;
  }

  public int getIndex() {
    return this.index;
  }

}
