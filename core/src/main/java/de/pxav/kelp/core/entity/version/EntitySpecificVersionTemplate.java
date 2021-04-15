package de.pxav.kelp.core.entity.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.entity.type.general.KelpProjectile;
import de.pxav.kelp.core.entity.util.PaintingType;
import org.bukkit.Art;

@KelpVersionTemplate
public abstract class EntitySpecificVersionTemplate {

  public abstract PaintingType getPaintingType(Art art);

  public abstract Art getBukkitPaintingType(PaintingType paintingType);

  public abstract <T extends KelpProjectile<?>> T launchProjectile();

}
