package de.pxav.kelp.core.inventory.material;

import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.KelpServer;
import de.pxav.kelp.core.version.KelpVersion;
import org.bukkit.Material;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This enum represents all materials you can use when writing a
 * kelp application. Note that you can only use materials which
 * are available in your minimum server version.
 *
 * @author pxav
 */
public enum KelpMaterial {

  // BANNERS
  BLACK_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },
  BLUE_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },
  BROWN_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },
  CYAN_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },
  GRAY_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },
  GREEN_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },
  LIGHT_BLUE_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },
  LIGHT_GRAY_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },
  LIME_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },
  MAGENTA_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },
  ORANGE_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },
  PINK_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },
  PURPLE_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },
  RED_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },
  WHITE_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },
  YELLOW_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isItem() { return false; }
  },

  // BANNER ITEMS
  BLACK_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },
  BLUE_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },
  BROWN_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },
  CYAN_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },
  GRAY_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },
  GREEN_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },
  LIGHT_BLUE_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },
  LIGHT_GRAY_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },
  LIME_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },
  MAGENTA_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },
  ORANGE_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },
  PINK_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },
  PURPLE_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },
  RED_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },
  WHITE_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },
  YELLOW_BANNER_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isBanner() { return true; }
    public float getFuelPower() { return 1.5F; }
  },

  // BANNER PATTERNS
  FLOWER_BANNER_PATTERN(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  GLOBE_BANNER_PATTERN(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  CREEPER_BANNER_PATTERN(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  MOJANG_BANNER_PATTERN(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  SKULL_BANNER_PATTERN(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },

  // WALL BANNERS
  BLACK_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },
  BLUE_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },
  BROWN_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },
  CYAN_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },
  GRAY_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },
  GREEN_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },
  LIGHT_BLUE_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },
  LIGHT_GRAY_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },
  LIME_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },
  MAGENTA_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },
  ORANGE_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },
  PINK_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },
  PURPLE_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },
  RED_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },
  WHITE_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },
  YELLOW_WALL_BANNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
  },

  // BOATS
  ACACIA_BOAT(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public boolean isEntity() { return true; }
    public boolean hasGravity() { return true; }
    public int getMaxStackSize() { return 1; }
    public float getFuelPower() { return 0.0F; }
  },
  BIRCH_BOAT(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public boolean isEntity() { return true; }
    public boolean hasGravity() { return true; }
    public int getMaxStackSize() { return 1; }
    public float getFuelPower() { return 0.0F; }
  },
  DARK_OAK_BOAT(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public boolean isEntity() { return true; }
    public boolean hasGravity() { return true; }
    public int getMaxStackSize() { return 1; }
    public float getFuelPower() { return 0.0F; }
  },
  JUNGLE_BOAT(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public boolean isEntity() { return true; }
    public boolean hasGravity() { return true; }
    public int getMaxStackSize() { return 1; }
    public float getFuelPower() { return 0.0F; }
  },
  OAK_BOAT(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isEntity() { return true; }
    public boolean hasGravity() { return true; }
    public int getMaxStackSize() { return 1; }
    public float getFuelPower() { return 0.0F; }
  },
  SPRUCE_BOAT(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public boolean isEntity() { return true; }
    public boolean hasGravity() { return true; }
    public int getMaxStackSize() { return 1; }
    public float getFuelPower() { return 0.0F; }
  },

  // BUTTONS
  ACACIA_BUTTON(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  BIRCH_BUTTON(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  DARK_OAK_BUTTON(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  JUNGLE_BUTTON(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  OAK_BUTTON(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  SPRUCE_BUTTON(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  STONE_BUTTON(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },

  // HARDENED CLAY / TERRACOTTA
  BLACK_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  BLUE_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  BROWN_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  CYAN_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  GRAY_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  GREEN_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  LIGHT_BLUE_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  LIGHT_GRAY_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  LIME_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  MAGENTA_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  ORANGE_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  PINK_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  PURPLE_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  RED_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  WHITE_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },
  YELLOW_TERRACOTTA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.25F; }
    public float getBlastResistance() { return 4.2F; }
    public int getMaxStackSize() { return 64; }
  },

  // GLAZED TERRACOTTA
  BLACK_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },
  BLUE_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },
  BROWN_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },
  CYAN_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },
  GRAY_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },
  GREEN_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },
  LIGHT_BLUE_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },
  LIGHT_GRAY_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },
  LIME_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },
  MAGENTA_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },
  ORANGE_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },
  PINK_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },
  PURPLE_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },
  RED_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },
  WHITE_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },
  YELLOW_GLAZED_TERRACOTTA(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.4F; }
    public float getBlastResistance() { return 1.4F; }
    public int getMaxStackSize() { return 64; }
  },

  // DOORS
  ACACIA_DOOR(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  BIRCH_DOOR(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  DARK_OAK_DOOR(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  IRON_DOOR(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 5.0F; }
    public float getBlastResistance() { return 5.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  JUNGLE_DOOR(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  OAK_DOOR(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  SPRUCE_DOOR(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },

  // FENCES
  ACACIA_FENCE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  BIRCH_FENCE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  DARK_OAK_FENCE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  JUNGLE_FENCE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  OAK_FENCE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  SPRUCE_FENCE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  NETHER_BRICK_FENCE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },

  // FENCE GATES
  ACACIA_FENCE_GATE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  BIRCH_FENCE_GATE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  DARK_OAK_FENCE_GATE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  JUNGLE_FENCE_GATE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  OAK_FENCE_GATE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  SPRUCE_FENCE_GATE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },

  // LEAVES
  ACACIA_LEAVES(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
  },
  BIRCH_LEAVES(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
  },
  DARK_OAK_LEAVES(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
  },
  JUNGLE_LEAVES(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
  },
  OAK_LEAVES(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
  },
  SPRUCE_LEAVES(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
  },

  // STRIPPED LOGS
  STRIPPED_ACACIA_LOG(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  STRIPPED_BIRCH_LOG(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  STRIPPED_DARK_OAK_LOG(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  STRIPPED_JUNGLE_LOG(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  STRIPPED_OAK_LOG(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  STRIPPED_SPRUCE_LOG(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },

  // LOGS
  ACACIA_LOG(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  BIRCH_LOG(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  DARK_OAK_LOG(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  JUNGLE_LOG(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  OAK_LOG(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  SPRUCE_LOG(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },

  // STRIPPED WOOD
  STRIPPED_ACACIA_WOOD(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  STRIPPED_BIRCH_WOOD(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  STRIPPED_DARK_OAK_WOOD(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  STRIPPED_JUNGLE_WOOD(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  STRIPPED_OAK_WOOD(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  STRIPPED_SPRUCE_WOOD(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },

  // PLANKS
  ACACIA_PLANKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  BIRCH_PLANKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  DARK_OAK_PLANKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  JUNGLE_PLANKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  OAK_PLANKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  SPRUCE_PLANKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },

  // CONCRETE POWDER
  BLACK_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  BLUE_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  BROWN_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  CYAN_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  GRAY_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  GREEN_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  LIGHT_BLUE_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  LIGHT_GRAY_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  LIME_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  MAGENTA_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  ORANGE_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  PINK_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  PURPLE_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  RED_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  WHITE_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  YELLOW_CONCRETE_POWDER(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },

  // CONCRETE
  BLACK_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },
  BLUE_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },
  BROWN_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },
  CYAN_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },
  GRAY_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },
  GREEN_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },
  LIGHT_BLUE_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },
  LIGHT_GRAY_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },
  LIME_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },
  MAGENTA_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },
  ORANGE_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },
  PINK_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },
  PURPLE_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },
  RED_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },
  WHITE_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },
  YELLOW_CONCRETE(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 1.8F; }
    public float getBlastResistance() { return 1.8F; }
    public int getMaxStackSize() { return 64; }
  },

  // PRESSURE PLATES
  ACACIA_PRESSURE_PLATE(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  BIRCH_PRESSURE_PLATE(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  DARK_OAK_PRESSURE_PLATE(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  HEAVY_WEIGHTED_PRESSURE_PLATE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  JUNGLE_PRESSURE_PLATE(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  LIGHT_WEIGHTED_PRESSURE_PLATE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  OAK_PRESSURE_PLATE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  SPRUCE_PRESSURE_PLATE(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  STONE_PRESSURE_PLATE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },

  // SAPLINGS
  ACACIA_SAPLING(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  BAMBOO_SAPLING(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  BIRCH_SAPLING(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  DARK_OAK_SAPLING(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  JUNGLE_SAPLING(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  OAK_SAPLING(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  SPRUCE_SAPLING(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },

  // POTTED SAPLING
  POTTED_ACACIA_SAPLING(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_BIRCH_SAPLING(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_DARK_OAK_SAPLING(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_JUNGLE_SAPLING(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_OAK_SAPLING(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_SPRUCE_SAPLING(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },

  // SIGNS
  ACACIA_SIGN(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  BIRCH_SIGN(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  DARK_OAK_SIGN(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  JUNGLE_SIGN(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  OAK_SIGN(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  SPRUCE_SIGN(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 16; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },

  // SIGNS
  ACACIA_SIGN_ITEM(KelpVersion.MC_1_14_0),
  BIRCH_SIGN_ITEM(KelpVersion.MC_1_14_0),
  DARK_OAK_SIGN_ITEM(KelpVersion.MC_1_14_0),
  JUNGLE_SIGN_ITEM(KelpVersion.MC_1_14_0),
  OAK_SIGN_ITEM(KelpVersion.MC_1_8_0),
  SPRUCE_SIGN_ITEM(KelpVersion.MC_1_14_0),

  // WALL SIGNS
  ACACIA_WALL_SIGN(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  BIRCH_WALL_SIGN(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  DARK_OAK_WALL_SIGN(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  JUNGLE_WALL_SIGN(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  OAK_WALL_SIGN(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  SPRUCE_WALL_SIGN(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },

  // SLABS
  ACACIA_SLAB(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  ANDESITE_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BIRCH_SLAB(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  BRICK_SLAB(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  COBBLESTONE_SLAB(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  CUT_RED_SANDSTONE_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  CUT_SANDSTONE_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DARK_OAK_SLAB(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  DARK_PRISMARINE_SLAB(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DIORITE_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  END_STONE_BRICK_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 9.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GRANITE_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  JUNGLE_SLAB(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  MOSSY_COBBLESTONE_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  MOSSY_STONE_BRICK_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  NETHER_BRICK_SLAB(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  OAK_SLAB(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  PETRIFIED_OAK_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  POLISHED_ANDESITE_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  POLISHED_DIORITE_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  POLISHED_GRANITE_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PRISMARINE_BRICK_SLAB(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PRISMARINE_SLAB(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PURPUR_SLAB(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  QUARTZ_SLAB(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  RED_NETHER_BRICK_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  RED_SANDSTONE_SLAB(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SANDSTONE_SLAB(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SMOOTH_QUARTZ_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SMOOTH_RED_SANDSTONE_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SMOOTH_SANDSTONE_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SMOOTH_STONE_SLAB(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SPRUCE_SLAB(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  STONE_BRICK_SLAB(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  STONE_SLAB(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },

  // STAIRS
  ACACIA_STAIRS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  ANDESITE_STAIRS(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  BIRCH_STAIRS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  BRICK_STAIRS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  COBBLESTONE_STAIRS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  DARK_OAK_STAIRS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  DARK_PRISMARINE_STAIRS(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  DIORITE_STAIRS(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  END_STONE_BRICK_STAIRS(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 9.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  GRANITE_STAIRS(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  JUNGLE_STAIRS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  MOSSY_COBBLESTONE_STAIRS(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  MOSSY_STONE_BRICK_STAIRS(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  NETHER_BRICK_STAIRS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  OAK_STAIRS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  POLISHED_ANDESITE_STAIRS(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  POLISHED_DIORITE_STAIRS(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  POLISHED_GRANITE_STAIRS(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  PRISMARINE_BRICK_STAIRS(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  PRISMARINE_STAIRS(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  PURPUR_STAIRS(KelpVersion.MC_1_9_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  QUARTZ_STAIRS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  RED_NETHER_BRICK_STAIRS(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  RED_SANDSTONE_STAIRS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  SANDSTONE_STAIRS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  SMOOTH_QUARTZ_STAIRS(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  SMOOTH_RED_SANDSTONE_STAIRS(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  SMOOTH_SANDSTONE_STAIRS(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  SPRUCE_STAIRS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  STONE_BRICK_STAIRS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  STONE_STAIRS(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },

  // TRAPDOORS
  ACACIA_TRAPDOOR(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  BIRCH_TRAPDOOR(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  DARK_OAK_TRAPDOOR(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  IRON_TRAPDOOR(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 5.0F; }
    public float getBlastResistance() { return 5.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  JUNGLE_TRAPDOOR(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  OAK_TRAPDOOR(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  SPRUCE_TRAPDOOR(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },

  ACTIVATOR_RAIL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.7F; }
    public float getBlastResistance() { return 0.7F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DETECTOR_RAIL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.7F; }
    public float getBlastResistance() { return 0.7F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  POWERED_RAIL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.7F; }
    public float getBlastResistance() { return 0.7F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  RAIL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.7F; }
    public float getBlastResistance() { return 0.7F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },

  // SPAWN EGGS
  BAT_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BEE_SPAWN_EGG(KelpVersion.MC_1_15_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BLAZE_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  CAT_SPAWN_EGG(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  CAVE_SPIDER_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  CHICKEN_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  COD_SPAWN_EGG(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  COW_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  CREEPER_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DOLPHIN_SPAWN_EGG(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DONKEY_SPAWN_EGG(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DROWNED_SPAWN_EGG(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  ELDER_GUARDIAN_SPAWN_EGG(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  ENDERMAN_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  ENDERMITE_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  EVOKER_SPAWN_EGG(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  FOX_SPAWN_EGG(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GHAST_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GUARDIAN_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  HORSE_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  HUSK_SPAWN_EGG(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LLAMA_SPAWN_EGG(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  MAGMA_CUBE_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  MOOSHROOM_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  MULE_SPAWN_EGG(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  OCELOT_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PANDA_SPAWN_EGG(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PARROT_SPAWN_EGG(KelpVersion.MC_1_12_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PHANTOM_SPAWN_EGG(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PIG_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PILLAGER_SPAWN_EGG(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  POLAR_BEAR_SPAWN_EGG(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PUFFERFISH_SPAWN_EGG(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  RABBIT_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  RAVAGER_SPAWN_EGG(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SALMON_SPAWN_EGG(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SHEEP_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SHULKER_SPAWN_EGG(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SILVERFISH_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SKELETON_HORSE_SPAWN_EGG(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SKELETON_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SLIME_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SPIDER_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SQUID_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  STRAY_SPAWN_EGG(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  TRADER_LLAMA_SPAWN_EGG(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  TROPICAL_FISH_SPAWN_EGG(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  TURTLE_SPAWN_EGG(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  VEX_SPAWN_EGG(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  VILLAGER_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  VINDICATOR_SPAWN_EGG(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  WANDERING_TRADER_SPAWN_EGG(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  WOLF_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  WITCH_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  WITHER_SKELETON_SPAWN_EGG(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  ZOMBIE_HORSE_SPAWN_EGG(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  ZOMBIE_PIGMAN_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  ZOMBIE_SPAWN_EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  ZOMBIE_VILLAGER_SPAWN_EGG(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },

  // SHULKER BOXES
  SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  BLACK_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  BLUE_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  BROWN_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  CYAN_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  GRAY_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  GREEN_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  LIGHT_BLUE_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  LIGHT_GRAY_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  LIME_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  MAGENTA_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  ORANGE_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  PINK_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  PURPLE_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  RED_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  WHITE_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  YELLOW_SHULKER_BOX(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },

  // WALLS
  ANDESITE_WALL(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BRICK_WALL(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  COBBLESTONE_WALL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DIORITE_WALL(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  END_STONE_BRICK_WALL(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 9.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GRANITE_WALL(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  MOSSY_COBBLESTONE_WALL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  MOSSY_STONE_BRICK_WALL(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  NETHER_BRICK_WALL(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PRISMARINE_WALL(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  RED_NETHER_BRICK_WALL(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  RED_SANDSTONE_WALL(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SANDSTONE_WALL(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  STONE_BRICK_WALL(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },

  // WALL SKULLS/HEADS
  CREEPER_WALL_HEAD(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  DRAGON_WALL_HEAD(KelpVersion.MC_1_9_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  PLAYER_WALL_HEAD(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  SKELETON_WALL_SKULL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  WITHER_SKELETON_WALL_SKULL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  ZOMBIE_WALL_HEAD(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },

  // SKULLS/HEADS
  CREEPER_HEAD(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DRAGON_HEAD(KelpVersion.MC_1_9_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PLAYER_HEAD(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  ZOMBIE_HEAD(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SKELETON_SKULL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  WITHER_SKELETON_SKULL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },

  // CARPET
  BLACK_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  BLUE_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  BROWN_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  CYAN_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  GRAY_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  GREEN_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  LIGHT_BLUE_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  LIGHT_GRAY_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  LIME_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  MAGENTA_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  ORANGE_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  PINK_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  PURPLE_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  RED_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  WHITE_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  YELLOW_CARPET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },

  // WOOL
  BLACK_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  BLUE_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  BROWN_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  CYAN_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  GRAY_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  GREEN_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  LIGHT_BLUE_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  LIGHT_GRAY_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  LIME_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  MAGENTA_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  ORANGE_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  PINK_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  PURPLE_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  RED_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  WHITE_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  YELLOW_WOOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },

  // BED
  BLACK_BED(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  BLUE_BED(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  BROWN_BED(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  CYAN_BED(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  GRAY_BED(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  GREEN_BED(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  LIGHT_BLUE_BED(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  LIGHT_GRAY_BED(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  LIME_BED(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  MAGENTA_BED(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  ORANGE_BED(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  PINK_BED(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  PURPLE_BED(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  RED_BED(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  WHITE_BED(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },
  YELLOW_BED(KelpVersion.MC_1_12_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
  },

  // DYE
  BLACK_DYE(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BLUE_DYE(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BROWN_DYE(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  CYAN_DYE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GRAY_DYE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GREEN_DYE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LIGHT_BLUE_DYE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LIGHT_GRAY_DYE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LIME_DYE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  MAGENTA_DYE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  ORANGE_DYE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PINK_DYE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PURPLE_DYE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  RED_DYE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  WHITE_DYE(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  YELLOW_DYE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },

  // GLASS PANES
  GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BLACK_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BLUE_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BROWN_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  CYAN_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GRAY_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GREEN_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LIGHT_BLUE_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LIGHT_GRAY_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LIME_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  MAGENTA_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  ORANGE_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PINK_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PURPLE_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  RED_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  WHITE_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  YELLOW_STAINED_GLASS_PANE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },

  // GLASS
  GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BLACK_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BLUE_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BROWN_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  CYAN_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GRAY_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GREEN_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LIGHT_BLUE_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LIGHT_GRAY_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LIME_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  MAGENTA_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  ORANGE_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PINK_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PURPLE_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  RED_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  WHITE_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  YELLOW_STAINED_GLASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },

  // POTIONS
  LINGERING_POTION(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  POTION(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  SPLASH_POTION(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },

  // SHOVELS
  DIAMOND_SHOVEL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  GOLDEN_SHOVEL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  IRON_SHOVEL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  STONE_SHOVEL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  WOODEN_SHOVEL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
    public float getFuelPower() { return 0.0F; }
  },

  // HOES
  DIAMOND_HOE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  GOLDEN_HOE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  IRON_HOE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  STONE_HOE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  WOODEN_HOE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
    public float getFuelPower() { return 0.0F; }
  },

  // AXES
  DIAMOND_AXE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  GOLDEN_AXE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  IRON_AXE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  STONE_AXE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  WOODEN_AXE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
    public float getFuelPower() { return 0.0F; }
  },

  // PICKAXE
  DIAMOND_PICKAXE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  GOLDEN_PICKAXE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  IRON_PICKAXE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  STONE_PICKAXE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  WOODEN_PICKAXE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
    public float getFuelPower() { return 0.0F; }
  },

  // SWORDS
  DIAMOND_SWORD(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  GOLDEN_SWORD(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  IRON_SWORD(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  STONE_SWORD(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  WOODEN_SWORD(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
    public float getFuelPower() { return 0.0F; }
  },

  // CORALS
  BRAIN_CORAL(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BRAIN_CORAL_BLOCK(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  BRAIN_CORAL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BRAIN_CORAL_WALL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  BUBBLE_CORAL(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BUBBLE_CORAL_BLOCK(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  BUBBLE_CORAL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BUBBLE_CORAL_WALL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  DEAD_BRAIN_CORAL(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DEAD_BRAIN_CORAL_BLOCK(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  DEAD_BRAIN_CORAL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DEAD_BRAIN_CORAL_WALL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  DEAD_BUBBLE_CORAL(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DEAD_BUBBLE_CORAL_BLOCK(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  DEAD_BUBBLE_CORAL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DEAD_BUBBLE_CORAL_WALL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  DEAD_FIRE_CORAL(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DEAD_FIRE_CORAL_BLOCK(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  DEAD_FIRE_CORAL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DEAD_FIRE_CORAL_WALL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  DEAD_HORN_CORAL(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DEAD_HORN_CORAL_BLOCK(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  DEAD_HORN_CORAL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DEAD_HORN_CORAL_WALL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  DEAD_TUBE_CORAL(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DEAD_TUBE_CORAL_BLOCK(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  DEAD_TUBE_CORAL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DEAD_TUBE_CORAL_WALL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  FIRE_CORAL(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  FIRE_CORAL_BLOCK(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  FIRE_CORAL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  FIRE_CORAL_WALL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  HORN_CORAL(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  HORN_CORAL_BLOCK(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  HORN_CORAL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  HORN_CORAL_WALL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  TUBE_CORAL(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  TUBE_CORAL_BLOCK(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  TUBE_CORAL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  TUBE_CORAL_WALL_FAN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },

  // MELONS
  MELON(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    public int getMaxStackSize() { return 64; }
  },
  MELON_SEEDS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  MELON_SLICE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  MELON_STEM(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  ATTACHED_MELON_STEM(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },

  // PUMPKINS
  ATTACHED_PUMPKIN_STEM(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  CARVED_PUMPKIN(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    public int getMaxStackSize() { return 64; }
  },
  PUMPKIN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  PUMPKIN_PIE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  PUMPKIN_SEEDS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PUMPKIN_STEM(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },

  // ARMOR (BOOTS)
  CHAINMAIL_BOOTS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  DIAMOND_BOOTS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  GOLDEN_BOOTS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  IRON_BOOTS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  LEATHER_BOOTS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },

  // ARMOR (CHESTPLATE)
  CHAINMAIL_CHESTPLATE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  DIAMOND_CHESTPLATE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  GOLDEN_CHESTPLATE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  IRON_CHESTPLATE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  LEATHER_CHESTPLATE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },

  // ARMOR (HELMET)
  CHAINMAIL_HELMET(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  DIAMOND_HELMET(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  GOLDEN_HELMET(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  IRON_HELMET(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  LEATHER_HELMET(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },

  // ARMOR (LEGGINGS)
  CHAINMAIL_LEGGINGS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  DIAMOND_LEGGINGS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  GOLDEN_LEGGINGS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  IRON_LEGGINGS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  LEATHER_LEGGINGS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },

  // OTHER
  ACACIA_WOOD(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  AIR(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 0; }
  },
  ALLIUM_FLOWER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    public boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
  },
  ANDESITE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  ANVIL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 5.0F; }
    public float getBlastResistance() { return 1200.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
    public boolean isInteractable() { return true; }
  },
  APPLE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  ARMOR_STAND(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 16; }
    public boolean isEntity() { return true; }
    public boolean isFlammable() { return true; }
    public boolean hasGravity() { return true; }
    boolean solidUnsafe() { return false; }
  },
  ARROW(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  AZURE_BLUET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BAKED_POTATO(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  BAMBOO(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  BARREL(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.5F; }
    public float getBlastResistance() { return 2.5F; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  BARRIER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return -1.0F; }
    public float getBlastResistance() { return 3600000.8F; }
    public int getMaxStackSize() { return 64; }
  },
  BEACON(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  BEDROCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return -1.0F; }
    public float getBlastResistance() { return 3600000.0F; }
    public int getMaxStackSize() { return 64; }
  },
  BEEF(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  BEEHIVE(KelpVersion.MC_1_15_0) {
    public float getHardness() { return 0.6F; }
    public float getBlastResistance() { return 0.6F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  BEETROOT(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  BEETROOTS(KelpVersion.MC_1_9_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  BEETROOT_SEEDS(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BEETROOT_SOUP(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
    public boolean isEdible() { return true; }
  },
  BEE_NEST(KelpVersion.MC_1_15_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  BELL(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 5.0F; }
    public float getBlastResistance() { return 5.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  BIRCH_WOOD(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  BLAST_FURNACE(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 3.5F; }
    public float getBlastResistance() { return 3.5F; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  BLAZE_POWDER(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BLAZE_ROD(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  BLUE_ICE(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 2.8F; }
    public float getBlastResistance() { return 2.8F; }
    public int getMaxStackSize() { return 64; }
  },
  BLUE_ORCHID(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BONE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BONE_BLOCK(KelpVersion.MC_1_10_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public int getMaxStackSize() { return 64; }
  },
  BONE_MEAL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BOOK(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BOOKSHELF(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 1.5F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  BOW(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
    public float getFuelPower() { return 0.0F; }
  },
  BOWL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  BREAD(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  BREWING_STAND(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  BREWING_STAND_ITEM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BRICK(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BRICKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  BROWN_MUSHROOM(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  BROWN_MUSHROOM_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
  },
  BUBBLE_COLUMN(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  BUCKET(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 16; }
  },
  CACTUS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.4F; }
    public float getBlastResistance() { return 0.4F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  CAKE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 1; }
    public boolean isInteractable() { return true; }
    public boolean isEdible() { return true; }
  },
  CAKE_ITEM(KelpVersion.MC_1_8_0) {
    public int getMaxStackSize() { return 1; }
    public boolean isBlock() { return false; }
  },
  CAMPFIRE(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  CARROT(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  CARROTS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  CARROT_ON_A_STICK(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  CARTOGRAPHY_TABLE(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.5F; }
    public float getBlastResistance() { return 2.5F; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  CAULDRON(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isInteractable() { return true; }
  },
  CAULDRON_ITEM(KelpVersion.MC_1_8_0) {
    public int getMaxStackSize() { return 64; }
    public boolean isBlock() { return false; }
  },
  CAVE_AIR(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  CHAIN_COMMAND_BLOCK(KelpVersion.MC_1_9_0) {
    public float getHardness() { return -1.0F; }
    public float getBlastResistance() { return 3600000.0F; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  CHARCOAL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  CHEST(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.5F; }
    public float getBlastResistance() { return 2.5F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  CHEST_MINECART(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isEntity() { return true; }
    public boolean hasGravity() { return true; }
    public boolean isInteractable() { return true; }
    public int getMaxStackSize() { return 1; }
  },
  CHICKEN(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  CHIPPED_ANVIL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 5.0F; }
    public float getBlastResistance() { return 1200.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
    public boolean isInteractable() { return true; }
  },
  CHISELED_QUARTZ_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public int getMaxStackSize() { return 64; }
  },
  CHISELED_RED_SANDSTONE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public int getMaxStackSize() { return 64; }
  },
  CHISELED_SANDSTONE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public int getMaxStackSize() { return 64; }
  },
  CHISELED_STONE_BRICKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  CHORUS_FLOWER(KelpVersion.MC_1_9_0) {
    public float getHardness() { return 0.4F; }
    public float getBlastResistance() { return 0.4F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  CHORUS_FRUIT(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  CHORUS_PLANT(KelpVersion.MC_1_9_0) {
    public float getHardness() { return 0.4F; }
    public float getBlastResistance() { return 0.4F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  CLAY(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.6F; }
    public float getBlastResistance() { return 0.6F; }
    public int getMaxStackSize() { return 64; }
  },
  CLAY_BALL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  CLOCK(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  COAL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  COAL_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 5.0F; }
    public float getBlastResistance() { return 6.0F; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  COAL_ORE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    public int getMaxStackSize() { return 64; }
  },
  COARSE_DIRT(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
  },
  COBBLESTONE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  COBWEB(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 4.0F; }
    public float getBlastResistance() { return 4.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  COCOA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  COCOA_BEANS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  COD(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  COD_BUCKET(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  COMMAND_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return -1.0F; }
    public float getBlastResistance() { return 3600000.0F; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  COMMAND_BLOCK_MINECART(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isEntity() { return true; }
    public boolean hasGravity() { return true; }
    public boolean isInteractable() { return true; }
    public int getMaxStackSize() { return 1; }
  },
  COMPARATOR(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  COMPASS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  COMPOSTER(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 0.6F; }
    public float getBlastResistance() { return 0.6F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  CONDUIT(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  COOKED_BEEF(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  COOKED_CHICKEN(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  COOKED_COD(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  COOKED_MUTTON(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  COOKED_PORKCHOP(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  COOKED_RABBIT(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  COOKED_SALMON(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  COOKIE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  CORNFLOWER(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  CRACKED_STONE_BRICKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  CRAFTING_TABLE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.5F; }
    public float getBlastResistance() { return 2.5F; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  CROSSBOW(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
    public float getFuelPower() { return 0.0F; }
  },
  CUT_RED_SANDSTONE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public int getMaxStackSize() { return 64; }
  },
  CUT_SANDSTONE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public int getMaxStackSize() { return 64; }
  },
  DAMAGED_ANVIL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 5.0F; }
    public float getBlastResistance() { return 1200.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
    public boolean isInteractable() { return true; }
  },
  DANDELION(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DARK_OAK_WOOD(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  DARK_PRISMARINE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  DAYLIGHT_DETECTOR(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  DEAD_BUSH(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  DEBUG_STICK(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  DIAMOND(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DIAMOND_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 5.0F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  DIAMOND_HORSE_ARMOR(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  DIAMOND_ORE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    public int getMaxStackSize() { return 64; }
  },
  DIORITE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  DIRT(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
  },
  DISPENSER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.5F; }
    public float getBlastResistance() { return 3.5F; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  DRAGON_BREATH(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  DRAGON_EGG(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 9.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
    public boolean isInteractable() { return true; }
  },
  DRIED_KELP(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  DRIED_KELP_BLOCK(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 2.5F; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  DROPPER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.5F; }
    public float getBlastResistance() { return 3.5F; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  EGG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 16; }
  },
  ELYTRA(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  EMERALD(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  EMERALD_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 5.0F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  EMERALD_ORE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    public int getMaxStackSize() { return 64; }
  },
  ENCHANTED_BOOK(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  ENCHANTED_GOLDEN_APPLE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  ENCHANTING_TABLE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 5.0F; }
    public float getBlastResistance() { return 1200.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  ENDER_CHEST(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 22.5F; }
    public float getBlastResistance() { return 600.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  ENDER_EYE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  ENDER_PEARL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 16; }
  },
  END_CRYSTAL(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  END_GATEWAY(KelpVersion.MC_1_9_0) {
    public float getHardness() { return -1.0F; }
    public float getBlastResistance() { return 3600000.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  END_PORTAL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return -1.0F; }
    public float getBlastResistance() { return 3600000.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  END_PORTAL_FRAME(KelpVersion.MC_1_8_0) {
    public float getHardness() { return -1.0F; }
    public float getBlastResistance() { return 3600000.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  END_ROD(KelpVersion.MC_1_9_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  END_STONE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 9.0F; }
    public int getMaxStackSize() { return 64; }
  },
  END_STONE_BRICKS(KelpVersion.MC_1_9_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 9.0F; }
    public int getMaxStackSize() { return 64; }
  },
  EXPERIENCE_BOTTLE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  FARMLAND(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.6F; }
    public float getBlastResistance() { return 0.6F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  FEATHER(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  FERMENTED_SPIDER_EYE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  FERN(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  FILLED_MAP(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  FIRE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  FIREWORK_ROCKET(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  FIREWORK_STAR(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  FIRE_CHARGE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  FISHING_ROD(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
    public float getFuelPower() { return 0.0F; }
  },
  FLETCHING_TABLE(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.5F; }
    public float getBlastResistance() { return 2.5F; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  FLINT(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  FLINT_AND_STEEL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  FLOWER_POT(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isInteractable() { return true; }
  },
  FLOWER_POT_ITEM(KelpVersion.MC_1_8_0) {
    public int getMaxStackSize() { return 64; }
    public boolean isBlock() { return false; }
  },
  FROSTED_ICE(KelpVersion.MC_1_9_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  FURNACE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.5F; }
    public float getBlastResistance() { return 3.5F; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  FURNACE_MINECART(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isEntity() { return true; }
    public boolean hasGravity() { return true; }
    public boolean isInteractable() { return true; }
    public int getMaxStackSize() { return 1; }
  },
  GHAST_TEAR(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GLASS_BOTTLE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GLISTERING_MELON_SLICE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GLOWSTONE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GLOWSTONE_DUST(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GOLDEN_APPLE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  GOLDEN_CARROT(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  GOLDEN_HORSE_ARMOR(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  GOLD_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  GOLD_INGOT(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GOLD_NUGGET(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GOLD_ORE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    public int getMaxStackSize() { return 64; }
  },
  GRANITE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  GRASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GRASS_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.6F; }
    public float getBlastResistance() { return 0.6F; }
    public int getMaxStackSize() { return 64; }
  },
  GRASS_PATH(KelpVersion.MC_1_9_0) {
    public float getHardness() { return 0.65F; }
    public float getBlastResistance() { return 0.65F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  GRAVEL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.6F; }
    public float getBlastResistance() { return 0.6F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  GRINDSTONE(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  GUNPOWDER(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  HAY_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
  },
  HEART_OF_THE_SEA(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  HONEYCOMB(KelpVersion.MC_1_15_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  HONEYCOMB_BLOCK(KelpVersion.MC_1_15_0) {
    public float getHardness() { return 0.6F; }
    public float getBlastResistance() { return 0.6F; }
    public int getMaxStackSize() { return 64; }
  },
  HONEY_BLOCK(KelpVersion.MC_1_15_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  HONEY_BOTTLE(KelpVersion.MC_1_15_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 16; }
    public boolean isEdible() { return true; }
  },
  HOPPER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 4.8F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  HOPPER_MINECART(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isEntity() { return true; }
    public boolean hasGravity() { return true; }
    public boolean isInteractable() { return true; }
    public int getMaxStackSize() { return 1; }
  },
  ICE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  INFESTED_CHISELED_STONE_BRICKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.75F; }
    public int getMaxStackSize() { return 64; }
  },
  INFESTED_COBBLESTONE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.75F; }
    public int getMaxStackSize() { return 64; }
  },
  INFESTED_CRACKED_STONE_BRICKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.75F; }
    public int getMaxStackSize() { return 64; }
  },
  INFESTED_MOSSY_STONE_BRICKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.75F; }
    public int getMaxStackSize() { return 64; }
  },
  INFESTED_STONE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.75F; }
    public int getMaxStackSize() { return 64; }
  },
  INFESTED_STONE_BRICKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.75F; }
    public int getMaxStackSize() { return 64; }
  },
  INK_SAC(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  IRON_BARS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 5.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  IRON_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 5.0F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  IRON_HORSE_ARMOR(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  IRON_INGOT(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  IRON_NUGGET(KelpVersion.MC_1_11_1) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  IRON_ORE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    public int getMaxStackSize() { return 64; }
  },
  ITEM_FRAME(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isEntity() { return true; }
    public boolean isInteractable() { return true; }
    public int getMaxStackSize() { return 64; }
  },
  JACK_O_LANTERN(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    public int getMaxStackSize() { return 64; }
  },
  JIGSAW(KelpVersion.MC_1_14_0) {
    public float getHardness() { return -1.0F; }
    public float getBlastResistance() { return 3600000.0F; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  JUKEBOX(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  JUNGLE_WOOD(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  KELP(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  KELP_PLANT(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  KNOWLEDGE_BOOK(KelpVersion.MC_1_12_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  LADDER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.4F; }
    public float getBlastResistance() { return 0.4F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  LANTERN(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 3.5F; }
    public float getBlastResistance() { return 3.5F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LAPIS_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    public int getMaxStackSize() { return 64; }
  },
  LAPIS_LAZULI(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LAPIS_ORE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    public int getMaxStackSize() { return 64; }
  },
  LARGE_FERN_LOWER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LAVA(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 100.0F; }
    public float getBlastResistance() { return 100.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  LAVA_BUCKET(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
    public float getFuelPower() { return 0.0F; }
  },
  LEAD(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LEATHER(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LEATHER_HORSE_ARMOR(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  LECTERN(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.5F; }
    public float getBlastResistance() { return 2.5F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  LEVER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  LILAC(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LILY_OF_THE_VALLEY(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LILY_PAD(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  LOOM(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.5F; }
    public float getBlastResistance() { return 2.5F; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  MAGMA_BLOCK(KelpVersion.MC_1_10_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
  },
  MAGMA_CREAM(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  MAP(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  MILK_BUCKET(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  MINECART(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isEntity() { return true; }
    public boolean hasGravity() { return true; }
    public boolean isInteractable() { return true; }
    public int getMaxStackSize() { return 1; }
  },
  MOSSY_COBBLESTONE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  MOSSY_STONE_BRICKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  MOVING_PISTON(KelpVersion.MC_1_8_0) {
    public float getHardness() { return -1.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  MUSHROOM_STEM(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
  },
  MUSHROOM_STEW(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
    public boolean isEdible() { return true; }
  },
  MUSIC_DISC_11(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  MUSIC_DISC_13(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  MUSIC_DISC_BLOCKS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  MUSIC_DISC_CAT(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  MUSIC_DISC_CHIRP(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  MUSIC_DISC_FAR(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  MUSIC_DISC_MALL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  MUSIC_DISC_MELLOHI(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  MUSIC_DISC_STAL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  MUSIC_DISC_STRAD(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  MUSIC_DISC_WAIT(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  MUSIC_DISC_WARD(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  MUTTON(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  MYCELIUM(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.6F; }
    public float getBlastResistance() { return 0.6F; }
    public int getMaxStackSize() { return 64; }
  },
  NAME_TAG(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  NAUTILUS_SHELL(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  NETHERRACK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.4F; }
    public float getBlastResistance() { return 0.4F; }
    public int getMaxStackSize() { return 64; }
  },
  NETHER_BRICK(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  NETHER_BRICKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  NETHER_PORTAL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return -1.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  NETHER_QUARTZ_ORE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    public int getMaxStackSize() { return 64; }
  },
  NETHER_STAR(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  NETHER_WART(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  NETHER_WART_BLOCK(KelpVersion.MC_1_10_0) {
    public float getHardness() { return 1.0F; }
    public float getBlastResistance() { return 1.0F; }
    public int getMaxStackSize() { return 64; }
  },
  NOTE_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  OAK_WOOD(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  OBSERVER(KelpVersion.MC_1_11_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  OBSIDIAN(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 50.0F; }
    public float getBlastResistance() { return 1200.0F; }
    public int getMaxStackSize() { return 64; }
  },
  ORANGE_TULIP(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  OXEYE_DAISY(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PACKED_ICE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
  },
  PAINTING(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isEntity() { return true; }
    public int getMaxStackSize() { return 64; }
  },
  PAPER(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PEONY(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PHANTOM_MEMBRANE(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PINK_TULIP(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PISTON(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 1.5F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PISTON_HEAD(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 1.5F; }
    boolean occludingUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  PODZOL(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
  },
  POISONOUS_POTATO(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  POLISHED_ANDESITE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  POLISHED_DIORITE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  POLISHED_GRANITE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  POPPED_CHORUS_FRUIT(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  POPPY(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PORKCHOP(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  POTATO(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  POTATOES(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  POTTED_ALLIUM(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_AZURE_BLUET(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_BAMBOO(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_BLUE_ORCHID(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_BROWN_MUSHROOM(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_CACTUS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_CORNFLOWER(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_DANDELION(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_DEAD_BUSH(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_FERN(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_LILY_OF_THE_VALLEY(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_ORANGE_TULIP(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_OXEYE_DAISY(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_PINK_TULIP(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_POPPY(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_RED_MUSHROOM(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_RED_TULIP(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_WHITE_TULIP(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  POTTED_WITHER_ROSE(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  PRISMARINE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  PRISMARINE_BRICKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  PRISMARINE_CRYSTALS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PRISMARINE_SHARD(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  PUFFERFISH(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  PUFFERFISH_BUCKET(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  PURPUR_BLOCK(KelpVersion.MC_1_9_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  PURPUR_PILLAR(KelpVersion.MC_1_9_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  QUARTZ(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  QUARTZ_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public int getMaxStackSize() { return 64; }
  },
  QUARTZ_PILLAR(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public int getMaxStackSize() { return 64; }
  },
  RABBIT(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  RABBIT_FOOT(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  RABBIT_HIDE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  RABBIT_STEW(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
    public boolean isEdible() { return true; }
  },
  REDSTONE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  REDSTONE_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 5.0F; }
    public float getBlastResistance() { return 6.0F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  REDSTONE_LAMP(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    public int getMaxStackSize() { return 64; }
  },
  REDSTONE_ORE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 3.0F; }
    public float getBlastResistance() { return 3.0F; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  REDSTONE_TORCH(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  REDSTONE_WALL_TORCH(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  REDSTONE_WIRE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  RED_MUSHROOM(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  RED_MUSHROOM_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
  },
  RED_NETHER_BRICKS(KelpVersion.MC_1_10_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  RED_SAND(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  RED_SANDSTONE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public int getMaxStackSize() { return 64; }
  },
  RED_TULIP(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  REPEATER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  REPEATING_COMMAND_BLOCK(KelpVersion.MC_1_9_0) {
    public float getHardness() { return -1.0F; }
    public float getBlastResistance() { return 3600000.0F; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  ROSE_BUSH(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  ROTTEN_FLESH(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  SADDLE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  SALMON(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  SALMON_BUCKET(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  SAND(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
    boolean gravityUnsafe() { return true; }
  },
  SANDSTONE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.8F; }
    public float getBlastResistance() { return 0.8F; }
    public int getMaxStackSize() { return 64; }
  },
  SCAFFOLDING(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  SCUTE(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SEAGRASS(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SEA_LANTERN(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.3F; }
    public float getBlastResistance() { return 0.3F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SEA_PICKLE(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SHEARS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  SHIELD(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  SHULKER_SHELL(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SLIME_BALL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SLIME_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    public int getMaxStackSize() { return 64; }
  },
  SMITHING_TABLE(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.5F; }
    public float getBlastResistance() { return 2.5F; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  SMOKER(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 3.5F; }
    public float getBlastResistance() { return 3.5F; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  SMOOTH_QUARTZ(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  SMOOTH_RED_SANDSTONE(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  SMOOTH_SANDSTONE(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  SMOOTH_STONE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  SNOW(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.1F; }
    public float getBlastResistance() { return 0.1F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SNOWBALL(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 16; }
  },
  SNOW_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    public int getMaxStackSize() { return 64; }
  },
  SOUL_SAND(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    public int getMaxStackSize() { return 64; }
  },
  SPAWNER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 5.0F; }
    public float getBlastResistance() { return 5.0F; }
    public int getMaxStackSize() { return 64; }
  },
  SPECTRAL_ARROW(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SPIDER_EYE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  SPONGE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.6F; }
    public float getBlastResistance() { return 0.6F; }
    public int getMaxStackSize() { return 64; }
  },
  SPRUCE_WOOD(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.0F; }
    public float getBlastResistance() { return 2.0F; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  STICK(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
  },
  STICKY_PISTON(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 1.5F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  STONE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  STONECUTTER(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 3.5F; }
    public float getBlastResistance() { return 3.5F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  STONE_BRICKS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 1.5F; }
    public float getBlastResistance() { return 6.0F; }
    public int getMaxStackSize() { return 64; }
  },
  STRING(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  STRUCTURE_BLOCK(KelpVersion.MC_1_9_0) {
    public float getHardness() { return -1.0F; }
    public float getBlastResistance() { return 3600000.0F; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  STRUCTURE_VOID(KelpVersion.MC_1_10_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SUGAR(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SUGAR_CANE(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SUGAR_CANE_BLOCK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  SUNFLOWER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  SUSPICIOUS_STEW(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
    public boolean isEdible() { return true; }
  },
  SWEET_BERRIES(KelpVersion.MC_1_14_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  SWEET_BERRY_BUSH(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
    public boolean isInteractable() { return true; }
  },
  TALL_GRASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  TALL_SEAGRASS(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  TIPPED_ARROW(KelpVersion.MC_1_9_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  TNT(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    public int getMaxStackSize() { return 64; }
    public boolean isInteractable() { return true; }
  },
  TNT_MINECART(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public boolean isEntity() { return true; }
    public boolean hasGravity() { return true; }
    public boolean isInteractable() { return true; }
    public int getMaxStackSize() { return 1; }
  },
  TORCH(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  TOTEM_OF_UNDYING(KelpVersion.MC_1_11_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  TRAPPED_CHEST(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 2.5F; }
    public float getBlastResistance() { return 2.5F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public int getMaxStackSize() { return 64; }
    public float getFuelPower() { return 0.0F; }
    public boolean isInteractable() { return true; }
  },
  TRIDENT(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  TRIPWIRE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  TRIPWIRE_HOOK(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  TROPICAL_FISH(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
    public boolean isEdible() { return true; }
  },
  TROPICAL_FISH_BUCKET(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  TURTLE_EGG(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.5F; }
    public float getBlastResistance() { return 0.5F; }
    boolean occludingUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  TURTLE_HELMET(KelpVersion.MC_1_13_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  VINE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.2F; }
    public float getBlastResistance() { return 0.2F; }
    boolean occludingUnsafe() { return false; }
    public boolean isFlammable() { return true; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  VOID_AIR(KelpVersion.MC_1_13_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  WALL_TORCH(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  WATER(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 100.0F; }
    public float getBlastResistance() { return 100.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public boolean isItem() { return false; }
  },
  WATER_BUCKET(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  WET_SPONGE(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.6F; }
    public float getBlastResistance() { return 0.6F; }
    public int getMaxStackSize() { return 64; }
  },
  WHEAT(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  WHEAT_SEEDS(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  WHITE_TULIP(KelpVersion.MC_1_8_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  WITHER_ROSE(KelpVersion.MC_1_14_0) {
    public float getHardness() { return 0.0F; }
    public float getBlastResistance() { return 0.0F; }
    boolean occludingUnsafe() { return false; }
    public boolean isBurnable() { return true; }
    boolean solidUnsafe() { return false; }
    public int getMaxStackSize() { return 64; }
  },
  WRITABLE_BOOK(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 1; }
  },
  WRITTEN_BOOK(KelpVersion.MC_1_8_0) {
    public boolean isBlock() { return false; }
    public int getMaxStackSize() { return 16; }
  },

  ;

  private KelpVersion since;

  KelpMaterial(KelpVersion since) {
    this.since = since;
  }

  public KelpVersion since() {
    return since;
  }

  public boolean isEdible() {
    return false;
  }

  public boolean isBlock() {
    return true;
  }

  public boolean isSolid() {
    return isBlock() && solidUnsafe();
  }

  public boolean isItem() {
    return true;
  }

  public boolean isBanner() {
    return false;
  }

  public boolean hasCollision() {
    return isBlock() && collisionUnsafe();
  }

  public boolean isPassable() {
    return isBlock() && !collisionUnsafe();
  }

  public boolean isRecord() {
    return false;
  }

  public boolean isFlammable() {
    return false;
  }

  public boolean isBurnable() {
    return false;
  }

  public boolean isInteractable() {
    return false;
  }

  public boolean isFuel() {
    return getFuelPower() > 0F;
  }

  // items smelted per item fuel
  public float getFuelPower() {
    return fuelPowerFromVersion(KelpServer.getVersion());
  }

  public float getHardness() {
    return 0F;
  }

  public float getBlastResistance() {
    return 0F;
  }

  public int getMaxStackSize() {
    return 0;
  }

  public boolean hasGravity() {
    return isBlock() && gravityUnsafe();
  }

  public boolean isOccluding() {
    return isBlock() && occludingUnsafe();
  }

  public boolean isEntity() {
    return false;
  }

  boolean collisionUnsafe() {
    return true;
  }

  boolean gravityUnsafe() {
    return false;
  }

  boolean occludingUnsafe() {
    return true;
  }

  boolean solidUnsafe() {
    return true;
  }

  float fuelPowerFromVersion(KelpVersion version) {
    return 0.0F;
  }

  public static KelpMaterial from(Material bukkitMaterial) {
    MaterialRepository repository = KelpPlugin.getInjector().getInstance(MaterialRepository.class);
    return repository.getKelpMaterial(bukkitMaterial.toString());
  }

  public static KelpMaterial from(Material bukkitMaterial, int subId) {
    MaterialRepository repository = KelpPlugin.getInjector().getInstance(MaterialRepository.class);
    return repository.getKelpMaterial(bukkitMaterial.toString(), subId);
  }

  public static MaterialContainer convert(KelpMaterial kelpMaterial) {
    MaterialRepository repository = KelpPlugin.getInjector().getInstance(MaterialRepository.class);
    return repository.getBukkitMaterial(kelpMaterial);
  }

  public static Material convertUnsafe(KelpMaterial kelpMaterial) {
    MaterialRepository repository = KelpPlugin.getInjector().getInstance(MaterialRepository.class);
    String[] materialData = repository.getMaterial(kelpMaterial).split(":");
    return Material.valueOf(materialData[0]);
  }

  /**
   * Returns a collection of materials included in newer versions than
   * the given one. So if you input 1.12 for example, only materials
   * added in 1.13 will be returned.
   *
   * @param version The version you want to use for the calculation.
   * @return The final collection
   */
  public static Collection<KelpMaterial> aboveVersion(KelpVersion version) {
    Collection<KelpMaterial> output = new ArrayList<>();

    if (version == KelpVersion.highestVersion()) {
      return output;
    }

    for (KelpMaterial material : KelpMaterial.values()) {
      if (material.since() == version) continue;
      if (KelpVersion.higherVersion(version,  material.since()) == material.since()) {
        output.add(material);
      }
    }

    return output;
  }

  /**
   * Returns a collection of materials added in older versions than
   * the given one. If you input 1.11 for example, only materials
   * contained in 1.10 will be returned.
   *
   * @param version The version
   * @return The final collection.
   */
  public static Collection<KelpMaterial> belowVersion(KelpVersion version) {
    Collection<KelpMaterial> output = new ArrayList<>();

    if (version == KelpVersion.lowestVersion()) {
      return output;
    }

    for (KelpMaterial material : KelpMaterial.values()) {
      if (material.since() == version) continue;
      if (KelpVersion.lowerVersion(version,  material.since()) == material.since()) {
        output.add(material);
      }
    }

    return output;
  }

  /**
   * Returns a collection of materials which have been added in the
   * given version. No materials of older or future versions are included.
   *
   * @param version The version you want to get the materials of.
   * @return A collection of newly added materials.
   */
  public static Collection<KelpMaterial> matchesVersion(KelpVersion version) {
    Collection<KelpMaterial> output = new ArrayList<>();

    for (KelpMaterial material : KelpMaterial.values()) {
      if (material.since() == version) {
        output.add(material);
      }
    }

    return output;
  }

  /**
   * Returns a collection of materials available in the given
   * kelp version.
   *
   * @param version The version you want to check the materials of.
   * @return The collection of materials included in the given version.
   */
  public static Collection<KelpMaterial> includedIn(KelpVersion version) {
    Collection<KelpMaterial> output = new ArrayList<>(belowVersion(version));
    output.addAll(matchesVersion(version));
    return output;
  }

  @Override
  public String toString() {
    return super.toString();
  }
}
