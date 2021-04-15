package de.pxav.kelp.core.player.prompt.sign;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.player.prompt.PromptTimeout;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * This version template is used for handling - especially opening - the sign prompt
 * to a specific player. This process requires packets.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class SignPromptVersionTemplate {

  /**
   * Opens the sign editor to a specific player. If the player clicks {@code Done} in this editor,
   * the response handler is called automatically.
   *
   * @param player          The player you want the prompt to show to.
   * @param initialLines    Pre-defined input. When the editor opens, there won't be an empty sign, but
   *                        already some lines written to it providing help to the player on which input
   *                        format is expected for example. The list follows a chronological order, so the
   *                        0th list item is the 1st line and so on. If you do not want a default input to
   *                        be displayed, simply pass an empty list here.
   * @param timeout         If you want to enable a timeout, you can configure it here. Detailed information on
   *                        how to do that can be found in {@link PromptTimeout}. If you want to disable timeout,
   *                        pass {@code null} here.
   * @param responseHandler The code handling the input by the player. If the player submits their input by pressing
   *                        {@code Done}, the {@link SignPromptResponseHandler#accept(List)} method is executed.
   *                        More information about handling the response can be found in {@link SignPromptResponseHandler}.
   */
  public abstract void openSignPrompt(Player player, List<String> initialLines, PromptTimeout timeout, SignPromptResponseHandler responseHandler);

}
