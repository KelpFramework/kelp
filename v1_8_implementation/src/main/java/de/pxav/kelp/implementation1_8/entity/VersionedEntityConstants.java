package de.pxav.kelp.implementation1_8.entity;

import de.pxav.kelp.core.entity.type.general.KelpProjectile;
import de.pxav.kelp.core.entity.util.PaintingType;
import de.pxav.kelp.core.entity.util.VillagerProfession;
import de.pxav.kelp.core.entity.util.VillagerType;
import de.pxav.kelp.core.entity.version.EntityConstantsVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.Art;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;

@Versioned
public class VersionedEntityConstants extends EntityConstantsVersionTemplate {

  @Override
  public PaintingType getPaintingType(Art art) {
    if (art == Art.KEBAB) {
      return PaintingType.KEBAB;
    } else if (art == Art.AZTEC) {
      return PaintingType.AZTEC;
    } else if (art == Art.ALBAN) {
      return PaintingType.ALBANIAN;
    } else if (art == Art.AZTEC2) {
      return PaintingType.AZTEC2;
    } else if (art == Art.BOMB) {
      return PaintingType.BOMB;
    } else if (art == Art.PLANT) {
      return PaintingType.PLANT;
    } else if (art == Art.WASTELAND) {
      return PaintingType.WASTELAND;
    } else if (art == Art.POOL) {
      return PaintingType.POOL;
    } else if (art == Art.COURBET) {
      return PaintingType.COURBET;
    } else if (art == Art.SEA) {
      return PaintingType.SEA;
    } else if (art == Art.SUNSET) {
      return PaintingType.SUNSET;
    } else if (art == Art.CREEBET) {
      return PaintingType.CREEBET;
    } else if (art == Art.WANDERER) {
      return PaintingType.WANDERER;
    } else if (art == Art.GRAHAM) {
      return PaintingType.GRAHAM;
    } else if (art == Art.MATCH) {
      return PaintingType.MATCH;
    } else if (art == Art.BUST) {
      return PaintingType.BUST;
    } else if (art == Art.STAGE) {
      return PaintingType.STAGE;
    } else if (art == Art.VOID) {
      return PaintingType.VOID;
    } else if (art == Art.SKULL_AND_ROSES) {
      return PaintingType.SKULL_AND_ROSES;
    } else if (art == Art.WITHER) {
      return PaintingType.WITHER;
    } else if (art == Art.FIGHTERS) {
      return PaintingType.FIGHTERS;
    } else if (art == Art.POINTER) {
      return PaintingType.POINTER;
    } else if (art == Art.PIGSCENE) {
      return PaintingType.PIGSCENE;
    } else if (art == Art.BURNINGSKULL) {
      return PaintingType.BURNING_SKULL;
    } else if (art == Art.SKELETON) {
      return PaintingType.SKELETON;
    } else if (art == Art.DONKEYKONG) {
      return PaintingType.DONKEY_KONG;
    }
    return PaintingType.ALBANIAN;
  }

  @Override
  public Art getBukkitPaintingType(PaintingType paintingType) {
    if (paintingType == PaintingType.KEBAB) {
      return Art.KEBAB;
    } else if (paintingType == PaintingType.AZTEC) {
      return Art.AZTEC;
    } else if (paintingType == PaintingType.ALBANIAN) {
      return Art.ALBAN;
    } else if (paintingType == PaintingType.AZTEC2) {
      return Art.AZTEC2;
    } else if (paintingType == PaintingType.BOMB) {
      return Art.BOMB;
    } else if (paintingType == PaintingType.PLANT) {
      return Art.PLANT;
    } else if (paintingType == PaintingType.WASTELAND) {
      return Art.WASTELAND;
    } else if (paintingType == PaintingType.POOL) {
      return Art.POOL;
    } else if (paintingType == PaintingType.COURBET) {
      return Art.COURBET;
    } else if (paintingType == PaintingType.SEA) {
      return Art.SEA;
    } else if (paintingType == PaintingType.SUNSET) {
      return Art.SUNSET;
    } else if (paintingType == PaintingType.CREEBET) {
      return Art.CREEBET;
    } else if (paintingType == PaintingType.WANDERER) {
      return Art.WANDERER;
    } else if (paintingType == PaintingType.GRAHAM) {
      return Art.GRAHAM;
    } else if (paintingType == PaintingType.MATCH) {
      return Art.MATCH;
    } else if (paintingType == PaintingType.BUST) {
      return Art.BUST;
    } else if (paintingType == PaintingType.STAGE) {
      return Art.STAGE;
    } else if (paintingType == PaintingType.VOID) {
      return Art.VOID;
    } else if (paintingType == PaintingType.SKULL_AND_ROSES) {
      return Art.SKULL_AND_ROSES;
    } else if (paintingType == PaintingType.WITHER) {
      return Art.WITHER;
    } else if (paintingType == PaintingType.FIGHTERS) {
      return Art.FIGHTERS;
    } else if (paintingType == PaintingType.POINTER) {
      return Art.POINTER;
    } else if (paintingType == PaintingType.PIGSCENE) {
      return Art.PIGSCENE;
    } else if (paintingType == PaintingType.BURNING_SKULL) {
      return Art.BURNINGSKULL;
    } else if (paintingType == PaintingType.SKELETON) {
      return Art.SKELETON;
    } else if (paintingType == PaintingType.DONKEY_KONG) {
      return Art.DONKEYKONG;
    }
    return Art.ALBAN;
  }

  @Override
  public VillagerProfession getVillagerProfession(String bukkitProfession) {
    if (bukkitProfession.equalsIgnoreCase("FARMER")) {
      return VillagerProfession.FARMER;
    } else if (bukkitProfession.equalsIgnoreCase("LIBRARIAN")) {
      return VillagerProfession.LIBRARIAN;
    } if (bukkitProfession.equalsIgnoreCase("PRIEST")) {
      return VillagerProfession.CLERIC;
    } if (bukkitProfession.equalsIgnoreCase("BLACKSMITH")) {
      return VillagerProfession.TOOL_SMITH;
    } if (bukkitProfession.equalsIgnoreCase("BUTCHER")) {
      return VillagerProfession.BUTCHER;
    }
    return VillagerProfession.NONE;
  }

  @Override
  public String getVillagerProfession(VillagerProfession villagerProfession) {
    if (villagerProfession == VillagerProfession.BUTCHER) {
      return "BUTCHER";
    } else if (villagerProfession == VillagerProfession.CLERIC) {
      return "PRIEST";
    } else if (villagerProfession == VillagerProfession.FARMER) {
      return "FARMER";
    } else if (villagerProfession == VillagerProfession.LIBRARIAN) {
      return "LIBRARIAN";
    }
    return "BLACKSMITH";
  }

  @Override
  public VillagerType getVillagerType(String bukkitType) {
    return VillagerType.NONE;
  }

  @Override
  public String getVillagerType(VillagerType villagerType) {
    return "NONE";
  }

  @Override
  public <T extends KelpProjectile<?>> T launchProjectile() {
    return null;
  }

}
