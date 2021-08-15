package de.pxav.kelp.implementation1_8.world;

import de.pxav.kelp.core.common.MathUtils;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.material.MaterialContainer;
import de.pxav.kelp.core.version.Versioned;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.region.CuboidRegion;
import de.pxav.kelp.core.world.util.KelpBlockFace;
import de.pxav.kelp.core.world.version.BlockVersionTemplate;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.KelpChunk;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.craftbukkit.v1_8_R3.util.CraftMagicNumbers;

import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements all version-dependent methods
 * of a {@link KelpBlock}.
 *
 * @author pxav
 */
@Versioned
public class VersionedBlock extends BlockVersionTemplate {

  /**
   * Gets the {@link KelpChunk} the given block is located in.
   *
   * @param block The block you want to get the chunk of.
   * @return The {@link KelpChunk} the given block is located in.
   */
  @Override
  public KelpChunk getChunk(KelpBlock block) {
    return KelpChunk.from(block.getBukkitBlock().getChunk());
  }

  /**
   * Gets the {@link KelpLocation} of the given block. This also
   * contains information like the current world.
   *
   * @param block The block you want to get the location of.
   * @return The {@link KelpLocation} of this world.
   */
  @Override
  public KelpLocation getLocation(KelpBlock block) {
    return KelpLocation.from(block.getBukkitBlock().getLocation());
  }

  /**
   * Gets the material of the current block.
   *
   * @param block The block you want to get the material of.
   * @return The {@link KelpMaterial} the given block is made of.
   */
  @Override
  public KelpMaterial getMaterial(KelpBlock block) {
    // if block has a sub id
    if (block.getBukkitBlock().getData() != 0) {
      return KelpMaterial.from(block.getBukkitBlock().getType(), block.getBukkitBlock().getData());
    }

    // if the block has no special data to obey
    return KelpMaterial.from(block.getBukkitBlock().getType());
  }

  /**
   * Sets the material of the given block to the given {@link KelpMaterial}.
   * So if you had an {@link KelpMaterial#AIR} block for example,
   *
   * @param block     The block you want to change the material of.
   * @param material  The new material you want the block to have.
   */
  @Override
  public void setMaterial(KelpBlock block, KelpMaterial material) {
    MaterialContainer newMaterial = KelpMaterial.convert(material);
    block.getBukkitBlock().setType(newMaterial.getBukkitMaterial());
    if (newMaterial.getSubId() != 0) {
      block.getBukkitBlock().setData(
        (byte) newMaterial.getSubId()
      );
    }
  }

  /**
   * Checks whether bone meal will have an effect on this block.
   * This means it will check whether the block is a sapling
   * or a plant that will grow if you right click it with bone
   * meal. More information about that can be found
   * <a href="https://minecraft.gamepedia.com/Bone_Meal">in this wiki
   * article</a>
   *
   * @param block The block you want to check the application state of.
   * @return {@code true} whether bone meal is applicable.
   */
  @Override
  public boolean canApplyBoneMeal(KelpBlock block) {
    // kelp material checks!
    return false;
  }

