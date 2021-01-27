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

  private SidebarUtils sidebarUtils;

  public StatelessTextComponent(SidebarUtils sidebarUtils) {
    this.sidebarUtils = sidebarUtils;
  }

  public static StatelessTextComponent create() {
    return new StatelessTextComponent(KelpPlugin.getInjector().getInstance(SidebarUtils.class));
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
//    String entry = sidebarUtils.randomEmptyEntry(parent);
//    Objective objective = parent.getObjective(DisplaySlot.SIDEBAR);
//
//    objective.getScore(entry).setScore(line);
//
//    Team team = parent.registerNewTeam("entry_" + line);
//    team.addEntry(entry);
//    update(parent);
//    return update();
    return output;
  }

  @Override
  public Map<Integer, String> update() {
    return null;
    //return Collections.singletonList(text);
  }

}
