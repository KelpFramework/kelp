package de.pxav.kelp.core.npc;

import com.google.inject.Inject;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.npc.version.NpcVersionTemplate;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpNpcFactory {

  private NpcVersionTemplate npcVersionTemplate;
  private KelpNpcRepository kelpNpcRepository;
  private KelpLogger logger;

  @Inject
  public KelpNpcFactory(NpcVersionTemplate npcVersionTemplate,
                        KelpNpcRepository kelpNpcRepository,
                        KelpLogger logger) {
    this.npcVersionTemplate = npcVersionTemplate;
    this.kelpNpcRepository = kelpNpcRepository;
    this.logger = logger;
  }

  public KelpNpc newKelpNpc() {
    return new KelpNpc(npcVersionTemplate, kelpNpcRepository, logger);
  }

}
