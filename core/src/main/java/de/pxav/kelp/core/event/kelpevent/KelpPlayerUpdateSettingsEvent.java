package de.pxav.kelp.core.event.kelpevent;

import de.pxav.kelp.core.player.PlayerChatVisibility;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpPlayerUpdateSettingsEvent extends PlayerEvent {

  private static final HandlerList handlers = new HandlerList();

  private SettingsUpdateStage updateStage;
  private String language;
  private int viewDistance;
  private PlayerChatVisibility playerChatVisibility;
  private boolean chatColorEnabled;

  public KelpPlayerUpdateSettingsEvent(Player who,
                                       SettingsUpdateStage updateStage,
                                       String language,
                                       int viewDistance,
                                       PlayerChatVisibility playerChatVisibility,
                                       boolean chatColorEnabled) {
    super(who);
    this.updateStage = updateStage;
    this.language = language;
    this.viewDistance = viewDistance;
    this.playerChatVisibility = playerChatVisibility;
    this.chatColorEnabled = chatColorEnabled;
  }

  public SettingsUpdateStage getUpdateStage() {
    return updateStage;
  }

  public String getLanguage() {
    return this.language;
  }

  public int getViewDistance() {
    return viewDistance;
  }

  public boolean getChatColorEnabled() {
    return chatColorEnabled;
  }

  public PlayerChatVisibility getChatVisibility() {
    return playerChatVisibility;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

}
