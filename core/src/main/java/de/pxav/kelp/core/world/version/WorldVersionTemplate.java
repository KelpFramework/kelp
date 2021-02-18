package de.pxav.kelp.core.world.version;

import de.pxav.kelp.core.application.KelpVersionTemplate;
import de.pxav.kelp.core.entity.type.DroppedItemEntity;
import de.pxav.kelp.core.entity.type.ItemDropType;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.*;
import de.pxav.kelp.core.world.util.ExplosionPower;
import de.pxav.kelp.core.world.util.WorldType;
import org.bukkit.Difficulty;

import java.util.Collection;
import java.util.UUID;

@KelpVersionTemplate
public abstract class WorldVersionTemplate {

  public abstract KelpLocation getSpawnLocation(KelpWorld world);

  public abstract KelpChunk getChunkAt(KelpWorld world, KelpLocation location);

  public abstract boolean canGenerateStructures(KelpWorld world);

  public abstract void createExplosion(KelpWorld world, KelpLocation location, ExplosionPower power, boolean breakBlocks, boolean igniteFire);

  public abstract DroppedItemEntity dropItem(KelpWorld world, KelpLocation location, KelpItem item, ItemDropType dropType);

  public abstract boolean animalsAllowed(KelpWorld world);

  public abstract boolean monstersAllowed(KelpWorld world);

  public abstract KelpBlock getBlockAt(KelpWorld world, KelpLocation location);

  public abstract KelpBlock getHighestBlockAt(KelpWorld world, KelpLocation location);

  public abstract Collection<KelpChunk> getLoadedChunks(KelpWorld world);

  public abstract Difficulty getDifficulty(KelpWorld world);

  public abstract WorldType getWorldType(KelpWorld world);

  public abstract double getHumidityAt(KelpWorld world, KelpLocation location);

  public abstract boolean shouldKeepSpawnInMemory(KelpWorld world);

  public abstract void setKeepSpawnInMemory(KelpWorld world, boolean keep);

  public abstract long getFullTime(KelpWorld world);

  public abstract long getGameTime(KelpWorld world);

  public abstract long getTime(KelpWorld world);

  public abstract Collection<KelpPlayer> getPlayers(KelpWorld world);
  public abstract boolean isPvPEnabled(KelpWorld world);

  public abstract void setPVP(KelpWorld world, boolean pvp);

  public abstract long getSeed(KelpWorld world);

  public abstract int getSeaLevel(KelpWorld world);

  public abstract double getTemperatureAt(KelpWorld world, KelpLocation location);

  public abstract int getThunderDuration(KelpWorld world);

  public abstract UUID getUUID(KelpWorld world);

  public abstract int getWeatherDuration(KelpWorld world);

  public abstract boolean hasStorm(KelpWorld world);

  public abstract boolean hasAutoSave(KelpWorld world);

  public abstract boolean isWeatherClear(KelpWorld world);

  public abstract boolean isThundering(KelpWorld world);

  public abstract void setDifficulty(KelpWorld world, Difficulty difficulty);

  public abstract void setFullTime(KelpWorld world, long fullTime);

  public abstract void setSpawnLocation(KelpWorld world, KelpLocation location);

  public abstract void setStorm(KelpWorld world, boolean storm);

  public abstract void setThundering(KelpWorld world, boolean thunder);

  public abstract void setThunderDuration(KelpWorld world, int duration);

  public abstract void setTime(KelpWorld world, long time);

  public abstract void strikeLightning(KelpWorld world, KelpLocation location, boolean effect);

}
