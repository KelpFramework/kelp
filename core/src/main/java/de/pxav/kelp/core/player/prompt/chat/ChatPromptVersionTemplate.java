package de.pxav.kelp.core.player.prompt.chat;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.prompt.PromptTimeout;
import de.pxav.kelp.core.player.prompt.SimplePromptResponseHandler;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * This version template is used for handling the simple chat prompt for players.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class ChatPromptVersionTemplate {

  /**
   * Sets the player's chat into chat prompt mode. This means that the normal
   * chat is disabled and messages are not visible for other players anymore.
   * If the player's input is handled successfully, the chat is turned into
   * normal mode again.
   *
   * @param simpleChatPrompt The {@link SimpleChatPrompt} to open. It can be built using
   *                         an instance of {@link KelpPlayer}.
   */
  public abstract void simpleChatPrompt(SimpleChatPrompt simpleChatPrompt);

}