  /**
   * Simulates the application of bone meal on a block.
   * This means if the block is a sapling for example, it
   * might grow to a tree or grass might spawn random flowers
   * and so on.
   *
   * @param kBlock    The block you want to simulate the application of.
   * @param blockFace The face of the block to apply the bone meal to.
   */
  @Override
  public void applyBoneMeal(KelpBlock kBlock, KelpBlockFace blockFace) {
    Block block = kBlock.getBukkitBlock();

    // cause those plant types to grow by 2 to 5 stages.
    if (kBlock.getMaterial() == KelpMaterial.POTATOES
      || kBlock.getMaterial() == KelpMaterial.CARROTS
      || craftBlock(kBlock).getType() == Material.CROPS
      || craftBlock(kBlock).getType() == Material.MELON_STEM
      || craftBlock(kBlock).getType() == Material.PUMPKIN_STEM) {

      // generate a random growth value between 2 and 5 which
      // is equal to the natural bone meal growth
      byte data = kBlock.getBukkitBlock().getData();
      byte newData = (byte) (data + ThreadLocalRandom.current().nextInt(2, 5));

      // a crop can only grow up to age 7, so set
      // it back there if the value should exceed that number
      if (newData > 7) {
        newData = (byte) 7;
      }

      // update the block data
      kBlock.getBukkitBlock().setData(newData);
      return;
    }

    // drop item if player clicks on sunflower, lilac, peony or rose bush
    if (kBlock.getBukkitBlock().getType() == Material.DOUBLE_PLANT) {
      byte data = block.getData();
      KelpMaterial material;

      // if the block is the upper part of a plant, get
      // the lower part to fetch the exact item dropped by
      // this plant
      if (data == 10) {
        KelpBlock baseBlock = kBlock.getBlockBelow();
        byte baseData = baseBlock.getBukkitBlock().getData();
        material = getDoublePlantMaterial(baseData);

      // if the block is the lower part of the plant
      // immediately get the item material
      } else {
        material = getDoublePlantMaterial(data);
      }

      // if there could not be any material fetched,
      // return.
      if (material == KelpMaterial.AIR) {
        return;
      }

      kBlock.getWorld().dropItemNaturally(
        kBlock.getLocation(),
        KelpItem.create()
          .material(material)
          .allowInteractions()
      );
      return;
    }

    // grow small fern and grass to tall gras and large fern
    if (kBlock.getBukkitBlock().getType() == Material.LONG_GRASS
      && kBlock.getBlockAbove().getMaterial() == KelpMaterial.AIR) {

      byte data = kBlock.getBukkitBlock().getData();

      // if it is small grass, build both tall grass blocks
      if (data == 1) {
        block.setType(Material.DOUBLE_PLANT);
        block.setData((byte) 2);

        Block above = kBlock.getBlockAbove().getBukkitBlock();
        above.setType(Material.DOUBLE_PLANT);
        above.setData((byte) 10);
      }

      // if it is small fern, build both large fern blocks
      if (data == 2) {
        block.setType(Material.DOUBLE_PLANT);
        block.setData((byte) 3);

        Block above = kBlock.getBlockAbove().getBukkitBlock();
        above.setType(Material.DOUBLE_PLANT);
        above.setData((byte) 10);
      }
      return;
    }

    // if a player clicks on a sapling there is a 45% chance
    // that it will grow by 1 stage. When it is on the second stage,
    // a real tree will grow
    if (kBlock.getBukkitBlock().getType() == Material.SAPLING) {
      byte data = kBlock.getBukkitBlock().getData();
      boolean grow = MathUtils.perCentChance(0.45);

      if (!grow) {
        return;
      }

      boolean spawned = false;

      // if the id is below 8, the tree is too young to
      // grow immediately, so it is set to the next growth stage.

      // if the id is higher than or equal to 8, try spawning
      // the tree
      switch (data) {
        case 0: // oak
          kBlock.getBukkitBlock().setData((byte) 8);
          break;
        case 1: // spruce
          kBlock.getBukkitBlock().setData((byte) 9);
          break;
        case 2: // birch
          kBlock.getBukkitBlock().setData((byte) 10);
          break;
        case 3: // jungle
          kBlock.getBukkitBlock().setData((byte) 11);
          break;
        case 4: // acacia
          kBlock.getBukkitBlock().setData((byte) 12);
          break;
        case 5: // dark oak
          kBlock.getBukkitBlock().setData((byte) 13);
          break;
        case 8:
          block.setType(Material.AIR);
          TreeType type = MathUtils.perCentChance(0.10) ? TreeType.BIG_TREE : TreeType.TREE;
          spawned = kBlock.getBukkitBlock().getWorld().generateTree(kBlock.getBukkitBlock().getLocation(), type);
          break;
        case 9:
          block.setType(Material.AIR);
          spawned = kBlock.getBukkitBlock().getWorld().generateTree(kBlock.getBukkitBlock().getLocation(), TreeType.REDWOOD);
          break;
        case 10:
          block.setType(Material.AIR);
          spawned = kBlock.getBukkitBlock().getWorld().generateTree(kBlock.getBukkitBlock().getLocation(), TreeType.BIRCH);
          break;
        case 11:
          block.setType(Material.AIR);
          spawned = kBlock.getBukkitBlock().getWorld().generateTree(kBlock.getBukkitBlock().getLocation(), TreeType.JUNGLE);
          break;
        case 12:
          block.setType(Material.AIR);
          spawned = kBlock.getBukkitBlock().getWorld().generateTree(kBlock.getBukkitBlock().getLocation(), TreeType.ACACIA);
          break;
        case 13:
          block.setType(Material.AIR);
          spawned = kBlock.getBukkitBlock().getWorld().generateTree(kBlock.getBukkitBlock().getLocation(), TreeType.DARK_OAK);
          break;
      }

      // if there was not enough space for the tree, reset it
      if (!spawned && data >= 8) {
        block.setType(Material.SAPLING);
        block.setData(data);
      }

      return;
    }

    // default case when player interacts with grass
    // for example
    ItemStack nmsStack = CraftItemStack.asNMSCopy(
      new org.bukkit.inventory.ItemStack(Material.INK_SACK, 1, (short) 15)
    );

    CraftWorld world = (CraftWorld) kBlock.getWorld().getBukkitWorld();

    ItemDye.a(nmsStack, world.getHandle(),
      new BlockPosition(
        kBlock.getX(),
        kBlock.getY() - 1,
        kBlock.getZ()
      )
    );

  }

