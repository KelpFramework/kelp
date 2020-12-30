package de.pxav.kelp.core.player.prompt;

/**
 * This constant describes if an input from any player prompt
 * has been accepted by the handler. If the prompt asked for a
 * number but the input also consists of alphabetical chars for
 * example, the handler would return {@link PromptResponseType#TRY_AGAIN},
 * which means that the prompt will ask the player again.
 *
 * This simplifies input validation by a lot.
 *
 * @author pxav
 */
public enum PromptResponseType {

  /**
   * The result is valid and has been accepted by the result handler.
   * The prompt will close.
   */
  ACCEPTED,

  /**
   * The result is invalid and has not been accepted by the result handler.
   * The prompt will open again asking for another input by the player until
   * the handler accepts it.
   */
  TRY_AGAIN

}
