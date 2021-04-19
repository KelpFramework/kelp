package de.pxav.kelp.core.entity.type;

import de.pxav.kelp.core.entity.type.general.BreedableAnimalEntity;
import de.pxav.kelp.core.world.KelpLocation;
import org.bukkit.Location;

public interface BeeEntity extends BreedableAnimalEntity<BeeEntity> {

  KelpLocation getHive();

  void setHive(KelpLocation hive);

  KelpLocation getFlower();

  void setFlower(KelpLocation flower);

  boolean hasNectar();

  void setHasNectar(boolean hasNectar);

  boolean hasStung();

  void setHasStung(boolean hasStung);

  int getAnger();

  void setAnger(int anger);

  int getCannotEnterHiveTicks();

  void setCannotEnterHiveTicks(int cannotEnterHiveTicks);

}
