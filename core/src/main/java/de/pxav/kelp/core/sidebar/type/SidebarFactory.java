package de.pxav.kelp.core.sidebar.type;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.sidebar.version.SidebarUpdaterVersionTemplate;
import de.pxav.kelp.core.sidebar.version.SidebarVersionTemplate;

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

  private SidebarVersionTemplate sidebarVersionTemplate;
  private SidebarUpdaterVersionTemplate updaterVersionTemplate;

  @Inject
  public SidebarFactory(SidebarVersionTemplate sidebarVersionTemplate,
                        SidebarUpdaterVersionTemplate updaterVersionTemplate) {
    this.sidebarVersionTemplate = sidebarVersionTemplate;
    this.updaterVersionTemplate = updaterVersionTemplate;
  }

  public SimpleSidebar newSimpleSidebar() {
    return new SimpleSidebar(sidebarVersionTemplate, updaterVersionTemplate);
  }

  public AnimatedSidebar newAnimatedSidebar() {
    return new AnimatedSidebar(sidebarVersionTemplate);
  }

}
