package de.pxav.kelp.core.player.message.image;

import de.pxav.kelp.core.KelpServer;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.message.InteractiveMessage;
import de.pxav.kelp.core.player.message.MessageComponent;
import de.pxav.kelp.core.player.prompt.chat.DefaultFont;
import de.pxav.kelp.core.version.KelpVersion;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.util.ChatPaginator;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;

/**
 * An {@code ImageMessage} is a message that allows you to display real
 * images (such as *.png files) in the chat. The image is resized to the
 * desired height and its pixels are approximated to the closest chat color
 * possible.
 *
 * You also have the option to append text lines after the actual
 * image using {@link #appendText(String...)} for example. Those append text
 * methods also exist as object array methods allowing you to use
 * {@link InteractiveMessage interactive messages} as well.
 *
 * Please note that the image conversion might be performance costly if you
 * do it too often or for too many players, so try to reuse the same instance
 * where possible. Once the {@link #render()} method has been called, the
 * converted image is saved in the cache and you can send it to different
 * players with {@link #sendToPlayer(KelpPlayer)}. If you are using this
 * in a loop, only call the send method in the loop and move the rest out
 * of it if possible.
 *
 * @author pxav
 */
public class ImageMessage {

  private String[] renderedLines;
  private Object[] appendMessages;

  private boolean centerAppendix = false;

  // if the source image has an alpha channel, transparent pixels
  // should be represented by this color. null if they should be
  // replaced with spaces.
  private ChatColor replaceTransparency = ChatColor.GRAY;

  // Whether Kelp should search for transparent pixels and replace them with
  // the above color. If false, transparent pixels are likely to become black
  private boolean detectTransparency = true;

  // The amount of lines the image should cover in the chat. The width is calculated
  // accordingly.
  private int chatHeight = 8;

  // the image to display in the chat.
  private BufferedImage bufferedImage = null;

  // the char to use to represent a pixel.
  private char imageChar = DefaultFont.BLOCK_SOLID.getCharacter();

  // whether RGB chat colors shall be used. This means Kelp
  // won't search the closest chat color, but display the exact
  // color in the chat. This is a 1.16+ feature.
  private boolean allowRGB = canUseRGB();

  /**
   * Creates a new {@code ImageMessage} instance, which can then
   * be used for further modification until it is finally shown to
   * a player.
   *
   * @return A new image message instance.
   */
  public static ImageMessage create() {
    return new ImageMessage();
  }

  /**
   * Sets the source image that should be displayed in the chat.
   *
   * It is recommended to use a low resolution with only a few pixels
   * in width and height (ideally equal to {@link #height(int)}) to avoid
   * too much loss of detail. However, it is also possible to insert
   * larger images here as they are automatically resized to the desired
   * height.
   *
   * @param image The image to display later in the chat.
   * @return An instance of this image message for fluent builder design.
   */
  public ImageMessage image(BufferedImage image) {
    this.bufferedImage = image;
    return this;
  }

  /**
   * Sets the character to be used to represent one pixel.
   * It is recommended to use something like a block character
   * here, so keep the edges between the pixels as small as possible.
   *
   * There are some presets in {@link DefaultFont}, see {@link #character(DefaultFont)}.
   *
   * @param imageChar The character to use when representing the pixels.
   * @return An instance of this image message for fluent builder design.
   */
  public ImageMessage character(char imageChar) {
    this.imageChar = imageChar;
    return this;
  }

  /**
   * Sets the character to be used to represent one pixel.
   * It is recommended to use something like a block character
   * here, so keep the edges between the pixels as small as possible
   * such as {@link DefaultFont#BLOCK_SOLID} {@code (default)},
   * {@link DefaultFont#BLOCK_MEDIUM_SHADE}, etc.
   *
   * If you don't want to use a preset character, see {@link #character(char)},
   * where you can use any character.
   *
   * @param imageChar The character to use when representing the pixels.
   * @return An instance of this image message for fluent builder design.
   */
  public ImageMessage character(DefaultFont imageChar) {
    this.imageChar = imageChar.getCharacter();
    return this;
  }

