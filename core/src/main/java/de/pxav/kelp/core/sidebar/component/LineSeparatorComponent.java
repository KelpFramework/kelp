package de.pxav.kelp.core.sidebar.component;

import de.pxav.kelp.core.sidebar.SidebarUtils;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

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
public class LineSeparatorComponent implements SimpleSidebarComponent {

  private int line;
  private int length;
  private char symbol;
  private ChatColor[] colors;

  private SidebarUtils sidebarUtils;

  LineSeparatorComponent(SidebarUtils sidebarUtils) {
    this.sidebarUtils = sidebarUtils;

    this.length = SeparatorLength.FULL;
    this.symbol = '-';
    this.colors = new ChatColor[] {ChatColor.DARK_GRAY, ChatColor.STRIKETHROUGH};
  }

  public LineSeparatorComponent score(int line) {
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
  public void render(Scoreboard parent) {
    String entry = sidebarUtils.randomEmptyEntry(parent);
    Objective objective = parent.getObjective(DisplaySlot.SIDEBAR);

    objective.getScore(entry).setScore(line);

    Team team = parent.registerNewTeam("entry_" + line);
    team.addEntry(entry);
    update(parent);
  }

  @Override
  public void update(Scoreboard parent) {
    Team team = parent.getTeam("entry_" + line);
    StringBuilder prefix = new StringBuilder();
    StringBuilder suffix = new StringBuilder();
    int totalLength = this.length + colors.length;

    for (ChatColor color : colors) {
      prefix.append(color);
      if (totalLength > 16) {
        suffix.append(color);
      }
    }

    for (int i = 0; i < length; i++) {
      prefix.append(symbol);
      if (totalLength > 16) {
        suffix.append(symbol);
      }
    }

    if (prefix.toString().length() > 16) {
      if (suffix.toString().length() > 16) {
        team.setSuffix(suffix.toString().substring(0, 16));
      } else {
        team.setSuffix(suffix.toString());
      }
      team.setPrefix(prefix.toString().substring(0, 16));
      return;
    }

    team.setPrefix(prefix.toString());
  }

  public static class SeparatorLength {
    public static final int FULL = 30;
    public static final int HALF = 15;
    public static final int SHORTEST = 1;
  }

}
