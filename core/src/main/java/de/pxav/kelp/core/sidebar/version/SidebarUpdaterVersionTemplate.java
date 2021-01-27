package de.pxav.kelp.core.sidebar.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.player.KelpPlayer;

@KelpVersionTemplate
public abstract class SidebarUpdaterVersionTemplate {

  public abstract void updateTitleOnly(String to, KelpPlayer player);

}
