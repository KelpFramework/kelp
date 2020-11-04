package de.pxav.kelp.core.player.prompt.chat;

import com.google.common.collect.Lists;
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

  public SimpleChatPrompt cancelFlag(String cancelFlag) {
    this.cancelFlag = cancelFlag;
    return this;
  }

  public SimpleChatPrompt enableEcho(String prefix, String suffix) {
    this.enableEcho = true;
    this.echoPrefix = prefix;
    this.echoSuffix = suffix;
    return this;
  }

  public SimpleChatPrompt disableEcho() {
    this.enableEcho = false;
    return this;
  }

  public SimpleChatPrompt withSyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, false, false);
    return this;
  }

  public SimpleChatPrompt withAsyncTimeout(int timeout, TimeUnit timeUnit, Runnable onTimeout) {
    this.timeout = new PromptTimeout(timeout, timeUnit, onTimeout, true, false);
    return this;
  }

  public SimpleChatPrompt onCancel(Runnable onCancel) {
    this.onCancel = onCancel;
    return this;
  }

  public void handle(SimplePromptResponseHandler responseHandler) {
    this.responseHandler = responseHandler;
    this.chatPromptVersionTemplate .simpleChatPrompt(this);
  }

  public SimplePromptResponseHandler getResponseHandler() {
    return responseHandler;
  }

  public String getCancelFlag() {
    return this.cancelFlag;
  }

  public PromptTimeout getTimeout() {
    return timeout;
  }

  public boolean isEchoEnabled() {
    return enableEcho;
  }

  public String getEchoPrefix() {
    return echoPrefix;
  }

  public String getEchoSuffix() {
    return echoSuffix;
  }

  public Runnable getOnCancel() {
    return onCancel;
  }

  public Player getPlayer() {
    return player;
  }
}
