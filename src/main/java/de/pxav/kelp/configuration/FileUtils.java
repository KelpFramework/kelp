package de.pxav.kelp.configuration;

import com.google.inject.Singleton;

import java.io.File;
import java.io.IOException;

/**
 * This class provides basic methods to manage your files
 * easily. It is an addition to the Apache File utils
 * used in this project.
 *
 * @author pxav
 */
@Singleton
public class FileUtils {

  /**
   * Checks if the given file exists and creates it
   * at the given path if it does not exist.
   *
   * @param file The file you want to check and eventually create.
   * @return Returns {@code true} if the file had to be created and did not exist.
   */
  public boolean createIfNotExists(File file) {
    if (file.exists()) return false;

    File directory = new File(file.getPath().replace("/" + file.getName(), ""));
    if (!directory.exists()) {
      directory.mkdirs();
    }

    try {
      file.createNewFile();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return true;
  }

}
