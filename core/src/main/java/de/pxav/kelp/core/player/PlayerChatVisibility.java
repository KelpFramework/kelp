package de.pxav.kelp.core.player;

/**
 * This enum represents the visibility of the chat
 * defined in the player's client settings. There you can
 * configure whether chat messages should be shown or not.
 *
 * Kelp intercepts this data when it is sent to the server
 * and makes it accessible for developers via the {@code KelpPlayer}
 * class.
 *
 * @author pxav
 */
public enum PlayerChatVisibility {

  /**
   * All messages should be shown to the player including
   * plugin-messages, messages by other players and
   * command feedback. This is the default setting in most clients.
   */
  SHOW_ALL_MESSAGES,

  /**
   * If the player chose this option they only want to see
   * command feedback and system messages such as
   * "Your gamemode has been changed", ...
   */
  COMMANDS_ONLY,

  /**
   * All messages are hidden to the player and they can not
   * see anything of the chat.
   */
  HIDDEN

}
