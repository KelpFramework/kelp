package de.pxav.kelp.core.entity.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.entity.KelpEntity;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@KelpVersionTemplate
public abstract class EntityVersionTemplate {

  public abstract void spawnEntity(KelpEntity entity);

}
