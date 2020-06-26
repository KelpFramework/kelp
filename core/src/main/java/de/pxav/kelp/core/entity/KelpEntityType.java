package de.pxav.kelp.core.entity;

import de.pxav.kelp.core.version.KelpVersion;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public enum  KelpEntityType {

  DROPPED_ITEM(KelpVersion.MC_1_8_0, null),
  EXPERIENCE_ORB(KelpVersion.MC_1_8_0, null),
  AREA_EFFECT_CLOUD(KelpVersion.MC_1_9_0, null),
  ELDER_GUARDIAN(KelpVersion.MC_1_8_0, null),
  WITHER_SKELETON(KelpVersion.MC_1_8_0, null),
  STRAY(KelpVersion.MC_1_10_0, null),
  EGG(KelpVersion.MC_1_8_0, null),
  LEASH_HITCH(KelpVersion.MC_1_8_0, null),
  PAINTING(KelpVersion.MC_1_8_0, null),
  ARROW(KelpVersion.MC_1_8_0, null),
  SNOWBALL(KelpVersion.MC_1_8_0, null),
  FIREBALL(KelpVersion.MC_1_8_0, null),
  SMALL_FIREBALL(KelpVersion.MC_1_8_0, null),
  ENDER_PEARL(KelpVersion.MC_1_8_0, null),
  ENDER_SIGNAL(KelpVersion.MC_1_8_0, null),
  SPLASH_POTION(KelpVersion.MC_1_8_0, null),
  THROWN_EXP_BOTTLE(KelpVersion.MC_1_8_0, null),
  ITEM_FRAME(KelpVersion.MC_1_8_0, null),
  WITHER_SKULL(KelpVersion.MC_1_8_0, null),
  PRIMED_TNT(KelpVersion.MC_1_8_0, null),
  FALLING_BLOCK(KelpVersion.MC_1_8_0, null),
  FIREWORK(KelpVersion.MC_1_8_0, null),
  HUSK(KelpVersion.MC_1_10_0, null),
  SPECTRAL_ARROW(KelpVersion.MC_1_9_0, null),
  SHULKER_BULLET(KelpVersion.MC_1_9_0, null),
  DRAGON_FIREBALL(KelpVersion.MC_1_9_0, null),
  ZOMBIE_VILLAGER(KelpVersion.MC_1_8_0, null),
  SKELETON_HORSE(KelpVersion.MC_1_8_0, null),
  ZOMBIE_HORSE(KelpVersion.MC_1_8_0, null),
  ARMOR_STAND(KelpVersion.MC_1_8_0, null),
  DONKEY(KelpVersion.MC_1_8_0, null),
  MULE(KelpVersion.MC_1_8_0, null),
  EVOKER_FANGS(KelpVersion.MC_1_11_0, null),
  EVOKER(KelpVersion.MC_1_11_0, null),
  VEX(KelpVersion.MC_1_11_0, null),
  VINDICATOR(KelpVersion.MC_1_11_0, null),
  ILLUSIONER(KelpVersion.MC_1_12_0, null),
  MINECART_COMMAND(KelpVersion.MC_1_8_0, null),
  BOAT(KelpVersion.MC_1_8_0, null),
  MINECART(KelpVersion.MC_1_8_0, null),
  MINECART_CHEST(KelpVersion.MC_1_8_0, null),
  MINECART_FURNACE(KelpVersion.MC_1_8_0, null),
  MINECART_TNT(KelpVersion.MC_1_8_0, null),
  MINECART_HOPPER(KelpVersion.MC_1_8_0, null),
  MINECART_MOB_SPAWNER(KelpVersion.MC_1_8_0, null),
  CREEPER(KelpVersion.MC_1_8_0, null),
  SKELETON(KelpVersion.MC_1_8_0, null),
  SPIDER(KelpVersion.MC_1_8_0, null),
  GIANT(KelpVersion.MC_1_8_0, null),
  ZOMBIE(KelpVersion.MC_1_8_0, null),
  SLIME(KelpVersion.MC_1_8_0, null),
  GHAST(KelpVersion.MC_1_8_0, null),
  PIG_ZOMBIE(KelpVersion.MC_1_8_0, null),
  ENDERMAN(KelpVersion.MC_1_8_0, null),
  CAVE_SPIDER(KelpVersion.MC_1_8_0, null),
  SILVERFISH(KelpVersion.MC_1_8_0, null),
  BLAZE(KelpVersion.MC_1_8_0, null),
  MAGMA_CUBE(KelpVersion.MC_1_8_0, null),
  ENDER_DRAGON(KelpVersion.MC_1_8_0, null),
  WITHER(KelpVersion.MC_1_8_0, null),
  BAT(KelpVersion.MC_1_8_0, null),
  WITCH(KelpVersion.MC_1_8_0, null),
  ENDERMITE(KelpVersion.MC_1_8_0, null),
  GUARDIAN(KelpVersion.MC_1_8_0, null),
  SHULKER(KelpVersion.MC_1_9_0, null),
  PIG(KelpVersion.MC_1_8_0, null),
  SHEEP(KelpVersion.MC_1_8_0, null),
  COW(KelpVersion.MC_1_8_0, null),
  CHICKEN(KelpVersion.MC_1_8_0, null),
  SQUID(KelpVersion.MC_1_8_0, null),
  WOLF(KelpVersion.MC_1_8_0, null),
  MUSHROOM_COW(KelpVersion.MC_1_8_0, null),
  SNOWMAN(KelpVersion.MC_1_8_0, null),
  OCELOT(KelpVersion.MC_1_8_0, null),
  IRON_GOLEM(KelpVersion.MC_1_8_0, null),
  HORSE(KelpVersion.MC_1_8_0, null),
  RABBIT(KelpVersion.MC_1_8_0, null),
  POLAR_BEAR(KelpVersion.MC_1_10_0, null),
  LLAMA(KelpVersion.MC_1_11_0, null),
  LLAMA_SPIT(KelpVersion.MC_1_11_0, null),
  PARROT(KelpVersion.MC_1_12_0, null),
  VILLAGER(KelpVersion.MC_1_8_0, null),
  ENDER_CRYSTAL(KelpVersion.MC_1_8_0, null),
  TURTLE(KelpVersion.MC_1_13_0, null),
  PHANTOM(KelpVersion.MC_1_13_0, null),
  TRIDENT(KelpVersion.MC_1_13_0, null),
  COD(KelpVersion.MC_1_13_0, null),
  SALMON(KelpVersion.MC_1_13_0, null),
  PUFFERFISH(KelpVersion.MC_1_13_0, null),
  TROPICAL_FISH(KelpVersion.MC_1_13_0, null),
  DROWNED(KelpVersion.MC_1_13_0, null),
  DOLPHIN(KelpVersion.MC_1_13_0, null),
  CAT(KelpVersion.MC_1_8_0, null),
  PANDA(KelpVersion.MC_1_14_0, null),
  PILLAGER(KelpVersion.MC_1_14_0, null),
  RAVAGER(KelpVersion.MC_1_14_0, null),
  TRADER_LLAMA(KelpVersion.MC_1_14_0, null),
  WANDERING_TRADER(KelpVersion.MC_1_14_0, null),
  FOX(KelpVersion.MC_1_14_0, null),
  FISHING_HOOK(KelpVersion.MC_1_8_0, null),
  LIGHTNING(KelpVersion.MC_1_8_0, null),
  PLAYER(KelpVersion.MC_1_8_0, null),
  UNKNOWN(KelpVersion.MC_1_8_0, null);

  private KelpVersion since;
  private Class<? extends KelpEntity> entityClass;

  KelpEntityType(KelpVersion since, Class<? extends KelpEntity> entityClass) {
    this.since = since;
    this.entityClass = entityClass;
  }

  public KelpVersion since() {
    return since;
  }

  public Class<? extends KelpEntity> getEntityClass() {
    return entityClass;
  }

  public static Collection<KelpEntityType> aboveVersion(KelpVersion version) {
    Collection<KelpEntityType> output = new ArrayList<>();

    if (version == KelpVersion.highestVersion()) {
      return output;
    }

    for (KelpEntityType entityType : KelpEntityType.values()) {
      if (entityType.since() == version) continue;
      if (KelpVersion.higherVersion(version,  entityType.since()) == entityType.since()) {
        output.add(entityType);
      }
    }

    return output;
  }

  public static Collection<KelpEntityType> belowVersion(KelpVersion version) {
    Collection<KelpEntityType> output = new ArrayList<>();

    if (version == KelpVersion.lowestVersion()) {
      return output;
    }

    for (KelpEntityType entityType : KelpEntityType.values()) {
      if (entityType.since() == version) continue;
      if (KelpVersion.lowerVersion(version,  entityType.since()) == entityType.since()) {
        output.add(entityType);
      }
    }

    return output;
  }

  public static Collection<KelpEntityType> matchesVersion(KelpVersion version) {
    Collection<KelpEntityType> output = new ArrayList<>();

    for (KelpEntityType entityType : KelpEntityType.values()) {
      if (entityType.since() == version) {
        output.add(entityType);
      }
    }

    return output;
  }

  public static Collection<KelpEntityType> includedIn(KelpVersion version) {
    Collection<KelpEntityType> output = new ArrayList<>(belowVersion(version));
    output.addAll(matchesVersion(version));
    return output;
  }

}
