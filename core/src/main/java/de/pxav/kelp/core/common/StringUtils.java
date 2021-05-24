package de.pxav.kelp.core.common;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;
import net.md_5.bungee.api.ChatColor;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class contains useful methods to work with strings
 * including normal char processing but also minecraft color codes.
 *
 * @author pxav
 */
public class StringUtils {

  // an array containing all color codes in alphabetical order.
  private static char[] colorCodes = new char[] {
          '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
          'a', 'b', 'c', 'd', 'e', 'f'
  };

  // an array containing all style codes in alphabetical order.
  private static char[] styleCodes = new char[] {
          'k', 'l', 'm', 'n', 'o', 'r'
  };

  /**
   * Removes the last char of the given text.
   *
   * @param text The text you want to remove the char of.
   * @return The given text without the last char.
   */
  public static String removeLastChar(String text) {
    // build a substring excluding the last char.
    return text.substring(0, text.length() - 1);
  }

  /**
   * Searches for all formatting codes inside the given
   * string and removes them so that you only
   * have the raw text without any formatting left.
   *
   * @param text The text you want to remove the color codes of.
   * @return The final string without color codes.
   */
  public static String removeFormattingCodes(String text) {
    List<String> codes = extractFormattingCodes(text);
    for (String current : codes) {
      text = text.replace(current, "");
    }
    return text;
  }

