package de.pxav.kelp.core.logger;

import com.google.common.collect.Maps;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.application.KelpApplication;

import java.util.Map;
import java.util.logging.*;

/**
 *
 * debug:
 * config, fine, finer, finest
 *
 * normal:
 * severe, warning, info
 *
 * @author pxav
 */
public class KelpLogger extends Logger {

  private static final String KELP_LOGGER_NAME = "KELP";
  private static Map<Class<? extends KelpApplication>, Logger> loggers = Maps.newHashMap();

  public static Logger of(Class<? extends KelpApplication> pluginClass) {
    if (pluginClass.getName().equalsIgnoreCase(KelpApplication.class.getName())) {
      return loggers.get(KelpApplication.class);
    }

    return loggers.get(pluginClass);
  }

  public static void registerLogger(Class<? extends KelpApplication> pluginClass, String loggerName) {
    if (pluginClass.getName().equalsIgnoreCase(KelpApplication.class.getName())) {
      loggers.put(pluginClass, new KelpLogger(KELP_LOGGER_NAME));
      return;
    }

    loggers.put(pluginClass, new KelpLogger(loggerName));
  }

  public KelpLogger() {
    super("NAME", null);
  }

  protected KelpLogger(String name) {
    super(name, null);

    // todo only log all if debug mode is enabled!
    setLevel(Level.ALL);
    setParent(KelpPlugin.getPlugin(KelpPlugin.class).getServer().getLogger());
  }

  @Override
  public void log(LogRecord record) {
    record.setMessage("[" + record.getLoggerName() + "] " + record.getMessage());
    super.log(record);
  }

  public void log(Level level, String... message) {
    for (String s : message) {
      log(level, s);
    }
  }

  public void log(LogLevel level, String message) {
    log(level.toJavaLogLevel(), message);
  }

  public void log(String message) {
    log(Level.INFO, message);
  }

  public void log(LogLevel level, String... message) {
    for (String s : message) {
      log(level.toJavaLogLevel(), s);
    }
  }

  public void log(String... message) {
    for (String s : message) {
      log(Level.INFO, s);
    }
  }

}