package de.pxav.kelp.core.player.prompt.anvil;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.prompt.SimplePromptResponseHandler;
import org.bukkit.entity.Player;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class AnvilPromptVersionTemplate {

  public abstract void openPrompt(Player player,
                                  String initialText,
                                  KelpMaterial sourceMaterial,
                                  Runnable onClose,
                                  SimplePromptResponseHandler handler);

}
