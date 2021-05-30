package de.pxav.kelp.core.player.hologram.component;

import java.util.Collection;

public abstract class HoloTextComponent<T extends HoloTextComponent<T>> extends HologramComponent<T> {

  public abstract Collection<String> getLines();

}
