package de.pxav.kelp.core.application;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.sidebar.Sidebar18;
import de.pxav.kelp.core.sidebar.version.VersionedSidebar;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class SimpleBinderModule extends AbstractModule {

  private final de.pxav.kelp.core.KelpPlugin plugin;

  public SimpleBinderModule(de.pxav.kelp.core.KelpPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  protected void configure() {
    bind(ExecutorService.class).toInstance(Executors.newCachedThreadPool());
    bind(JavaPlugin.class).toInstance(this.plugin);
    bind(ClassLoader.class).toInstance(KelpPlugin.class.getClassLoader());
    bind(VersionedSidebar.class).toInstance(new Sidebar18(this.plugin));
  }

  public Injector createInjector() {
    return Guice.createInjector(this);
  }

}
