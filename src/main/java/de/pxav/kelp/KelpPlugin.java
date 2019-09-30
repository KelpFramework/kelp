package de.pxav.kelp;

import com.google.inject.Injector;
import com.google.inject.Singleton;
import de.pxav.kelp.application.KelpApplicationRepository;
import de.pxav.kelp.application.SimpleBinderModule;
import de.pxav.kelp.configuration.ConfigurationRepository;
import de.pxav.kelp.configuration.internal.KelpDefaultConfiguration;
import de.pxav.kelp.logger.KelpLogger;
import de.pxav.kelp.logger.LogLevel;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.io.File;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Plugin(name = "Kelp", version = "0.1-SNAPSHOT")
@Author("pxav")
@Description("A cross version spigot framework.")
@Singleton
public class KelpPlugin extends JavaPlugin {

  private static Injector injector;

  @Override
  public void onLoad() {
    SimpleBinderModule simpleBinderModule = new SimpleBinderModule(this);
    injector = simpleBinderModule.createInjector();
    injector.injectMembers(this);

    injector.getInstance(ConfigurationRepository.class).loadAll(this.getClass().getPackage().getName());
    injector.getInstance(KelpLogger.class).loadLoggerFiles();

    injector.getInstance(KelpLogger.class).log(
            "Loading Kelp Framework, begin logging...",
            "[OK] Successfully loaded internal configurations!",
            "[OK] Successfully loaded logger files!",
            "Begin loading plugins from /kelp_plugins/..."
    );

    injector.getInstance(KelpApplicationRepository.class)
            .detectGamePlugins(new File(Bukkit.getWorldContainer(), "kelp_plugins"))
            .load()
            .enable();

    injector.getInstance(KelpLogger.class).log(
            "[OK] Plugins loaded successfully!",
            "Loading sequence completed"
    );
  }

  @Override
  public void onEnable() {
    String[] developmentMode = injector.getInstance(KelpDefaultConfiguration.class)
            .getBooleanValue(injector.getInstance(KelpDefaultConfiguration.class)
                    .developmentMode())
            ? new String[] {
                "System is running in development mode.",
                "=> Logging messages with DEBUG level as well."
              }
            : new String[] {
                "System is running in production mode.",
                "=> Messages with DEBUG level are not logged."
              };

    injector.getInstance(KelpLogger.class).log(
            " _   __       _    ",
            "| | / /      | |        __  __  ",
            "| |/ /   ___ | | _ __   \\ \\ \\ \\ ",
            "|    \\  / _ \\| || '_ \\   \\ \\ \\ \\",
            "| |\\  \\|  __/| || |_) |  / / / /",
            "\\_| \\_/ \\___||_|| .__/  /_/ /_/ ",
            "                | |            ",
            "                |_|          ",
            "",
            "Enabling KelpFramework, running version " + this.getDescription().getVersion(),
            "Developed & maintained by pxav with love <3"
    );
    injector.getInstance(KelpLogger.class).log(developmentMode);

    injector.getInstance(KelpLogger.class).log(LogLevel.DEBUG, "debug log");
    injector.getInstance(KelpLogger.class).consoleLog(LogLevel.DEBUG, "debug log");
    injector.getInstance(KelpLogger.class).writeLog(LogLevel.DEBUG, "debug log");

    injector.getInstance(KelpApplicationRepository.class).enablePlugins();
  }

  @Override
  public void onDisable() {
    injector.getInstance(KelpLogger.class).log("Disabling plugins....");
    injector.getInstance(KelpApplicationRepository.class).disablePlugins();
    injector.getInstance(KelpLogger.class).log("[OK] Disabled plugins!");

    injector.getInstance(KelpLogger.class).log(
            " _   __       _    ",
            "| | / /      | |        __  __  ",
            "| |/ /   ___ | | _ __   \\ \\ \\ \\ ",
            "|    \\  / _ \\| || '_ \\   \\ \\ \\ \\",
            "| |\\  \\|  __/| || |_) |  / / / /",
            "\\_| \\_/ \\___||_|| .__/  /_/ /_/ ",
            "                | |            ",
            "                |_|          ",
            "",
            "Successfully shut down Kelp services.",
            "Thanks for using Kelp and goodbye."
    );

    injector.getInstance(KelpLogger.class).archiveLog();
    injector.getInstance(KelpLogger.class).log("Saved log in archive. Disabling logger service.");
  }

  public static Injector getInjector() {
    return injector;
  }

}
