package de.pxav.kelp.core.logger;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.configuration.KelpFileUtils;
import de.pxav.kelp.core.configuration.internal.KelpDefaultConfiguration;
import org.apache.commons.io.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This logger class can be used to log and save information
 * which is created during the runtime of a Kelp plugin.
 *
 * The logger system can send normal console logs and also
 * use an own file system. This file system uses the latest log
 * file to save information created in the current session and
 * copies this information into a file archive when the session
 * is ended.
 *
 * A session is the period in which the plugin is running,
 * so the time period between the enable and disable of the plugin/server.
 *
 * @author pxav
 */
@Singleton
public final class KelpLogger {

  private final KelpPlugin kelpPlugin;
  private final Injector injector;
  private final KelpFileUtils kelpFileUtils;
  private final KelpDefaultConfiguration defaultConfig;
  private final Date DATE;
  private final String sessionBegin;
  private final File LATEST_FILE;

  private File currentFile;

  @Inject
  public KelpLogger(Injector injector,
                    KelpFileUtils kelpFileUtils,
                    KelpDefaultConfiguration defaultConfig) {
    this.kelpPlugin = KelpPlugin.getPlugin(KelpPlugin.class);
    this.injector = injector;
    this.kelpFileUtils = kelpFileUtils;
    this.defaultConfig = defaultConfig;

    this.DATE = new Date();
    this.LATEST_FILE = new File("kelp_logs", "latest.log");
    this.sessionBegin = new SimpleDateFormat("HH-mm-ss_dd-MM-yyyy").format(DATE);
  }

