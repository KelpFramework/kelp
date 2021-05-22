package de.pxav.kelp.implementation1_8.entity;

import com.google.inject.Inject;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.entity.util.CatType;
import de.pxav.kelp.core.entity.util.potion.PotionVersionTemplate;
import de.pxav.kelp.core.entity.version.EntityConstantsVersionTemplate;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.metadata.ItemMetadataVersionTemplate;
import de.pxav.kelp.core.inventory.version.InventoryVersionTemplate;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.particle.version.ParticleVersionTemplate;
import de.pxav.kelp.core.sound.SoundRepository;
import de.pxav.kelp.core.version.Versioned;
import de.pxav.kelp.implementation1_8.entity.type.*;
import de.pxav.kelp.implementation1_8.player.BossBarLocationUpdater;
import de.pxav.kelp.implementation1_8.player.VersionedKelpPlayer;
import net.minecraft.server.v1_8_R3.*;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHorse;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftOcelot;
import org.bukkit.entity.*;
import org.bukkit.entity.Item;
import org.bukkit.entity.minecart.*;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedEntityType extends EntityTypeVersionTemplate {

  private EntityConstantsVersionTemplate entityConstantsVersionTemplate;
  private InventoryVersionTemplate inventoryVersionTemplate;
  private PotionVersionTemplate potionVersionTemplate;
  private FixedItemFrameListener fixedItemFrameListener;
  private ItemMetadataVersionTemplate itemMetadataVersionTemplate;
  private BossBarLocationUpdater bossBarLocationUpdater;
  private SoundRepository soundRepository;
  private ParticleVersionTemplate particleVersionTemplate;
  private JavaPlugin javaPlugin;
  private KelpLogger logger;

  @Inject
  public VersionedEntityType(EntityConstantsVersionTemplate entityConstantsVersionTemplate,
                             InventoryVersionTemplate inventoryVersionTemplate,
                             PotionVersionTemplate potionVersionTemplate,
                             FixedItemFrameListener fixedItemFrameListener,
                             ItemMetadataVersionTemplate itemMetadataVersionTemplate,
                             BossBarLocationUpdater bossBarLocationUpdater,
                             SoundRepository soundRepository,
                             ParticleVersionTemplate particleVersionTemplate,
                             JavaPlugin javaPlugin,
                             KelpLogger logger) {
    this.entityConstantsVersionTemplate = entityConstantsVersionTemplate;
    this.inventoryVersionTemplate = inventoryVersionTemplate;
    this.potionVersionTemplate = potionVersionTemplate;
    this.fixedItemFrameListener = fixedItemFrameListener;
    this.itemMetadataVersionTemplate = itemMetadataVersionTemplate;
    this.bossBarLocationUpdater = bossBarLocationUpdater;
    this.soundRepository = soundRepository;
    this.particleVersionTemplate = particleVersionTemplate;
    this.javaPlugin = javaPlugin;
    this.logger = logger;
  }

  @Override
  public KelpEntity<?> newKelpEntity(KelpEntityType entityType, Location location) {
    return getOrCreateEntity(entityType, location, null);
  }

  @Override
  public KelpEntity<?> getKelpEntity(org.bukkit.entity.Entity bukkitEntity) {
    Entity nmsEntity = ((CraftEntity) bukkitEntity).getHandle();
    return getOrCreateEntity(KelpEntityType.UNKNOWN, bukkitEntity.getLocation(), nmsEntity);
  }

  /**
   * Creates a new kelp entity instance based on either the given kelp entity type or the nms
   * entity you pass. This basically allows you to convert a bukkit entity into a kelp entity
   * or create a new entity without spawning it yet.
   *
   * @param entityType The {@link KelpEntityType kelp entity type} of the entity you want to create.
   *                   If you don't want to create a new one, but convert an existing, pass either
   *                   {@link KelpEntityType#UNKNOWN} or {@code null} here.
   * @param location   The location of the entity you want to convert/create. This may never be null!
   * @param nmsEntity  The NMS instance of the entity you want to convert. If you just want to create
   *                   an entity, pass {@code null} here.
   * @return The {@link KelpEntity kelp entity} instance of the newly created or converted entity.
   */
  private KelpEntity<?> getOrCreateEntity(KelpEntityType entityType, Location location, Object nmsEntity) {

    // whether a new entity should be created. If false, an existing bukkit entity
    // should be converted to a kelp entity.
    boolean create = false;

    if (nmsEntity == null) {
      create = true;
    } else if (!(nmsEntity instanceof Entity)) {
      //todo log error!
    }

    // avoid null pointers when type is not passed correctly
    if (entityType == null) {
      entityType = KelpEntityType.UNKNOWN;
    }

    Entity entity = nmsEntity == null ? null : (Entity) nmsEntity;
    CraftWorld craftWorld = (CraftWorld) location.getWorld();
    KelpEntity<?> output = null;

    if (entityType == KelpEntityType.PLAYER || entity instanceof EntityPlayer) {
      output = new VersionedKelpPlayer(entity, entityType, location, this, logger, bossBarLocationUpdater, soundRepository, particleVersionTemplate, javaPlugin);
    } else if (entityType == KelpEntityType.DROPPED_ITEM || entity instanceof EntityItem) {
      if (create) {
        entity = craftWorld.createEntity(location, Item.class);
      }
      output = new VersionedItem(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.EXPERIENCE_ORB || entity instanceof EntityExperienceOrb) {
      if (create) {
        entity = craftWorld.createEntity(location, ExperienceOrb.class);
      }
      output = new VersionedExperienceOrb(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.ELDER_GUARDIAN || (entity instanceof EntityGuardian && ((EntityGuardian)entity).isElder())) {
      if (create) {
        entity = craftWorld.createEntity(location, Guardian.class);
      }
      output = new VersionedElderGuardian(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.WITHER_SKELETON || (entity instanceof EntitySkeleton && ((EntitySkeleton)entity).getSkeletonType() == 1)) {
      if (create) {
        entity = craftWorld.createEntity(location, Skeleton.class);
      }
      output = new VersionedWitherSkeleton(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.CHICKEN_EGG || entity instanceof EntityEgg) {
      if (create) {
        entity = craftWorld.createEntity(location, Egg.class);
      }
      output = new VersionedThrownChickenEgg(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.LEASH_HITCH || entity instanceof EntityLeash) {
      if (create) {
        entity = craftWorld.createEntity(location, LeashHitch.class);
      }
      output = new VersionedLeashHitch(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.PAINTING || entity instanceof EntityPainting) {
      if (create) {
        entity = craftWorld.createEntity(location, Painting.class);
      }
      output = new VersionedPainting(entity, entityType, location, this, entityConstantsVersionTemplate);
    } else if (entityType == KelpEntityType.ARROW || entity instanceof EntityArrow) {
      if (create) {
        entity = craftWorld.createEntity(location, Arrow.class);
      }
      output = new VersionedArrow(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.SNOWBALL || entity instanceof EntitySnowball) {
      if (create) {
        entity = craftWorld.createEntity(location, Snowball.class);
      }
      output = new VersionedThrownSnowball(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.FIREBALL
      || (entity instanceof EntityFireball && !(entity instanceof EntitySmallFireball) && !(entity instanceof EntityWitherSkull))) {
      if (create) {
        entity = craftWorld.createEntity(location, Fireball.class);
      }
      output = new VersionedThrownFireball(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.SMALL_FIREBALL || entity instanceof EntitySmallFireball) {
      if (create) {
        entity = craftWorld.createEntity(location, SmallFireball.class);
      }
      output = new VersionedSmallFireball(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.ENDER_PEARL || entity instanceof EntityEnderPearl) {
      if (create) {
        entity = craftWorld.createEntity(location, EnderPearl.class);
      }
      output = new VersionedThrownEnderPearl(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.ENDER_SIGNAL || entity instanceof EntityEnderSignal) {
      if (create) {
        entity = craftWorld.createEntity(location, EnderSignal.class);
      }
      output = new VersionedEnderSignal(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.SPLASH_POTION || entity instanceof EntityPotion) {
      if (create) {
        entity = craftWorld.createEntity(location, ThrownPotion.class);
      }
      output = new VersionedThrownPotion(entity, entityType, location, this, potionVersionTemplate);
    } else if (entityType == KelpEntityType.THROWN_EXP_BOTTLE || entity instanceof EntityThrownExpBottle) {
      if (create) {
        entity = craftWorld.createEntity(location, ThrownExpBottle.class);
      }
      output = new VersionedThrownExpBottle(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.ITEM_FRAME || entity instanceof EntityItemFrame) {
      if (create) {
        entity = craftWorld.createEntity(location, ItemFrame.class);
      }
      output = new VersionedItemFrame(entity, entityType, location, this, fixedItemFrameListener);
    } else if (entityType == KelpEntityType.WITHER_SKULL || entity instanceof EntityWitherSkull) {
      if (create) {
        entity = craftWorld.createEntity(location, WitherSkull.class);
      }
      output = new VersionedThrownWitherSkull(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.PRIMED_TNT || entity instanceof EntityTNTPrimed) {
      if (create) {
        entity = craftWorld.createEntity(location, TNTPrimed.class);
      }
      output = new VersionedPrimedTnt(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.FALLING_BLOCK || entity instanceof EntityFallingBlock) {
      if (create) {
        entity = craftWorld.createEntity(location, FallingBlock.class);
      }
      output = new VersionedFallingBlock(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.FIREWORK || entity instanceof EntityFireworks) {
      if (create) {
        entity = craftWorld.createEntity(location, Egg.class);
      }
      output = new VersionedFirework(entity, entityType, location, this, itemMetadataVersionTemplate);
    } else if (entityType == KelpEntityType.ZOMBIE_VILLAGER || (entity instanceof EntityZombie && ((EntityZombie)entity).isVillager())) {
      if (create) {
        entity = craftWorld.createEntity(location, Zombie.class);
      }
      output = new VersionedZombieVillager(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.SKELETON_HORSE) {
      entity = craftWorld.createEntity(location, Horse.class);
      output = new VersionedSkeletonHorse(entity, entityType, location, this, inventoryVersionTemplate);
    } else if (entityType == KelpEntityType.ZOMBIE_HORSE) {
      entity = craftWorld.createEntity(location, Horse.class);
      output = new VersionedZombieHorse(entity, entityType, location, this, inventoryVersionTemplate);
    } else if (entityType == KelpEntityType.ARMOR_STAND || entity instanceof EntityArmorStand) {
      if (create) {
        entity = craftWorld.createEntity(location, ArmorStand.class);
      }
      output = new VersionedArmorStandEntity(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.MULE) {
      entity = craftWorld.createEntity(location, Horse.class);
      output = new VersionedMule(entity, entityType, location, this, inventoryVersionTemplate);
    } else if (entityType == KelpEntityType.DONKEY) {
      entity = craftWorld.createEntity(location, Horse.class);
      output = new VersionedDonkey(entity, entityType, location, this, inventoryVersionTemplate);
    } else if (entityType == KelpEntityType.SHEEP || entity instanceof EntitySheep) {
      if (create) {
        entity = craftWorld.createEntity(location, Sheep.class);
      }
      output = new VersionedSheep(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.MINECART_COMMAND || entity instanceof EntityMinecartCommandBlock) {
      if (create) {
        entity = craftWorld.createEntity(location, CommandMinecart.class);
      }
      output = new VersionedCommandMinecartEntity(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.BOAT || entity instanceof EntityBoat) {
      if (create) {
        entity = craftWorld.createEntity(location, Boat.class);
      }
      output = new VersionedBoat(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.MINECART || entity instanceof EntityMinecartRideable) {
      if (create) {
        entity = craftWorld.createEntity(location, RideableMinecart.class);
      }
      output = new VersionedRideableMinecart(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.MINECART_CHEST || entity instanceof EntityMinecartChest) {
      if (create) {
        entity = craftWorld.createEntity(location, org.bukkit.entity.minecart.StorageMinecart.class);
      }
      output = new VersionedStorageMinecart(entity, entityType, location, this, inventoryVersionTemplate);
    } else if (entityType == KelpEntityType.MINECART_FURNACE || entity instanceof EntityMinecartFurnace) {
      if (create) {
        entity = craftWorld.createEntity(location, org.bukkit.entity.minecart.PoweredMinecart.class);
      }
      output = new VersionedPoweredMinecart(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.MINECART_HOPPER || entity instanceof EntityMinecartHopper) {
      if (create) {
        entity = craftWorld.createEntity(location, HopperMinecart.class);
      }
      output = new VersionedHopperMinecart(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.MINECART_MOB_SPAWNER || entity instanceof EntityMinecartMobSpawner) {
      if (create) {
        entity = craftWorld.createEntity(location, SpawnerMinecart.class);
      }
      output = new VersionedSpawnerMinecart(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.MINECART_TNT || entity instanceof EntityMinecartTNT) {
      if (create) {
        entity = craftWorld.createEntity(location, ExplosiveMinecart.class);
      }
      output = new VersionedExplosiveMinecart(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.CREEPER || entity instanceof EntityCreeper) {
      if (create) {
        entity = craftWorld.createEntity(location, Creeper.class);
      }
      output = new VersionedCreeper(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.SKELETON || entity instanceof EntitySkeleton) {
      if (create) {
        entity = craftWorld.createEntity(location, Skeleton.class);
      }
      output = new VersionedSkeleton(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.SPIDER || (entity instanceof EntitySpider && !(entity instanceof EntityCaveSpider))) {
      if (create) {
        entity = craftWorld.createEntity(location, Spider.class);
      }
      output = new VersionedSpider(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.ZOMBIE_GIANT || entity instanceof EntityGiantZombie) {
      if (create) {
        entity = craftWorld.createEntity(location, Giant.class);
      }
      output = new VersionedZombieGiant(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.SLIME || (entity instanceof EntitySlime && !(entity instanceof EntityMagmaCube))) {
      if (create) {
        entity = craftWorld.createEntity(location, Slime.class);
      }
      output = new VersionedSlime(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.GHAST || entity instanceof EntityGhast) {
      if (create) {
        entity = craftWorld.createEntity(location, Ghast.class);
      }
      output = new VersionedGhast(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.PIG_ZOMBIE || entity instanceof EntityPigZombie) {
      if (create) {
        entity = craftWorld.createEntity(location, PigZombie.class);
      }
      output = new VersionedPigZombie(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.ENDERMAN || entity instanceof EntityEnderman) {
      if (create) {
        entity = craftWorld.createEntity(location, Enderman.class);
      }
      output = new VersionedEnderMan(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.CAVE_SPIDER || entity instanceof EntityCaveSpider) {
      if (create) {
        entity = craftWorld.createEntity(location, CaveSpider.class);
      }
      output = new VersionedCaveSpider(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.SILVERFISH || entity instanceof EntitySilverfish) {
      if (create) {
        entity = craftWorld.createEntity(location, Silverfish.class);
      }
      output = new VersionedSilverfish(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.BLAZE || entity instanceof EntityBlaze) {
      if (create) {
        entity = craftWorld.createEntity(location, Blaze.class);
      }
      output = new VersionedBlazeEntity(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.MAGMA_CUBE || entity instanceof EntityMagmaCube) {
      if (create) {
        entity = craftWorld.createEntity(location, MagmaCube.class);
      }
      output = new VersionedMagmaCube(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.ENDER_DRAGON || entity instanceof EntityEnderDragon) {
      if (create) {
        entity = craftWorld.createEntity(location, EnderDragon.class);
      }
      output = new VersionedEnderDragon(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.WITHER || entity instanceof EntityWither) {
      if (create) {
        entity = craftWorld.createEntity(location, Wither.class);
      }
      output = new VersionedWither(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.BAT || entity instanceof EntityBat) {
      if (create) {
        entity = craftWorld.createEntity(location, Bat.class);
      }
      output = new VersionedBat(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.WITCH || entity instanceof EntityWitch) {
      if (create) {
        entity = craftWorld.createEntity(location, Witch.class);
      }
      output = new VersionedWitch(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.ENDERMITE || entity instanceof EntityEndermite) {
      if (create) {
        entity = craftWorld.createEntity(location, Endermite.class);
      }
      output = new VersionedEndermite(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.GUARDIAN || entity instanceof EntityGuardian) {
      if (create) {
        entity = craftWorld.createEntity(location, Guardian.class);
      }
      output = new VersionedGuardian(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.PIG || entity instanceof EntityPig) {
      if (create) {
        entity = craftWorld.createEntity(location, Pig.class);
      }
      output = new VersionedPig(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.COW || (entity instanceof EntityCow && !(entity instanceof EntityMushroomCow))) {
      if (create) {
        entity = craftWorld.createEntity(location, Cow.class);
      }
      output = new VersionedCow(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.CHICKEN || entity instanceof EntityChicken) {
      if (create) {
        entity = craftWorld.createEntity(location, Chicken.class);
      }
      output = new VersionedChicken(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.SQUID || entity instanceof EntitySquid) {
      if (create) {
        entity = craftWorld.createEntity(location, Squid.class);
      }
      output = new VersionedSquid(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.WOLF || entity instanceof EntityWolf) {
      if (create) {
        entity = craftWorld.createEntity(location, Wolf.class);
      }
      output = new VersionedWolf(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.MUSHROOM_COW || entity instanceof EntityMushroomCow) {
      if (create) {
        entity = craftWorld.createEntity(location, MushroomCow.class);
      }
      output = new VersionedMushroomCow(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.SNOWMAN || entity instanceof EntitySnowman) {
      if (create) {
        entity = craftWorld.createEntity(location, Snowman.class);
      }
      output = new VersionedSnowman(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.OCELOT) {
      entity = craftWorld.createEntity(location, Ocelot.class);
      output = new VersionedOcelot(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.IRON_GOLEM || entity instanceof EntityIronGolem) {
      if (create) {
        entity = craftWorld.createEntity(location, IronGolem.class);
      }
      output = new VersionedIronGolem(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.HORSE || entity instanceof EntityHorse) {
      if (create) {
        entity = craftWorld.createEntity(location, Horse.class);
      }
      output = new VersionedHorse(entity, entityType, location, this, inventoryVersionTemplate, entityConstantsVersionTemplate);
    } else if (entityType == KelpEntityType.RABBIT || entity instanceof EntityRabbit) {
      if (create) {
        entity = craftWorld.createEntity(location, Rabbit.class);
      }
      output = new VersionedRabbit(entity, entityType, location, this, entityConstantsVersionTemplate);
    } else if (entityType == KelpEntityType.VILLAGER || entity instanceof EntityVillager) {
      if (create) {
        entity = craftWorld.createEntity(location, Villager.class);
      }
      output = new VersionedVillager(entity, entityType, location, this, inventoryVersionTemplate, entityConstantsVersionTemplate);
    } else if (entityType == KelpEntityType.ENDER_CRYSTAL || entity instanceof EntityEnderCrystal) {
      if (create) {
        entity = craftWorld.createEntity(location, EnderCrystal.class);
      }
      output = new VersionedEnderCrystal(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.CAT) {
        entity = craftWorld.createEntity(location, Ocelot.class);
      output = new VersionedCat(entity, entityType, location, this, entityConstantsVersionTemplate);
    } else if (entityType == KelpEntityType.FISHING_HOOK || entity instanceof EntityFishingHook) {
      if (create) {
        entity = craftWorld.createEntity(location, FishHook.class);
      }
      output = new VersionedFishHook(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.LIGHTNING || entity instanceof EntityLightning) {
      if (create) {
        entity = craftWorld.createEntity(location, LightningStrike.class);
      }
      output = new VersionedLightning(entity, entityType, location, this);
    } else if (entityType == KelpEntityType.ZOMBIE || entity instanceof EntityZombie) {
      if (create) {
        entity = craftWorld.createEntity(location, Zombie.class);
      }
      output = new VersionedZombie(entity, entityType, location, this);
    }

    //todo make type fetching more efficient

    if (entity instanceof EntityOcelot) {
      CraftOcelot ocelot = (CraftOcelot) entity.getBukkitEntity();
      if (ocelot.getCatType() == Ocelot.Type.WILD_OCELOT) {
        output = new VersionedOcelot(entity, entityType, location, this);
      } else {
        output = new VersionedCat(entity, entityType, location, this, entityConstantsVersionTemplate)
          .setCatType(entityConstantsVersionTemplate.getCatType(ocelot.getCatType().toString()));
      }
    }

    if (entity instanceof EntityHorse) {
      CraftHorse horse = (CraftHorse) entity.getBukkitEntity();
      if (horse.getVariant() == Horse.Variant.DONKEY) {
        output = new VersionedDonkey(entity, entityType, location, this, inventoryVersionTemplate);
      } else if (horse.getVariant() == Horse.Variant.MULE) {
        output = new VersionedMule(entity, entityType, location, this, inventoryVersionTemplate);
      } else if (horse.getVariant() == Horse.Variant.SKELETON_HORSE) {
        output = new VersionedSkeletonHorse(entity, entityType, location, this, inventoryVersionTemplate);
      } else if (horse.getVariant() == Horse.Variant.UNDEAD_HORSE) {
        output = new VersionedZombieHorse(entity, entityType, location, this, inventoryVersionTemplate);
      } else if (horse.getVariant() == Horse.Variant.HORSE) {
        output = new VersionedHorse(entity, entityType, location, this, inventoryVersionTemplate, entityConstantsVersionTemplate);
      }
    }

    if (output instanceof VersionedLivingEntity) {
      System.out.println("is living entity");
      ((VersionedLivingEntity<?>)output).setPotionVersionTemplate(potionVersionTemplate);
    }

    return output;
  }

}
