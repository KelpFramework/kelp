package de.pxav.kelp.core.entity.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.entity.type.general.KelpProjectile;
import de.pxav.kelp.core.entity.util.PaintingType;
import de.pxav.kelp.core.entity.util.VillagerProfession;
import de.pxav.kelp.core.entity.util.VillagerType;
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

  public abstract <T extends KelpProjectile<?>> T launchProjectile();

}
