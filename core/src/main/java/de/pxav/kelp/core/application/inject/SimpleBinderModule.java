package de.pxav.kelp.core.application.inject;

import com.google.inject.AbstractModule;
import de.pxav.kelp.core.KelpPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * This is the default module for the Guice injector.
 * It binds things like the {@code ClassLoader} or the plugin
 * instance to the corresponding instances.
 *
 * @author pxav
 */
public final class SimpleBinderModule extends AbstractModule {

  // instance of the current plugin
  private final KelpPlugin plugin;

  public SimpleBinderModule(KelpPlugin plugin) {
    this.plugin = plugin;
  }

  /**
   * Configures the {@code AbstractModel}
   * @see AbstractModule
   */
  @Override
  protected void configure() {
    bind(ExecutorService.class).toInstance(Executors.newCachedThreadPool());
    bind(ScheduledExecutorService.class).toInstance(Executors.newScheduledThreadPool(1));
    bind(JavaPlugin.class).toInstance(this.plugin);
    bind(ClassLoader.class).toInstance(KelpPlugin.class.getClassLoader());
  }

}
