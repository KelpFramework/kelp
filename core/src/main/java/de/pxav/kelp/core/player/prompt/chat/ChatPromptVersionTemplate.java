package de.pxav.kelp.core.player.prompt.chat;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.prompt.PromptTimeout;
import de.pxav.kelp.core.player.prompt.SimplePromptResponseHandler;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class ChatPromptVersionTemplate {

  public abstract void simpleChatPrompt(SimpleChatPrompt simpleChatPrompt);

}
