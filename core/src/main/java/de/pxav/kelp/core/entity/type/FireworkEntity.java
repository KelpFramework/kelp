package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.KelpProjectile;
import de.pxav.kelp.core.inventory.metadata.FireworkMetadata;
import org.bukkit.entity.Firework;

public interface FireworkEntity extends KelpProjectile<FireworkEntity> {

  FireworkEntity detonate();

  FireworkMetadata getFireworkMetadata();

  FireworkEntity setFireworkMetadata(FireworkMetadata fireworkMetadata);

  boolean isShotAtAngle();

  boolean setShotAtAngle(boolean shotAtAngle);

}
