package de.pxav.kelp.core.player.hologram.component;

import java.util.Collection;
import java.util.Collections;

public class SimpleHoloText extends HoloTextComponent<SimpleHoloText> {

  private String text;

  public static SimpleHoloText create() {
    return new SimpleHoloText();
  }

  public SimpleHoloText text(String text) {
    this.text = text;
    return this;
  }

  public String getText() {
    return text;
  }

  @Override
  public Collection<String> getLines() {
    return Collections.singletonList(this.text);
  }

}
