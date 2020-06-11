package de.pxav.kelp.core.common;

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
public class KelpFileUtils {

  /**
   * Checks if the given file exists and creates it
   * at the given path if it does not exist.
   *
   * @param file The file you want to check and eventually create.
   * @return Returns {@code true} if the file had to be created and did not exist.
   */
  public boolean createIfNotExists(File file) {
    if (file.exists()) return false;

    File directory = file.getParentFile();

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

  /**
   * Checks if the given folder exists and creates it
   * at the given path if it does not exist.
   *
   * @param file The folder you want to check and eventually create.
   * @return Returns {@code true} if the folder had to be created and did not exist.
   */
  public boolean createFolderIfNotExists(File file) {
    if (file.exists()) return false;
    file.mkdirs();
    return true;
  }

}
