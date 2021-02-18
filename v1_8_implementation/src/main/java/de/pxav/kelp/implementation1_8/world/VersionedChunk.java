package de.pxav.kelp.implementation1_8.world;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.version.Versioned;
import de.pxav.kelp.core.world.*;
import net.minecraft.server.v1_8_R3.EntityHuman;
import org.bukkit.craftbukkit.v1_8_R3.CraftChunk;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.Random;

@Versioned
public class VersionedChunk extends ChunkVersionTemplate {

  @Override
  public boolean contains(KelpChunk chunk, KelpBlock block) {
    return false;
  }

  @Override
  public KelpBlock getBlockAt(KelpChunk chunk, KelpLocation location) {
    return KelpBlock.from(chunk.getBukkitChunk().getBlock(
      location.getBlockX(),
      location.getBlockY(),
      location.getBlockZ())
    );
  }

  @Override
  public Collection<KelpPlayer> getPlayers(KelpChunk chunk, KelpLocation location) {
    Collection<KelpPlayer> output = Lists.newArrayList();
    net.minecraft.server.v1_8_R3.Chunk nmsChunk = craftChunk(chunk).getHandle();

    Arrays.stream(nmsChunk.entitySlices).forEach(entitySlice -> {
      Object[] entityArray = entitySlice.toArray();

      for (Object entityObject : entityArray) {
        if (entityObject instanceof EntityHuman) {
          EntityHuman entityHuman = (EntityHuman) entityObject;
          Player player = (Player) entityHuman.getBukkitEntity();
          KelpPlayer kelpPlayer = KelpPlayer.from(player);
          output.add(kelpPlayer);
        }
      }

    });

    return output;
  }

  @Override
  public KelpWorld getWorld(KelpChunk chunk) {
    return KelpWorld.from(chunk.getBukkitChunk().getWorld());
  }

  @Override
  public int getX(KelpChunk chunk) {
    return chunk.getBukkitChunk().getX();
  }

  @Override
  public int getZ(KelpChunk chunk) {
    return chunk.getBukkitChunk().getZ();
  }

  @Override
  public boolean isLoaded(KelpChunk chunk) {
    return chunk.getBukkitChunk().isLoaded();
  }

  @Override
  public void load(KelpChunk chunk) {
    chunk.getBukkitChunk().load();
  }

  @Override
  public void unload(KelpChunk chunk) {
    chunk.getBukkitChunk().unload();
  }

  @Override
  public boolean isSlimeChunk(KelpChunk chunk) {
    long seed = chunk.getBukkitChunk().getWorld().getSeed();
    int x = chunk.getX();
    int y = chunk.getZ();
    return (new Random(seed + ((long) x * x * 4987142) + (x * 5947611L) + (long) y * y * 4392871L + (y * 389711L) ^ 987234911L)).nextInt(10) == 0;
  }

  private CraftChunk craftChunk(KelpChunk chunk) {
    return (CraftChunk) chunk.getBukkitChunk();
  }

}
