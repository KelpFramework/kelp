package de.pxav.kelp.core.configuration.parse.yaml;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.configuration.parse.ConfigurationParser;

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class YamlConfigurationParser extends ConfigurationParser {

  private static final int WORDS_PER_FOLDED_SCALAR_LINE = 4;

  public YamlConfigurationParser(Reader reader) {
    super(reader);
  }

  @Override
  public Map<String, Object> readValues() {

  }

  public List<String> parseContents(Map<String, Object> valuePool) {

    try {

      List<String> dumpLines = Lists.newArrayList();

      // the current key path the parser points to
      String currentKey = "";
      boolean copiedList = false;
      boolean foldedScalar = false;
      boolean scalarBlock = false;
      boolean copiedScalar = false;

      // the line that is currently read
      String line = "";

      // indentation of the previous line
      int lastIndent = 0;

      whileLabel: while ((line = this.bufferedReader.readLine()) != null) {

        if (line.replace(" ", "").isEmpty()) {
          dumpLines.add("");
        }

        // if content is indented
        if (line.startsWith(" ")) {

          int indent = 0;
          for (char character : line.toCharArray()) {

            // If there is an indented comment before any key
            // has been introduced, we know that this line won't
            // contain a key, so we can skip it and continue with the
            // next line.
            if (character == '#') {
              dumpLines.add(line);
              continue whileLabel;
            }

            // We have found a list element, so the current key has to be
            // the key of a list.
            if (character == '-') {
              if (copiedList) {
                continue whileLabel;
              }

              if (valuePool.containsKey(currentKey) && valuePool.get(currentKey) instanceof List) {
                copiedList = true;
                for (Object element : ((List<?>) valuePool.get(currentKey))) {
                  String lineToDump = generateIndent(indent) + "- " + element;
                  dumpLines.add(lineToDump);
                }
              } else {
                dumpLines.add(line);
              }

              continue whileLabel;
            } else if (character != ' ') {
              copiedList = false;
            }

            // count amount of indents, which is used to
            // determine the layer structure of the YAML file
            // as well as keeping the indentation in the output
            // file.
            if (character == ' ') {
              indent++;
            } else {
              break;
            }
          }

          if (scalarBlock && indent < lastIndent) {
            scalarBlock = false;
            foldedScalar = false;
            copiedScalar = false;
            lastIndent = indent;
          } else if (scalarBlock) {
            lastIndent = indent;

            if (copiedScalar) {
              continue;
            }

            if (valuePool.containsKey(currentKey)) {
              String scalarContent = valuePool.get(currentKey).toString();
              copiedScalar = true;

              if (foldedScalar) {
                foldedScalar = false;
                String[] scalarLines = scalarContent.split(" ");
                if (scalarLines.length <= 4) {
                  dumpLines.add(generateIndent(indent) + scalarContent);
                } else {
                  for (int lines = 0; lines < scalarLines.length / WORDS_PER_FOLDED_SCALAR_LINE; lines++) {
                    StringBuilder lineBuilder = new StringBuilder(generateIndent(indent));
                    for (int word = lines * 4; word < (lines + 1) * 4; word++) {
                      lineBuilder.append(scalarLines[word]).append(" ");
                    }
                    dumpLines.add(lineBuilder.toString());
                  }

                  int missingWords = scalarLines.length % WORDS_PER_FOLDED_SCALAR_LINE;
                  if (missingWords != 0) {
                    StringBuilder lineBuilder = new StringBuilder(generateIndent(indent));
                    for (int i = scalarLines.length - missingWords; i < scalarLines.length; i++) {
                      lineBuilder.append(scalarLines[i]).append(" ");
                    }
                    dumpLines.add(lineBuilder.toString());
                  }

                }
              } else {
                System.out.println(valuePool.get(currentKey).toString());

                String[] scalarLines = scalarContent.split("\n");

                for (String scalarLine : scalarLines) {
                  dumpLines.add(generateIndent(indent) + scalarLine);
                }
              }
            } else {
              dumpLines.add(line);
            }

            continue;
          }

          // When the current indentation is equal to the previous
          // one, we know the keys are both on the same layer.
          if (indent == lastIndent) {
            String[] keyPath = currentKey.split("\\.");
            String[] keyValuePair = line.replace(" ", "").split(":");
            String newKeyName = keyValuePair[0];
            keyPath[keyPath.length - 1] = newKeyName;
            StringBuilder newKeyBuilder = new StringBuilder();
            for (int i = 0; i < keyPath.length; i++) {
              newKeyBuilder.append(keyPath[i]);
              if (i < keyPath.length - 1) {
                newKeyBuilder.append(".");
              }
            }
            currentKey = newKeyBuilder.toString();

            // if the key has a value
            if (keyValuePair.length == 2) {

              // characters introducing a folded (>) or block (|) scalar
              if (keyValuePair[1].startsWith(">") || keyValuePair[1].startsWith("|")) {
                if (keyValuePair[1].startsWith(">")) {
                  foldedScalar = true;
                }
                scalarBlock = true;
                dumpLines.add(line);
              } else {
                dumpLines.addAll(fetchValueLine(valuePool, line, currentKey, newKeyName, indent));
              }

            }

            // the key has no value, so it introduces a new paragraph
            else {
              dumpLines.add(line);
            }

            continue;
          }

          // If the indent count of this line is higher than the one
          // of the previous line, this line has to be a sub-layer/key
          // of the previous line.
          if (indent > lastIndent) {
            lastIndent = indent;

            String[] keyValuePair = line.replace(" ", "").split(":");
            String newKeyName = keyValuePair[0];
            currentKey = currentKey + "." + newKeyName;

            if (keyValuePair.length == 2) {
              // characters introducing a folded (>) or block (|) scalar
              if (keyValuePair[1].startsWith(">") || keyValuePair[1].startsWith("|")) {
                if (keyValuePair[1].startsWith(">")) {
                  foldedScalar = true;
                }
                scalarBlock = true;
                dumpLines.add(line);
              } else {
                dumpLines.addAll(fetchValueLine(valuePool, line, currentKey, newKeyName, indent));
              }
            }

            // the key has no value, so it introduces a new paragraph
            else {
              dumpLines.add(line);
            }

            continue;
          }

          // new upper level section
          // as condition "indent < lastIndent" always is true here
          // based on the prior checks.

          lastIndent = indent;

          String[] keyPath = currentKey.split("\\.");
          StringBuilder newKeyBuilder = new StringBuilder();
          for (int i = 0; i < keyPath.length - 2; i++) {
            newKeyBuilder.append(keyPath[i]);
            if (i < keyPath.length - 3) {
              newKeyBuilder.append(".");
            }
          }

          String[] keyValuePair = line.replace(" ", "").split(":");
          String newKeyName = keyValuePair[0];

          currentKey = newKeyBuilder + "." + newKeyName;

          if (keyValuePair.length == 2) {
            // characters introducing a folded (>) or block (|) scalar
            if (keyValuePair[1].startsWith(">") || keyValuePair[1].startsWith("|")) {
              if (keyValuePair[1].startsWith(">")) {
                foldedScalar = true;
              }
              scalarBlock = true;
              dumpLines.add(line);
            } else {
              dumpLines.addAll(fetchValueLine(valuePool, line, currentKey, newKeyName, indent));
            }
          } else {
            dumpLines.add(line);
          }

          continue;

          // when a line is not indented and starts with a '#',
          // we know this line represents a top-level comment that
          // can have no values attached later within the same line.
        } else if (line.startsWith("#")) {
          dumpLines.add(line);

          // when this statement is reached, the parser already knows that this
          // line is not indented, nor it is a comment, so we know it must be a
          // top level key. This can mean that either a value is directly assigned
          // in this line or a new paragraph is introduced.
        } else if (!line.isEmpty()) {
          String[] keyValuePair = line.replace(" ", "").split(":");
          currentKey = keyValuePair[0];

          if (keyValuePair.length == 2) {
            dumpLines.addAll(fetchValueLine(valuePool, line, currentKey, currentKey, 0));
          }

          // the key has no value, so it introduces a new paragraph
          else {
            dumpLines.add(line);
          }
        }

      }

      return dumpLines;
    }

    // if file is not found or any other I/O exception occurs
    catch (IOException e) {
      e.printStackTrace();
    }

    // properly close file readers when finished
    finally {
      try {
        if (bufferedReader != null) {
          bufferedReader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return Lists.newArrayList();
  }

  /**
   * Fetches the inline comment from a given line of a YAML-configuration.
   * An inline comment is a comment made in the same line as a value declaration,
   * for example: {@code someKey: 'A value' # this is an inline comment}.
   * Hence, a comment that covers a dedicated line is not considered an inline comment.
   *
   * Please note that a # does not always introduce a new comment. If used in
   * key names for example, #s are completely legal as well:
   * {@code key#1: 'Key #1's value'}. As you can see they can also be used in
   * values. This method automatically checks for such purposes and
   * returns an empty string in such cases.
   *
   * However, inline comments can not exist inside scalar blocks as any chars
   * are considered as part of the scalar. It is therefore impossible to reliably
   * detect whether we are in a scalar line without any context given. So don't
   * use this method if you know the given line is part of a scalar block.
   *
   * @param line The configuration line to fetch the inline comment from.
   * @return The extracted inline comment of the given line including the {@code #} char.
   *         The rest of the line will be removed.
   */
  private String fetchInlineComment(String line) {
    String noSpaces = line.replace(" ", "");
    // no inline comment if the '#' is located as the first char of a line
    // or if the line does not contain a '#' at all.
    if (!line.contains("#") || noSpaces.startsWith("#")) {
      return "";
    }

    // if the current line is a list item
    if (noSpaces.startsWith("-")) {
      for (int i = 0; i < line.length(); i++) {
        char c = line.charAt(i);
        if (c == '#') {
          char before = line.charAt(i - 1);
          if (before == ' ') {
            return line.substring(i);
          }
        }
      }
    }

    // whether a ':' has been found in the string
    boolean keyMarkFound = false;

    boolean enclosedValue = false;

    for (int i = 0; i < line.length(); i++) {
      char c = line.charAt(i);

      if (c == ':') {
        keyMarkFound = true;
      }

      if ((c == '\'' || c == '"') && keyMarkFound) {
        if (enclosedValue) {
          enclosedValue = false;
          continue;
        }

        enclosedValue = true;
      }

      if (c == '#' && !enclosedValue) {
        char before = line.charAt(i - 1);
        if (before == ' ') {
          return line.substring(i);
        }
      }

    }

    return "";
  }

  private String generateIndent(int indent) {
    StringBuilder indentBuilder = new StringBuilder();
    for (int i = 0; i < indent; i++) {
      indentBuilder.append(" ");
    }
    return indentBuilder.toString();
  }

  private List<String> fetchValueLine(Map<String, Object> valuePool,
                                      String line,
                                      String fullKey,
                                      String singleKey,
                                      int indent) {
    if (!valuePool.containsKey(fullKey)) {
      return Collections.singletonList(line);
    }

    Object oldValue = valuePool.get(fullKey);

    // check if the old value is a multiline string represented by a block scalar
    if (oldValue instanceof String) {
      String value = oldValue.toString();

      if (value.contains("\n") || value.contains("\\n") || value.contains("\r")) {
        List<String> output = Lists.newArrayList();
        output.add(generateIndent(indent) + singleKey + ": | " + fetchInlineComment(line));

        // since Java 8 you can use \R to detect line breaks on all platforms
        // instead of using something like /\n|\r|\\n/, which was used before.
        String[] scalarLines = value.split("\\R");

        for (String scalarLine : scalarLines) {
          output.add(generateIndent(indent + 2) + scalarLine);
        }

        return output;
      }

    }

    return Collections.singletonList(generateIndent(indent) + singleKey + ": " + oldValue.toString() + " " + fetchInlineComment(line));

  }

}
