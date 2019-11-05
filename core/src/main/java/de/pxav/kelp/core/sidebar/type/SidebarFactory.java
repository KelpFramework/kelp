package de.pxav.kelp.core.sidebar.type;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.sidebar.component.SidebarComponentFactory;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.sidebar.version.VersionedSidebar;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * This class can be used to easily build
 * sidebar objects of the requested type.
 *
 * The sidebars depend on certain classes and
 * in can be messy if you inject these dependencies
 * each time you create a new sidebar, so this class
 * injects them automatically each time you call
 * one of the {@code #new...} methods.
 *
 * @author pxav
 */
@Singleton
public class SidebarFactory {

  private KelpLogger logger;
  private JavaPlugin javaPlugin;
  private VersionedSidebar versionedSidebar;
  private SidebarComponentFactory sidebarComponentFactory;

  @Inject
  public SidebarFactory(KelpLogger logger,
                        JavaPlugin javaPlugin,
                        VersionedSidebar versionedSidebar,
                        SidebarComponentFactory sidebarComponentFactory) {
    this.logger = logger;
    this.javaPlugin = javaPlugin;
    this.versionedSidebar = versionedSidebar;
    this.sidebarComponentFactory = sidebarComponentFactory;
  }

  public SimpleSidebar newSimpleSidebar() {
    return new SimpleSidebar(logger, javaPlugin, versionedSidebar, sidebarComponentFactory);
  }

  public AnimatedSidebar newAnimatedSidebar() {
    return new AnimatedSidebar(versionedSidebar);
  }

}
