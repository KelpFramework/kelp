package de.pxav.kelp.core.event.kelpevent;

import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.region.KelpRegion;
import org.bukkit.event.HandlerList;

public class PlayerEnterRegionEvent extends KelpRegionEvent {

  private static final HandlerList handlers = new HandlerList();

  private KelpPlayer player;

  public PlayerEnterRegionEvent(KelpRegion region, KelpPlayer player) {
    super(region);
    this.player = player;
  }

  public KelpPlayer getPlayer() {
    return player;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

}
