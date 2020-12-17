package de.pxav.kelp.core.player.message;

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
