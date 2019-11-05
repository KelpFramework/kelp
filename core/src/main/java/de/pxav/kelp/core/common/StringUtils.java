package de.pxav.kelp.core.common;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;

import java.util.Collections;
import java.util.List;

/**
 * This class contains useful methods to work with strings
 * including normal char processing but also minecraft color codes.
 *
 * @author pxav
 */
@Singleton
public class StringUtils {

  // an array containing all color codes in alphabetical order.
  private char[] colorCodes = new char[] {
          '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
          'a', 'b', 'c', 'd', 'e', 'f'
  };

  // an array containing all style codes in alphabetical order.
  private char[] styleCodes = new char[] {
          'k', 'l', 'm', 'n', 'o', 'r'
  };

  /**
   * Removes the last char of the given text.
   *
   * @param text The text you want to remove the char of.
   * @return The given text without the last char.
   */
  public String removeLastChar(String text) {
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
  public String removeFormattingCodes(String text) {
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
  public List<String> extractColorCodes(String text) {
    List<String> output = Lists.newArrayList();
    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) == '§' && this.isColorCode(text.charAt(i + 1))) {
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
  public List<String> extractStyleCodes(String text) {
    List<String> output = Lists.newArrayList();
    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) == '§' && this.isStyleCode(text.charAt(i + 1))) {
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
  public List<String> extractFormattingCodes(String text) {
    List<String> output = Lists.newArrayList();
    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) != '§') {
        continue;
      }

      if (text.length() == i + 1) {
        break;
      }

      if (this.isStyleCode(text.charAt(i + 1))
              || this.isColorCode(text.charAt(i + 1))) {
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
  public String lastFormattingCodesOf(String text) {
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
  public String lastFullFormattingCodesOf(String text) {
    // check if the color codes have a single '§' at the end.
    // this has to be removed as we only want to have full
    // color codes.
    if (this.lastFormattingCodesOf(text).endsWith("§")) {
      String colorCodes = this.lastFormattingCodesOf(text);
      return this.removeLastChar(colorCodes);
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
  public boolean isColorCode(char indicator) {
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
  public boolean isStyleCode(char indicator) {
    for (char current : styleCodes) {
      if (current == indicator) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param indicator The indicator of the code you want to check.
   * @return {@code true} if the either a color code or a style code.
   */
  public boolean isFormattingCode(char indicator) {
    return isColorCode(indicator) || isStyleCode(indicator);
  }

}