  /**
   * Enables or disables the use of RGB colors in the message.
   * RGB colors in the chat are a feature introduced in 1.16, which
   * is why it is only available in this version or higher. If you
   * have the opportunity, it is recommended to use it as then
   * the colors can be displayed more accurate and less detail is
   * lost when approximating the image colors to Minecraft's 15
   * chat colors.
   *
   * By default, this value is based on whether your server meets
   * the requirements for RGB, so no change should be necessary.
   * If you use {@code true} here, the value is automatically set
   * to {@code false} again if your server does not support RGB colors.
   *
   * @param allowRGB {@code true} if RGB colors should be used.
   * @return An instance of this image message for fluent builder design.
   */
  public ImageMessage allowRGB(boolean allowRGB) {
    if (!canUseRGB()) {
      allowRGB = false;
    }
    this.allowRGB = allowRGB;
    return this;
  }

  /**
   * Sets the height of this message in the chat. This is equal
   * to the amount of lines covered by this message in the chat.
   * The image is resized accordingly.
   *
   * @param height The amount of lines the image message should cover in the chat.
   * @return An instance of this image message for fluent builder design.
   */
  public ImageMessage height(int height) {
    this.chatHeight = height;
    return this;
  }

  /**
   * Sets an array of text to be displayed after the
   * image message itself. One element in this array is displayed
   * after one line of the image, so ideally the array provided
   * by this method has the same length as given in {@link #height(int)}.
   * But your array may also be shorter, if you don't want to append
   * text at the last lines of the image.
   *
   * This method only supports String/text messages. If you want
   * to insert an {@link InteractiveMessage} as well, use {@link #appendText(Object...)}
   * instead.
   *
   * @param text The lines of text to append after the image.
   * @return An instance of this image message for fluent builder design.
   */
  public ImageMessage appendText(String... text) {
    this.appendMessages = text;
    this.centerAppendix = false;
    return this;
  }

  /**
   * Sets an array of text to be displayed after the
   * image message itself. One element in this array is displayed
   * after one line of the image, so ideally the array provided
   * by this method has the same length as given in {@link #height(int)}.
   * But your array may also be shorter, if you don't want to append
   * text at the last lines of the image.
   *
   * In contrast to {@link #appendText(String...)}, this method allows
   * you to insert {@link InteractiveMessage interactive messages} as well.
   *
   * @param text The lines of text to append after the image.
   * @return An instance of this image message for fluent builder design.
   */
  public ImageMessage appendText(Object... text) {
    this.appendMessages = text;
    this.centerAppendix = false;
    return this;
  }

  /**
   * Sets an array of text to be displayed after the
   * image message itself. One element in this array is displayed
   * after one line of the image, so ideally the array provided
   * by this method has the same length as given in {@link #height(int)}.
   * But your array may also be shorter, if you don't want to append
   * text at the last lines of the image.
   *
   * In addition to the other methods, this method also centers your message
   * within a line.
   *
   * @param text The lines of text to append after the image.
   * @return An instance of this image message for fluent builder design.
   */
  public ImageMessage appendCenteredText(String... text) {
    this.appendMessages = text;
    this.centerAppendix = true;
    return this;
  }

  /**
   * Sets an array of text to be displayed after the
   * image message itself. One element in this array is displayed
   * after one line of the image, so ideally the array provided
   * by this method has the same length as given in {@link #height(int)}.
   * But your array may also be shorter, if you don't want to append
   * text at the last lines of the image.
   *
   * In contrast to {@link #appendText(String...)}, this method allows
   * you to insert {@link InteractiveMessage interactive messages} as well.
   *
   * In addition to the other methods, this method also centers your message
   * within a line.
   *
   * @param text The lines of text to append after the image.
   * @return An instance of this image message for fluent builder design.
   */
  public ImageMessage appendCenteredText(Object... text) {
    this.appendMessages = text;
    this.centerAppendix = true;
    return this;
  }

