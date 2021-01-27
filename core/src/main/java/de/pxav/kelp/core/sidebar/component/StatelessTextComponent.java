package de.pxav.kelp.core.sidebar.component;

import com.google.common.collect.Maps;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.sidebar.SidebarUtils;

import java.util.Map;

/**
 * This component can display static text in a sidebar. So unlike
 * the {@link StatefulListComponent} the text will not change after an
 * update. This is useful to display static information such as a link
 * to your website, etc.
 *
 * @author pxav
 */
public class StatelessTextComponent extends SidebarComponent {

  private String text = "SimpleTextComponent";
  private int line;

  public static StatelessTextComponent create() {
    return new StatelessTextComponent();
  }

  /**
   * Sets the text to be displayed by this component. This text cannot be changed
   * once it has been set.
   *
   * @param text The static text to be displayed.
   * @return Instance of the current component for more fluent builder design.
   */
  public StatelessTextComponent text(String text) {
    this.text = text;
    return this;
  }

  /**
   * Sets the line number of the component to be placed in the sidebar.
   * Please note that this line id represents an absolute position and
   * should therefore be unique. No components should have the same line
   * number.
   *
   * @param line The line of the sidebar, where the component should
   *             be visible.
   * @return The current component instance for fluent builder design.
   */
  public StatelessTextComponent line(int line) {
    this.line = line;
    return this;
  }

  /**
   * Renders all the information provided in the component
   * to a map containing the final information to be rendered
   * to the sidebar (the lines where the text should be placed and
   * the text to write there).
   *
   * @return A map, where the key is the absolute line where
   *         the component should be placed in the sidebar and
   *         the value is the actual text for that line.
   */
  @Override
  public Map<Integer, String> render() {
    Map<Integer, String> output = Maps.newHashMap();
    output.put(this.line, this.text);
    return output;
  }

}
