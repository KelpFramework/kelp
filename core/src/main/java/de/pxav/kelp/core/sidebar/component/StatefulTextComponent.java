package de.pxav.kelp.core.sidebar.component;

import com.google.common.collect.Maps;

import java.util.Map;
import java.util.function.Supplier;

/**
 * This component allows you to display dynamic content
 * on sidebars. While the {@link StatelessTextComponent} can only display
 * static, non-updatable content, this component can be updated.
 * Example use cases for that would be displaying the player's
 * coins or a timer.
 *
 * @author pxav
 */
public class StatefulTextComponent extends SidebarComponent {

  // absolute position to place the component
  private int line;

  private Supplier<Object> text;

  public StatefulTextComponent() {
    text = () -> "StatefulTextComponent";
  }

  public static StatefulTextComponent create() {
    return new StatefulTextComponent();
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
  public StatefulTextComponent line(int line) {
    this.line = line;
    return this;
  }

  /**
   * Sets the text to be displayed in the component.
   *
   * @param text The dynamic text to be displayed.
   * @return Instance of the current component for more fluent builder design.
   */
  public StatefulTextComponent text(Supplier<Object> text) {
    this.text = text;
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
    output.put(line, String.valueOf(text.get()));
    return output;
  }

}
