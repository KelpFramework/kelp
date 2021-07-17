package de.pxav.kelp.core.common;

import com.google.inject.Singleton;
import de.pxav.kelp.core.KelpPlugin;
import org.apache.commons.io.FileUtils;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.logging.Level;

/**
 * This class provides basic methods to manage your files
 * easily. It is an addition to the Apache File utils
 * used in this project.
 *
 * @author pxav
 */
@Singleton
public class KelpFileUtils {

  // always replaces file no matter if there is another resource file
  public static void saveResource(String resourcePath, File outputFile) {


    if (resourcePath == null || resourcePath.equals("")) {
      throw new IllegalArgumentException("ResourcePath cannot be null or empty");
    }

    InputStream inputStream = getResource(resourcePath);
    if (inputStream == null) {
      throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in ..........");
    }

    try {
      FileUtils.copyInputStreamToFile(inputStream, outputFile);
    } catch (IOException ex) {
      //logger.log(Level.SEVERE, "Could not save " + outputFile.getName() + " to " + outputFile, ex);
      System.out.println("failed saving resource " + resourcePath);
    }
  }

  public static InputStream getResource(String resourcePath) {
    if (resourcePath == null) {
      throw new IllegalArgumentException("Filename cannot be null");
    }

    //resourcePath = "/" + resourcePath;
    resourcePath = resourcePath.replace('\\', '/');

    try {
      URL url = KelpPlugin.class.getClassLoader().getResource(resourcePath);

      if (url == null) {
        return null;
      }

      URLConnection connection = url.openConnection();
      connection.setUseCaches(false);
      return connection.getInputStream();
    } catch (IOException ex) {
      return null;
    }
  }

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
