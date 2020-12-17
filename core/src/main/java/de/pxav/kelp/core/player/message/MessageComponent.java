package de.pxav.kelp.core.player.message;

import java.io.File;
import java.net.URL;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class MessageComponent {

  private String text;
  private MessageClickAction clickAction;
  private MessageHoverAction hoverAction;
  private Object clickValue;
  private Object hoverValue;

  private MessageComponent() {}

  public static MessageComponent create() {
    return new MessageComponent();
  }

  public MessageComponent text(String text) {
    this.text = text;
    return this;
  }

  public MessageComponent clickAction(MessageClickAction clickAction) {
    this.clickAction = clickAction;
    return this;
  }

  public MessageComponent hoverAction(MessageHoverAction hoverAction) {
    this.hoverAction = hoverAction;
    return this;
  }

  public MessageComponent clickValue(Object clickValue) {
    this.clickValue = clickValue;
    return this;
  }

  public MessageComponent hoverValue(Object hoverValue) {
    this.hoverValue = hoverValue;
    return this;
  }

  public MessageComponent executeCommandOnClick(String command) {
    this.clickAction = MessageClickAction.EXECUTE_COMMAND;
    this.clickValue = command;
    return this;
  }

  public MessageComponent suggestCommandOnClick(String command) {
    this.clickAction = MessageClickAction.SUGGEST_COMMAND;
    this.clickValue = command;
    return this;
  }

  public MessageComponent openURLOnClick(String url) {
    this.clickAction = MessageClickAction.OPEN_URL;
    this.clickValue = url;
    return this;
  }

  public MessageComponent openURLOnClick(URL url) {
    this.clickAction = MessageClickAction.OPEN_URL;
    this.clickValue = url.getPath();
    return this;
  }

  public MessageComponent openFileOnClick(URL url) {
    this.clickAction = MessageClickAction.OPEN_FILE;
    this.clickValue = url.getPath();
    return this;
  }

  public MessageComponent openFileOnClick(File file) {
    this.clickAction = MessageClickAction.OPEN_FILE;
    this.clickValue = file.getPath();
    return this;
  }

  public MessageComponent openFileOnClick(String filePath) {
    this.clickAction = MessageClickAction.OPEN_FILE;
    this.clickValue = filePath;
    return this;
  }

  public MessageComponent sendChatMessageOnClick(String message) {
    this.clickAction = MessageClickAction.SEND_CHAT_MESSAGE;
    this.clickValue = message;
    return this;
  }

  public MessageComponent copyToClipboardOnClick(String text) {
    this.clickAction = MessageClickAction.COPY_TO_CLIPBOARD;
    this.clickValue = text;
    return this;
  }

  public MessageComponent changeBookPageOnClick(int page) {
    this.clickAction = MessageClickAction.CHANGE_PAGE;
    this.clickValue = page;
    return this;
  }

  public MessageComponent showMessageOnHover(String message) {
    this.hoverAction = MessageHoverAction.SHOW_MESSAGE;
    this.hoverValue = message;
    return this;
  }

  public MessageClickAction getClickAction() {
    return clickAction;
  }

  public MessageHoverAction getHoverAction() {
    return hoverAction;
  }

  public Object getClickValue() {
    return clickValue;
  }

  public Object getHoverValue() {
    return hoverValue;
  }

  public String getText() {
    return text;
  }
}
