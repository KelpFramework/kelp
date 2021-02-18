package de.pxav.kelp.implementation1_8.world;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.entity.type.DroppedItemEntity;
import de.pxav.kelp.core.entity.type.ItemDropType;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.version.Versioned;
import de.pxav.kelp.core.world.*;
import net.minecraft.server.v1_8_R3.EntityHuman;
import org.bukkit.Chunk;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.UUID;

@Versioned
public class VersionedWorld extends WorldVersionTemplate {

  @Override
  public KelpLocation getSpawnLocation(KelpWorld world) {
    return KelpLocation.from(world.getBukkitWorld().getSpawnLocation());
  }

  @Override
  public KelpChunk getChunkAt(KelpWorld world, KelpLocation location) {
    return KelpChunk.from(world.getBukkitWorld().getChunkAt(location.getBukkitLocation()));
  }

  @Override
  public boolean canGenerateStructures(KelpWorld world) {
    return world.getBukkitWorld().canGenerateStructures();
  }

  @Override
  public void createExplosion(KelpWorld world, KelpLocation location, ExplosionPower power, boolean breakBlocks, boolean igniteFire) {
    Location bukkitLocation = location.getBukkitLocation();
    world.getBukkitWorld().createExplosion(
      bukkitLocation.getX(),
      bukkitLocation.getY(),
      bukkitLocation.getZ(),
      power.getPower(),
      igniteFire,
      breakBlocks
    );
  }

  @Override
  public DroppedItemEntity dropItem(KelpWorld world, KelpLocation location, KelpItem item, ItemDropType dropType) {
    if (dropType == ItemDropType.NORMAL) {
      return DroppedItemEntity.from(world.getBukkitWorld()
        .dropItem(
          location.getBukkitLocation(),
          item.getItemStack())
        );
    } else if (dropType == ItemDropType.NATURAL) {
      return DroppedItemEntity.from(world.getBukkitWorld()
        .dropItemNaturally(
          location.getBukkitLocation(),
          item.getItemStack())
      );
    }
    return null;
  }

  @Override
  public boolean animalsAllowed(KelpWorld world) {
    return world.getBukkitWorld().getAllowAnimals();
  }

  @Override
  public boolean monstersAllowed(KelpWorld world) {
    return world.getBukkitWorld().getAllowMonsters();
  }

  @Override
  public KelpBlock getBlockAt(KelpWorld world, KelpLocation location) {
    return KelpBlock.from(world.getBukkitWorld().getBlockAt(location.getBukkitLocation()));
  }

  @Override
  public KelpBlock getHighestBlockAt(KelpWorld world, KelpLocation location) {
    return KelpBlock.from(world.getBukkitWorld().getHighestBlockAt(location.getBukkitLocation()));
  }

  @Override
  public Collection<KelpChunk> getLoadedChunks(KelpWorld world) {
    Collection<KelpChunk> chunks = Lists.newArrayList();
    for (Chunk loadedChunk : world.getBukkitWorld().getLoadedChunks()) {
      chunks.add(KelpChunk.from(loadedChunk));
    }
    return chunks;
  }

  @Override
  public Difficulty getDifficulty(KelpWorld world) {
    return world.getBukkitWorld().getDifficulty();
  }

  @Override
  public WorldType getWorldType(KelpWorld world) {
    World.Environment environment = world.getBukkitWorld().getEnvironment();
    switch (environment) {
      case NETHER:
        return WorldType.THE_NETHER;
      case THE_END:
        return WorldType.THE_END;
      case NORMAL:
        return WorldType.NORMAL;
    }
    return WorldType.NORMAL;
  }

  @Override
  public double getHumidityAt(KelpWorld world, KelpLocation location) {
    return world.getBukkitWorld().getHumidity((int) location.getX(), (int) location.getZ());
  }

  @Override
  public boolean shouldKeepSpawnInMemory(KelpWorld world) {
    return world.getBukkitWorld().getKeepSpawnInMemory();
  }

  @Override
  public void setKeepSpawnInMemory(KelpWorld world, boolean keep) {
    world.getBukkitWorld().setKeepSpawnInMemory(keep);
  }