  /**
   * Takes the given data about this message and generates an array of
   * all chat lines required to display the image in the chat. This array
   * is then saved for this message instance, so that you can send it to
   * multiple players without too much resource costs.
   *
   * The array generated by this method can be retrieved using {@link #getRenderedLines()}.
   * However, this method does not render the appendix messages provided in
   * {@link #appendText(String...)} for example. This is done when the message
   * is actually {@link #sendToPlayer(KelpPlayer) sent} to a player.
   *
   * @return An instance of this image message for fluent builder design.
   */
  public ImageMessage render() {
    ChatColor[][] chatColors = toChatColorArray(this.bufferedImage, this.chatHeight);
    renderedLines = toImgMessage(chatColors, this.imageChar);
    return this;
  }

  /**
   * Gets the lines generated by the {@link #render()} method.
   * This does not include the appendix texts defined by methods such
   * as {@link #appendText(String...)}
   *
   * @return  An array of all chat lines required to display the image.
   *          {@code null} if the {@link #render() render} method has not been called yet.
   */
  public String[] getRenderedLines() {
    return renderedLines;
  }

  /**
   * Sends the image message to a player. In this final step, the appendix
   * messages are also rendered including any {@link InteractiveMessage interactive
   * messages} if existent. {@link #render()} has to be called at least once
   * before this method can deliver an appropriate output.
   *
   * @param player The player to send the message to.
   * @return An instance of this image message for fluent builder design.
   */
  public ImageMessage sendToPlayer(KelpPlayer player) {
    for (int line = 0; line < renderedLines.length; line++) {

      // if there are no more elements left in the appendix array
      // but the image array is longer, only send the image messages
      // that are left.
      if (appendMessages.length <= line) {
        player.sendMessage(renderedLines[line]);
        continue;
      }

      // total length left on the line (length from the end of the image
      // to the end of the chat line)
      int totalLength = ChatPaginator.AVERAGE_CHAT_PAGE_WIDTH - renderedLines[line].length();

      // the appendix is an object array, so interactive messages can be rendered as well.
      if (appendMessages[line] instanceof InteractiveMessage) {
        InteractiveMessage interactiveMessage = (InteractiveMessage) appendMessages[line];
        if (centerAppendix) {
          String textWithoutColorCodes = interactiveMessage.getRawText();
          interactiveMessage.insertComponentAt(0, MessageComponent.create()
            .text(renderedLines[line] + center(textWithoutColorCodes.length(), totalLength)));
        } else {
          interactiveMessage.insertComponentAt(0, MessageComponent.create()
            .text(renderedLines[line]));
        }

        player.sendInteractiveMessage(interactiveMessage);
        continue;
      }

      String textLine = String.valueOf(appendMessages[line]);
      String toSend;

      if (centerAppendix) {
        toSend = renderedLines[line] + center(textLine.length(), totalLength) + textLine;
      } else {
        toSend = renderedLines[line] + textLine;
      }

      player.sendMessage(toSend);

    }
    return this;
  }

  /**
   * Takes the color array generated by {@link #toChatColorArray(BufferedImage, int)}
   * and creates the string array to be displayed later in the chat. This array
   * is equal to {@link #getRenderedLines()}.
   *
   * @param colors      The two-dimensional color array representing every pixel
   *                    of the image.
   * @param imageChar   The character to be used in the chat for one pixel.
   * @return An array of the rendered lines. Equal to {@link #getRenderedLines()}.
   */
  private String[] toImgMessage(ChatColor[][] colors, char imageChar) {
    String[] lines = new String[colors[0].length];
    for (int y = 0; y < colors[0].length; y++) {
      StringBuilder line = new StringBuilder();
      for (ChatColor[] value : colors) {
        ChatColor color = value[y];
        line.append((color != null) ? value[y].toString() + imageChar : "  ");
      }
      lines[y] = line.toString() + ChatColor.RESET;
    }
    return lines;
  }

  /**
   * Checks if all requirements for the use of RGB chat colors are
   * fulfilled. This feature is only available in Minecraft 1.16+,
   * so it is sufficient to check the server version here.
   *
   * @return {@code true} if this server supports RGB chat colors.
   */
  private boolean canUseRGB() {
    return KelpServer.getVersion().isHigherThanOrEqualTo(KelpVersion.MC_1_16_0);
  }

