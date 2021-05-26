package de.pxav.kelp.core.logger;

import com.google.common.collect.Maps;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.application.KelpApplication;

import java.util.Map;
import java.util.logging.*;

/**
 * A utility class to log messages from plugins. This uses the
 * java logging library in the background and therefore aligns
 * seamlessly with bukkit's logging framework. The logs sent
 * via this class are displayed in the console and written to
 * the server logs just as normal bukkit messages.
 *
 * You can retrieve a logger instance for your plugin using
 * {@code KelpLogger.of(YourPluginMainClass.class)} and then
 * use all normal logging methods provided by the {@link Logger}
 * class.
 *
 * When sending logs, please consider which levels you assign
 * to your messages. Not every message might be logged depending
 * on whether debug mode is enabled in the config.
 * {@link Level#INFO}, {@link Level#WARNING}, {@link Level#SEVERE}
 * are always logged, while {@link Level#CONFIG}, {@link Level#FINE},
 * {@link Level#FINER}, and {@link Level#FINEST} are only logged
 * if developer mode is enabled.
 *
 * @author pxav
 */
public class KelpLogger extends Logger {

  // whether development mode is enabled in the kelp configuration
  private static boolean debugMode = true;

  // saves the logger instances for the individual plugin classes
  private static Map<Class<? extends KelpApplication>, Logger> loggers = Maps.newHashMap();

  /**
   * Gets the logger instance of the given {@link KelpApplication application}.
   *
   *
   * @param pluginClass The main class of the plugin/application you want to get
   *                    the logger of. If you want to get the logger of the {@code core module},
   *                    pass {@code KelpApplication.class} here. If you want to
   * @return The instance of the requested logger.
   */
  public static Logger of(Class<? extends KelpApplication> pluginClass) {
    if (pluginClass.getName().equalsIgnoreCase(KelpApplication.class.getName())) {
      return loggers.get(KelpApplication.class);
    }

    return loggers.get(pluginClass);
  }

  /**
   * Registers a new logger for the given {@code KelpApplication}. This method is called
   * for all applications before they are loaded, so that you can also use the logger in
   * the {@link KelpApplication#onLoad() load method} of your plugin.
   *
   * This method should only be used internally by Kelp and not by application developers.
   *
   * @param pluginClass   The main class of the plugin/application you want to register the logger for.
   * @param loggerName    The name of this logger. It is displayed in front of every message
   *                      sent by this logger. By convention this should be the name of the plugin
   *                      contained by the {@link de.pxav.kelp.core.application.NewKelpApplication plugin annotation}.
   */
  public static void registerLogger(Class<? extends KelpApplication> pluginClass, String loggerName) {
    if (pluginClass.getName().equalsIgnoreCase(KelpApplication.class.getName())) {
      loggers.put(pluginClass, new KelpLogger("KELP"));
      return;
    }

    loggers.put(pluginClass, new KelpLogger(loggerName));
  }

  /**
   * Enables or disables the debug mode. The debug mode is usually configured
   * in the default configuration of Kelp and determines if log messages with
   * a debug level shall be logged. For more information on debug levels, check
   * the class documentation.
   *
   * @param debugModeEnabled {@code true} if debug mode should be enabled.
   */
  public static void setDebugMode(boolean debugModeEnabled) {
    debugMode = debugModeEnabled;
  }

  /**
   * Constructs a new {@code KelpLogger} instance with the given name.
   *
   * When creating a logger with this constructor, the level is automatically
   * configured based on the value provided by {@link #setDebugMode(boolean)},
   * so make sure to set this value before registering any loggers.
   *
   * @param name The name of the new logger. By Kelp-convention, this should
   *             be the name of the application this logger is used for or
   *             'Kelp' if it is the logger for the core module. If the logger
   *             is used for a
   */
  protected KelpLogger(String name) {
    super(name, null);

    if (debugMode) {
      setLevel(Level.ALL);
    } else {
      setLevel(Level.INFO);
    }

    setParent(KelpPlugin.getPlugin(KelpPlugin.class).getServer().getLogger());
  }

  /**
   * This method overwrites the default log method of java's
   * logger to be able to influence the logger format without
   * having to inject a separate formatting handler. It inserts
   * the prefix of the plugin, which sends the log message and then
   * executes the {@link Logger#log(LogRecord)} just as normal, so
   * that Java can check for the level, etc.
   *
   * @param record The record to log.
   */
  @Override
  public void log(LogRecord record) {
    record.setMessage("[" + record.getLoggerName() + "] " + record.getMessage());
    super.log(record);
  }

}