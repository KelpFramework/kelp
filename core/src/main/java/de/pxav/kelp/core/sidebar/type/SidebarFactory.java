package de.pxav.kelp.core.sidebar.type;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.sidebar.component.SidebarComponentFactory;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.sidebar.version.SidebarUpdaterVersionTemplate;
import de.pxav.kelp.core.sidebar.version.SidebarVersionTemplate;
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
  private SidebarVersionTemplate sidebarVersionTemplate;
  private SidebarComponentFactory sidebarComponentFactory;
  private SidebarUpdaterVersionTemplate updaterVersionTemplate;

  @Inject
  public SidebarFactory(KelpLogger logger,
                        JavaPlugin javaPlugin,
                        SidebarVersionTemplate sidebarVersionTemplate,
                        SidebarComponentFactory sidebarComponentFactory,
                        SidebarUpdaterVersionTemplate updaterVersionTemplate) {
    this.logger = logger;
    this.javaPlugin = javaPlugin;
    this.sidebarVersionTemplate = sidebarVersionTemplate;
    this.sidebarComponentFactory = sidebarComponentFactory;
    this.updaterVersionTemplate = updaterVersionTemplate;
  }

  public SimpleSidebar newSimpleSidebar() {
    return new SimpleSidebar(sidebarVersionTemplate, updaterVersionTemplate);
  }

  public AnimatedSidebarOld newAnimatedSidebar() {
    return new AnimatedSidebarOld(sidebarVersionTemplate);
  }

}
