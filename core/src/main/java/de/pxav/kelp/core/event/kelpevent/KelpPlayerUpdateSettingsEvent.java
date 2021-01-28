package de.pxav.kelp.core.event.kelpevent;

import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.PlayerChatVisibility;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

/**
 * This event is triggered when the client settings of a player change.
 * It is triggered before the settings are actually updated in the
 * {@link KelpPlayer} class, so you can do comparisons between the old
 * and new values if you wish.
 *
 * @author pxav
 */
public class KelpPlayerUpdateSettingsEvent extends KelpPlayerEvent {

  // list of all event handlers listening for this event
  private static final HandlerList handlers = new HandlerList();

  // the stage when the settings changed
  private SettingsUpdateStage updateStage;

  // the new client language.
  private String language;

  // the new client view distance.
  private int viewDistance;

  // the new chat visibility
  private PlayerChatVisibility playerChatVisibility;

  // whether the player has chat colors enabled.
  private boolean chatColorEnabled;

  public KelpPlayerUpdateSettingsEvent(KelpPlayer who,
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

  /**
   * @return  How the server received the settings update. Look into the documentation
   *          of {@link SettingsUpdateStage} for more detailed information.
   */
  public SettingsUpdateStage getUpdateStage() {
    return updateStage;
  }

  public String getLanguage() {
    return this.language;
  }

  public boolean hasLanguageChanged() {
    return !this.language.equalsIgnoreCase(player.getClientLanguage());
  }

  public int getViewDistance() {
    return viewDistance;
  }

  public boolean hasViewDistanceChanged() {
    return viewDistance != player.getClientViewDistance();
  }

  public boolean getChatColorEnabled() {
    return chatColorEnabled;
  }

  /**
   * Returns {@code true} if the chat color setting has changed.
   * @return if the chat color setting has changed.
   */
  public boolean hasChatColorChanged() {
    return chatColorEnabled != player.isPlayerChatColorEnabled();
  }

  /**
   * Get the updated chat visibility.
   * @return the updated chat visibility.
   */
  public PlayerChatVisibility getChatVisibility() {
    return playerChatVisibility;
  }

  /**
   * Returns {@code true} if the chat visibility settings have changed.
   * @return if the chat visibility settings have changed.
   */
  public boolean hasChatVisibilityChanged() {
    return playerChatVisibility != player.getPlayerChatVisibility();
  }

  /**
   * Returns the kelp player instance of the player whose settings have changed.
   * @return the kelp player instance of the player whose settings have changed.
   */
  public KelpPlayer getKelpPlayer() {
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
