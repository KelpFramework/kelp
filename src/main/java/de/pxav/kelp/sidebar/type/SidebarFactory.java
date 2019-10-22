package de.pxav.kelp.sidebar.type;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.logger.KelpLogger;
import de.pxav.kelp.sidebar.component.SidebarComponentFactory;
import de.pxav.kelp.sidebar.version.VersionedSidebar;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A class description goes here.
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
