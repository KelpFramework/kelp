package de.pxav.kelp.application;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import de.pxav.kelp.KelpPlugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class SimpleBinderModule extends AbstractModule {

  private final KelpPlugin plugin;

  public SimpleBinderModule(KelpPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  protected void configure() {
    bind(ExecutorService.class).toInstance(Executors.newCachedThreadPool());
    bind(JavaPlugin.class).toInstance(this.plugin);
    bind(ClassLoader.class).toInstance(KelpPlugin.class.getClassLoader());
  }

  public Injector createInjector() {
    return Guice.createInjector(this);
  }

}
