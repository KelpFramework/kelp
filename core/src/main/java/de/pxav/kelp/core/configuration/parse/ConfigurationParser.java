package de.pxav.kelp.core.configuration.parse;

import de.pxav.kelp.core.configuration.parse.yaml.YamlConfigurationParser;
import org.apache.commons.io.FilenameUtils;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class ConfigurationParser {

  protected BufferedReader bufferedReader = null;

  public static ConfigurationParser getParserFor(File file) {
    String fileExtension = FilenameUtils.getExtension(file.getName());
    switch (fileExtension) {
      case "yaml": case "yml":
        try {
          return new YamlConfigurationParser(new FileReader(file));
        } catch (FileNotFoundException e) {
          return new YamlConfigurationParser(null);
        }
    }
    return null;
  }

  public ConfigurationParser(Reader reader) {
    setReader(reader);
  }

  private void setReader(Reader reader) {
    if (reader != null) {
      this.bufferedReader = reader instanceof BufferedReader
        ? (BufferedReader) reader
        : new BufferedReader(reader);
    }
  }

  public boolean patchNecessary(String templatePath, InputStreamReader templateReader) {
    ConfigurationParser templateParser = getParserFor(new File(templatePath));

    if (templateParser == null) {
      System.out.println("Template file format is not supported!");
      return false;
    }

    templateParser.setReader(templateReader);

    Set<String> templateKeys = templateParser.readValues().keySet();
    Set<String> existingKeys = readValues().keySet();

    if (existingKeys.containsAll(templateKeys)) {
      System.out.println("No patch necessary!");
      return false;
    }

    return true;
  }

  public abstract Map<String, Object> readValues();

  public abstract List<String> parseContents(Map<String, Object> valuePool);

}
