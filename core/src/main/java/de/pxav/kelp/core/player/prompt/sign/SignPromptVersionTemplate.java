package de.pxav.kelp.core.player.prompt.sign;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;

import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class SignPromptVersionTemplate {

  public abstract void openSignPrompt(KelpPlayer player, List<String> initialText, SignPromptResponseHandler responseHandler);

}