  /**
   * A util method that is used to center a text in the chat line.
   * It takes the total length of the chat line and the length covered by
   * the given text and calculates the left padding accordingly.
   *
   * So this method only returns a string made of spaces, which can
   * then be applied as left padding.
   *
   * @param textLength    The {@link String#length() length} of the text to be centered.
   * @param totalLength   The length of the chat line defined by the amount of chars or
   *                      pixels that can fit in there. Things like bukkits chat paginator
   *                      ({@link ChatPaginator#AVERAGE_CHAT_PAGE_WIDTH}) might help here.
   * @return A string made of spaces representing the left padding needed to center the string.
   */
  private String center(int textLength, int totalLength) {
    int leftPadding = (totalLength - textLength) / 2;
    StringBuilder leftBuilder = new StringBuilder();
    for (int i = 0; i < leftPadding; i++) {
      leftBuilder.append(" ");
    }
    return leftBuilder.toString();
  }

  /**
   * Takes a source image and a desired height and generates
   * a two-dimensional array of the colors needed to display the
   * image in the chat.
   *
   * The returned array contains all pixels, where the first
   * dimension is the x axis and the second is the y axis.
   *
   * The colors are approximated depending on whether RGB colors
   * have been enabled or not.
   *
   * This method automatically resizes the image to the given height
   * and keeps the aspect ratio using {@link #resizeImage(BufferedImage, int, int)}
   *
   * @param image     The image to convert the pixels of (does not yet
   *                  have to be of the correct size).
   * @param height    The height of the final image/the final chat message.
   * @return A two-dimensional array of the chat colors representing the pixels of the image.
   */
  private ChatColor[][] toChatColorArray(BufferedImage image, int height) {
    double ratio = (double) image.getHeight() / image.getWidth();
    int width = (int) (height / ratio);
    if (width > 16) {
      width = 16;
    }

    BufferedImage resized = resizeImage(image, width, height);

    // whether the image has at least one transparent pixel
    boolean hasTransparency = resized.getColorModel().hasAlpha();

    ChatColor[][] chatImg = new ChatColor[resized.getWidth()][resized.getHeight()];
    for (int x = 0; x < resized.getWidth(); x++) {
      for (int y = 0; y < resized.getHeight(); y++) {
        if (detectTransparency && hasTransparency && isTransparentPixel(resized, x, y)) {
          chatImg[x][y] = replaceTransparency == null ? null : replaceTransparency;
          continue;
        }

        int rgb = resized.getRGB(x, y);
        Color color = Color.fromRGB(rgb);
        if (!allowRGB) {
          ChatColor closest = color.getClosestChatColor();
          chatImg[x][y] = closest;
          continue;
        }
        chatImg[x][y] = ChatColor.of(color);
      }
    }
    return chatImg;
  }

  /**
   * A util method that checks if the pixel at the given coordinates
   * of the given image is transparent. This can only return true if the image
   * format supports transparency/has an alpha channel at all, which can be checked
   * using {@link ColorModel#hasAlpha()}.
   *
   * @param image   The image to check the pixel of.
   * @param x       The x coordinate of the pixel to be checked.
   * @param y       The y coordinate of the pixel to be checked.
   * @return {@code true} if the given pixel is transparent.
   */
  private boolean isTransparentPixel(BufferedImage image, int x, int y) {
    int pixel = image.getRGB(x,y);
    return (pixel >> 24) == 0x00;
  }

  /**
   * A util method that changes the dimensions of the given image to the given
   * width and height. It is recommended to use the same aspect ratio of
   * width and height as in the original image, so that there is no stretch
   * effect. This method does not crop the image, but only transforms it
   * to match the given parameters.
   *
   * @param originalImage   The image to resize.
   * @param width           The new width of the image.
   * @param height          The new height of the image.
   * @return The image with the new width and height.
   */
  private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
    AffineTransform af = new AffineTransform();
    af.scale(
      width / (double) originalImage.getWidth(),
      height / (double) originalImage.getHeight());

    AffineTransformOp operation = new AffineTransformOp(af, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    return operation.filter(originalImage, null);
  }
  
}
