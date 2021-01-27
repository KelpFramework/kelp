package de.pxav.kelp.core.sidebar.component;

import com.google.common.collect.Maps;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.sidebar.SidebarUtils;

import java.util.Map;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class StatelessTextComponent extends SidebarComponent {

  private String text = "SimpleTextComponent";
  private int line;

  public static StatelessTextComponent create() {
    return new StatelessTextComponent();
  }

  public StatelessTextComponent text(String text) {
    this.text = text;
    return this;
  }

  public StatelessTextComponent line(int line) {
    this.line = line;
    return this;
  }

  @Override
  public Map<Integer, String> render() {
    Map<Integer, String> output = Maps.newHashMap();
    output.put(this.line, this.text);
    return output;
  }

}
