package de.pxav.kelp.core.player.prompt;

/**
 * This interface should be implemented by every class handling the input
 * of a simple prompt (Chat or anvil). If you want to handle a sign prompt,
 * please use {@link de.pxav.kelp.core.player.prompt.sign.SignPromptResponseHandler}.
 *
 * @author pxav
 */
public interface SimplePromptResponseHandler {

  /**
   * This method is executed every time a player submits their prompt
   * input to the server. Then you can check this input and decide whether
   * it is valid and the prompt can be closed or whether the player
   * has to try it again.
   *
   * @param input The input sent by the player.
   * @return Whether the player's input was valid and the prompt may close
   *         or whether the player has to try again.
   */
  PromptResponseType accept(String input);

}
