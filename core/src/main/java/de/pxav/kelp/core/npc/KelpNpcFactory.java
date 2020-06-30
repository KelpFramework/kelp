package de.pxav.kelp.core.npc;

import com.google.inject.Inject;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.npc.version.NpcVersionTemplate;

/**
 * This is a simple factory class for {@code KelpNpc}.
 * Always use this class if you need to create a new instance
 * of a {@code KelpNpc}.
 *
 * @see KelpNpc
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

  /**
   * Creates a new instance and automatically injects all required dependencies.
   * @return A new {@code KelpNpc} instance.
   */
  public KelpNpc newKelpNpc() {
    return new KelpNpc(npcVersionTemplate, kelpNpcRepository, logger);
  }

}
