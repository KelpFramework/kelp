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

  public static ImageMessage create() {
    return new ImageMessage();
  }

  public ImageMessage image(BufferedImage image) {
    this.bufferedImage = image;
    return this;
  }

  public ImageMessage character(char imageChar) {
    this.imageChar = imageChar;
    return this;
  }

  public ImageMessage character(DefaultFont imageChar) {
    this.imageChar = imageChar.getCharacter();
    return this;
  }

  public ImageMessage allowRGB(boolean allowRGB) {
    if (!canUseRGB()) {
      allowRGB = false;
    }
    this.allowRGB = allowRGB;
    return this;
  }

  public ImageMessage height(int height) {
    this.chatHeight = height;
    return this;
  }

  public ImageMessage appendText(String... text) {
    this.appendMessages = text;
    this.centerAppendix = false;
    return this;
  }

  public ImageMessage appendText(Object... text) {
    this.appendMessages = text;
    this.centerAppendix = false;
    return this;
  }

  public ImageMessage appendCenteredText(String... text) {
    this.appendMessages = text;
    this.centerAppendix = true;
    return this;
  }

  public ImageMessage appendCenteredText(Object... text) {
    this.appendMessages = text;
    this.centerAppendix = true;
    return this;
  }

  public ImageMessage render() {
    ChatColor[][] chatColors = toChatColorArray(this.bufferedImage, this.chatHeight);
    renderedLines = toImgMessage(chatColors, this.imageChar);
    return this;
  }

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

  public String[] getRenderedLines() {
    return renderedLines;
  }

  public ImageMessage sendToPlayer(KelpPlayer player) {
    for (int line = 0; line < renderedLines.length; line++) {
      if (appendMessages.length <= line) {
        player.sendMessage(renderedLines[line]);
        continue;
      }

      // total length left on the line (length from the end of the image
      // to the end of the chat line)
      int totalLength = ChatPaginator.AVERAGE_CHAT_PAGE_WIDTH - renderedLines[line].length();

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

  private static boolean canUseRGB() {
    return KelpServer.getVersion().isHigherThanOrEqualTo(KelpVersion.MC_1_16_0);
  }

  private String center(int textLength, int totalLength) {
    int leftPadding = (totalLength - textLength) / 2;
    StringBuilder leftBuilder = new StringBuilder();
    for (int i = 0; i < leftPadding; i++) {
      leftBuilder.append(" ");
    }
    return leftBuilder.toString();
  }

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

  private boolean isTransparentPixel(BufferedImage image, int x, int y) {
    int pixel = image.getRGB(x,y);
    return (pixel >> 24) == 0x00;
  }

  private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
    AffineTransform af = new AffineTransform();
    af.scale(
      width / (double) originalImage.getWidth(),
      height / (double) originalImage.getHeight());

    AffineTransformOp operation = new AffineTransformOp(af, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    return operation.filter(originalImage, null);
  }
  
}
