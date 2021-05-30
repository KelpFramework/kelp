package de.pxav.kelp.core.player.hologram.component;

import java.util.Collection;

public class EmptyTextComponent extends HoloTextComponent<EmptyTextComponent> {

  public static EmptyTextComponent create() {
    return new EmptyTextComponent();
  }

  @Override
  public Collection<String> getLines() {
    return null;
  }

}
