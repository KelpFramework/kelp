package de.pxav.kelp.core.player.prompt.chat;

import de.pxav.kelp.core.player.prompt.PromptTimeout;
import de.pxav.kelp.core.player.prompt.SimplePromptResponseHandler;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

/**
 * The simple chat prompt is a way for developers to simply query
 * small text data from the player. An example would be setting a nickname, etc.
 *
 * For querying the input, the normal chat text box is used and the normal chat is
 * disabled for the player while they type in their input. The user can exit at
 * every time by using an exit flag and an optional echo can be configured.
 *
 * If the input has been accepted or the player has left the input mode, the player
 * is allowed to write into the normal public chat again.
 *
 * @author pxav
 */
public class SimpleChatPrompt {

  private final ChatPromptVersionTemplate chatPromptVersionTemplate;

  private final Player player;
  private String cancelFlag;
  private boolean enableEcho = false;
  private String echoPrefix = "";
  private String echoSuffix = "";
  private Runnable onCancel;
  private PromptTimeout timeout = new PromptTimeout();
  private SimplePromptResponseHandler responseHandler;

  public SimpleChatPrompt(Player player, ChatPromptVersionTemplate chatPromptVersionTemplate) {
    this.player = player;
    this.chatPromptVersionTemplate = chatPromptVersionTemplate;
  }

  /**
   * Defines the message a player has to type in order to exit the
   * prompt and use the chat normally again.
   *
   * @param cancelFlag The string a player has to type in order to exit again.
   * @return The current instance of the prompt for fluent builder design.
   */
  public SimpleChatPrompt cancelFlag(String cancelFlag) {
    this.cancelFlag = cancelFlag;
    return this;
  }

  /**
   * Enables an echo for the input the player types. An echo is a
   * message printing the input the player typed into the chat again
   * to make the input processing more transparent and allow the player
   * to find typos, etc.
   *
   * @param prefix The text that should be displayed in front of the actual input.
   *               Can also be used to provide color codes for the message.
   *               Choose {@code null} if you do not want a prefix.
   * @param suffix The text that should be displayed after the actual input.
   *               Choose {@code null} if you want no suffix.
   * @return The current instance of the prompt for fluent builder design.
   */
  public SimpleChatPrompt enableEcho(String prefix, String suffix) {
    this.enableEcho = true;
    this.echoPrefix = prefix;
    this.echoSuffix = suffix;
    return this;
  }

  /**
   * Disables the echo message. An echo message is a message printing
   * the input from the player into the chat again so that the input
   * processing becomes more transparent for the player.
   *
   * @return The current instance of the prompt for fluent builder design.
   */
  public SimpleChatPrompt disableEcho() {
    this.enableEcho = false;
    return this;
  }

  /**
   * Enables a timeout for the current chat prompt. More detailed information
   * about timeouts can be found in {@link PromptTimeout}.
   *
   * The code provided in the timeout is executed synchronously. Use
   * {@link #withAsyncTimeout(int, TimeUnit, Runnable)} to invert that.
   *
   * @param timeout     The amount of time that has to pass until the prompt times out.
   * @param timeUnit    The {@link TimeUnit} of the given {@code timeout}.
   * @param onTimeout   The code that should be executed when the prompt times out.
   *                    In this case, this code is executed synchronously.
   * @return The current instance of the prompt for fluent builder design.
   */
  public SimpleChatPrompt withSyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, false, false);
    return this;
  }

  /**
   * Enables a timeout for the current chat prompt. More detailed information
   * about timeouts can be found in {@link PromptTimeout}.
   *
   * The code provided in the timeout is executed asynchronously. Use
   * {@link #withSyncTimeout(int, TimeUnit, Runnable)} to invert that.
   *
   * @param timeout     The amount of time that has to pass until the prompt times out.
   * @param timeUnit    The {@link TimeUnit} of the given {@code timeout}.
   * @param onTimeout   The code that should be executed when the prompt times out.
   *                    In this case, this code is executed asynchronously.
   * @return The current instance of the prompt for fluent builder design.
   */
  public SimpleChatPrompt withAsyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, true, false);
    return this;
  }

  /**
   * Defines the code that should be executed when the player enters
   * the exit flag defined in {@link #cancelFlag(String)} to exit the
   * prompt.
   *
   * @param onCancel The code to be executed when the player exits the prompt.
   * @return The current instance of the prompt for fluent builder design.
   */
  public SimpleChatPrompt onCancel(Runnable onCancel) {
    this.onCancel = onCancel;
    return this;
  }

  /**
   * Defines the handler that should handle the input of the player.
   * More detailed information can be found in {@link SimplePromptResponseHandler}.
   *
   * @param responseHandler The class to handle the prompt's input.
   */
  public void handle(SimplePromptResponseHandler responseHandler) {
    this.responseHandler = responseHandler;
    this.chatPromptVersionTemplate.simpleChatPrompt(this);
  }

  /**
   * @return The code handling the prompt's input string.
   */
  public SimplePromptResponseHandler getResponseHandler() {
    return responseHandler;
  }

  /**
   * @return The text a player has to type in order to exit the prompt and use the normal chat again.
   */
  public String getCancelFlag() {
    return this.cancelFlag;
  }

  /**
   * @return Detailed information about the prompt timeout if a timeout has been enabled.
   */
  public PromptTimeout getTimeout() {
    return timeout;
  }

  /**
   * @return whether echo messages are enabled.
   */
  public boolean isEchoEnabled() {
    return enableEcho;
  }

  /**
   * @return The prefix of the echo message. (might be {@code null}!)
   */
  public String getEchoPrefix() {
    return echoPrefix;
  }

  /**
   * @return The suffix of the echo message. (might be {@code null}!)
   */
  public String getEchoSuffix() {
    return echoSuffix;
  }

  /**
   * @return the code that should be executed when the player enters the exit flag defined in {@link #cancelFlag(String)}
   */
  public Runnable getOnCancel() {
    return onCancel;
  }

  /**
   * @return the player who owns the current prompt.
   */
  public Player getPlayer() {
    return player;
  }
}
