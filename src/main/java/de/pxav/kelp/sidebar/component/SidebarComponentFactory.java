package de.pxav.kelp.sidebar.component;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.logger.KelpLogger;
import de.pxav.kelp.sidebar.SidebarUtils;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public final class SidebarComponentFactory {

  private KelpLogger kelpLogger;
  private SidebarUtils sidebarUtils;

  @Inject
  public SidebarComponentFactory(KelpLogger kelpLogger, SidebarUtils sidebarUtils) {
    this.kelpLogger = kelpLogger;
    this.sidebarUtils = sidebarUtils;
  }

  public SimpleTextComponent newSimpleTextComponent() {
    return new SimpleTextComponent(sidebarUtils, kelpLogger);
  }

  public EmptyLineComponent newEmptyLineComponent() {
    return new EmptyLineComponent(sidebarUtils);
  }

  public EmptyLineComponent newEmptyLineComponent(int line) {
    return new EmptyLineComponent(sidebarUtils).score(line);
  }

  public LineSeparatorComponent newLineSeparatorComponent() {
    return new LineSeparatorComponent(sidebarUtils);
  }

}
