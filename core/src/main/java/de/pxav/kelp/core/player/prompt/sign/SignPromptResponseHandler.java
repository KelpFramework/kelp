package de.pxav.kelp.core.player.prompt.sign;

import de.pxav.kelp.core.player.prompt.PromptResponseType;

import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public interface SignPromptResponseHandler {

  PromptResponseType accept(List<String> input);

}
