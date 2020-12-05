package de.pxav.kelp.core.event.kelpevent;

import de.pxav.kelp.core.player.KelpPlayer;
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

  private KelpPlayer who;
  private SettingsUpdateStage updateStage;
  private String language;
  private int viewDistance;
  private PlayerChatVisibility playerChatVisibility;
  private boolean chatColorEnabled;

  public KelpPlayerUpdateSettingsEvent(KelpPlayer who,
                                       SettingsUpdateStage updateStage,
                                       String language,
                                       int viewDistance,
                                       PlayerChatVisibility playerChatVisibility,
                                       boolean chatColorEnabled) {
    super(who.getBukkitPlayer());
    this.who = who;
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

  public boolean hasLanguageChanged() {
    return !this.language.equalsIgnoreCase(who.getClientLanguage());
  }

  public int getViewDistance() {
    return viewDistance;
  }

  public boolean hasViewDistanceChanged() {
    return viewDistance != who.getClientViewDistance();
  }

  public boolean getChatColorEnabled() {
    return chatColorEnabled;
  }

  public boolean hasChatColorChanged() {
    return chatColorEnabled != who.isPlayerChatColorEnabled();
  }

  public PlayerChatVisibility getChatVisibility() {
    return playerChatVisibility;
  }

  public boolean hasChatVisibilityChanged() {
    return playerChatVisibility != who.getPlayerChatVisibility();
  }

  public KelpPlayer getKelpPlayer() {
    return who;
  }

  @Override
  public HandlerList getHandlers() {
    return handlers;
  }

  public static HandlerList getHandlerList() {
    return handlers;
  }

}
