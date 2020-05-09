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
  private KelpNpcRepository kelpNpcRepository;

  @Inject
  public KelpNpcFactory(NpcVersionTemplate npcVersionTemplate, KelpNpcRepository kelpNpcRepository) {
    this.npcVersionTemplate = npcVersionTemplate;
    this.kelpNpcRepository = kelpNpcRepository;
  }

  public KelpNpc newKelpNpc() {
    return new KelpNpc(npcVersionTemplate, kelpNpcRepository);
  }

}
