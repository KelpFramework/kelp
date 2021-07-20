package de.pxav.kelp.core.configuration;

import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ConfigurationParser {

  private static final int WORDS_PER_FOLDED_SCALAR_LINE = 4;

  private File file;
  private Map<String, Object> valuePool;

  public static ConfigurationParser create(File file, Map<String, Object> valuePool) {
    return new ConfigurationParser(file, valuePool);
  }

  private ConfigurationParser(File file, Map<String, Object> valuePool) {
    this.file = file;
    this.valuePool = valuePool;
  }

  public List<String> parseContents() {

    FileReader fileReader = null;
    BufferedReader bufferedReader = null;

    System.out.println("Existing value pool: ");
    valuePool.forEach((key, val) -> {
      System.out.println(key + ": " +val);
    });

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

      fileReader = new FileReader(this.file);
      bufferedReader = new BufferedReader(fileReader);

      whileLabel: while ((line = bufferedReader.readLine()) != null) {

        if (line.isEmpty()) {
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
                continue;
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
            } else {
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
          } else if (scalarBlock) {
            if (copiedScalar) {
              continue;
            }

            if (valuePool.containsKey(currentKey)) {
              String scalarContent = valuePool.get(currentKey).toString();
              copiedScalar = true;

              if (foldedScalar) {
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
                String[] scalarLines = scalarContent.split("\n");
                for (String scalarLine : scalarLines) {
                  dumpLines.add(generateIndent(indent) + scalarLine);
                }
              }
            }

            dumpLines.add(line);

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
                dumpLines.add(fetchValueLine(line, currentKey, newKeyName, indent));
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
              dumpLines.add(fetchValueLine(line, currentKey, newKeyName, indent));
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
            dumpLines.add(fetchValueLine(line, currentKey, newKeyName, indent));
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
            dumpLines.add(fetchValueLine(line, currentKey, currentKey, 0));
          }

          // the key has no value, so it introduces a new paragraph
          else {
            dumpLines.add(line);
          }
        }

      }

      // todo add support for block scalars

      System.out.println("dump lines: ");
      dumpLines.forEach(current -> {
        System.out.println(current);
      });

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
          fileReader.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }

    return Lists.newArrayList();
  }

  private int handleList() {
    return 0;
  }

  private int handleScalar() {
    return 0;
  }

  private String generateIndent(int indent) {
    StringBuilder indentBuilder = new StringBuilder();
    for (int i = 0; i < indent; i++) {
      indentBuilder.append(" ");
    }
    return indentBuilder.toString();
  }

  private String fetchValueLine(String line,
                                String fullKey,
                                String singleKey,
                                int indent) {
    if (!this.valuePool.containsKey(fullKey)) {
      return line;
    }

    Object oldValue = this.valuePool.get(fullKey);
    StringBuilder lineBuilder = new StringBuilder(generateIndent(indent));
    lineBuilder.append(singleKey).append(": ").append(oldValue.toString());
    return lineBuilder.toString();

  }

  private static interface YamlValueParser {

  }

}
