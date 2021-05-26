package de.pxav.kelp.core.player.message.image;

import de.pxav.kelp.core.KelpServer;
import de.pxav.kelp.core.version.KelpVersion;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.util.ChatPaginator;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

public class ImageMessage {

  private final Color[] chatColors = {
    new Color(0, 0, 0),
    new Color(0, 0, 170),
    new Color(0, 170, 0),
    new Color(0, 170, 170),
    new Color(170, 0, 0),
    new Color(170, 0, 170),
    new Color(255, 170, 0),
    new Color(170, 170, 170),
    new Color(85, 85, 85),
    new Color(85, 85, 255),
    new Color(85, 255, 85),
    new Color(85, 255, 255),
    new Color(255, 85, 85),
    new Color(255, 85, 255),
    new Color(255, 255, 85),
    new Color(255, 255, 255),
  };

  private String[] renderedLines;

  private int chatHeight = 8;
  private BufferedImage bufferedImage = null;
  private char imageChar = ImageChar.BLOCK.getChar();
  private boolean allowRGB = KelpServer.getVersion().isHigherThanOrEqualTo(KelpVersion.MC_1_16_0);

  public ImageMessage(BufferedImage image, int height, char imgChar) {

  }

  public ImageMessage(ChatColor[][] chatColors, char imgChar) {
    renderedLines = toImgMessage(chatColors, imgChar);
  }

  public ImageMessage(String... imgLines) {
    renderedLines = imgLines;
  }


  public ImageMessage appendText(String... text) {
    for (int y = 0; y < renderedLines.length; y++) {
      if (text.length > y) {
        renderedLines[y] += " " + text[y];
      }
    }
    return this;
  }

  public void render() {
    ChatColor[][] chatColors = toChatColorArray(this.bufferedImage, this.chatHeight);
    renderedLines = toImgMessage(chatColors, this.imageChar);
  }

  public ImageMessage appendCenteredText(String... text) {
    for (int y = 0; y < renderedLines.length; y++) {
      if (text.length > y) {
        int len = ChatPaginator.AVERAGE_CHAT_PAGE_WIDTH - renderedLines[y].length();
        renderedLines[y] = renderedLines[y] + center(text[y], len);
      } else {
        return this;
      }
    }
    return this;
  }

  private ChatColor[][] toChatColorArray(BufferedImage image, int height) {
    double ratio = (double) image.getHeight() / image.getWidth();
    int width = (int) (height / ratio);
    if (width > 10) {
      width = 10;
    }

    BufferedImage resized = resizeImage(image, width, height);

    ChatColor[][] chatImg = new ChatColor[resized.getWidth()][resized.getHeight()];
    for (int x = 0; x < resized.getWidth(); x++) {
      for (int y = 0; y < resized.getHeight(); y++) {
        int rgb = resized.getRGB(x, y);
        if (!allowRGB) {
          ChatColor closest = getClosestChatColor(new Color(rgb, true));
          chatImg[x][y] = closest;
          continue;
        }
        chatImg[x][y] = ChatColor.of(new Color(rgb, true));
      }
    }
    return chatImg;
  }

  private String[] toImgMessage(ChatColor[][] colors, char imgchar) {
    String[] lines = new String[colors[0].length];
    for (int y = 0; y < colors[0].length; y++) {
      StringBuilder line = new StringBuilder();
      for (ChatColor[] value : colors) {
        ChatColor color = value[y];
        line.append((color != null) ? value[y].toString() + imgchar : ImageChar.TRANSPARENT.getChar());
      }
      lines[y] = line.toString() + ChatColor.RESET;
    }
    return lines;
  }

  private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
    AffineTransform af = new AffineTransform();
    af.scale(
      width / (double) originalImage.getWidth(),
      height / (double) originalImage.getHeight());

    AffineTransformOp operation = new AffineTransformOp(af, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
    return operation.filter(originalImage, null);
  }

  private double getDistance(Color c1, Color c2) {
    double rmean = (c1.getRed() + c2.getRed()) / 2.0;
    double r = c1.getRed() - c2.getRed();
    double g = c1.getGreen() - c2.getGreen();
    int b = c1.getBlue() - c2.getBlue();
    double weightR = 2 + rmean / 256.0;
    double weightG = 4.0;
    double weightB = 2 + (255 - rmean) / 256.0;
    return weightR * r * r + weightG * g * g + weightB * b * b;
  }

  private boolean areIdentical(Color c1, Color c2) {
    return Math.abs(c1.getRed() - c2.getRed()) <= 5 &&
      Math.abs(c1.getGreen() - c2.getGreen()) <= 5 &&
      Math.abs(c1.getBlue() - c2.getBlue()) <= 5;
  }

  private ChatColor getClosestChatColor(Color color) {
    if (color.getAlpha() < 128) return null;

    int index = 0;
    double best = -1;

    for (int i = 0; i < chatColors.length; i++) {
      if (areIdentical(chatColors[i], color)) {
        return ChatColor.values()[i];
      }
    }

    for (int i = 0; i < chatColors.length; i++) {
      double distance = getDistance(color, chatColors[i]);
      if (distance < best || best == -1) {
        best = distance;
        index = i;
      }
    }

    return ChatColor.values()[index];
  }

  private String center(String s, int length) {
    if (s.length() > length) {
      return s.substring(0, length);
    } else if (s.length() == length) {
      return s;
    } else {
      int leftPadding = (length - s.length()) / 2;
      StringBuilder leftBuilder = new StringBuilder();
      for (int i = 0; i < leftPadding; i++) {
        leftBuilder.append(" ");
      }
      return leftBuilder + s;
    }
  }

  public String[] getRenderedLines() {
    return renderedLines;
  }

  public void sendToPlayer(Player player) {
    for (String line : renderedLines) {
      player.sendMessage(line);
    }
  }
  
}
