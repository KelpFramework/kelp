package de.pxav.kelp.implementation1_8.world;

import de.pxav.kelp.core.common.MathUtils;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.material.MaterialContainer;
import de.pxav.kelp.core.version.Versioned;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.version.BlockVersionTemplate;
import de.pxav.kelp.core.world.KelpBlock;
import de.pxav.kelp.core.world.KelpChunk;
import net.minecraft.server.v1_8_R3.BlockPosition;
import net.minecraft.server.v1_8_R3.BlockSapling;
import net.minecraft.server.v1_8_R3.ItemDye;
import net.minecraft.server.v1_8_R3.ItemStack;
import org.bukkit.Material;
import org.bukkit.TreeType;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.block.CraftBlock;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

import java.util.concurrent.ThreadLocalRandom;

@Versioned
public class VersionedBlock extends BlockVersionTemplate {

  @Override
  public KelpChunk getChunk(KelpBlock block) {
    return KelpChunk.from(block.getBukkitBlock().getChunk());
  }

  @Override
  public KelpLocation getLocation(KelpBlock block) {
    return KelpLocation.from(block.getBukkitBlock().getLocation());
  }

  @Override
  public KelpMaterial getMaterial(KelpBlock block) {
    // if block has a sub id
    if (block.getBukkitBlock().getData() != 0) {
      return KelpMaterial.from(block.getBukkitBlock().getType(), block.getBukkitBlock().getData());
    }

    // if the block has no special data to obey
    return KelpMaterial.from(block.getBukkitBlock().getType());
  }

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

  @Override
  public boolean canApplyBoneMeal(KelpBlock kBlock) {
    // kelp material checks!
    return false;
  }

  @Override
  public void applyBoneMeal(KelpBlock kBlock, BlockFace blockFace) {
    Block block = kBlock.getBukkitBlock();

    // cause those plant types to grow by 2 to 5 stages.
    if (kBlock.getMaterial() == KelpMaterial.POTATOES
      || kBlock.getMaterial() == KelpMaterial.CARROTS
      || craftBlock(kBlock).getType() == Material.CROPS
      || craftBlock(kBlock).getType() == Material.MELON_STEM
      || craftBlock(kBlock).getType() == Material.PUMPKIN_STEM) {
        byte data = kBlock.getBukkitBlock().getData();
        byte newData = (byte) (data + ThreadLocalRandom.current().nextInt(2, 5));
        if (newData > 7) {
          newData = (byte) 7;
        }
        kBlock.getBukkitBlock().setData(newData);
      return;
    }

    // drop item if player clicks on sunflower, lilac, peony or rose bush
    if (kBlock.getBukkitBlock().getType() == Material.DOUBLE_PLANT) {
      byte data = block.getData();
      KelpMaterial material;

      if (data == 10) {
        KelpBlock baseBlock = kBlock.getBlockBelow();
        byte baseData = baseBlock.getBukkitBlock().getData();
        material = getDoublePlantMaterial(baseData);
      } else {
        material = getDoublePlantMaterial(data);
      }

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
      if (data == 1) {
        block.setType(Material.DOUBLE_PLANT);
        block.setData((byte) 2);

        Block above = kBlock.getBlockAbove().getBukkitBlock();
        above.setType(Material.DOUBLE_PLANT);
        above.setData((byte) 10);
      }

      if (data == 2) {
        block.setType(Material.DOUBLE_PLANT);
        block.setData((byte) 3);

        Block above = kBlock.getBlockAbove().getBukkitBlock();
        above.setType(Material.DOUBLE_PLANT);
        above.setData((byte) 10);
      }
      return;
    }

    if (kBlock.getBukkitBlock().getType() == Material.SAPLING) {
      byte data = kBlock.getBukkitBlock().getData();
      boolean grow = MathUtils.perCentChance(45);

      if (!grow) {
        return;
      }

      boolean spawned = false;

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
          TreeType type = MathUtils.perCentChance(10) ? TreeType.BIG_TREE : TreeType.TREE;
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

  private CraftBlock craftBlock(KelpBlock block) {
    return (CraftBlock) block.getBukkitBlock();
  }

}
