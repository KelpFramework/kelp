package de.pxav.kelp.core.npc.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.npc.KelpNpc;
import org.bukkit.entity.Player;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class NpcVersionTemplate {

  public abstract void spawnNpc(KelpNpc npc, Player player);

}
