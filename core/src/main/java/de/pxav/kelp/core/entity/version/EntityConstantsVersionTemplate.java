package de.pxav.kelp.core.entity.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.entity.type.RabbitEntity;
import de.pxav.kelp.core.entity.type.general.KelpProjectile;
import de.pxav.kelp.core.entity.util.*;
import org.bukkit.Art;
import org.bukkit.entity.Villager;

@KelpVersionTemplate
public abstract class EntityConstantsVersionTemplate {

  public abstract PaintingType getPaintingType(Art art);

  public abstract Art getBukkitPaintingType(PaintingType paintingType);

  public abstract VillagerProfession getVillagerProfession(String bukkitProfession);

  public abstract String getVillagerProfession(VillagerProfession villagerType);

  public abstract VillagerType getVillagerType(String bukkitType);

  public abstract String getVillagerType(VillagerType villagerType);

  public abstract CatType getCatType(String bukkitCatType);

  public abstract String getCatType(CatType catType);

  public abstract String getHorseColor(HorseColor horseColor);

  public abstract HorseColor getHorseColor(String horseColor);

  public abstract String getHorseStyle(HorseStyle horseStyle);

  public abstract HorseStyle getHorseStyle(String horseStyle);

  public abstract String getRabbitType(RabbitType rabbitType);

  public abstract RabbitType getRabbitType(String rabbitType);

  public abstract <T extends KelpProjectile<?>> T launchProjectile();

}