  /**
   * This method loads the file for the latest log
   * and prepares it for use. This means that its
   * contents are cleared and can only be found in the
   * file of the last session.
   */
  public void loadLoggerFiles() {
    try {
      kelpFileUtils.createIfNotExists(LATEST_FILE);
      FileChannel.open(Paths.get(LATEST_FILE.getPath()), StandardOpenOption.WRITE).truncate(0).close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Logs a message of a certain plugin.
   * This method prints your message to the console and writes it to a file.
   *
   * @param pluginMainClass The main class of your Kelp-Plugin.
   *                        Needs to extend from {@code KelpApplication}
   * @param logLevel The level of your log message.
   *                 -> Which significance does it have
   *                 for the operations of your plugin.?
   * @param message The actual messages you want to log.
   * @see KelpApplication
   */
  public void log(Class<? extends KelpApplication> pluginMainClass,
                  LogLevel logLevel, String... message) {
    this.consoleLog(pluginMainClass, logLevel, message);
    this.writeLog(pluginMainClass, logLevel, message);
  }

  /**
   * Logs a message of a certain plugin.
   * You can use this method if your message does not express
   * a critical or special state. The default level is set to {@code INFO}.
   *
   * This method prints your message to the console and writes it to a file.
   *
   * @param pluginMainClass The main class of your Kelp-Plugin.
   *                        Needs to extend from {@code KelpApplication}
   * @param message The actual messages you want to log.
   * @see KelpApplication
   */
  public void log(Class<? extends KelpApplication> pluginMainClass,
                  String... message) {
    this.consoleLog(pluginMainClass, message);
    this.writeLog(pluginMainClass, message);
  }

  /**
   * Logs a general message of Kelp which is independent
   * from any other plugin. So {@code 'Kelp'} will displayed in
   * front of your message instead of your plugin name.
   *
   * Use this method if you don't want to express a special state.
   * {@code INFO} level is taken as default.
   *
   * This method prints your message to the console and writes it to a file.
   *
   * @param message The actual message you want to log.
   */
  public void log(String... message) {
    this.consoleLog(message);
    this.writeLog(message);
  }

  /**
   * Logs a general message of Kelp which is independent
   * from any other plugin. So {@code 'Kelp'} will displayed in
   * front of your message instead of your plugin name.
   *
   * This method prints your message to the console and writes it to a file.
   *
   * @param level The level of your log message.
   *              -> Which significance does it have for the operations of kelp?
   * @param message The actual message you want to log.
   */
  public void log(LogLevel level, String... message) {
    this.consoleLog(level, message);
    this.writeLog(level, message);
  }

  /**
   * Logs a message of a certain plugin.
   * This method prints your message to the
   * console and does not write it to a file.
   *
   * @param pluginMainClass The main class of your Kelp-Plugin.
   *                        Needs to extend from {@code KelpApplication}
   * @param logLevel The level of your log message.
   *                 -> Which significance does it have
   *                 for the operations of your plugin?
   * @param messages The actual messages you want to log.
   * @see KelpApplication
   */
  public void consoleLog(Class<? extends KelpApplication> pluginMainClass,
                         LogLevel logLevel, String... messages) {
    if (logLevel == LogLevel.DEBUG
            && !defaultConfig.getBooleanValue(defaultConfig.developmentMode())) {
      return;
    }

    KelpApplication kelpApplication = injector.getInstance(pluginMainClass);
    for (String message : messages) {
      kelpPlugin.getLogger().log(logLevel.toJavaLogLevel(),
              "[" + kelpApplication.getInformation().getApplicationName() + "]" + message);
    }
  }

  /**
   * Logs a message of a certain plugin.
   * This method prints your message to the
   * console and does not write it to a file.
   *
   * Use this method for messages that do not have
   * special content, because {@code INFO} is taken as
   * default log level.
   *
   * @param pluginMainClass The main class of your Kelp-Plugin.
   *                        Needs to extend from {@code KelpApplication}
   * @param messages The actual messages you want to log.
   * @see KelpApplication
   */
  public void consoleLog(Class<? extends KelpApplication> pluginMainClass,
                         String... messages) {
    KelpApplication kelpApplication = injector.getInstance(pluginMainClass);
    for (String message : messages) {
      kelpPlugin.getLogger().log(LogLevel.INFO.toJavaLogLevel(),
              "[" + kelpApplication.getInformation().getApplicationName() + "]" + message);
    }
  }

  /**
   * Logs a general message of Kelp which is independent
   * from any other plugin. So {@code 'Kelp'} will displayed in
   * front of your message instead of your plugin name.
   *
   * This method only prints your message to the console
   * and does not log it to a file.
   *
   * @param logLevel The level of your log message.
   *              -> Which significance does it have for the operations of kelp?
   * @param messages The actual message you want to log.
   */
  public void consoleLog(LogLevel logLevel, String... messages) {
    if (logLevel == LogLevel.DEBUG
            && !defaultConfig.getBooleanValue(defaultConfig.developmentMode())) {
      return;
    }

    for (String message : messages)
    kelpPlugin.getLogger().log(logLevel.toJavaLogLevel(), message);
  }

  /**
   * Logs a general message of Kelp which is independent
   * from any other plugin. So {@code 'Kelp'} will displayed in
   * front of your message instead of your plugin name.
   *
   * Don't use this method if you have special messages that
   * should express warnings, etc. The default log level
   * is set to {@code INFO}.
   *
   * This method only prints your message to the console
   * and does not log it to a file.
   *
   * @param messages The actual message you want to log.
   */
  public void consoleLog(String... messages) {
    for (String message : messages) {
      kelpPlugin.getLogger().log(LogLevel.INFO.toJavaLogLevel(), message);
    }
  }

  /**
   * Logs a general message of Kelp which is independent
   * from any other plugin. So {@code 'Kelp'} will displayed in
   * front of your message instead of your plugin name.
   *
   * Use this method if you don't want to express a special state.
   * {@code INFO} level is taken as default.
   *
   * This method only writes your messages in a file
   * and does not print it to the console.
   *
   * @param messages The actual message you want to log.
   */
  public void writeLog(String... messages) {
    for (String message : messages) {
      writeToLatestLog(LogLevel.INFO, message);
    }
  }

  /**
   * Logs a general message of Kelp which is independent
   * from any other plugin. So {@code 'Kelp'} will displayed in
   * front of your message instead of your plugin name.
   *
   * Use this method if you don't want to express a special state.
   * {@code INFO} level is taken as default.
   *
   * This method only writes your messages in a file
   * and does not print it to the console.
   *
   * @param logLevel The level of your log message.
   *                 -> Which significance does it have
   *                 for the operations of your plugin?
   * @param messages The actual message you want to log.
   */
  public void writeLog(LogLevel logLevel, String... messages) {
    if (logLevel == LogLevel.DEBUG
            && !defaultConfig.getBooleanValue(defaultConfig.developmentMode())) {
      return;
    }

    for (String message : messages) {
      writeToLatestLog(logLevel, message);
    }
  }

  /**
   * Logs a message of a certain plugin.
   * This method only writes your message to
   * a file but does not print it to the console.
   *
   * Use this method for messages that do not have
   * special content, because {@code INFO} is taken as
   * default log level.
   *
   * @param pluginMainClass The main class of your Kelp-Plugin.
   *                        Needs to extend from {@code KelpApplication}
   * @param messages The actual messages you want to log.
   * @see KelpApplication
   */
  public void writeLog(Class<? extends KelpApplication> pluginMainClass,
                       String... messages) {
    KelpApplication kelpApplication = injector.getInstance(pluginMainClass);
    for (String message : messages) {
      writeToLatestLog(LogLevel.INFO, "["
                      + kelpApplication.getInformation().getApplicationName()
                      + "]"
                      + message);
    }
  }

  /**
   * Logs a message of a certain plugin.
   * This method only writes your message to
   * a file but does not print it to the console.
   *
   * @param pluginMainClass The main class of your Kelp-Plugin.
   *                        Needs to extend from {@code KelpApplication}
   * @param level The level of your log message.
   *                 -> Which significance does it have
   *                 for the operations of your plugin?
   * @param messages The actual messages you want to log.
   * @see KelpApplication
   */
  public void writeLog(Class<? extends KelpApplication> pluginMainClass,
                       LogLevel level, String... messages) {
    if (level == LogLevel.DEBUG
            && !defaultConfig.getBooleanValue(defaultConfig.developmentMode())) {
      return;
    }

    KelpApplication kelpApplication = injector.getInstance(pluginMainClass);
    for (String message : messages) {
      writeToLatestLog(level,"["
                      + kelpApplication.getInformation().getApplicationName()
                      + "]"
                      + message);
    }
  }

  /**
   * Copies the content of the latest log file
   * into the file of the current session.
   * If this file does not exist it will be created automatically.
   */
  public void archiveLog() {
    try {
      String fileName = "log_" + sessionBegin;
      currentFile = new File("kelp_logs", fileName + ".log");
      kelpFileUtils.createIfNotExists(currentFile);
      FileUtils.copyFile(LATEST_FILE, currentFile);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Writes the given lines into the given file
   * and automatically puts a prefix in front of every message.
   *
   * @param file The file you want to save the messages to.
   * @param level The level of your log message.
   *              -> Which significance does it have
   *              for the operations of your plugin.?
   *    * @param messages The actual messages you want to insert into the file.
   * @param messages The actual messages you want to insert into the file.
   */
  private void writeToLogFile(File file, LogLevel level, String... messages) {
    try {

      // load the file in append mode which means that
      // the content will not be cleared when it's opened.
      FileWriter fileWriter = new FileWriter(file, true);
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

      // iterate all messages and write them into the file line by line.
      for (String message : messages) {
        bufferedWriter.write(this.generatePrefix() + " [" + level + "]: " + message);

        // move the cursor to a new line
        bufferedWriter.newLine();
      }

      // close the writers and save the file
      bufferedWriter.close();
      fileWriter.close();

    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Writes the given messages into the latest log file.
   *
   * @param level The level of your log message.
   *              -> Which significance does it have
   *              for the operations of your plugin.?
   * @param messages The actual messages you want to insert into the file.
   */
  private void writeToLatestLog(LogLevel level, String... messages) {
    // TODO: maybe add an asynchronous type here...
    this.writeToLogFile(LATEST_FILE, level, messages);
  }

  /**
   * Generates a prefix which can be sent in front of every written log message.
   * This prefix contains the time format so that you know when the action occurred.
   *
   * @return The final prefix including symbols and formatting
   */
  private String generatePrefix() {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
    return "[" + simpleDateFormat.format(DATE) + "]";
  }

}