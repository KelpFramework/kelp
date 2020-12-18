package de.pxav.kelp.core.player.prompt;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * This class is used to create possible timeouts for player
 * prompts. You can configure that your plugin waits for 10
 * seconds until it will automatically close the prompt again
 * if the player has given no valid input during that time.
 *
 * In addition, you can define what should happen on a timeout
 * by configuring a {@link Runnable}, which can be used to
 * send messages or play sounds.
 *
 * @author pxav
 */
public class PromptTimeout {

  // how long the server should wait for an input
  private int timeout = 0;
  private TimeUnit timeUnit = TimeUnit.MILLISECONDS;

  // the code to be executed when the prompt times out
  // (can be used to send messages, play sounds, ...)
  private Runnable onTimeout;

  // whether the runnable should be executed on an async thread
  private boolean async = true;

  // whether the prompt should be closed on timeout.
  private boolean closeOnTimeout = false;

  // the task id of the scheduler handling the timeout.
  private UUID taskId;

  public PromptTimeout() {}

  public PromptTimeout(int timeout, TimeUnit timeUnit) {
    this.timeout = timeout;
    this.timeUnit = timeUnit;
  }

  public PromptTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout, boolean async, boolean closeOnTimeout) {
    this.timeout = timeout;
    this.timeUnit = timeUnit;
    this.onTimeout = onTimeout;
    this.async = async;
    this.closeOnTimeout = closeOnTimeout;
  }

  /**
   * Sets the kelp-internal task id of the scheduler handling the timeout.
   * This scheduler also executes the runnable {@link #getOnTimeout()} later.
   *
   * @param taskId The id of the scheduler that handles the timeout.
   */
  public void setTaskId(UUID taskId) {
    this.taskId = taskId;
  }

  /**
   * @return the time to wait until the prompt times out in the unit provided in {@link #getTimeUnit()}
   */
  public int getTimeout() {
    return timeout;
  }

  /**
   * @return The code that should be executed when the prompt times out.
   */
  public Runnable getOnTimeout() {
    return onTimeout;
  }

  /**
   * @return the {@link TimeUnit} of the provided time out time in {@link #getTimeout()}
   */
  public TimeUnit getTimeUnit() {
    return timeUnit;
  }

  /**
   * @return whether the prompt should be closed on timeout.
   */
  public boolean shouldCloseOnTimeout() {
    return this.closeOnTimeout;
  }

  /**
   * @return  whether the given runnable ({@link #getOnTimeout()}) should be
   *          executed asynchronously.
   */
  public boolean isAsync() {
    return async;
  }

  /**
   * @return The kelp-internal task id of the scheduler handling the timeout.
   */
  public UUID getTaskId() {
    return taskId;
  }

}