  /**
   * Searches for all color codes in the given text.
   * This excludes style codes like §l, §k, etc.
   *
   * @param text The text you want to get the color codes of.
   * @return The color codes in chronological order.
   */
  public static List<String> extractColorCodes(String text) {
    List<String> output = Lists.newArrayList();
    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) == '§' && isColorCode(text.charAt(i + 1))) {
        output.add("§" + text.charAt(i + 1));
      }
    }
    return output;
  }

  /**
   * Searches for all style codes in the given text.
   * This excludes color codes like §3, §b, etc.
   *
   * @param text The text you want to get the style codes of.
   * @return The style codes in chronological order.
   */
  public static List<String> extractStyleCodes(String text) {
    List<String> output = Lists.newArrayList();
    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) == '§' && isStyleCode(text.charAt(i + 1))) {
        output.add("§" + text.charAt(i + 1));
      }
    }
    return output;
  }

  /**
   * Searches for all formatting codes in a string
   * no matter if they are style or color.
   *
   * @param text The text you want to get the codes of.
   * @return The formatting codes in chronological order.
   */
  public static List<String> extractFormattingCodes(String text) {
    List<String> output = Lists.newArrayList();
    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) != '§') {
        continue;
      }

      if (text.length() == i + 1) {
        break;
      }

      if (isStyleCode(text.charAt(i + 1))
              || isColorCode(text.charAt(i + 1))) {
        output.add("§" + text.charAt(i + 1));
      }
    }
    return output;
  }

  /**
   * Extracts all color codes from the given text
   * and checks which of these have an impact on the last char(s)
   * of it. So for example for the text '§6Hello §7You' it
   * would return '§7' because this is the last color code
   * in the string.
   *
   * This also includes style codes. The method returns the (one!)
   * last color code and all of the following style codes,
   * which come after it. So for example the text
   * '§6Hello §7§oYou' would return
   * '§7§o'.
   *
   * Furthermore this method searches for uncompleted color codes.
   * If your text ends with a '§' this will also be appended to
   * the final output. If you do not want this to happen,
   * please use {@code #lastFullFormattingCodesOf(text)}.
   *
   * @param text The text you want to check.
   * @return The last color and style codes.
   */
  public static String lastFormattingCodesOf(String text) {
    // get all formatting codes of the text and reverse their order.
    List<String> reversed = extractFormattingCodes(text);
    Collections.reverse(reversed);

    List<String> codes = Lists.newArrayList();

    /*
      This loop iterates through all color codes of the text.
      If it finds a style code it will simply add it to the list
      and continue until it finds a color codes. Then it will
      add this color code to the list and break immediately.
     */
    for (String current : reversed) {
      if (isStyleCode(current.charAt(1))) {
        codes.add(current);
        continue;
      }
      if (isColorCode(current.charAt(1))) {
        codes.add(current);
        break;
      }
    }
    Collections.reverse(codes);

    // check for uncompleted color codes and optionally add them
    if (text.length() >= 16 && text.substring(0, 16).endsWith("§")) {
      codes.add("§");
    }

    return codes.toString()
            .replace("[", "")
            .replace("]", "")
            .replace(",", "")
            .replace(" ", "");
  }

  /**
   * Extracts all color codes from the given text
   * and checks which of these have an impact on the last char(s)
   * of it. So for example for the text '§6Hello §7You' it
   * would return '§7' because this is the last color code
   * in the string.
   *
   * This also includes style codes. The method returns the (one!)
   * last color code and all of the following style codes,
   * which come after it. So for example the text
   * '§6Hello §7§oYou' would return
   * '§7§o'.
   *
   * Please not that this method will only give you
   * full formatting codes like '§a' and not '§'.
   * If you want this to happen use {@code lastFormattingCodesOf(text)}.
   *
   * @param text The text you want to check.
   * @return The last color and style codes.
   */
  public static String lastFullFormattingCodesOf(String text) {
    // check if the color codes have a single '§' at the end.
    // this has to be removed as we only want to have full
    // color codes.
    if (lastFormattingCodesOf(text).endsWith("§")) {
      String colorCodes = lastFormattingCodesOf(text);
      return removeLastChar(colorCodes);
    }
    return lastFormattingCodesOf(text);
  }

  /**
   * Checks the given indicator for a formatting code
   * and decides whether it's a color code.
   * Color codes are codes which do not affect the style
   * of a string by changing font type, etc. but only
   * the color.
   *
   * @param indicator The indicator symbol of the code you want to check
   *                  e. g. '1' for dark blue
   * @return {@code true} if the given indicator is
   */
  public static boolean isColorCode(char indicator) {
    for (char current : colorCodes) {
      if (current == indicator) {
        return true;
      }
    }
    return false;
  }

  /**
   * Checks the given indicator for a formatting code
   * and decides whether it's a style code.
   * Style codes are codes that do not affect the color
   * of the given string, but for example the font type (bold).
   *
   * @param indicator The indicator symbol of the code you want to check
   *                  e. g. 'l' for bold
   * @return {@code true} if the given indicator is a style code.
   */
  public static boolean isStyleCode(char indicator) {
    for (char current : styleCodes) {
      if (current == indicator) {
        return true;
      }
    }
    return false;
  }

  /**
   * Takes a formatting code char (without the {@code '§'} in front)
   * and converts it to a {@link ChatColor} object of the md_5 library.
   * If there could be no chat color found, {@link ChatColor#WHITE WHITE}
   * will be returned.
   *
   * @param formattingCode The formatting code to be converted.
   * @return The corresponding md_5 chat color.
   */
  public static ChatColor getChatColor(char formattingCode) {
    return isFormattingCode(formattingCode)
      ? ChatColor.getByChar(formattingCode)
      : ChatColor.WHITE;
  }

  /**
   * Takes a formatting code char (without the {@code '§'} in front)
   * and converts it to a {@link org.bukkit.ChatColor} object of the bukkit library.
   * If there could be no chat color found, {@link org.bukkit.ChatColor#WHITE WHITE}
   * will be returned.
   *
   * @param formattingCode The formatting code to be converted.
   * @return The corresponding bukkit chat color.
   */
  public static org.bukkit.ChatColor getBukkitChatColor(char formattingCode) {
    return isFormattingCode(formattingCode)
      ? org.bukkit.ChatColor.getByChar(formattingCode)
      : org.bukkit.ChatColor.WHITE;
  }

  /**
   * Takes a formatting code with syntax {@code '§x'} and converts
   * it to a {@link ChatColor} object of spigot/md_5 library. If the
   * given code could not be found, {@link org.bukkit.ChatColor#WHITE WHITE}
   * is returned.
   *
   * @param formattingCode The formatting code to be converted.
   * @return  The final {@link ChatColor} to be returned. {@code null} if the
   *          color was not found.
   */
  public static ChatColor getChatColor(String formattingCode) {
    char code = formattingCode.charAt(1);
    return isFormattingCode(code) && formattingCode.charAt(0) == '§'
      ? ChatColor.getByChar(code)
      : ChatColor.WHITE;
  }

  /**
   * Takes a formatting code with syntax {@code '§x'} and converts
   * it to a {@link org.bukkit.ChatColor} object of bukkit. If the
   * given code could not be found, {@link org.bukkit.ChatColor#WHITE WHITE}
   * is returned.
   *
   * @param formattingCode The formatting code to be converted.
   * @return The final {@link org.bukkit.ChatColor} to be returned.
   */
  public static org.bukkit.ChatColor getBukkitChatColor(String formattingCode) {
    char code = formattingCode.charAt(1);
    return isFormattingCode(code) && formattingCode.charAt(0) == '§'
      ? org.bukkit.ChatColor.getByChar(code)
      : org.bukkit.ChatColor.WHITE;
  }

  /**
   * Checks whether the given text ends with a color code.
   * This does not check for style codes.
   *
   * @param text The text to be checked for color codes.
   * @return The last color code of the text. {@code null} if there
   *         was no color code to be detected.
   */
  public static String endsWithColorCode(String text) {
    if (text.length() < 2) {
      return null;
    }

    String code = text.substring(text.length() - 2);
    if (code.charAt(0) == '§' && isColorCode(code.charAt(1))) {
      return code;
    }

    return null;
  }

  /**
   * Checks whether the given text ends with any formatting code
   * (no matter if style or color).
   *
   * @param text The text to be checked for formatting codes.
   * @return The last formatting code of the text. {@code null} if there
   *         was no color code to be detected.
   */
  public static String endsWithFormattingCode(String text) {
    // if the text is less than 2 chars long there can
    // be no formatting code, so return immediately.
    if (text.length() < 2) {
      return null;
    }

    String code = text.substring(text.length() - 2);
    if (code.charAt(0) == '§' && isFormattingCode(code.charAt(1))) {
      return code;
    }

    return null;
  }

  /**
   * Picks a random color code id. This means the result will only be
   * something like {@code '1', 'b', ...} without a {@code '§'} in front.
   *
   * @return Any random bukkit color code.
   */
  public static char randomColorCode() {
    return colorCodes[ThreadLocalRandom.current().nextInt(colorCodes.length - 1)];
  }

  /**
   * Picks a random style code. This means the result will only be
   * something like {@code 'k', 'o', ...} without a {@code '§'} in front.
   *
   * @return
   */
  public static char randomStyleCode() {
    return styleCodes[ThreadLocalRandom.current().nextInt(colorCodes.length - 1)];
  }

  /**
   * Picks any random formatting code from the list. This can either be
   * a result of {@link #randomStyleCode()} or {@link #randomColorCode()}.
   *
   * @return Any random foramtting code without {@code '§'} in front.
   */
  public static char randomFormattingCode() {
    return ThreadLocalRandom.current().nextBoolean() ? randomColorCode() : randomStyleCode();
  }

  /**
   * @param indicator The indicator of the code you want to check.
   * @return {@code true} if the either a color code or a style code.
   */
  public static boolean isFormattingCode(char indicator) {
    return isColorCode(indicator) || isStyleCode(indicator);
  }

}
