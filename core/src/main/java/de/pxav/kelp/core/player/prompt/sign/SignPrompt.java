package de.pxav.kelp.core.player.prompt.sign;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.player.prompt.PromptTimeout;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class represents a sign prompt.
 *
 * @author pxav
 */
public class SignPrompt {

  private final SignPromptVersionTemplate signPromptVersionTemplate;

  private final Player player;
  private List<String> initialLines = Lists.newArrayList();
  private PromptTimeout timeout = new PromptTimeout();
  private SignPromptResponseHandler responseHandler;

  public SignPrompt(Player player, SignPromptVersionTemplate signPromptVersionTemplate) {
    this.player = player;
    this.signPromptVersionTemplate = signPromptVersionTemplate;
  }

  /**
   * Define the default lines of the sign the player edits. If you
   * only want to change the first line of the sign, you only have to
   * set the first list element and leave the other 3 slots empty ({@code null}).
   *
   * @param initialLines The default text lines of the sign in chronological order.
   * @return The current prompt instance for fluent builder design.
   */
  public SignPrompt initialLines(List<String> initialLines) {
    this.initialLines = initialLines;
    return this;
  }

  /**
   * Adds a synchronous timeout to the prompt, which means that the code provided in {@code onTimeout}
   * is executed on the server's main thread.
   *
   * @param timeout     How long it should take until the prompt times out.
   * @param timeUnit    Provide a unit for the above {@code timeout}
   * @param onTimeout   The code that should be executed when the prompt times out.
   *                    If your code is thread-safe you might want to choose
   *                    {@link #withAsyncTimeout(int, TimeUnit, Runnable)} instead.
   * @return The current prompt instance for fluent builder design.
   */
  public SignPrompt withSyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, false, false);
    return this;
  }

  /**
   * Adds an asynchronous timeout to the prompt, which means that the code provided in {@code onTimeout}
   * is executed on a thread other than the main thread.
   *
   * @param timeout     How long it should take until the prompt times out.
   * @param timeUnit    Provide a unit for the above {@code timeout}
   * @param onTimeout   The code that should be executed when the prompt times out.
   *                    Make sure the code is thread-safe. If this is not possible,
   *                    choose {@link #withSyncTimeout(int, TimeUnit, Runnable)} instead.
   * @return The current prompt instance for fluent builder design.
   */
  public SignPrompt withAsyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, true, false);
    return this;
  }

  /**
   * Provide the handler class implementing {@link SignPromptResponseHandler} interface and
   * handling the input provided by the player in the sign editor.
   *
   * @param responseHandler The handler class for the player's input.
   */
  public void handle(SignPromptResponseHandler responseHandler) {
    this.responseHandler = responseHandler;
    this.signPromptVersionTemplate.openSignPrompt(this.player, this.initialLines, this.timeout, this.responseHandler);
  }

  /**
   * Gets the current response handler for the sign prompt.
   *
   * @return The current reponse handler for the sign prompt.
   */
  public SignPromptResponseHandler getResponseHandler() {
    return responseHandler;
  }

  /**
   * Gets a list of the default lines displayed when the sign
   * editor opens for the first time.
   *
   * @return The default text written on the sign.
   */
  public List<String> getInitialLines() {
    return initialLines;
  }

  /**
   * Gets all information about the timeout configuration. More information about that
   * can be found in {@link PromptTimeout}
   *
   * @return The current prompt timeout configuration.
   */
  public PromptTimeout getTimeout() {
    return timeout;
  }
}
