package de.pxav.kelp.core.player.prompt.sign;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.player.prompt.PromptTimeout;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * A class description goes here.
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

  public SignPrompt initialLines(List<String> initialLines) {
    this.initialLines = initialLines;
    return this;
  }

  public SignPrompt withSyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, false, false);
    return this;
  }

  public SignPrompt withAsyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, true, false);
    return this;
  }

  public void handle(SignPromptResponseHandler responseHandler) {
    this.responseHandler = responseHandler;
    this.signPromptVersionTemplate.openSignPrompt(this.player, this.initialLines, this.timeout, this.responseHandler);
  }

  public SignPromptResponseHandler getResponseHandler() {
    return responseHandler;
  }

  public List<String> getInitialLines() {
    return initialLines;
  }

  public PromptTimeout getTimeout() {
    return timeout;
  }
}
