package de.pxav.kelp.core.event.kelpevent.sidebar;

import de.pxav.kelp.core.event.kelpevent.KelpPlayerEvent;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.sidebar.type.KelpSidebar;
import org.bukkit.event.HandlerList;

/**
 * This event is triggered when any {@link KelpSidebar<>} is updated no
 * matter if it is a lazy or normal update. Title updates are not
 * included.
 *
 * @author pxav
 */
public class KelpSidebarUpdateEvent extends KelpPlayerEvent {

  private static final HandlerList handlers = new HandlerList();

  private boolean lazyUpdate;
  private KelpSidebar sidebar;

  public KelpSidebarUpdateEvent(KelpPlayer who, KelpSidebar sidebar, boolean lazyUpdate) {
    super(who);
    this.sidebar = sidebar;
    this.lazyUpdate = lazyUpdate;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

  /**
   * Checks if the update was performed lazy, which means that
   * no scores have been added/deleted from the board.
   *
   * @return {@code true} if the update was lazy.
   */
  public boolean isLazyUpdate() {
    return lazyUpdate;
  }

  /**
   * Gets an instance of the scoreboard that has been updated.
   * It can be casted to the specific type such as {@link de.pxav.kelp.core.sidebar.type.AnimatedSidebar}
   * for example if needed.
   *
   * @return The current sidebar object.
   */
  public KelpSidebar getSidebar() {
    return sidebar;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

}
