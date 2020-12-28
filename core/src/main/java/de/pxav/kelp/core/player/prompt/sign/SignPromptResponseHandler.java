package de.pxav.kelp.core.player.prompt.sign;

import de.pxav.kelp.core.player.prompt.PromptResponseType;

import java.util.List;

/**
 * This interface is used to handle the input provided by a
 * {@link SignPrompt}, similar to the {@link de.pxav.kelp.core.player.prompt.SimplePromptResponseHandler}
 * with the only exception that this handles takes a list of strings instead of
 * a single string, as a sign can hold up to 4 lines of text.
 * Each item in the list represents one line of the sign, so the maximum length of the
 * list is {@code 4} and the maximum index {@code 3}.
 *
 * This class should be implemented by every class handling sign input.
 *
 * @author pxav
 */
public interface SignPromptResponseHandler {

  /**
   * This method is executed when the player of the current sign
   * prompt submits their input by pressing {@code 'Done'} in the sign
   * editor GUI.
   *
   * @param input The lines the player has written on the sign.
   *              Each list item represents one line on the sign
   *              in chronological order. So the 0th item is the
   *              1st line on the sign, the 1st list item is the
   *              2nd line on the sign, ...
   * @return  Whether the input was valid ({@link PromptResponseType#ACCEPTED}) and the
   *          prompt may be closed now, or if the input was invalid ({@link PromptResponseType#TRY_AGAIN})
   *          and the player has to try another input.
   */
  PromptResponseType accept(List<String> input);

}
