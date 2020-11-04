package de.pxav.kelp.core.player.prompt;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class PromptTimeout {

  private int timeout = 0;
  private TimeUnit timeUnit = TimeUnit.MILLISECONDS;
  private Runnable onTimeout;
  private boolean async = true;
  private boolean closeOnTimeout = false;

  private UUID taskId;

  public PromptTimeout() { }

  public PromptTimeout(int timeout, TimeUnit timeUnit) {
    this.timeout = timeout;
    this.timeUnit = timeUnit;
  }

  public PromptTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout, boolean async, boolean closeOnTimeout) {
    this.timeout = timeout;
    this.timeUnit = timeUnit;
    this.onTimeout = onTimeout;
    this.closeOnTimeout = closeOnTimeout;
  }

  public void setTaskId(UUID taskId) {
    this.taskId = taskId;
  }

  public int getTimeout() {
    return timeout;
  }

  public Runnable getOnTimeout() {
    return onTimeout;
  }

  public TimeUnit getTimeUnit() {
    return timeUnit;
  }

  public boolean shouldCloseOnTimeout() {
    return this.closeOnTimeout;
  }

  public boolean isAsync() {
    return async;
  }

  public UUID getTaskId() {
    return taskId;
  }

}