  @Override
  public CuboidRegion getBoundingBox(KelpBlock block) {
    CraftBlock craftBlock = craftBlock(block);
    net.minecraft.server.v1_8_R3.Block nmsBlock = CraftMagicNumbers.getBlock(craftBlock);

    CraftWorld craftWorld = (CraftWorld) craftBlock.getWorld();
    World world = craftWorld.getHandle();
    BlockPosition position = new BlockPosition(block.getX(), block.getY(), block.getZ());

    // this gets the collision bounding box first
    AxisAlignedBB axisAlignedBB = nmsBlock.a(world, position, (IBlockData) null);

    // if the block has no collision (e. g. vines, sugar cane, etc.)
    // calculate the bounding box according to the selection bounding box
    if (axisAlignedBB == null) {
      axisAlignedBB = new AxisAlignedBB(
        (double)position.getX() + nmsBlock.B(), (double)position.getY() + nmsBlock.D(),
        (double)position.getZ() + nmsBlock.F(), (double)position.getX() + nmsBlock.C(),
        (double)position.getY() + nmsBlock.E(), (double)position.getZ() + nmsBlock.G());
    }

    return CuboidRegion.create(
      KelpLocation.from(block.getWorldName(), axisAlignedBB.a, axisAlignedBB.b, axisAlignedBB.c),
      KelpLocation.from(block.getWorldName(), axisAlignedBB.d, axisAlignedBB.e, axisAlignedBB.f)
    );
  }

  /**
   * Converts the block data of a flower type double plant and converts
   * it to the corresponding item which is dropped by the plant.
   *
   * @param data The data of the plant (either sub id of an item or
   *             data of a block)
   * @return The kelp material of the plant.
   */
  private KelpMaterial getDoublePlantMaterial(byte data) {
    KelpMaterial material = KelpMaterial.AIR;
    switch (data) {
      case 0:
        material = KelpMaterial.SUNFLOWER;
        break;
      case 1:
        material = KelpMaterial.LILAC;
        break;
      case 4:
        material = KelpMaterial.ROSE_BUSH;
        break;
      case 5:
        material = KelpMaterial.PEONY;
        break;
    }
    return material;
  }

  /**
   * Converts the given kelp block to a craftbukkit
   * block allowing for better NMS integration.
   *
   * @param block The block to convert
   * @return The final craftbukkit block
   */
  private CraftBlock craftBlock(KelpBlock block) {
    return (CraftBlock) block.getBukkitBlock();
  }

}
