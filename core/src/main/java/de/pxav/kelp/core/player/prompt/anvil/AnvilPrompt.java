package de.pxav.kelp.core.player.prompt.anvil;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.player.prompt.PromptTimeout;
import de.pxav.kelp.core.player.prompt.SimplePromptResponseHandler;
import de.pxav.kelp.core.player.prompt.sign.SignPrompt;
import de.pxav.kelp.core.player.prompt.sign.SignPromptResponseHandler;
import de.pxav.kelp.core.player.prompt.sign.SignPromptVersionTemplate;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * An anvil prompt is a simple and intuitive way to query user input.
 * The player is presented an anvil gui and can use the renaming line
 * as text prompt. The result item is used to confirm the input, which
 * can be handled with a {@link SimplePromptResponseHandler}.
 *
 * @author pxav
 */
public class AnvilPrompt {

  private final AnvilPromptVersionTemplate anvilPromptVersionTemplate;

  private final Player player;
  private String initialText = "";
  private KelpMaterial sourceMaterial = KelpMaterial.NAME_TAG;
  private Runnable onClose;
  private PromptTimeout timeout = new PromptTimeout();
  private SimplePromptResponseHandler responseHandler;

  public AnvilPrompt(Player player, AnvilPromptVersionTemplate anvilPromptVersionTemplate) {
    this.player = player;
    this.anvilPromptVersionTemplate = anvilPromptVersionTemplate;
  }

  /**
   * Give the prompt a default text that is displayed when the gui opens.
   *
   * @param initialText The text you want to display on inventory open.
   * @return The current prompt instance for fluent builder design.
   */
  public AnvilPrompt initialText(String initialText) {
    this.initialText = initialText;
    return this;
  }

  /**
   * Sets the source material used in the anvil inventory. The source item is
   * on the very left slot and basically is the item the player is renaming.
   * This material will be used for the result item later as well on which the
   * player has to click in order to confirm their input.
   *
   * @param kelpMaterial The material you want to use for source and result.
   * @return The current prompt instance for fluent builder design.
   */
  public AnvilPrompt sourceMaterial(KelpMaterial kelpMaterial) {
    this.sourceMaterial = kelpMaterial;
    return this;
  }

  /**
   * Define what should happen when the player closes their inventory manually.
   *
   * @param onClose The code that should be executed.
   * @return The current prompt instance for fluent builder design.
   */
  public AnvilPrompt onClose(Runnable onClose) {
    this.onClose = onClose;
    return this;
  }

  /**
   * Configures a synchronous timeout. That means that the code which is ran on timeout will
   * be executed on the server's main thread. You should prefer this option if you know your
   * actions are not thread-safe.
   *
   * @param timeout         How long it should take until the prompt times out.
   * @param timeUnit        Provide a time unit for the {@code timeout} value.
   * @param onTimeout       Define what should happen when the player closes its inventory.
   *                        Please check if your actions are thread safe. If so, use {@link #withAsyncTimeout(int, TimeUnit, Runnable, boolean)}
   *                        instead.
   * @param closeOnTimeout  Should the inventory be closed when the player prompt times out?
   * @return The current prompt instance for fluent builder design.
   */
  public AnvilPrompt withSyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout, boolean closeOnTimeout) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, false, closeOnTimeout);
    return this;
  }

  /**
   * Configures an asynchronous timeout. That means that the code which is ran on timeout will
   * be executed on another thread than the server's main thread. You should prefer this option
   * if you know your actions are thread-safe or you can jump to the main thread easily.
   *
   * @param timeout         How long it should take until the prompt times out.
   * @param timeUnit        Provide a time unit for the {@code timeout} value.
   * @param onTimeout       Define what should happen when the player closes its inventory.
   *                        Please check if your actions are thread safe. If not, use {@link #withSyncTimeout(int, TimeUnit, Runnable)}
   *                        instead.
   * @param closeOnTimeOut  Should the inventory be closed when the prompt times out?
   * @return The current prompt instance for fluent builder design.
   */
  public AnvilPrompt withAsyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout, boolean closeOnTimeOut) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, true, closeOnTimeOut);
    return this;
  }

  /**
   * Configures a synchronous timeout. That means that the code which is ran on timeout will
   * be executed on the server's main thread. You should prefer this option if you know your
   * actions are not thread-safe.
   *
   * This method closes the inventory by default. If you want to change that, use
   * {@link #withSyncTimeout(int, TimeUnit, Runnable, boolean)}.
   *
   * @param timeout         How long it should take until the prompt times out.
   * @param timeUnit        Provide a time unit for the {@code timeout} value.
   * @param onTimeout       Define what should happen when the player closes its inventory.
   *                        Please check if your actions are thread safe. If so, use {@link #withAsyncTimeout(int, TimeUnit, Runnable)}
   *                        instead.
   * @return The current prompt instance for fluent builder design.
   */
  public AnvilPrompt withSyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, false, true);
    return this;
  }

  /**
   * Configures an asynchronous timeout. That means that the code which is ran on timeout will
   * be executed on another thread than the server's main thread. You should prefer this option
   * if you know your actions are thread-safe or you can jump to the main thread easily.
   *
   * This method closes the inventory by default. If you want to change that, use
   * {@link #withAsyncTimeout(int, TimeUnit, Runnable, boolean)}.
   *
   * @param timeout         How long it should take until the prompt times out.
   * @param timeUnit        Provide a time unit for the {@code timeout} value.
   * @param onTimeout       Define what should happen when the player closes its inventory.
   *                        Please check if your actions are thread safe. If not, use {@link #withSyncTimeout(int, TimeUnit, Runnable)}
   *                        instead.
   * @return The current prompt instance for fluent builder design.
   */
  public AnvilPrompt withAsyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, true, true);
    return this;
  }

  /**
   * Provide the handler class handling the input of the player.
   *
   * @param responseHandler The handler class implementing {@link SimplePromptResponseHandler}
   */
  public void handle(SimplePromptResponseHandler responseHandler) {
    this.responseHandler = responseHandler;
    this.anvilPromptVersionTemplate.openPrompt(this.player, this.initialText, this.sourceMaterial, this.onClose, this.timeout, this.responseHandler);
  }

  /**
   * Gets the instance of the response handler for the current prompt.
   * If no handler has been defined, it will return {@code null}.
   * @return The current prompt response handler.
   */
  public SimplePromptResponseHandler getResponseHandler() {
    return responseHandler;
  }

  /**
   * Gets the default text that is displayed when the gui opens.
   * @return The initial text of the current anvil prompt.
   */
  public String getInitialText() {
    return this.initialText;
  }

  /**
   * Gets all information about the timeout configuration. More information about that
   * can be found in {@link PromptTimeout}
   * @return The current prompt timeout configuration.
   */
  public PromptTimeout getTimeout() {
    return timeout;
  }

}
