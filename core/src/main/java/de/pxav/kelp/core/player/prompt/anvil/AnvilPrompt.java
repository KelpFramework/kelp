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
 * A class description goes here.
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

  public AnvilPrompt initialText(String initialText) {
    this.initialText = initialText;
    return this;
  }

  public AnvilPrompt sourceMaterial(KelpMaterial kelpMaterial) {
    this.sourceMaterial = kelpMaterial;
    return this;
  }

  public AnvilPrompt onClose(Runnable onClose) {
    this.onClose = onClose;
    return this;
  }

  public AnvilPrompt withSyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout, boolean closeOnTimeout) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, false, closeOnTimeout);
    return this;
  }

  public AnvilPrompt withAsyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout, boolean closeOnTimeOut) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, true, closeOnTimeOut);
    return this;
  }

  public AnvilPrompt withSyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, false, true);
    return this;
  }

  public AnvilPrompt withAsyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, true, true);
    return this;
  }

  public void handle(SimplePromptResponseHandler responseHandler) {
    this.responseHandler = responseHandler;
    this.anvilPromptVersionTemplate.openPrompt(this.player, this.initialText, this.sourceMaterial, this.onClose, this.timeout, this.responseHandler);
  }

  public SimplePromptResponseHandler getResponseHandler() {
    return responseHandler;
  }

  public String getInitialText() {
    return this.initialText;
  }

  public PromptTimeout getTimeout() {
    return timeout;
  }

}
