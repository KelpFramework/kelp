package de.pxav.kelp.core.npc;

import com.google.inject.Inject;
import de.pxav.kelp.core.npc.version.NpcVersionTemplate;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpNpcFactory {

  private NpcVersionTemplate npcVersionTemplate;

  @Inject
  public KelpNpcFactory(NpcVersionTemplate npcVersionTemplate) {
    this.npcVersionTemplate = npcVersionTemplate;
  }

  public KelpNpc newKelpNpc() {
    return new KelpNpc(npcVersionTemplate);
  }

}
