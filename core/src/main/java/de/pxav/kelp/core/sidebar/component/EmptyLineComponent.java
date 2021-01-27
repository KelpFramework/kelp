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
 * You could also write them manually as a {@code SimpleTextComponent},
 * but this way makes it more maintainable, because you don't
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

  private int line;

  public static EmptyLineComponent create() {
    return new EmptyLineComponent();
  }

  public EmptyLineComponent line(int line) {
    this.line = line;
    return this;
  }

  @Override
  public Map<Integer, String> render() {
    Map<Integer, String> output = Maps.newHashMap();
    output.put(line, " ");
    return output;
  }

}
