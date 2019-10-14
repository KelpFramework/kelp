package de.pxav.kelp.common;

import com.google.common.collect.Lists;
import com.google.inject.Singleton;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class StringUtils {

  private char[] colorCodes = new char[] {
          '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
          'a', 'b', 'c', 'd', 'e', 'f'
  };

  private char[] styleCodes = new char[] {
          'k', 'l', 'm', 'n', 'o', 'r'
  };

  public String removeLastChar(String text) {
    return text.substring(0, text.length() - 1);
  }

  public List<String> extractColorCodes(String text) {
    List<String> output = Lists.newArrayList();
    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) == '§' && this.isColorCode(text.charAt(i + 1))) {
        output.add("§" + text.charAt(i + 1));
      }
    }
    return output;
  }

  public List<String> extractStyleCodes(String text) {
    List<String> output = Lists.newArrayList();
    for (int i = 0; i < text.length(); i++) {
      if (text.charAt(i) == '§' && this.isStyleCode(text.charAt(i + 1))) {
        output.add("§" + text.charAt(i + 1));
      }
    }
    return output;
  }

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

  public String lastFormattingCodesOf(String text) {
    List<String> reversed = extractFormattingCodes(text);
    Collections.reverse(reversed);
    List<String> codes = Lists.newArrayList();

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

    if (text.length() >= 16 && text.substring(0, 16).endsWith("§")) {
      codes.add("§");
    }

    return codes.toString()
            .replace("[", "")
            .replace("]", "")
            .replace(",", "")
            .replace(" ", "");
  }

  public boolean isColorCode(char indicator) {
    for (char current : colorCodes) {
      if (current == indicator) {
        return true;
      }
    }
    return false;
  }

  public boolean isStyleCode(char indicator) {
    for (char current : styleCodes) {
      if (current == indicator) {
        return true;
      }
    }
    return false;
  }

}
