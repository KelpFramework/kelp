package de.pxav.kelp.core.configuration.parse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ConfigurationDumper {

  private File file;

  public static ConfigurationDumper create(File file) {
    return new ConfigurationDumper(file);
  }

  private ConfigurationDumper(File file) {
    this.file = file;
  }

  public void dump(List<String> content) {

    FileWriter fileWriter = null;
    BufferedWriter bufferedWriter = null;

    try {

      fileWriter = new FileWriter(file);
      bufferedWriter = new BufferedWriter(fileWriter);

      for (String line : content) {
        bufferedWriter.write(line);
        bufferedWriter.newLine();
      }

    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      try {
        if (bufferedWriter != null) {
          bufferedWriter.close();
          fileWriter.close();
        }
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

}
