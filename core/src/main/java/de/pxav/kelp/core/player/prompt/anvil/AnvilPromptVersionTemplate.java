package de.pxav.kelp.core.player.prompt.anvil;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.prompt.SimplePromptResponseHandler;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class AnvilPromptVersionTemplate {

  public abstract void openPrompt(KelpPlayer player, String initialText, KelpMaterial sourceMaterial, SimplePromptResponseHandler handler);

}
