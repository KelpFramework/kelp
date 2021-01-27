package de.pxav.kelp.core.sidebar.component;

import com.google.common.collect.Maps;
import de.pxav.kelp.core.scheduler.synchronize.Retrievable;

import java.util.Map;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class StatefulTextComponent extends SidebarComponent {

  private int line;
  private Retrievable text;

  public static StatefulTextComponent create() {
    return new StatefulTextComponent();
  }

  public StatefulTextComponent line(int line) {
    this.line = line;
    return this;
  }

  public StatefulTextComponent text(Retrievable retrievableText) {
    this.text = retrievableText;
    return this;
  }

  @Override
  public Map<Integer, String> render() {
    Map<Integer, String> output = Maps.newHashMap();
    output.put(line, String.valueOf(text.retrieve()));
    return output;
  }

}
