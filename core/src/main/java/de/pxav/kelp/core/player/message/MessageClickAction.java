package de.pxav.kelp.core.player.message;

import de.pxav.kelp.core.version.KelpVersion;
import de.pxav.kelp.core.version.SinceKelpVersion;

/**
 * Describes a click action that should be performed when
 * an {@link InteractiveMessage} is clicked.
 *
 * You can apply those actions via {@link MessageComponent}
 *
 * @author pxav
 */
public enum  MessageClickAction {

  /**
   * A given command is executed when the player clicks the message.
   * Prefixing the message with a {@code /} is not necessary.
   */
  EXECUTE_COMMAND,

  /**
   * Suggests a given command to the player. This means that the command
   * is written to the player's chat text box but not executed yet.
   */
  SUGGEST_COMMAND,

  /**
   * Opens a given URL to the player, which can be a website. Be sure to
   * prefix your message with {@code https://} in this case.
   */
  OPEN_URL,

  /**
   * Opens a file at the given file path. Please note that only server files
   * can be accessed. Local files of the client cannot be queried.
   */
  OPEN_FILE,

  /**
   * Changes the page of a book.
   */
  CHANGE_PAGE,

  /**
   * Makes the player send a chat message under his name.
   */
  SEND_CHAT_MESSAGE,

  /**
   * Copies a given text into the player's clipboard. Please note that
   * this is not available in all versions.
   */
  @SinceKelpVersion(KelpVersion.MC_1_14_0)
  COPY_TO_CLIPBOARD,

}
