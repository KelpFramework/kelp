package de.pxav.kelp.core.sidebar.component;

import com.google.common.collect.Maps;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.sidebar.SidebarUtils;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Map;

/**
 * This sidebar component can be used to generate empty lines.
 * You could also write them manually as a {@code StatelessTextComponent}
 * for example, this way however makes it more maintainable, because you don't
 * have to choose color codes manually:
 *
 * To display an empty line in a scoreboard you have to use invisible
 * characters like formatting codes (§6, §l) or spaces.
 * But you may not use one line twice, so when you use '§6§l' for
 * one line, you have to use §7§l for the next one and so on.
 *
 * This makes it more difficult to add new empty lines which
 * is why it's recommended to simply use this component.
 *
 * @author pxav
 */
public class EmptyLineComponent extends SidebarComponent {

  // the line to place the component in the sidebar
  private int line;

  public static EmptyLineComponent create() {
    return new EmptyLineComponent();
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
  public EmptyLineComponent line(int line) {
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
    output.put(line, " ");
    return output;
  }

}
