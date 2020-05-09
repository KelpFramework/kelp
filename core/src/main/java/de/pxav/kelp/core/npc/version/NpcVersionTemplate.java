package de.pxav.kelp.core.npc.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.npc.KelpNpc;
import de.pxav.kelp.core.npc.KelpNpcMeta;
import org.bukkit.entity.Player;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class NpcVersionTemplate {

  public abstract KelpNpcMeta spawnNpc(KelpNpc npc, Player player);

  public abstract void deSpawn(KelpNpc npc, Player player);

  public abstract void refresh(KelpNpc npc, Player player);

}
