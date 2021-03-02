package de.pxav.kelp.implementation1_8.inventory;

import com.google.inject.Inject;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.material.MaterialRepository;
import de.pxav.kelp.core.inventory.material.MaterialVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.Material;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedMaterial extends MaterialVersionTemplate {

  private MaterialRepository materialRepository;

  @Inject
  public VersionedMaterial(MaterialRepository materialRepository) {
    this.materialRepository = materialRepository;
  }

  @Override
  public void defineDefaults() {
    long startedAt = System.currentTimeMillis();

    // BANNER ITEMS
    materialRepository.addMaterial(KelpMaterial.BLACK_BANNER_ITEM, Material.BANNER.toString());
    materialRepository.addMaterial(KelpMaterial.BLUE_BANNER_ITEM, Material.BANNER.toString() + ":4");
    materialRepository.addMaterial(KelpMaterial.BROWN_BANNER_ITEM, Material.BANNER.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.CYAN_BANNER_ITEM, Material.BANNER.toString() + ":6");
    materialRepository.addMaterial(KelpMaterial.GRAY_BANNER_ITEM, Material.BANNER.toString() + ":8");
    materialRepository.addMaterial(KelpMaterial.GREEN_BANNER_ITEM, Material.BANNER.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.LIGHT_BLUE_BANNER_ITEM, Material.BANNER.toString() + ":12");
    materialRepository.addMaterial(KelpMaterial.LIGHT_GRAY_BANNER_ITEM, Material.BANNER.toString() + ":7");
    materialRepository.addMaterial(KelpMaterial.LIME_BANNER_ITEM, Material.BANNER.toString() + ":10");
    materialRepository.addMaterial(KelpMaterial.MAGENTA_BANNER_ITEM, Material.BANNER.toString() + ":13");
    materialRepository.addMaterial(KelpMaterial.ORANGE_BANNER_ITEM, Material.BANNER.toString() + ":14");
    materialRepository.addMaterial(KelpMaterial.PINK_BANNER_ITEM, Material.BANNER.toString() + ":9");
    materialRepository.addMaterial(KelpMaterial.PURPLE_BANNER_ITEM, Material.BANNER.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.RED_BANNER_ITEM, Material.BANNER.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.WHITE_BANNER_ITEM, Material.BANNER.toString() + ":15");
    materialRepository.addMaterial(KelpMaterial.YELLOW_BANNER_ITEM, Material.BANNER.toString() + ":11");

    // STANDING BANNERS
    materialRepository.addMaterial(KelpMaterial.BLACK_BANNER, Material.STANDING_BANNER.toString());
    materialRepository.addMaterial(KelpMaterial.BLUE_BANNER, Material.STANDING_BANNER.toString() + ":4");
    materialRepository.addMaterial(KelpMaterial.BROWN_BANNER, Material.STANDING_BANNER.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.CYAN_BANNER, Material.STANDING_BANNER.toString() + ":6");
    materialRepository.addMaterial(KelpMaterial.GRAY_BANNER, Material.STANDING_BANNER.toString() + ":8");
    materialRepository.addMaterial(KelpMaterial.GREEN_BANNER, Material.STANDING_BANNER.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.LIGHT_BLUE_BANNER, Material.STANDING_BANNER.toString() + ":12");
    materialRepository.addMaterial(KelpMaterial.LIGHT_GRAY_BANNER, Material.STANDING_BANNER.toString() + ":7");
    materialRepository.addMaterial(KelpMaterial.LIME_BANNER, Material.STANDING_BANNER.toString() + ":10");
    materialRepository.addMaterial(KelpMaterial.MAGENTA_BANNER, Material.STANDING_BANNER.toString() + ":13");
    materialRepository.addMaterial(KelpMaterial.ORANGE_BANNER, Material.STANDING_BANNER.toString() + ":14");
    materialRepository.addMaterial(KelpMaterial.PINK_BANNER, Material.STANDING_BANNER.toString() + ":9");
    materialRepository.addMaterial(KelpMaterial.PURPLE_BANNER, Material.STANDING_BANNER.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.RED_BANNER, Material.STANDING_BANNER.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.WHITE_BANNER, Material.STANDING_BANNER.toString() + ":15");
    materialRepository.addMaterial(KelpMaterial.YELLOW_BANNER, Material.STANDING_BANNER.toString() + ":11");

    // WALL BANNERS
    materialRepository.addMaterial(KelpMaterial.BLACK_WALL_BANNER, Material.WALL_BANNER.toString());
    materialRepository.addMaterial(KelpMaterial.BLUE_WALL_BANNER, Material.WALL_BANNER.toString() + ":4");
    materialRepository.addMaterial(KelpMaterial.BROWN_WALL_BANNER, Material.WALL_BANNER.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.CYAN_WALL_BANNER, Material.WALL_BANNER.toString() + ":6");
    materialRepository.addMaterial(KelpMaterial.GRAY_WALL_BANNER, Material.WALL_BANNER.toString() + ":8");
    materialRepository.addMaterial(KelpMaterial.GREEN_WALL_BANNER, Material.WALL_BANNER.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.LIGHT_BLUE_WALL_BANNER, Material.WALL_BANNER.toString() + ":12");
    materialRepository.addMaterial(KelpMaterial.LIGHT_GRAY_WALL_BANNER, Material.WALL_BANNER.toString() + ":7");
    materialRepository.addMaterial(KelpMaterial.LIME_WALL_BANNER, Material.WALL_BANNER.toString() + ":10");
    materialRepository.addMaterial(KelpMaterial.MAGENTA_WALL_BANNER, Material.WALL_BANNER.toString() + ":13");
    materialRepository.addMaterial(KelpMaterial.ORANGE_WALL_BANNER, Material.WALL_BANNER.toString() + ":14");
    materialRepository.addMaterial(KelpMaterial.PINK_WALL_BANNER, Material.WALL_BANNER.toString() + ":9");
    materialRepository.addMaterial(KelpMaterial.PURPLE_WALL_BANNER, Material.WALL_BANNER.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.RED_WALL_BANNER, Material.WALL_BANNER.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.WHITE_WALL_BANNER, Material.WALL_BANNER.toString() + ":15");
    materialRepository.addMaterial(KelpMaterial.YELLOW_WALL_BANNER, Material.WALL_BANNER.toString() + ":11");

    // BOATS
    materialRepository.addMaterial(KelpMaterial.OAK_BOAT, Material.BOAT.toString());

    // BUTTONS
    materialRepository.addMaterial(KelpMaterial.STONE_BUTTON, Material.STONE_BUTTON.toString());
    materialRepository.addMaterial(KelpMaterial.OAK_BUTTON, Material.WOOD_BUTTON.toString());

    // HARDENED CLAY
    materialRepository.addMaterial(KelpMaterial.BLACK_TERRACOTTA, Material.STAINED_CLAY.toString() + ":15");
    materialRepository.addMaterial(KelpMaterial.BLUE_TERRACOTTA, Material.STAINED_CLAY.toString() + ":11");
    materialRepository.addMaterial(KelpMaterial.BROWN_TERRACOTTA, Material.STAINED_CLAY.toString() + ":12");
    materialRepository.addMaterial(KelpMaterial.CYAN_TERRACOTTA, Material.STAINED_CLAY.toString() + ":9");
    materialRepository.addMaterial(KelpMaterial.GRAY_TERRACOTTA, Material.STAINED_CLAY.toString() + ":7");
    materialRepository.addMaterial(KelpMaterial.GREEN_TERRACOTTA, Material.STAINED_CLAY.toString() + ":13");
    materialRepository.addMaterial(KelpMaterial.LIGHT_BLUE_TERRACOTTA, Material.STAINED_CLAY.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.LIGHT_GRAY_TERRACOTTA, Material.STAINED_CLAY.toString() + ":8");
    materialRepository.addMaterial(KelpMaterial.LIME_TERRACOTTA, Material.STAINED_CLAY.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.MAGENTA_TERRACOTTA, Material.STAINED_CLAY.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.ORANGE_TERRACOTTA, Material.STAINED_CLAY.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.PINK_TERRACOTTA, Material.STAINED_CLAY.toString() + ":6");
    materialRepository.addMaterial(KelpMaterial.PURPLE_TERRACOTTA, Material.STAINED_CLAY.toString() + ":10");
    materialRepository.addMaterial(KelpMaterial.RED_TERRACOTTA, Material.STAINED_CLAY.toString() + ":14");
    materialRepository.addMaterial(KelpMaterial.TERRACOTTA, Material.HARD_CLAY.toString());
    materialRepository.addMaterial(KelpMaterial.WHITE_TERRACOTTA, Material.STAINED_CLAY.toString());
    materialRepository.addMaterial(KelpMaterial.YELLOW_TERRACOTTA, Material.STAINED_CLAY.toString() + ":4");

    // DOORS
    materialRepository.addMaterial(KelpMaterial.ACACIA_DOOR, Material.ACACIA_DOOR.toString());
    materialRepository.addMaterial(KelpMaterial.BIRCH_DOOR, Material.BIRCH_DOOR.toString());
    materialRepository.addMaterial(KelpMaterial.DARK_OAK_DOOR, Material.DARK_OAK_DOOR.toString());
    materialRepository.addMaterial(KelpMaterial.IRON_DOOR, Material.IRON_DOOR.toString());
    materialRepository.addMaterial(KelpMaterial.JUNGLE_DOOR, Material.JUNGLE_DOOR.toString());
    materialRepository.addMaterial(KelpMaterial.OAK_DOOR, Material.WOODEN_DOOR.toString());
    materialRepository.addMaterial(KelpMaterial.SPRUCE_DOOR, Material.SPRUCE_DOOR.toString());

    // FENCES
    materialRepository.addMaterial(KelpMaterial.ACACIA_FENCE, Material.ACACIA_FENCE.toString());
    materialRepository.addMaterial(KelpMaterial.BIRCH_FENCE, Material.BIRCH_FENCE.toString());
    materialRepository.addMaterial(KelpMaterial.DARK_OAK_FENCE, Material.DARK_OAK_FENCE.toString());
    materialRepository.addMaterial(KelpMaterial.JUNGLE_FENCE, Material.JUNGLE_FENCE.toString());
    materialRepository.addMaterial(KelpMaterial.OAK_FENCE, Material.FENCE.toString());
    materialRepository.addMaterial(KelpMaterial.SPRUCE_FENCE, Material.SPRUCE_FENCE.toString());
    materialRepository.addMaterial(KelpMaterial.NETHER_BRICK_FENCE, Material.NETHER_FENCE.toString());

    // FENCE GATE
    materialRepository.addMaterial(KelpMaterial.ACACIA_FENCE_GATE, Material.ACACIA_FENCE_GATE.toString());
    materialRepository.addMaterial(KelpMaterial.BIRCH_FENCE_GATE, Material.BIRCH_FENCE_GATE.toString());
    materialRepository.addMaterial(KelpMaterial.DARK_OAK_FENCE_GATE, Material.DARK_OAK_FENCE_GATE.toString());
    materialRepository.addMaterial(KelpMaterial.JUNGLE_FENCE_GATE, Material.JUNGLE_FENCE_GATE.toString());
    materialRepository.addMaterial(KelpMaterial.OAK_FENCE_GATE, Material.FENCE_GATE.toString());
    materialRepository.addMaterial(KelpMaterial.SPRUCE_FENCE_GATE, Material.SPRUCE_FENCE_GATE.toString());

    // LEAVES
    materialRepository.addMaterial(KelpMaterial.ACACIA_LEAVES, Material.LEAVES_2.toString());
    materialRepository.addMaterial(KelpMaterial.BIRCH_LEAVES, Material.LEAVES.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.DARK_OAK_LEAVES, Material.LEAVES_2.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.JUNGLE_LEAVES, Material.LEAVES.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.OAK_LEAVES, Material.LEAVES.toString());
    materialRepository.addMaterial(KelpMaterial.SPRUCE_LEAVES, Material.LEAVES.toString() + ":1");

    // LOGS
    materialRepository.addMaterial(KelpMaterial.ACACIA_LOG, Material.LOG_2.toString());
    materialRepository.addMaterial(KelpMaterial.BIRCH_LOG, Material.LOG.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.DARK_OAK_LOG, Material.LOG_2.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.JUNGLE_LOG, Material.LOG.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.OAK_LOG, Material.LOG.toString());
    materialRepository.addMaterial(KelpMaterial.SPRUCE_LOG, Material.LOG.toString() + ":1");

    // WOOD PLANKS
    materialRepository.addMaterial(KelpMaterial.ACACIA_PLANKS, Material.WOOD.toString() + ":4");
    materialRepository.addMaterial(KelpMaterial.BIRCH_PLANKS, Material.WOOD.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.DARK_OAK_PLANKS, Material.WOOD.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.JUNGLE_PLANKS, Material.WOOD.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.OAK_PLANKS, Material.WOOD.toString());
    materialRepository.addMaterial(KelpMaterial.SPRUCE_PLANKS, Material.WOOD.toString() + ":1");

    // PRESSURE PLATES
    materialRepository.addMaterial(KelpMaterial.HEAVY_WEIGHTED_PRESSURE_PLATE, Material.GOLD_PLATE.toString());
    materialRepository.addMaterial(KelpMaterial.LIGHT_WEIGHTED_PRESSURE_PLATE, Material.IRON_PLATE.toString());
    materialRepository.addMaterial(KelpMaterial.OAK_PRESSURE_PLATE, Material.WOOD_PLATE.toString());
    materialRepository.addMaterial(KelpMaterial.STONE_PRESSURE_PLATE, Material.STONE_PLATE.toString());

    // SAPLINGS
    materialRepository.addMaterial(KelpMaterial.ACACIA_SAPLING, Material.SAPLING.toString() + ":4");
    materialRepository.addMaterial(KelpMaterial.BIRCH_SAPLING, Material.SAPLING.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.DARK_OAK_SAPLING, Material.SAPLING.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.JUNGLE_SAPLING, Material.SAPLING.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.OAK_SAPLING, Material.SAPLING.toString());
    materialRepository.addMaterial(KelpMaterial.SPRUCE_SAPLING, Material.SAPLING.toString() + ":1");

    // SIGNS
    materialRepository.addMaterial(KelpMaterial.OAK_SIGN_ITEM, Material.SIGN.toString());
    materialRepository.addMaterial(KelpMaterial.OAK_SIGN, Material.SIGN_POST.toString());
    materialRepository.addMaterial(KelpMaterial.OAK_WALL_SIGN, Material.WALL_SIGN.toString());

    // SLAB
    materialRepository.addMaterial(KelpMaterial.ACACIA_SLAB, Material.WOOD_STEP.toString() + ":4");
    materialRepository.addMaterial(KelpMaterial.BIRCH_SLAB, Material.WOOD_STEP.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.BRICK_SLAB, Material.STEP.toString() + ":4");
    materialRepository.addMaterial(KelpMaterial.COBBLESTONE_SLAB, Material.STEP.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.DARK_OAK_SLAB, Material.WOOD_STEP.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.JUNGLE_SLAB, Material.WOOD_STEP.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.NETHER_BRICK_SLAB, Material.STEP.toString() + ":6");
    materialRepository.addMaterial(KelpMaterial.OAK_SLAB, Material.WOOD_STEP.toString());
    materialRepository.addMaterial(KelpMaterial.QUARTZ_SLAB, Material.STEP.toString() + ":7");
    materialRepository.addMaterial(KelpMaterial.RED_SANDSTONE_SLAB, Material.STONE_SLAB2.toString());
    materialRepository.addMaterial(KelpMaterial.SANDSTONE_SLAB, Material.STEP.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.SPRUCE_SLAB, Material.WOOD_STEP.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.STONE_BRICK_SLAB, Material.STEP.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.STONE_SLAB, Material.STEP.toString());

    // STAIRS
    materialRepository.addMaterial(KelpMaterial.ACACIA_STAIRS, Material.ACACIA_STAIRS.toString());
    materialRepository.addMaterial(KelpMaterial.BIRCH_STAIRS, Material.BIRCH_WOOD_STAIRS.toString());
    materialRepository.addMaterial(KelpMaterial.BRICK_STAIRS, Material.BRICK_STAIRS.toString());
    materialRepository.addMaterial(KelpMaterial.COBBLESTONE_STAIRS, Material.COBBLESTONE_STAIRS.toString());
    materialRepository.addMaterial(KelpMaterial.DARK_OAK_STAIRS, Material.DARK_OAK_STAIRS.toString());
    materialRepository.addMaterial(KelpMaterial.JUNGLE_STAIRS, Material.JUNGLE_WOOD_STAIRS.toString());
    materialRepository.addMaterial(KelpMaterial.NETHER_BRICK_STAIRS, Material.NETHER_BRICK_STAIRS.toString());
    materialRepository.addMaterial(KelpMaterial.OAK_STAIRS, Material.WOOD_STAIRS.toString());
    materialRepository.addMaterial(KelpMaterial.QUARTZ_STAIRS, Material.QUARTZ_STAIRS.toString());
    materialRepository.addMaterial(KelpMaterial.RED_SANDSTONE_STAIRS, Material.RED_SANDSTONE_STAIRS.toString());
    materialRepository.addMaterial(KelpMaterial.SANDSTONE_STAIRS, Material.SANDSTONE_STAIRS.toString());
    materialRepository.addMaterial(KelpMaterial.SPRUCE_STAIRS, Material.SPRUCE_WOOD_STAIRS.toString());
    materialRepository.addMaterial(KelpMaterial.STONE_BRICK_STAIRS, Material.SMOOTH_STAIRS.toString());

    // TRAPDOORS
    materialRepository.addMaterial(KelpMaterial.ACTIVATOR_RAIL, Material.ACTIVATOR_RAIL.toString());
    materialRepository.addMaterial(KelpMaterial.DETECTOR_RAIL, Material.DETECTOR_RAIL.toString());
    materialRepository.addMaterial(KelpMaterial.POWERED_RAIL, Material.POWERED_RAIL.toString());
    materialRepository.addMaterial(KelpMaterial.RAIL, Material.RAILS.toString());

    // RAILS
    materialRepository.addMaterial(KelpMaterial.IRON_TRAPDOOR, Material.IRON_TRAPDOOR.toString());

    // SPAWN EGGS
    materialRepository.addMaterial(KelpMaterial.BAT_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":65");
    materialRepository.addMaterial(KelpMaterial.BLAZE_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":61");
    materialRepository.addMaterial(KelpMaterial.CAVE_SPIDER_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":59");
    materialRepository.addMaterial(KelpMaterial.CHICKEN_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":93");
    materialRepository.addMaterial(KelpMaterial.COW_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":92");
    materialRepository.addMaterial(KelpMaterial.CREEPER_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":50");
    materialRepository.addMaterial(KelpMaterial.ENDERMAN_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":58");
    materialRepository.addMaterial(KelpMaterial.ENDERMITE_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":67");
    materialRepository.addMaterial(KelpMaterial.GHAST_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":56");
    materialRepository.addMaterial(KelpMaterial.GUARDIAN_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":68");
    materialRepository.addMaterial(KelpMaterial.HORSE_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":100");
    materialRepository.addMaterial(KelpMaterial.MAGMA_CUBE_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":62");
    materialRepository.addMaterial(KelpMaterial.MOOSHROOM_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":96");
    materialRepository.addMaterial(KelpMaterial.OCELOT_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":98");
    materialRepository.addMaterial(KelpMaterial.PIG_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":90");
    materialRepository.addMaterial(KelpMaterial.RABBIT_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":101");
    materialRepository.addMaterial(KelpMaterial.SHEEP_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":91");
    materialRepository.addMaterial(KelpMaterial.SILVERFISH_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":60");
    materialRepository.addMaterial(KelpMaterial.SKELETON_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":51");
    materialRepository.addMaterial(KelpMaterial.SLIME_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":55");
    materialRepository.addMaterial(KelpMaterial.SPIDER_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":52");
    materialRepository.addMaterial(KelpMaterial.SQUID_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":94");
    materialRepository.addMaterial(KelpMaterial.VILLAGER_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":120");
    materialRepository.addMaterial(KelpMaterial.WOLF_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":95");
    materialRepository.addMaterial(KelpMaterial.WITCH_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":66");
    materialRepository.addMaterial(KelpMaterial.ZOMBIE_PIGMAN_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":57");
    materialRepository.addMaterial(KelpMaterial.ZOMBIE_SPAWN_EGG, Material.MONSTER_EGG.toString() + ":54");

    // WALLS
    materialRepository.addMaterial(KelpMaterial.COBBLESTONE_WALL, Material.COBBLE_WALL.toString());
    materialRepository.addMaterial(KelpMaterial.MOSSY_COBBLESTONE_WALL, Material.COBBLE_WALL.toString() + ":1");

    // HEADS/SKULLS
    materialRepository.addMaterial(KelpMaterial.CREEPER_HEAD, Material.SKULL_ITEM.toString() + ":4");
    materialRepository.addMaterial(KelpMaterial.PLAYER_HEAD, Material.SKULL_ITEM.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.ZOMBIE_HEAD, Material.SKULL_ITEM.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.SKELETON_SKULL, Material.SKULL_ITEM.toString());
    materialRepository.addMaterial(KelpMaterial.WITHER_SKELETON_SKULL, Material.SKULL_ITEM.toString() + ":1");

    // CARPETS
    materialRepository.addMaterial(KelpMaterial.BLACK_CARPET, Material.CARPET.toString() + ":15");
    materialRepository.addMaterial(KelpMaterial.BLUE_CARPET, Material.CARPET.toString() + ":11");
    materialRepository.addMaterial(KelpMaterial.BROWN_CARPET, Material.CARPET.toString() + ":12");
    materialRepository.addMaterial(KelpMaterial.CYAN_CARPET, Material.CARPET.toString() + ":9");
    materialRepository.addMaterial(KelpMaterial.GRAY_CARPET, Material.CARPET.toString() + ":7");
    materialRepository.addMaterial(KelpMaterial.GREEN_CARPET, Material.CARPET.toString() + ":13");
    materialRepository.addMaterial(KelpMaterial.LIGHT_BLUE_CARPET, Material.CARPET.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.LIGHT_GRAY_CARPET, Material.CARPET.toString() + ":8");
    materialRepository.addMaterial(KelpMaterial.LIME_CARPET, Material.CARPET.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.MAGENTA_CARPET, Material.CARPET.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.ORANGE_CARPET, Material.CARPET.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.PINK_CARPET, Material.CARPET.toString() + ":6");
    materialRepository.addMaterial(KelpMaterial.PURPLE_CARPET, Material.CARPET.toString() + ":10");
    materialRepository.addMaterial(KelpMaterial.RED_CARPET, Material.CARPET.toString() + ":14");
    materialRepository.addMaterial(KelpMaterial.WHITE_CARPET, Material.CARPET.toString());
    materialRepository.addMaterial(KelpMaterial.YELLOW_CARPET, Material.CARPET.toString() + ":4");

    // WOOL
    materialRepository.addMaterial(KelpMaterial.BLACK_WOOL, Material.WOOL.toString() + ":15");
    materialRepository.addMaterial(KelpMaterial.BLUE_WOOL, Material.WOOL.toString() + ":11");
    materialRepository.addMaterial(KelpMaterial.BROWN_WOOL, Material.WOOL.toString() + ":12");
    materialRepository.addMaterial(KelpMaterial.CYAN_WOOL, Material.WOOL.toString() + ":9");
    materialRepository.addMaterial(KelpMaterial.GRAY_WOOL, Material.WOOL.toString() + ":7");
    materialRepository.addMaterial(KelpMaterial.GREEN_WOOL, Material.WOOL.toString() + ":13");
    materialRepository.addMaterial(KelpMaterial.LIGHT_BLUE_WOOL, Material.WOOL.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.LIGHT_GRAY_WOOL, Material.WOOL.toString() + ":8");
    materialRepository.addMaterial(KelpMaterial.LIME_WOOL, Material.WOOL.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.MAGENTA_WOOL, Material.WOOL.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.ORANGE_WOOL, Material.WOOL.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.PINK_WOOL, Material.WOOL.toString() + ":6");
    materialRepository.addMaterial(KelpMaterial.PURPLE_WOOL, Material.WOOL.toString() + ":10");
    materialRepository.addMaterial(KelpMaterial.RED_WOOL, Material.WOOL.toString() + ":14");
    materialRepository.addMaterial(KelpMaterial.WHITE_WOOL, Material.WOOL.toString());
    materialRepository.addMaterial(KelpMaterial.YELLOW_WOOL, Material.WOOL.toString() + ":4");

    // BED
    materialRepository.addMaterial(KelpMaterial.RED_BED, Material.BED.toString());

    // DYE
    materialRepository.addMaterial(KelpMaterial.CYAN_DYE, Material.INK_SACK.toString() + ":6");
    materialRepository.addMaterial(KelpMaterial.GRAY_DYE, Material.INK_SACK.toString() + ":8");
    materialRepository.addMaterial(KelpMaterial.GREEN_DYE, Material.INK_SACK.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.LIGHT_BLUE_DYE, Material.INK_SACK.toString() + ":12");
    materialRepository.addMaterial(KelpMaterial.LIGHT_GRAY_DYE, Material.INK_SACK.toString() + ":7");
    materialRepository.addMaterial(KelpMaterial.LIME_DYE, Material.INK_SACK.toString() + ":10");
    materialRepository.addMaterial(KelpMaterial.MAGENTA_DYE, Material.INK_SACK.toString() + ":13");
    materialRepository.addMaterial(KelpMaterial.ORANGE_DYE, Material.INK_SACK.toString() + ":14");
    materialRepository.addMaterial(KelpMaterial.PINK_DYE, Material.INK_SACK.toString() + ":9");
    materialRepository.addMaterial(KelpMaterial.PURPLE_DYE, Material.INK_SACK.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.RED_DYE, Material.INK_SACK.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.YELLOW_DYE, Material.INK_SACK.toString() + ":11");

    // GLASS PANES
    materialRepository.addMaterial(KelpMaterial.GLASS_PANE, Material.THIN_GLASS.toString());
    materialRepository.addMaterial(KelpMaterial.BLACK_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString() + ":15");
    materialRepository.addMaterial(KelpMaterial.BLUE_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString() + ":11");
    materialRepository.addMaterial(KelpMaterial.BROWN_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString() + ":12");
    materialRepository.addMaterial(KelpMaterial.CYAN_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString() + ":9");
    materialRepository.addMaterial(KelpMaterial.GRAY_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString() + ":7");
    materialRepository.addMaterial(KelpMaterial.GREEN_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString() + ":13");
    materialRepository.addMaterial(KelpMaterial.LIGHT_BLUE_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.LIGHT_GRAY_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString() + ":8");
    materialRepository.addMaterial(KelpMaterial.LIME_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.MAGENTA_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.ORANGE_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.PINK_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString() + ":6");
    materialRepository.addMaterial(KelpMaterial.PURPLE_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString() + ":10");
    materialRepository.addMaterial(KelpMaterial.RED_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString() + ":14");
    materialRepository.addMaterial(KelpMaterial.WHITE_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString());
    materialRepository.addMaterial(KelpMaterial.YELLOW_STAINED_GLASS_PANE, Material.STAINED_GLASS_PANE.toString() + ":4");

    // GLASS
    materialRepository.addMaterial(KelpMaterial.GLASS, Material.GLASS.toString());
    materialRepository.addMaterial(KelpMaterial.BLACK_STAINED_GLASS, Material.STAINED_GLASS.toString() + ":15");
    materialRepository.addMaterial(KelpMaterial.BLUE_STAINED_GLASS, Material.STAINED_GLASS.toString() + ":11");
    materialRepository.addMaterial(KelpMaterial.BROWN_STAINED_GLASS, Material.STAINED_GLASS.toString() + ":12");
    materialRepository.addMaterial(KelpMaterial.CYAN_STAINED_GLASS, Material.STAINED_GLASS.toString() + ":9");
    materialRepository.addMaterial(KelpMaterial.GRAY_STAINED_GLASS, Material.STAINED_GLASS.toString() + ":7");
    materialRepository.addMaterial(KelpMaterial.GREEN_STAINED_GLASS, Material.STAINED_GLASS.toString() + ":13");
    materialRepository.addMaterial(KelpMaterial.LIGHT_BLUE_STAINED_GLASS, Material.STAINED_GLASS.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.LIGHT_GRAY_STAINED_GLASS, Material.STAINED_GLASS.toString() + ":8");
    materialRepository.addMaterial(KelpMaterial.LIME_STAINED_GLASS, Material.STAINED_GLASS.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.MAGENTA_STAINED_GLASS, Material.STAINED_GLASS.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.ORANGE_STAINED_GLASS, Material.STAINED_GLASS.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.PINK_STAINED_GLASS, Material.STAINED_GLASS.toString() + ":6");
    materialRepository.addMaterial(KelpMaterial.PURPLE_STAINED_GLASS, Material.STAINED_GLASS.toString() + ":10");
    materialRepository.addMaterial(KelpMaterial.RED_STAINED_GLASS, Material.STAINED_GLASS.toString() + ":14");
    materialRepository.addMaterial(KelpMaterial.WHITE_STAINED_GLASS, Material.STAINED_GLASS.toString());
    materialRepository.addMaterial(KelpMaterial.YELLOW_STAINED_GLASS, Material.STAINED_GLASS.toString() + ":4");

    // SHOVELS
    materialRepository.addMaterial(KelpMaterial.DIAMOND_SHOVEL, Material.DIAMOND_SPADE.toString());
    materialRepository.addMaterial(KelpMaterial.GOLDEN_SHOVEL, Material.GOLD_SPADE.toString());
    materialRepository.addMaterial(KelpMaterial.IRON_SHOVEL, Material.IRON_SPADE.toString());
    materialRepository.addMaterial(KelpMaterial.STONE_SHOVEL, Material.STONE_SPADE.toString());
    materialRepository.addMaterial(KelpMaterial.WOODEN_SHOVEL, Material.WOOD_SPADE.toString());

    // HOES
    materialRepository.addMaterial(KelpMaterial.DIAMOND_HOE, Material.DIAMOND_HOE.toString());
    materialRepository.addMaterial(KelpMaterial.GOLDEN_HOE, Material.GOLD_HOE.toString());
    materialRepository.addMaterial(KelpMaterial.IRON_HOE, Material.IRON_HOE.toString());
    materialRepository.addMaterial(KelpMaterial.STONE_HOE, Material.STONE_HOE.toString());
    materialRepository.addMaterial(KelpMaterial.WOODEN_HOE, Material.WOOD_HOE.toString());

    // AXE
    materialRepository.addMaterial(KelpMaterial.DIAMOND_AXE, Material.DIAMOND_AXE.toString());
    materialRepository.addMaterial(KelpMaterial.GOLDEN_AXE, Material.GOLD_AXE.toString());
    materialRepository.addMaterial(KelpMaterial.IRON_AXE, Material.IRON_AXE.toString());
    materialRepository.addMaterial(KelpMaterial.STONE_AXE, Material.STONE_AXE.toString());
    materialRepository.addMaterial(KelpMaterial.WOODEN_AXE, Material.WOOD_AXE.toString());

    // PICKAXE
    materialRepository.addMaterial(KelpMaterial.DIAMOND_PICKAXE, Material.DIAMOND_PICKAXE.toString());
    materialRepository.addMaterial(KelpMaterial.GOLDEN_PICKAXE, Material.GOLD_PICKAXE.toString());
    materialRepository.addMaterial(KelpMaterial.IRON_PICKAXE, Material.IRON_PICKAXE.toString());
    materialRepository.addMaterial(KelpMaterial.STONE_PICKAXE, Material.STONE_PICKAXE.toString());
    materialRepository.addMaterial(KelpMaterial.WOODEN_PICKAXE, Material.WOOD_PICKAXE.toString());

    // SWORDS
    materialRepository.addMaterial(KelpMaterial.DIAMOND_SWORD, Material.DIAMOND_SWORD.toString());
    materialRepository.addMaterial(KelpMaterial.GOLDEN_SWORD, Material.GOLD_SWORD.toString());
    materialRepository.addMaterial(KelpMaterial.IRON_SWORD, Material.IRON_SWORD.toString());
    materialRepository.addMaterial(KelpMaterial.STONE_SWORD, Material.STONE_SWORD.toString());
    materialRepository.addMaterial(KelpMaterial.WOODEN_SWORD, Material.WOOD_SWORD.toString());

    // MELONS
    materialRepository.addMaterial(KelpMaterial.MELON, Material.MELON_BLOCK.toString());
    materialRepository.addMaterial(KelpMaterial.MELON_SEEDS, Material.MELON_SEEDS.toString());
    materialRepository.addMaterial(KelpMaterial.MELON_SLICE, Material.MELON.toString());
    materialRepository.addMaterial(KelpMaterial.MELON_STEM, Material.MELON_STEM.toString());

    // PUMPKINS
    materialRepository.addMaterial(KelpMaterial.CARVED_PUMPKIN, Material.PUMPKIN.toString());
    materialRepository.addMaterial(KelpMaterial.PUMPKIN_PIE, Material.PUMPKIN_PIE.toString());
    materialRepository.addMaterial(KelpMaterial.PUMPKIN_SEEDS, Material.PUMPKIN_SEEDS.toString());
    materialRepository.addMaterial(KelpMaterial.PUMPKIN_STEM, Material.PUMPKIN_STEM.toString());

    // ARMOR BOOTS
    materialRepository.addMaterial(KelpMaterial.CHAINMAIL_BOOTS, Material.CHAINMAIL_BOOTS.toString());
    materialRepository.addMaterial(KelpMaterial.DIAMOND_BOOTS, Material.DIAMOND_BOOTS.toString());
    materialRepository.addMaterial(KelpMaterial.GOLDEN_BOOTS, Material.GOLD_BOOTS.toString());
    materialRepository.addMaterial(KelpMaterial.IRON_BOOTS, Material.IRON_BOOTS.toString());
    materialRepository.addMaterial(KelpMaterial.LEATHER_BOOTS, Material.LEATHER_BOOTS.toString());

    materialRepository.addMaterial(KelpMaterial.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_CHESTPLATE.toString());
    materialRepository.addMaterial(KelpMaterial.DIAMOND_CHESTPLATE, Material.DIAMOND_CHESTPLATE.toString());
    materialRepository.addMaterial(KelpMaterial.GOLDEN_CHESTPLATE, Material.GOLD_CHESTPLATE.toString());
    materialRepository.addMaterial(KelpMaterial.IRON_CHESTPLATE, Material.IRON_CHESTPLATE.toString());
    materialRepository.addMaterial(KelpMaterial.LEATHER_CHESTPLATE, Material.LEATHER_CHESTPLATE.toString());

    materialRepository.addMaterial(KelpMaterial.CHAINMAIL_HELMET, Material.CHAINMAIL_HELMET.toString());
    materialRepository.addMaterial(KelpMaterial.DIAMOND_HELMET, Material.DIAMOND_HELMET.toString());
    materialRepository.addMaterial(KelpMaterial.GOLDEN_HELMET, Material.GOLD_HELMET.toString());
    materialRepository.addMaterial(KelpMaterial.IRON_HELMET, Material.IRON_HELMET.toString());
    materialRepository.addMaterial(KelpMaterial.LEATHER_HELMET, Material.LEATHER_HELMET.toString());

    materialRepository.addMaterial(KelpMaterial.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_LEGGINGS.toString());
    materialRepository.addMaterial(KelpMaterial.DIAMOND_LEGGINGS, Material.DIAMOND_LEGGINGS.toString());
    materialRepository.addMaterial(KelpMaterial.GOLDEN_LEGGINGS, Material.GOLD_LEGGINGS.toString());
    materialRepository.addMaterial(KelpMaterial.IRON_LEGGINGS, Material.IRON_LEGGINGS.toString());
    materialRepository.addMaterial(KelpMaterial.LEATHER_LEGGINGS, Material.LEATHER_LEGGINGS.toString());

    // OTHER
    materialRepository.addMaterial(KelpMaterial.ACACIA_WOOD, Material.LOG_2.toString() + ":12");
    materialRepository.addMaterial(KelpMaterial.AIR, Material.AIR.toString());
    materialRepository.addMaterial(KelpMaterial.ALLIUM_FLOWER, Material.RED_ROSE.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.ANDESITE, Material.STONE.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.ANVIL, Material.ANVIL.toString());
    materialRepository.addMaterial(KelpMaterial.APPLE, Material.APPLE.toString());
    materialRepository.addMaterial(KelpMaterial.ARMOR_STAND, Material.ARMOR_STAND.toString());
    materialRepository.addMaterial(KelpMaterial.ARROW, Material.ARROW.toString());
    materialRepository.addMaterial(KelpMaterial.AZURE_BLUET, Material.RED_ROSE.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.BAKED_POTATO, Material.BAKED_POTATO.toString());
    materialRepository.addMaterial(KelpMaterial.BARRIER, Material.BARRIER.toString());
    materialRepository.addMaterial(KelpMaterial.BEACON, Material.BEACON.toString());
    materialRepository.addMaterial(KelpMaterial.BEDROCK, Material.BEDROCK.toString());
    materialRepository.addMaterial(KelpMaterial.BEEF, Material.RAW_BEEF.toString());
    materialRepository.addMaterial(KelpMaterial.BIRCH_WOOD, Material.LOG.toString() + ":14");
    materialRepository.addMaterial(KelpMaterial.BLAZE_POWDER, Material.BLAZE_POWDER.toString());
    materialRepository.addMaterial(KelpMaterial.BLAZE_ROD, Material.BLAZE_ROD.toString());
    materialRepository.addMaterial(KelpMaterial.BLUE_ORCHID, Material.RED_ROSE.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.BONE, Material.BONE.toString());
    materialRepository.addMaterial(KelpMaterial.BONE_MEAL, Material.INK_SACK.toString() + ":15");
    materialRepository.addMaterial(KelpMaterial.BOOK, Material.BOOK.toString());
    materialRepository.addMaterial(KelpMaterial.BOOKSHELF, Material.BOOKSHELF.toString());
    materialRepository.addMaterial(KelpMaterial.BOW, Material.BOW.toString());
    materialRepository.addMaterial(KelpMaterial.BOWL, Material.BOWL.toString());
    materialRepository.addMaterial(KelpMaterial.BREAD, Material.BREAD.toString());
    materialRepository.addMaterial(KelpMaterial.BREWING_STAND, Material.BREWING_STAND.toString());
    materialRepository.addMaterial(KelpMaterial.BREWING_STAND_ITEM, Material.BREWING_STAND_ITEM.toString());
    materialRepository.addMaterial(KelpMaterial.BRICK, Material.CLAY_BRICK.toString());
    materialRepository.addMaterial(KelpMaterial.BRICKS, Material.BRICK.toString());
    materialRepository.addMaterial(KelpMaterial.BROWN_MUSHROOM, Material.BROWN_MUSHROOM.toString());
    materialRepository.addMaterial(KelpMaterial.BROWN_MUSHROOM_BLOCK, Material.HUGE_MUSHROOM_1.toString());
    materialRepository.addMaterial(KelpMaterial.BUCKET, Material.BUCKET.toString());
    materialRepository.addMaterial(KelpMaterial.CACTUS, Material.CACTUS.toString());
    materialRepository.addMaterial(KelpMaterial.CAKE, Material.CAKE_BLOCK.toString());
    materialRepository.addMaterial(KelpMaterial.CAKE_ITEM, Material.CAKE.toString());
    materialRepository.addMaterial(KelpMaterial.CARROT, Material.CARROT_ITEM.toString());
    materialRepository.addMaterial(KelpMaterial.CARROTS, Material.CARROT.toString());
    materialRepository.addMaterial(KelpMaterial.CARROT_ON_A_STICK, Material.CARROT_STICK.toString());
    materialRepository.addMaterial(KelpMaterial.CAULDRON, Material.CAULDRON.toString());
    materialRepository.addMaterial(KelpMaterial.CAULDRON_ITEM, Material.CAULDRON_ITEM.toString());
    materialRepository.addMaterial(KelpMaterial.CHARCOAL, Material.COAL.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.CHEST, Material.CHEST.toString());
    materialRepository.addMaterial(KelpMaterial.CHEST_MINECART, Material.STORAGE_MINECART.toString());
    materialRepository.addMaterial(KelpMaterial.CHICKEN, Material.RAW_CHICKEN.toString());
    materialRepository.addMaterial(KelpMaterial.CHISELED_QUARTZ_BLOCK, Material.QUARTZ_BLOCK.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.CHISELED_RED_SANDSTONE, Material.RED_SANDSTONE.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.CHISELED_SANDSTONE, Material.SANDSTONE.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.CHISELED_STONE_BRICKS, Material.SMOOTH_BRICK.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.CLAY, Material.CLAY.toString());
    materialRepository.addMaterial(KelpMaterial.CLAY_BALL, Material.CLAY_BALL.toString());
    materialRepository.addMaterial(KelpMaterial.CLOCK, Material.WATCH.toString());
    materialRepository.addMaterial(KelpMaterial.COAL, Material.COAL.toString());
    materialRepository.addMaterial(KelpMaterial.COAL_BLOCK, Material.COAL_BLOCK.toString());
    materialRepository.addMaterial(KelpMaterial.COAL_ORE, Material.COAL_ORE.toString());
    materialRepository.addMaterial(KelpMaterial.COARSE_DIRT, Material.DIRT.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.COBBLESTONE, Material.COBBLESTONE.toString());
    materialRepository.addMaterial(KelpMaterial.COBWEB, Material.WEB.toString());
    materialRepository.addMaterial(KelpMaterial.COCOA, Material.COCOA.toString());
    materialRepository.addMaterial(KelpMaterial.COCOA_BEANS, Material.INK_SACK.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.COMMAND_BLOCK, Material.COMMAND.toString());
    materialRepository.addMaterial(KelpMaterial.COMMAND_BLOCK_MINECART, Material.COMMAND_MINECART.toString());
    materialRepository.addMaterial(KelpMaterial.COMPARATOR, Material.REDSTONE_COMPARATOR.toString()); // ON AND OFF?!
    materialRepository.addMaterial(KelpMaterial.COMPASS, Material.COMPASS.toString());
    materialRepository.addMaterial(KelpMaterial.COOKED_BEEF, Material.COOKED_BEEF.toString());
    materialRepository.addMaterial(KelpMaterial.COOKED_CHICKEN, Material.COOKED_CHICKEN.toString());
    materialRepository.addMaterial(KelpMaterial.COOKED_MUTTON, Material.COOKED_MUTTON.toString());
    materialRepository.addMaterial(KelpMaterial.COOKED_PORKCHOP, Material.GRILLED_PORK.toString());
    materialRepository.addMaterial(KelpMaterial.COOKED_RABBIT, Material.COOKED_RABBIT.toString());
    materialRepository.addMaterial(KelpMaterial.COOKED_SALMON, Material.COOKED_FISH.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.COOKIE, Material.COOKIE.toString());
    materialRepository.addMaterial(KelpMaterial.CRACKED_STONE_BRICKS, Material.SMOOTH_BRICK.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.CRAFTING_TABLE, Material.WORKBENCH.toString());
    materialRepository.addMaterial(KelpMaterial.CUT_RED_SANDSTONE, Material.RED_SANDSTONE.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.CUT_SANDSTONE, Material.SANDSTONE.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.DAMAGED_ANVIL, Material.ANVIL.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.DANDELION, Material.YELLOW_FLOWER.toString());
    materialRepository.addMaterial(KelpMaterial.DARK_OAK_WOOD, Material.LOG_2.toString() + ":13");
    materialRepository.addMaterial(KelpMaterial.DARK_PRISMARINE, Material.PRISMARINE.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.DAYLIGHT_DETECTOR, Material.DAYLIGHT_DETECTOR.toString()); // INVERTED!!
    materialRepository.addMaterial(KelpMaterial.DEAD_BUSH, Material.DEAD_BUSH.toString());
    materialRepository.addMaterial(KelpMaterial.DIAMOND, Material.DIAMOND.toString());
    materialRepository.addMaterial(KelpMaterial.DIAMOND_BLOCK, Material.DIAMOND_BLOCK.toString());
    materialRepository.addMaterial(KelpMaterial.DIAMOND_HORSE_ARMOR, Material.DIAMOND_BARDING.toString());
    materialRepository.addMaterial(KelpMaterial.DIAMOND_ORE, Material.DIAMOND_ORE.toString());
    materialRepository.addMaterial(KelpMaterial.DIORITE, Material.STONE.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.DIRT, Material.DIRT.toString());
    materialRepository.addMaterial(KelpMaterial.DISPENSER, Material.DISPENSER.toString());
    materialRepository.addMaterial(KelpMaterial.DRAGON_EGG, Material.DRAGON_EGG.toString());
    materialRepository.addMaterial(KelpMaterial.DROPPER, Material.DROPPER.toString());
    materialRepository.addMaterial(KelpMaterial.EGG, Material.EGG.toString());
    materialRepository.addMaterial(KelpMaterial.EMERALD, Material.EMERALD.toString());
    materialRepository.addMaterial(KelpMaterial.EMERALD_BLOCK, Material.EMERALD_BLOCK.toString());
    materialRepository.addMaterial(KelpMaterial.EMERALD_ORE, Material.EMERALD_ORE.toString());
    materialRepository.addMaterial(KelpMaterial.ENCHANTED_BOOK, Material.ENCHANTED_BOOK.toString());
    materialRepository.addMaterial(KelpMaterial.ENCHANTED_GOLDEN_APPLE, Material.GOLDEN_APPLE.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.ENCHANTING_TABLE, Material.ENCHANTMENT_TABLE.toString());
    materialRepository.addMaterial(KelpMaterial.ENDER_CHEST, Material.ENDER_CHEST.toString());
    materialRepository.addMaterial(KelpMaterial.ENDER_EYE, Material.EYE_OF_ENDER.toString());
    materialRepository.addMaterial(KelpMaterial.ENDER_PEARL, Material.ENDER_PEARL.toString());
    materialRepository.addMaterial(KelpMaterial.END_PORTAL, Material.ENDER_PORTAL.toString());
    materialRepository.addMaterial(KelpMaterial.END_PORTAL_FRAME, Material.ENDER_PORTAL_FRAME.toString());
    materialRepository.addMaterial(KelpMaterial.END_STONE, Material.ENDER_STONE.toString());
    materialRepository.addMaterial(KelpMaterial.EXPERIENCE_BOTTLE, Material.EXP_BOTTLE.toString());
    materialRepository.addMaterial(KelpMaterial.FARMLAND, Material.SOIL.toString());
    materialRepository.addMaterial(KelpMaterial.FEATHER, Material.FEATHER.toString());
    materialRepository.addMaterial(KelpMaterial.FERMENTED_SPIDER_EYE, Material.FERMENTED_SPIDER_EYE.toString());
    materialRepository.addMaterial(KelpMaterial.FERN, Material.LONG_GRASS.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.FILLED_MAP, Material.MAP.toString());
    materialRepository.addMaterial(KelpMaterial.FIRE, Material.FIRE.toString());
    materialRepository.addMaterial(KelpMaterial.FIREWORK_ROCKET, Material.FIREWORK.toString());
    materialRepository.addMaterial(KelpMaterial.FIREWORK_STAR, Material.FIREWORK_CHARGE.toString());
    materialRepository.addMaterial(KelpMaterial.FIRE_CHARGE, Material.FIREBALL.toString());
    materialRepository.addMaterial(KelpMaterial.FISHING_ROD, Material.FISHING_ROD.toString());
    materialRepository.addMaterial(KelpMaterial.FLINT, Material.FLINT.toString());
    materialRepository.addMaterial(KelpMaterial.FLINT_AND_STEEL, Material.FLINT_AND_STEEL.toString());
    materialRepository.addMaterial(KelpMaterial.FLOWER_POT, Material.FLOWER_POT.toString());
    materialRepository.addMaterial(KelpMaterial.FLOWER_POT_ITEM, Material.FLOWER_POT_ITEM.toString());
    materialRepository.addMaterial(KelpMaterial.FURNACE, Material.FURNACE.toString()); // BURNING FURNACE!
    materialRepository.addMaterial(KelpMaterial.FURNACE_MINECART, Material.POWERED_MINECART.toString());
    materialRepository.addMaterial(KelpMaterial.GHAST_TEAR, Material.GHAST_TEAR.toString());
    materialRepository.addMaterial(KelpMaterial.GLASS_BOTTLE, Material.GLASS_BOTTLE.toString());
    materialRepository.addMaterial(KelpMaterial.GLISTERING_MELON_SLICE, Material.SPECKLED_MELON.toString());
    materialRepository.addMaterial(KelpMaterial.GLOWSTONE, Material.GLOWSTONE.toString());
    materialRepository.addMaterial(KelpMaterial.GLOWSTONE_DUST, Material.GLOWSTONE_DUST.toString());
    materialRepository.addMaterial(KelpMaterial.GOLDEN_APPLE, Material.GOLDEN_APPLE.toString());
    materialRepository.addMaterial(KelpMaterial.GOLDEN_CARROT, Material.GOLDEN_CARROT.toString());
    materialRepository.addMaterial(KelpMaterial.GOLDEN_HORSE_ARMOR, Material.GOLD_BARDING.toString());
    materialRepository.addMaterial(KelpMaterial.GOLD_BLOCK, Material.GOLD_BLOCK.toString());
    materialRepository.addMaterial(KelpMaterial.GOLD_INGOT, Material.GOLD_INGOT.toString());
    materialRepository.addMaterial(KelpMaterial.GOLD_NUGGET, Material.GOLD_NUGGET.toString());
    materialRepository.addMaterial(KelpMaterial.GOLD_ORE, Material.GOLD_ORE.toString());
    materialRepository.addMaterial(KelpMaterial.GRANITE, Material.STONE.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.GRASS, Material.LONG_GRASS.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.GRASS_BLOCK, Material.GRASS.toString());
    materialRepository.addMaterial(KelpMaterial.GRAVEL, Material.GRAVEL.toString());
    materialRepository.addMaterial(KelpMaterial.GUNPOWDER, Material.SULPHUR.toString());
    materialRepository.addMaterial(KelpMaterial.HAY_BLOCK, Material.HAY_BLOCK.toString());
    materialRepository.addMaterial(KelpMaterial.HOPPER, Material.HOPPER.toString());
    materialRepository.addMaterial(KelpMaterial.HOPPER_MINECART, Material.HOPPER_MINECART.toString());
    materialRepository.addMaterial(KelpMaterial.ICE, Material.ICE.toString());
    materialRepository.addMaterial(KelpMaterial.INFESTED_CHISELED_STONE_BRICKS, Material.MONSTER_EGG.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.INFESTED_COBBLESTONE, Material.MONSTER_EGG.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.INFESTED_CRACKED_STONE_BRICKS, Material.MONSTER_EGG.toString() + ":4");
    materialRepository.addMaterial(KelpMaterial.INFESTED_MOSSY_STONE_BRICKS, Material.MONSTER_EGG.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.INFESTED_STONE, Material.MONSTER_EGG.toString());
    materialRepository.addMaterial(KelpMaterial.INFESTED_STONE_BRICKS, Material.MONSTER_EGG.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.INK_SAC, Material.INK_SACK.toString());
    materialRepository.addMaterial(KelpMaterial.IRON_BARS, Material.IRON_FENCE.toString());
    materialRepository.addMaterial(KelpMaterial.IRON_BLOCK, Material.IRON_BLOCK.toString());
    materialRepository.addMaterial(KelpMaterial.IRON_HORSE_ARMOR, Material.IRON_BARDING.toString());
    materialRepository.addMaterial(KelpMaterial.IRON_INGOT, Material.IRON_INGOT.toString());
    materialRepository.addMaterial(KelpMaterial.IRON_ORE, Material.IRON_ORE.toString());
    materialRepository.addMaterial(KelpMaterial.ITEM_FRAME, Material.ITEM_FRAME.toString());
    materialRepository.addMaterial(KelpMaterial.JACK_O_LANTERN, Material.JACK_O_LANTERN.toString());
    materialRepository.addMaterial(KelpMaterial.JUKEBOX, Material.JUKEBOX.toString());
    materialRepository.addMaterial(KelpMaterial.JUNGLE_WOOD, Material.LOG.toString() + ":15");
    materialRepository.addMaterial(KelpMaterial.LADDER, Material.LADDER.toString());
    materialRepository.addMaterial(KelpMaterial.LAPIS_BLOCK, Material.LAPIS_BLOCK.toString());
    materialRepository.addMaterial(KelpMaterial.LAPIS_LAZULI, Material.INK_SACK.toString() + ":4");
    materialRepository.addMaterial(KelpMaterial.LAPIS_ORE, Material.LAPIS_ORE.toString());
    materialRepository.addMaterial(KelpMaterial.LARGE_FERN_LOWER, Material.DOUBLE_PLANT.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.LAVA, Material.LAVA.toString());
    materialRepository.addMaterial(KelpMaterial.LAVA_BUCKET, Material.LAVA_BUCKET.toString());
    materialRepository.addMaterial(KelpMaterial.LEAD, Material.LEASH.toString());
    materialRepository.addMaterial(KelpMaterial.LEATHER, Material.LEATHER.toString());
    materialRepository.addMaterial(KelpMaterial.LEVER, Material.LEVER.toString());
    materialRepository.addMaterial(KelpMaterial.LILAC, Material.DOUBLE_PLANT.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.LILY_PAD, Material.WATER_LILY.toString());
    materialRepository.addMaterial(KelpMaterial.MAGMA_CREAM, Material.MAGMA_CREAM.toString());
    materialRepository.addMaterial(KelpMaterial.MAP, Material.EMPTY_MAP.toString());
    materialRepository.addMaterial(KelpMaterial.MILK_BUCKET, Material.MILK_BUCKET.toString());
    materialRepository.addMaterial(KelpMaterial.MINECART, Material.MINECART.toString());
    materialRepository.addMaterial(KelpMaterial.MOSSY_COBBLESTONE, Material.MOSSY_COBBLESTONE.toString());
    materialRepository.addMaterial(KelpMaterial.MOSSY_STONE_BRICKS, Material.SMOOTH_BRICK.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.MOVING_PISTON, Material.PISTON_MOVING_PIECE.toString());
    //materialRepository.addMaterial(KelpMaterial.MUSHROOM_STEM, Material.MUSHR.toString() + ":13");
    materialRepository.addMaterial(KelpMaterial.MUSHROOM_STEW, Material.MUSHROOM_SOUP.toString());
    materialRepository.addMaterial(KelpMaterial.MUSIC_DISC_11, Material.RECORD_11.toString());
    materialRepository.addMaterial(KelpMaterial.MUSIC_DISC_13, Material.GOLD_RECORD.toString());
    materialRepository.addMaterial(KelpMaterial.MUSIC_DISC_BLOCKS, Material.RECORD_3.toString());
    materialRepository.addMaterial(KelpMaterial.MUSIC_DISC_CAT, Material.GREEN_RECORD.toString());
    materialRepository.addMaterial(KelpMaterial.MUSIC_DISC_CHIRP, Material.RECORD_4.toString());
    materialRepository.addMaterial(KelpMaterial.MUSIC_DISC_FAR, Material.RECORD_5.toString());
    materialRepository.addMaterial(KelpMaterial.MUSIC_DISC_MALL, Material.RECORD_6.toString());
    materialRepository.addMaterial(KelpMaterial.MUSIC_DISC_MELLOHI, Material.RECORD_7.toString());
    materialRepository.addMaterial(KelpMaterial.MUSIC_DISC_STAL, Material.RECORD_8.toString());
    materialRepository.addMaterial(KelpMaterial.MUSIC_DISC_STRAD, Material.RECORD_9.toString());
    materialRepository.addMaterial(KelpMaterial.MUSIC_DISC_WAIT, Material.RECORD_12.toString());
    materialRepository.addMaterial(KelpMaterial.MUSIC_DISC_WARD, Material.RECORD_10.toString());
    materialRepository.addMaterial(KelpMaterial.MUTTON, Material.MUTTON.toString());
    materialRepository.addMaterial(KelpMaterial.MYCELIUM, Material.MYCEL.toString());
    materialRepository.addMaterial(KelpMaterial.NAME_TAG, Material.NAME_TAG.toString());
    materialRepository.addMaterial(KelpMaterial.NETHERRACK, Material.NETHERRACK.toString());
    materialRepository.addMaterial(KelpMaterial.NETHER_BRICK, Material.NETHER_BRICK_ITEM.toString());
    materialRepository.addMaterial(KelpMaterial.NETHER_BRICKS, Material.NETHER_BRICK.toString());
    materialRepository.addMaterial(KelpMaterial.NETHER_PORTAL, Material.PORTAL.toString());
    materialRepository.addMaterial(KelpMaterial.NETHER_QUARTZ_ORE, Material.QUARTZ_ORE.toString());
    materialRepository.addMaterial(KelpMaterial.NETHER_STAR, Material.NETHER_STAR.toString());
    materialRepository.addMaterial(KelpMaterial.NETHER_WART, Material.NETHER_WARTS.toString());
    materialRepository.addMaterial(KelpMaterial.NOTE_BLOCK, Material.NOTE_BLOCK.toString());
    materialRepository.addMaterial(KelpMaterial.OAK_WOOD, Material.LOG.toString() + ":12");
    materialRepository.addMaterial(KelpMaterial.OBSIDIAN, Material.OBSIDIAN.toString());
    materialRepository.addMaterial(KelpMaterial.ORANGE_TULIP, Material.RED_ROSE.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.OXEYE_DAISY, Material.RED_ROSE.toString() + ":8");
    materialRepository.addMaterial(KelpMaterial.PACKED_ICE, Material.PACKED_ICE.toString());
    materialRepository.addMaterial(KelpMaterial.PAINTING, Material.PAINTING.toString());
    materialRepository.addMaterial(KelpMaterial.PAPER, Material.PAPER.toString());
    materialRepository.addMaterial(KelpMaterial.PEONY, Material.DOUBLE_PLANT.toString() + ":5");
    materialRepository.addMaterial(KelpMaterial.PINK_TULIP, Material.RED_ROSE.toString() + ":7");
    materialRepository.addMaterial(KelpMaterial.PISTON, Material.PISTON_BASE.toString());
    materialRepository.addMaterial(KelpMaterial.PISTON_HEAD, Material.PISTON_EXTENSION.toString());
    materialRepository.addMaterial(KelpMaterial.PODZOL, Material.DIRT.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.POISONOUS_POTATO, Material.POISONOUS_POTATO.toString());
    materialRepository.addMaterial(KelpMaterial.POLISHED_ANDESITE, Material.STONE.toString() + ":6");
    materialRepository.addMaterial(KelpMaterial.POLISHED_DIORITE, Material.STONE.toString() + ":4");
    materialRepository.addMaterial(KelpMaterial.POLISHED_GRANITE, Material.STONE.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.POPPY, Material.RED_ROSE.toString());
    materialRepository.addMaterial(KelpMaterial.PORKCHOP, Material.PORK.toString());
    materialRepository.addMaterial(KelpMaterial.POTATO, Material.POTATO_ITEM.toString());
    materialRepository.addMaterial(KelpMaterial.POTATOES, Material.POTATO.toString());
//    materialRepository.addMaterial(KelpMaterial.POTTED_ALLIUM, Material.LOG_2.toString() + ":13");
//    materialRepository.addMaterial(KelpMaterial.POTTED_AZURE_BLUET, Material.LOG_2.toString() + ":13");
//    materialRepository.addMaterial(KelpMaterial.POTTED_BLUE_ORCHID, Material.LOG_2.toString() + ":13");
//    materialRepository.addMaterial(KelpMaterial.POTTED_BROWN_MUSHROOM, Material.LOG_2.toString() + ":13");
//    materialRepository.addMaterial(KelpMaterial.POTTED_CACTUS, Material.LOG_2.toString() + ":13");
//    materialRepository.addMaterial(KelpMaterial.POTTED_DANDELION, Material.LOG_2.toString() + ":13");
//    materialRepository.addMaterial(KelpMaterial.POTTED_DEAD_BUSH, Material.LOG_2.toString() + ":13");
//    materialRepository.addMaterial(KelpMaterial.POTTED_FERN, Material.LOG_2.toString() + ":13");
//    materialRepository.addMaterial(KelpMaterial.POTTED_ORANGE_TULIP, Material.LOG_2.toString() + ":13");
//    materialRepository.addMaterial(KelpMaterial.POTTED_OXEYE_DAISY, Material.LOG_2.toString() + ":13");
//    materialRepository.addMaterial(KelpMaterial.POTTED_PINK_TULIP, Material.LOG_2.toString() + ":13");
//    materialRepository.addMaterial(KelpMaterial.POTTED_POPPY, Material.LOG_2.toString() + ":13");
//    materialRepository.addMaterial(KelpMaterial.POTTED_RED_MUSHROOM, Material.LOG_2.toString() + ":13");
//    materialRepository.addMaterial(KelpMaterial.POTTED_RED_TULIP, Material.LOG_2.toString() + ":13");
//    materialRepository.addMaterial(KelpMaterial.POTTED_WHITE_TULIP, Material.LOG_2.toString() + ":13");
    materialRepository.addMaterial(KelpMaterial.PRISMARINE, Material.PRISMARINE.toString() + ":13");
    materialRepository.addMaterial(KelpMaterial.PRISMARINE_BRICKS, Material.PRISMARINE.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.PRISMARINE_CRYSTALS, Material.PRISMARINE_CRYSTALS.toString());
    materialRepository.addMaterial(KelpMaterial.PRISMARINE_SHARD, Material.PRISMARINE_SHARD.toString());
    materialRepository.addMaterial(KelpMaterial.PUFFERFISH, Material.RAW_FISH.toString() + ":3");
    materialRepository.addMaterial(KelpMaterial.QUARTZ, Material.QUARTZ.toString());
    materialRepository.addMaterial(KelpMaterial.QUARTZ_BLOCK, Material.QUARTZ_BLOCK.toString());
    materialRepository.addMaterial(KelpMaterial.QUARTZ_PILLAR, Material.QUARTZ_BLOCK.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.RABBIT, Material.RABBIT.toString());
    materialRepository.addMaterial(KelpMaterial.RABBIT_FOOT, Material.RABBIT_FOOT.toString());
    materialRepository.addMaterial(KelpMaterial.RABBIT_HIDE, Material.RABBIT_HIDE.toString());
    materialRepository.addMaterial(KelpMaterial.RABBIT_STEW, Material.RABBIT_STEW.toString());
    materialRepository.addMaterial(KelpMaterial.REDSTONE, Material.REDSTONE.toString());
    materialRepository.addMaterial(KelpMaterial.REDSTONE_BLOCK, Material.REDSTONE_BLOCK.toString());
    materialRepository.addMaterial(KelpMaterial.REDSTONE_LAMP, Material.REDSTONE_LAMP_OFF.toString()); // LAMP ON!
    materialRepository.addMaterial(KelpMaterial.REDSTONE_ORE, Material.REDSTONE_ORE.toString()); // GLOWING!
    materialRepository.addMaterial(KelpMaterial.REDSTONE_TORCH, Material.REDSTONE_TORCH_ON.toString()); // ON/OFF
    materialRepository.addAlias(KelpMaterial.REDSTONE_WALL_TORCH, KelpMaterial.REDSTONE_TORCH);
    materialRepository.addMaterial(KelpMaterial.REDSTONE_WIRE, Material.REDSTONE_WIRE.toString());
    materialRepository.addMaterial(KelpMaterial.RED_MUSHROOM, Material.RED_MUSHROOM.toString());
    materialRepository.addMaterial(KelpMaterial.RED_MUSHROOM_BLOCK, Material.HUGE_MUSHROOM_2.toString());
    materialRepository.addMaterial(KelpMaterial.RED_SAND, Material.SAND.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.RED_SANDSTONE, Material.RED_SANDSTONE.toString());
    materialRepository.addMaterial(KelpMaterial.RED_TULIP, Material.RED_ROSE.toString() + ":4");
    materialRepository.addMaterial(KelpMaterial.REPEATER, Material.DIODE.toString()); // BLOCK ON AND OFF
    materialRepository.addMaterial(KelpMaterial.ROSE_BUSH, Material.DOUBLE_PLANT.toString() + ":4");
    materialRepository.addMaterial(KelpMaterial.ROTTEN_FLESH, Material.ROTTEN_FLESH.toString());
    materialRepository.addMaterial(KelpMaterial.SADDLE, Material.SADDLE.toString());
    materialRepository.addMaterial(KelpMaterial.SALMON, Material.RAW_FISH.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.SAND, Material.SAND.toString());
    materialRepository.addMaterial(KelpMaterial.SANDSTONE, Material.SANDSTONE.toString());
    materialRepository.addMaterial(KelpMaterial.SEA_LANTERN, Material.SEA_LANTERN.toString());
    materialRepository.addMaterial(KelpMaterial.SHEARS, Material.SHEARS.toString());
    materialRepository.addMaterial(KelpMaterial.SLIME_BALL, Material.SLIME_BALL.toString());
    materialRepository.addMaterial(KelpMaterial.SLIME_BLOCK, Material.SLIME_BLOCK.toString());
    //materialRepository.addMaterial(KelpMaterial.SMOOTH_RED_SANDSTONE, Material.RED_SANDSTONE.toString() + ":2"); not available in 1.8
    //materialRepository.addMaterial(KelpMaterial.SMOOTH_SANDSTONE, Material.SANDSTONE.toString() + ":2"); not available yet
    materialRepository.addMaterial(KelpMaterial.SMOOTH_STONE, Material.DOUBLE_STEP.toString() + ":8");
    materialRepository.addMaterial(KelpMaterial.SNOW, Material.SNOW.toString());
    materialRepository.addMaterial(KelpMaterial.SNOWBALL, Material.SNOW_BALL.toString());
    materialRepository.addMaterial(KelpMaterial.SNOW_BLOCK, Material.SNOW_BLOCK.toString());
    materialRepository.addMaterial(KelpMaterial.SOUL_SAND, Material.SOUL_SAND.toString());
    materialRepository.addMaterial(KelpMaterial.SPAWNER, Material.MOB_SPAWNER.toString());
    materialRepository.addMaterial(KelpMaterial.SPIDER_EYE, Material.SPIDER_EYE.toString());
    materialRepository.addMaterial(KelpMaterial.SPONGE, Material.SPONGE.toString());
    materialRepository.addMaterial(KelpMaterial.SPRUCE_WOOD, Material.LOG.toString() + ":13");
    materialRepository.addMaterial(KelpMaterial.STICK, Material.STICK.toString());
    materialRepository.addMaterial(KelpMaterial.STICKY_PISTON, Material.PISTON_STICKY_BASE.toString());
    materialRepository.addMaterial(KelpMaterial.STONE, Material.STONE.toString());
    materialRepository.addMaterial(KelpMaterial.STONE_BRICKS, Material.SMOOTH_BRICK.toString());
    materialRepository.addMaterial(KelpMaterial.STRING, Material.STRING.toString());
    materialRepository.addMaterial(KelpMaterial.SUGAR, Material.SUGAR.toString());
    materialRepository.addMaterial(KelpMaterial.SUGAR_CANE, Material.SUGAR_CANE.toString());
    materialRepository.addMaterial(KelpMaterial.SUGAR_CANE_BLOCK, Material.SUGAR_CANE_BLOCK.toString());
    materialRepository.addMaterial(KelpMaterial.SUNFLOWER, Material.DOUBLE_PLANT.toString());
    materialRepository.addMaterial(KelpMaterial.TALL_GRASS, Material.DOUBLE_PLANT.toString() + ":2");
    materialRepository.addMaterial(KelpMaterial.TNT, Material.TNT.toString());
    materialRepository.addMaterial(KelpMaterial.TNT_MINECART, Material.EXPLOSIVE_MINECART.toString());
    materialRepository.addMaterial(KelpMaterial.TORCH, Material.TORCH.toString());
    materialRepository.addMaterial(KelpMaterial.TRAPPED_CHEST, Material.TRAPPED_CHEST.toString());
    materialRepository.addMaterial(KelpMaterial.TRIPWIRE, Material.TRIPWIRE.toString());
    materialRepository.addMaterial(KelpMaterial.TRIPWIRE_HOOK, Material.TRIPWIRE_HOOK.toString());
    materialRepository.addMaterial(KelpMaterial.VINE, Material.VINE.toString());
    materialRepository.addAlias(KelpMaterial.WALL_TORCH, KelpMaterial.TORCH);
    materialRepository.addMaterial(KelpMaterial.WATER, Material.WATER.toString());
    materialRepository.addMaterial(KelpMaterial.WATER_BUCKET, Material.WATER_BUCKET.toString());
    materialRepository.addMaterial(KelpMaterial.WET_SPONGE, Material.SPONGE.toString() + ":1");
    materialRepository.addMaterial(KelpMaterial.WHEAT, Material.WHEAT.toString());
    materialRepository.addMaterial(KelpMaterial.WHEAT_SEEDS, Material.SEEDS.toString());
    materialRepository.addMaterial(KelpMaterial.WHITE_TULIP, Material.RED_ROSE.toString() + ":6");
    materialRepository.addMaterial(KelpMaterial.WRITABLE_BOOK, Material.BOOK_AND_QUILL.toString());
    materialRepository.addMaterial(KelpMaterial.WRITTEN_BOOK, Material.WRITTEN_BOOK.toString());

    long elapsedTime = System.currentTimeMillis() - startedAt;
    System.out.println("[VERSION-1.8] Successfully defined item names (took " + elapsedTime + "ms)");
  }

}
