package de.pxav.kelp.core.configuration;

import com.google.common.collect.Lists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class ConfigurationParser {

  private File file;

  public static ConfigurationParser create(File file) {
    return new ConfigurationParser(file);
  }

  private ConfigurationParser(File file) {
    this.file = file;
  }

  public List<String> parseContents(Map<String, Object> valueBackup) {

    FileReader fileReader = null;
    BufferedReader bufferedReader = null;

    try {

      List<String> dumpLines = Lists.newArrayList();

      // the current key path the parser points to
      String currentKey = "";
      boolean copiedList = false;

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
                  StringBuilder lineBuilder = new StringBuilder();
                  for (int i = 0; i < indent; i++) {
                    lineBuilder.append(" ");
                  }
                  lineBuilder.append("- ");
                  lineBuilder.append(element);
                  dumpLines.add(lineBuilder.toString());
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

          // new key on same layer
          if (indent == lastIndent) {
            System.out.println("new key on same layer");
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
            System.out.println("current key: " + currentKey);

            // if the key has a value
            if (keyValuePair.length == 2) {
              dumpLines.add(fetchValueLine(line, currentKey, newKeyName, indent, valueBackup));
            }

            // the key has no value, so it introduces a new paragraph
            else {
              dumpLines.add(line);
            }

            continue;
          }

          // new key on sub layer
          if (indent > lastIndent) {
            lastIndent = indent;
            System.out.println("new key on sub layer");

            String[] keyValuePair = line.replace(" ", "").split(":");
            String newKeyName = keyValuePair[0];
            currentKey = currentKey + "." + newKeyName;
            System.out.println("current key: " + currentKey);

            if (keyValuePair.length == 2) {
              dumpLines.add(fetchValueLine(line, currentKey, newKeyName, indent, valueBackup));
            }

            // the key has no value, so it introduces a new paragraph
            else {
              dumpLines.add(line);
            }

            continue;
          }

          // new upper level section
          if (indent < lastIndent) {
            lastIndent = indent;
            System.out.println("new key on upper section");

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
            System.out.println("current key: " + currentKey);

            if (keyValuePair.length == 2) {
              dumpLines.add(fetchValueLine(line, currentKey, newKeyName, indent, valueBackup));
            } else {
              dumpLines.add(line);
            }

            continue;
          }

        } else if (line.startsWith("#")) {
          System.out.println("top level comment");
          dumpLines.add(line);
        } else if (!line.isEmpty()) {
          System.out.println("top level key");

          String[] keyValuePair = line.replace(" ", "").split(":");
          currentKey = keyValuePair[0];

          if (keyValuePair.length == 2) {
            dumpLines.add(fetchValueLine(line, currentKey, currentKey, 0, valueBackup));
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

  private String fetchValueLine(String line, String fullKey, String singleKey, int indent, Map<String, Object> valuePool) {
    if (!valuePool.containsKey(fullKey)) {
      return line;
    }

    Object oldValue = valuePool.get(fullKey);
    StringBuilder lineBuilder = new StringBuilder();
    for (int i = 0; i < indent; i++) {
      lineBuilder.append(" ");
    }
    lineBuilder.append(singleKey).append(": ");
    //todo support lists
    lineBuilder.append(oldValue.toString());
    return lineBuilder.toString();

  }

}
