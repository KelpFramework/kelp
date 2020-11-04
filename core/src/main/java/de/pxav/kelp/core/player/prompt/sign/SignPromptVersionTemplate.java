package de.pxav.kelp.core.player.prompt.sign;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.prompt.PromptTimeout;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class SignPromptVersionTemplate {

  public abstract void openSignPrompt(Player player, List<String> initialLines, PromptTimeout timeout, SignPromptResponseHandler responseHandler);

}
