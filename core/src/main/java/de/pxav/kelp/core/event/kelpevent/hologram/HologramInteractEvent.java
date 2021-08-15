package de.pxav.kelp.core.event.kelpevent.hologram;

import de.pxav.kelp.core.event.kelpevent.EntityInteractAction;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.hologram.KelpHologram;
import org.bukkit.event.HandlerList;

public class HologramInteractEvent extends HologramEvent {

  private static final HandlerList handlers = new HandlerList();

  private KelpPlayer player;
  private boolean componentInteract;
  private EntityInteractAction action;

  public HologramInteractEvent(KelpHologram hologram, KelpPlayer player, EntityInteractAction action, boolean componentInteract) {
    super(hologram);
    this.player = player;
    this.componentInteract = componentInteract;
    this.action = action;
  }

  public boolean isComponentInteract() {
    return componentInteract;
  }

  public EntityInteractAction getAction() {
    return action;
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
