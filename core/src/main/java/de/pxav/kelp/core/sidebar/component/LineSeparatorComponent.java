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

  public LineSeparatorComponent line(int line) {
    this.line = line;
    return this;
  }

  public LineSeparatorComponent symbol(char symbol) {
    this.symbol = symbol;
    return this;
  }

  public LineSeparatorComponent color(ChatColor... colors) {
    this.colors = colors;
    return this;
  }

  public LineSeparatorComponent length(int length) {
    this.length = length;
    return this;
  }

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

  public static class SeparatorLength {
    public static final int FULL = 30;
    public static final int HALF = 15;
    public static final int SHORTEST = 1;
  }

}
