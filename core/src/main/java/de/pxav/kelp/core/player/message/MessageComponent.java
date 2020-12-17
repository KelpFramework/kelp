package de.pxav.kelp.core.player.message;

import java.io.File;
import java.net.URL;

/**
 * A message component is a part/segment of an {@link InteractiveMessage}.
 * Unlike normal spigot {@link net.md_5.bungee.api.chat.TextComponent} you can add
 * unlimited components to a clickable/hoverable message, so you can have
 * different click events in one chat line/chat message.
 *
 * In order to keep control over those different events, you divide your message
 * into different components: If you have a message for editing a player report
 * for example, you have a message showing different options:
 * {@code [SERVER] Select an option: BAN PLAYER | KICK PLAYER | CONTINUE}
 *
 * For the prefix and the message, you would create a component with no events,
 * but then you would append a new component for each of the options (ban, kick, continue).
 *
 * For each component you can select what should happen when a player hovers over a message or
 * clicks it.
 *
 * You can apply chat colors by using {@code § + code id (e. g. §a, §4, ...)}. If you want to use
 * a style code (such as underlining, obfuscating, etc.) only use those codes in combination
 * with a color code ({@code §a§o} not {@code §o})
 *
 * @author pxav
 */
public class MessageComponent {

  // the text of the message displayed in the chat.
  private String text;

  // the type of action to be performed when the message is clicked.
  private MessageClickAction clickAction;

  // the type of action to be performed when the message is hovered.
  private MessageHoverAction hoverAction;

  // Values for the individual event types. If you have EXECUTE_COMMAND
  // for example, you pass a string with the command here.
  private Object clickValue;
  private Object hoverValue;

  private MessageComponent() {}

  /**
   * Static factory method. If you want to create new instances of message
   * components, use this method instead of the normal constructor.
   *
   * @return A new instance of a message component.
   */
  public static MessageComponent create() {
    return new MessageComponent();
  }

