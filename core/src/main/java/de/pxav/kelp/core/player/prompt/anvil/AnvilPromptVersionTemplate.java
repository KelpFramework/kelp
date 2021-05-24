package de.pxav.kelp.core.player.prompt.anvil;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.player.prompt.PromptTimeout;
import de.pxav.kelp.core.player.prompt.SimplePromptResponseHandler;
import org.bukkit.entity.Player;

/**
 * This version template is used to handle version specific stuff
 * for anvil prompts.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class AnvilPromptVersionTemplate {

  /**
   * Opens a new anvil prompt to the given player. This means that the player
   * will see an anvil GUI and has to enter a text. After that he can confirm this
   * text by clicking on the result item at the very right of the inventory.
   * Then the input can be handled and the plugin can decide whether it accepts
   * the input or the player has to try again.
   *
   * @param player          The player you want the prompt to open to.
   * @param initialText     The initial text which should already be in the text line as default input.
   * @param sourceMaterial  The material to use in the inventory on the left slot (the item that is actually
   *                        renamed and the item that has the default name) as well as in the right result
   *                        slot (the item used to confirm the input).
   * @param onClose         If the player closes their inventory manually while being in the prompt, what should happen?
   * @param timeout         If you want to enable a timeout, you can configure it here. If you don't want
   *                        a timeout, pass {@code null}.
   * @param handler         The class handling the input by the player. More detailed information can be found
   *                        at {@link SimplePromptResponseHandler}
   */
  public abstract void openPrompt(Player player,
                                  String initialText,
                                  KelpMaterial sourceMaterial,
                                  Runnable onClose,
                                  PromptTimeout timeout,
                                  SimplePromptResponseHandler handler);

}