  @Override
  public long getFullTime(KelpWorld world) {
    return world.getBukkitWorld().getFullTime();
  }

  @Override
  public long getGameTime(KelpWorld world) {
    return ((CraftWorld)world.getBukkitWorld()).getHandle().getWorldData().getTime();
  }

  @Override
  public long getTime(KelpWorld world) {
    return world.getBukkitWorld().getTime();
  }

  @Override
  public Collection<KelpPlayer> getPlayers(KelpWorld world) {
    Collection<KelpPlayer> output = Lists.newArrayList();
    for (EntityHuman human : craftWorld(world).getHandle().players) {
      HumanEntity bukkitEntity = human.getBukkitEntity();
      if (bukkitEntity instanceof Player) {
        output.add(KelpPlayer.from((Player) bukkitEntity));
      }
    }
    return output;
  }

  @Override
  public boolean isPvPEnabled(KelpWorld world) {
    return world.getBukkitWorld().getPVP();
  }

  @Override
  public void setPVP(KelpWorld world, boolean pvp) {
    world.getBukkitWorld().setPVP(pvp);
  }

  @Override
  public long getSeed(KelpWorld world) {
    return world.getBukkitWorld().getSeed();
  }

  @Override
  public int getSeaLevel(KelpWorld world) {
    return world.getBukkitWorld().getSeaLevel();
  }

  @Override
  public double getTemperatureAt(KelpWorld world, KelpLocation location) {
    return world.getBukkitWorld().getTemperature((int) location.getX(), (int) location.getZ());
  }

  @Override
  public int getThunderDuration(KelpWorld world) {
    return world.getBukkitWorld().getThunderDuration();
  }

  @Override
  public UUID getUUID(KelpWorld world) {
    return world.getBukkitWorld().getUID();
  }

  @Override
  public int getWeatherDuration(KelpWorld world) {
    return world.getBukkitWorld().getWeatherDuration();
  }

  @Override
  public boolean hasStorm(KelpWorld world) {
    return world.getBukkitWorld().hasStorm();
  }

  @Override
  public boolean hasAutoSave(KelpWorld world) {
    return world.getBukkitWorld().isAutoSave();
  }

  @Override
  public boolean isWeatherClear(KelpWorld world) {
    return !(world.getBukkitWorld().hasStorm() || world.getBukkitWorld().isThundering());
  }

  @Override
  public boolean isThundering(KelpWorld world) {
    return world.getBukkitWorld().isThundering();
  }

  @Override
  public void setDifficulty(KelpWorld world, Difficulty difficulty) {
    world.getBukkitWorld().setDifficulty(difficulty);
  }

  @Override
  public void setFullTime(KelpWorld world, long fullTime) {
    world.getBukkitWorld().setFullTime(fullTime);
  }

  @Override
  public void setSpawnLocation(KelpWorld world, KelpLocation location) {
    world.getBukkitWorld().setSpawnLocation(
      location.getBukkitLocation().getBlockX(),
      location.getBukkitLocation().getBlockY(),
      location.getBukkitLocation().getBlockZ()
    );
  }

  @Override
  public void setStorm(KelpWorld world, boolean storm) {
    world.getBukkitWorld().setStorm(storm);
  }

  @Override
  public void setThundering(KelpWorld world, boolean thunder) {
    world.getBukkitWorld().setThundering(thunder);
  }

  @Override
  public void setThunderDuration(KelpWorld world, int duration) {
    world.getBukkitWorld().setThunderDuration(duration);
  }

  @Override
  public void setTime(KelpWorld world, long time) {
    world.getBukkitWorld().setTime(time);
  }

  @Override
  public void strikeLightning(KelpWorld world, KelpLocation location, boolean effect) {
    if (effect) {
      world.getBukkitWorld().strikeLightningEffect(location.getBukkitLocation());
      return;
    }
    world.getBukkitWorld().strikeLightning(location.getBukkitLocation());
  }

  private CraftWorld craftWorld(KelpWorld world) {
    return ((CraftWorld) world.getBukkitWorld());
  }

}
