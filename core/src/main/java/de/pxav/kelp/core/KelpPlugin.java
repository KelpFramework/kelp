package de.pxav.kelp.core;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import de.pxav.kelp.core.application.KelpApplicationRepository;
import de.pxav.kelp.core.application.inject.VersionBinderModule;
import de.pxav.kelp.core.command.KelpCommandRepository;
import de.pxav.kelp.core.configuration.ConfigurationRepository;
import de.pxav.kelp.core.configuration.internal.KelpDefaultConfiguration;
import de.pxav.kelp.core.inventory.KelpInventoryRepository;
import de.pxav.kelp.core.listener.EventRegistration;
import de.pxav.kelp.core.npc.KelpNpcRepository;
import de.pxav.kelp.core.sidebar.SidebarRepository;
import de.pxav.kelp.core.application.inject.SimpleBinderModule;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.sound.SoundVersionTemplate;
import de.pxav.kelp.core.version.KelpVersion;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.plugin.Description;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.io.File;
import java.util.logging.Level;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Plugin(name = "Kelp", version = "0.0.3-SNAPSHOT")
@Author("pxav")
@Description("A cross version spigot framework.")
@Singleton
public class KelpPlugin extends JavaPlugin {

  private static Injector injector;

  @Override
  public void onLoad() {
    getLogger().log(Level.INFO, "[BINDER] Using default bukkit logger until Kelp-Logger is available.");
    getLogger().log(Level.INFO, "[BINDER] Loading Kelp-Binder...");

    SimpleBinderModule simpleBinderModule = new SimpleBinderModule(this);
    VersionBinderModule versionBinderModule = new VersionBinderModule(this, new File(Bukkit.getWorldContainer(),"kelp_versions"));
    injector = Guice.createInjector(simpleBinderModule, versionBinderModule);
    injector.injectMembers(this);

    logger().log("[BINDER] Successfully injected all version implementations and members. ");
    logger().log("[BINDER] Using normal Kelp-Logger now.");

    injector.getInstance(ConfigurationRepository.class).loadAll(this.getClass().getPackage().getName());
    injector.getInstance(KelpLogger.class).loadLoggerFiles();

    logger().log("[CONFIG] Successfully loaded logger files.");
    logger().log("[APP] Detecting KelpApplications.");

    injector.getInstance(KelpApplicationRepository.class)
            .detectKelpApplications(new File(Bukkit.getWorldContainer(), "kelp_plugins"))
            .load()
            .callOnLoad();

    logger().log("[APP] KelpApplications loaded successfully!");
  }

  @Override
  public void onEnable() {
    this.logKelpLogo("Enabling KelpFramework, running version " + this.getDescription().getVersion(),
      "Developed & maintained by pxav and the open-source community with love <3", "");

    if (injector.getInstance(KelpDefaultConfiguration.class).getBooleanValue("development-mode")) {
      logger().log("[GENERAL] Kelp is running in development mode. This allows you to access developer features.",
        "[GENERAL] Messages with LogLevel.DEBUG are logged. If you do not want that, enable production mode in the config file.");
    }

    KelpVersion kelpVersion = KelpVersion.withBukkitVersion(Bukkit.getBukkitVersion());
    logger().log("[VERSION] Checking server environment:");
    if (kelpVersion == null) {
      logger().log(LogLevel.WARNING, "[VERSION] Server is running on " + Bukkit.getVersion() + " which is currently not supported by Kelp.");
      logger().log("[VERSION] We are working on making Kelp compatible with as many versions as possible.");
      logger().log("[VERSION] Check the GitHub Repo (https://github.com/PXAV/kelp) for more information about supported versions.");
      Bukkit.getPluginManager().disablePlugin(this);
    } else {
      logger().log("[VERSION] Server is running on " + Bukkit.getVersion() + " which is equal to Kelp-Version " + kelpVersion);
      if (KelpVersion.versionImplementationExists(kelpVersion)) {
        logger().log("[VERSION] This version is fully supported by Kelp.");
      } else {
        logger().log("[VERSION] This version is supported by Kelp, but there has not been written any version implementation",
          "You will have to wait until you can finally use this version on your server.");
      }
    }

    injector.getInstance(EventRegistration.class).initialize(this.getClass().getPackage().getName());

    injector.getInstance(SidebarRepository.class).loadSidebars(this.getClass().getPackage().getName());
    injector.getInstance(SidebarRepository.class).schedule();

    injector.getInstance(KelpCommandRepository.class).loadCommands(this.getClass().getPackage().getName());

    injector.getInstance(KelpNpcRepository.class).startScheduler();

    injector.getInstance(KelpApplicationRepository.class).enableApplications();

    logger().log("[VERSION] Initializing and enabling version implementation module.");
    injector.getInstance(VersionBinderModule.getMainClass()).init(null, injector);
    injector.getInstance(VersionBinderModule.getMainClass()).onEnable();
    logger().log("[VERSION] Enabled Version implementation!");

    injector.getInstance(SoundVersionTemplate.class).defineDefaults();
    injector.getInstance(KelpInventoryRepository.class).loadMaterials();
    logger().log("[GENERAL] Successfully enabled KelpFramework. Have fun.");

    logger().log("[GENERAL] NOTE: Kelp is still under heavy development and will contain bugs. " +
      "Applications developed with the current version might not work with future releases. Do not use " +
      "Kelp in production environments, but only for testing purposes. Report bugs and issues to https://github.com/PXAV/kelp " +
      "to get them fixed in the next version.");
  }

  @Override
  public void onDisable() {
    injector.getInstance(KelpApplicationRepository.class).disableApplications();

    injector.getInstance(KelpNpcRepository.class).stopScheduler();
    injector.getInstance(SidebarRepository.class).interruptAnimations();

    logger().log("[VERSION] Disabling version implementation module");
    injector.getInstance(VersionBinderModule.getMainClass()).onDisable();

    this.logKelpLogo("Successfully shut down all Kelp services.",
      "Thank you for using Kelp and goodbye!");

    injector.getInstance(KelpLogger.class).archiveLog();
    injector.getInstance(KelpLogger.class).log("[GENERAL] Saved log to archive. Disabling logger service.");
  }

  public static Injector getInjector() {
    return injector;
  }

  private void logKelpLogo(String... additionalLines) {
    injector.getInstance(KelpLogger.class).log(
      " _   __       _    ",
      "| | / /      | |        __  __  ",
      "| |/ /   ___ | | _ __   \\ \\ \\ \\ ",
      "|    \\  / _ \\| || '_ \\   \\ \\ \\ \\",
      "| |\\  \\|  __/| || |_) |  / / / /",
      "\\_| \\_/ \\___||_|| .__/  /_/ /_/ ",
      "                | |            ",
      "                |_|          ",
      ""
    );
    injector.getInstance(KelpLogger.class).log(additionalLines);
  }

  private KelpLogger logger() {
    return injector.getInstance(KelpLogger.class);
  }

}
