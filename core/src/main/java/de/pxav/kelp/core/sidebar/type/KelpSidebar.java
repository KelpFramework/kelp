package de.pxav.kelp.core.sidebar.type;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.sidebar.component.SidebarComponent;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public abstract class KelpSidebar<T extends KelpSidebar> {

  protected Collection<SidebarComponent> components = Lists.newArrayList();
  protected int latestLines = 0;

  public T addComponent(SidebarComponent component) {
    this.components.add(component);
    return (T) this;
  }

  public T removeComponent(SidebarComponent component) {
    this.components.remove(component);
    return (T) this;
  }

  public T clearComponents() {
    this.components.clear();
    return (T) this;
  }

  public Collection<SidebarComponent> getComponents() {
    return this.components;
  }

  public int getLatestLines() {
    return latestLines;
  }

  public T setLatestLines(int latestLines) {
    this.latestLines = latestLines;
    return (T) this;
  }

  public abstract void render(KelpPlayer player);

  public abstract void update(KelpPlayer player);

  public abstract void remove(KelpPlayer player);
//
//  protected void unregisterAllTeams() {
//    scoreboard.getTeams().forEach(current -> {
//      if (current.getName().startsWith("entry_")) {
//        current.unregister();
//      }
//    });
//  }

}
