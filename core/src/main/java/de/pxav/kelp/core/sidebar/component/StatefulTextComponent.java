package de.pxav.kelp.core.sidebar.component;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.function.Supplier;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class StatefulTextComponent extends SidebarComponent {

  private int line;
  private Supplier<Object> text;

  public StatefulTextComponent() {
    text = () -> "StatefulTextComponent";
  }

  public static StatefulTextComponent create() {
    return new StatefulTextComponent();
  }

  public StatefulTextComponent line(int line) {
    this.line = line;
    return this;
  }

  public StatefulTextComponent text(Supplier<Object> text) {
    this.text = text;
    return this;
  }

  @Override
  public Map<Integer, String> render() {
    Map<Integer, String> output = Maps.newHashMap();
    output.put(line, String.valueOf(text.get()));
    return output;
  }

}
