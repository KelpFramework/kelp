package de.pxav.kelp.core.sidebar.component;

import com.google.common.collect.Maps;
import de.pxav.kelp.core.sidebar.SidebarUtils;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.Map;

/**
 * This scoreboard component is used to easily create line separators.
 * With these you can simply create separators between paragraphs of your
 * sidebar, which might be helpful to express paragraphs.
 *
 * You could create separators manually by using a text component,
 * but this might get unmaintainable over time, because you cannot
 * simply change the char type, color or amount. So instead of doing
 * this yourself every time, you can use this component.
 *
 * By default the char type is set to '-' which is repeated for 30 times.
 * THe default color codes are §8§m to create a straight horizontal line.
 *
 * @author pxav
 */
public class LineSeparatorComponent extends SidebarComponent {

  private int line;
  private int length;
  private char symbol;
  private ChatColor[] colors;

  public LineSeparatorComponent() {
    this.length = SeparatorLength.FULL;
    this.symbol = '-';
    this.colors = new ChatColor[] {ChatColor.DARK_GRAY, ChatColor.STRIKETHROUGH};
  }

  public static LineSeparatorComponent create() {
    return new LineSeparatorComponent();
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
  public LineSeparatorComponent line(int line) {
    this.line = line;
    return this;
  }

  /**
   * Sets the symbol to be repeated n times by the component, while
   * {@code n} is equal to the length set by {@link #length(int)}
   *
   * @param symbol The symbol you want to set.
   * @return Instance of the current component for more fluent builder design.
   */
  public LineSeparatorComponent symbol(char symbol) {
    this.symbol = symbol;
    return this;
  }

  /**
   * The colors in which the line separator symbols should be displayed.
   * You can also apply style codes such as {@link ChatColor#STRIKETHROUGH}
   * here.
   *
   * @param colors The colors to be displayed.
   * @return Instance of the current component for more fluent builder design.
   */
  public LineSeparatorComponent color(ChatColor... colors) {
    this.colors = colors;
    return this;
  }

  /**
   * Sets the amount of times the given symbol should be repeated.
   *
   * @param length The length of your line separator.
   * @return Instance of the current component for more fluent builder design.
   */
  public LineSeparatorComponent length(int length) {
    this.length = length;
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
    StringBuilder builder = new StringBuilder();

    for (ChatColor color : colors) {
      builder.append(color);
    }

    for (int i = 0; i < length; i++) {
      builder.append(symbol);
    }

    output.put(line, builder.toString());
    return output;
  }

  /**
   * Contains some static values for possible length of
   * a line separator.
   */
  public static class SeparatorLength {
    public static final int FULL = 30;
    public static final int HALF = 15;
    public static final int SHORTEST = 1;
  }

}