  /**
   * The text which should appear in the chat when this component is rendered.
   *
   * @param text The text to be shown in the chat line.
   *
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent text(String text) {
    this.text = text;
    return this;
  }

  /**
   * Sets the click event action. Please remember to set the corresponding
   * value as well.
   *
   * @param clickAction The action to be performed when the component is clicked.
   *
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent clickAction(MessageClickAction clickAction) {
    this.clickAction = clickAction;
    return this;
  }

  /**
   * Sets the hover event action. Please remember to set the corresponding
   * hover value as well.
   *
   * @param hoverAction The action to be performed when the player hovers
   *                    over the component.
   *
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent hoverAction(MessageHoverAction hoverAction) {
    this.hoverAction = hoverAction;
    return this;
  }

  /**
   * Sets the value of the currently set click event. If you selected
   * EXECUTE_COMMAND as click event, pass the command here.
   *
   * @param clickValue The value of the click event.
   *
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent clickValue(Object clickValue) {
    this.clickValue = clickValue;
    return this;
  }

  /**
   * Sets the value of the currently set hover event. If you selected
   * SHOW_MESSAGE as hover event, pass the message here.
   *
   * @param hoverValue The value of the hover event.
   *
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent hoverValue(Object hoverValue) {
    this.hoverValue = hoverValue;
    return this;
  }

  /**
   * Executes a command when the message is clicked. If the player who clicks
   * the message does not have permissions to execute it, the command won't be executed.
   *
   * @param command The command to be executed by the player.
   *
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent executeCommandOnClick(String command) {
    this.clickAction = MessageClickAction.EXECUTE_COMMAND;
    this.clickValue = command;
    return this;
  }

  /**
   * Copies the given command or message to the player's chat text box.
   *
   * @param command The command/message to be pasted to the chat box.
   *
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent suggestCommandOnClick(String command) {
    this.clickAction = MessageClickAction.SUGGEST_COMMAND;
    this.clickValue = command;
    return this;
  }

  /**
   * Opens the given url to the player. This can be a website for example.
   *
   * @param url The {@code url} to be opened. If you want to open a website
   *            please remember to prefix your message with {@code https://}.
   *
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent openURLOnClick(String url) {
    this.clickAction = MessageClickAction.OPEN_URL;
    this.clickValue = url;
    return this;
  }

  /**
   * Opens the given url to the player. This can be a website for example.
   *
   * @param url The {@code url} to be opened. If you want to open a website
   *            please remember to prefix your message with {@code https://}.
   *            This method only takes the path of the given {@link URL} object
   *            with {@link URL#getPath()}.
   *
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent openURLOnClick(URL url) {
    this.clickAction = MessageClickAction.OPEN_URL;
    this.clickValue = url.getPath();
    return this;
  }

  /**
   * Opens the file at the given location when the player clicks on the
   * message. Please ensure that the requested file is on the file system
   * of the host machine and not on the client side as the server does not
   * have access to those files.
   *
   * @param url The URL where the file is located. This method only takes the
   *            URL path with {@link URL#getPath()}.
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent openFileOnClick(URL url) {
    this.clickAction = MessageClickAction.OPEN_FILE;
    this.clickValue = url.getPath();
    return this;
  }

  /**
   * Opens the file at the given location when the player clicks on the
   * message. Please ensure that the requested file is on the file system
   * of the host machine and not on the client side as the server does not
   * have access to those files.
   *
   * @param file The file object of the file to be accessed.
   *
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent openFileOnClick(File file) {
    this.clickAction = MessageClickAction.OPEN_FILE;
    this.clickValue = file.getPath();
    return this;
  }

  /**
   * Opens the file at the given location when the player clicks on the
   * message. Please ensure that the requested file is on the file system
   * of the host machine and not on the client side as the server does not
   * have access to those files.
   *
   * @param filePath The path of the file to be accessed.
   *
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent openFileOnClick(String filePath) {
    this.clickAction = MessageClickAction.OPEN_FILE;
    this.clickValue = filePath;
    return this;
  }

  /**
   * Makes the player sends a chat message under his name when they click
   * the message.
   *
   * @param message The message to be sent by the player.
   *
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent sendChatMessageOnClick(String message) {
    this.clickAction = MessageClickAction.SEND_CHAT_MESSAGE;
    this.clickValue = message;
    return this;
  }

  /**
   * When the player clicks on the message a given text will
   * be copied to its clipboard.
   *
   * @param text The text to be copied.
   *
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent copyToClipboardOnClick(String text) {
    this.clickAction = MessageClickAction.COPY_TO_CLIPBOARD;
    this.clickValue = text;
    return this;
  }

  /**
   * Changes the page of the current player book.
   *
   * @param page The page to navigate to.
   *
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent changeBookPageOnClick(int page) {
    this.clickAction = MessageClickAction.CHANGE_PAGE;
    this.clickValue = page;
    return this;
  }

  /**
   * Sets the hover event to showing a message.
   *
   * @param message The message to be shown when the player hovers over
   *                the chat message.
   * @return The current component instance for fluent builder design.
   */
  public MessageComponent showMessageOnHover(String message) {
    this.hoverAction = MessageHoverAction.SHOW_MESSAGE;
    this.hoverValue = message;
    return this;
  }

  /**
   * Returns the type of action that should be performed
   * when a player clicks on the message.
   * @return The click action type.
   */
  public MessageClickAction getClickAction() {
    return clickAction;
  }

  /**
   * Returns the type of action that should be performed
   * when a player hovers over the message.
   * @return The hover action type.
   */
  public MessageHoverAction getHoverAction() {
    return hoverAction;
  }

  /**
   * Returns the event value which is set for the click event.
   * If you selected SUGGEST_COMMAND, the command is returned here.
   * @return The click event value object.
   */
  public Object getClickValue() {
    return clickValue;
  }

  /**
   * Returns the event value which is set for the hover event.
   * If you selected SHOW_MESSAGE, the message is returned here.
   * @return The hover event value object.
   */
  public Object getHoverValue() {
    return hoverValue;
  }

  /**
   * Returns the text displayed in the chat line.
   * @return The text of the component.
   */
  public String getText() {
    return text;
  }
}
