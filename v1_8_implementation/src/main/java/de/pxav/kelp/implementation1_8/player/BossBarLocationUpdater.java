package de.pxav.kelp.implementation1_8.player;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.player.PlayerVersionTemplate;
import de.pxav.kelp.core.scheduler.synchronize.ServerMainThread;
import de.pxav.kelp.core.scheduler.type.SchedulerFactory;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.Vector;

import javax.persistence.Entity;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class BossBarLocationUpdater {

  private ConcurrentMap<UUID, Integer> bossBarEntities = Maps.newConcurrentMap();
  private ConcurrentMap<UUID, String> bossBarMessages = Maps.newConcurrentMap();

  private SchedulerFactory schedulerFactory;

  @Inject
  public BossBarLocationUpdater(SchedulerFactory schedulerFactory) {
    this.schedulerFactory = schedulerFactory;
  }

  public void remove(UUID player) {
    if (bossBarEntities.containsKey(player)) {
      CraftPlayer craftPlayer = (CraftPlayer) Bukkit.getPlayer(player);
      PacketPlayOutEntityDestroy destroyPacket = new PacketPlayOutEntityDestroy(bossBarEntities.get(player));
      craftPlayer.getHandle().playerConnection.sendPacket(destroyPacket);
    }

    this.bossBarEntities.remove(player);
    this.bossBarMessages.remove(player);
  }

  public void add(UUID player, int entityId, String message) {
    this.bossBarEntities.put(player, entityId);
    this.bossBarMessages.put(player, message);
  }

  @EventHandler
  public void handlePlayerMove(PlayerMoveEvent event) {
    Player player = event.getPlayer();
    if (!bossBarEntities.containsKey(player.getUniqueId())) {
      return;
    }

    CraftPlayer craftPlayer = (CraftPlayer) player;
    Vector direction = craftPlayer.getLocation().getDirection();
    Location location = craftPlayer.getLocation().add(direction.multiply(40));
    String message = this.bossBarMessages.getOrDefault(player.getUniqueId(), "Custom Boss Bar Message");

    EntityWither entityWither = new EntityWither(craftPlayer.getHandle().getWorld());
    entityWither.setInvisible(true);
    entityWither.setCustomName(message);
    entityWither.setCustomNameVisible(false);
    entityWither.setLocation(location.getX(), location.getY(), location.getZ(), 0, 0);

    PacketPlayOutSpawnEntityLiving spawnPacket = new PacketPlayOutSpawnEntityLiving(entityWither);

    this.remove(player.getUniqueId());
    this.add(player.getUniqueId(), entityWither.getId(), message);

    craftPlayer.getHandle().playerConnection.sendPacket(spawnPacket);
  }

  /**
   * Scheduler based updater may lead to a laggy behaviour of smoke particles.
   * If move event lags to much, use this method.
   * reminder for @pxav ^^
   */
  public void startScheduler() {
    schedulerFactory.newRepeatingScheduler()
      .async()
      .every(1500)
      .milliseconds()
      .waitForTaskCompletion(true)
      .run((taskId) -> {

        if (bossBarEntities.isEmpty()) {
          return;
        }

        bossBarEntities.forEach((uuid, entityId) -> {
          CraftPlayer craftPlayer = (CraftPlayer) Bukkit.getPlayer(uuid);
          Vector direction = craftPlayer.getLocation().getDirection();
          Location location = craftPlayer.getLocation().add(direction.multiply(20));
          String message = this.bossBarMessages.getOrDefault(uuid, "Custom Boss Bar Message");

          EntityWither entityWither = new EntityWither(craftPlayer.getHandle().getWorld());
          entityWither.setInvisible(true);
          entityWither.setCustomName(message);
          entityWither.setCustomNameVisible(false);
          entityWither.setLocation(location.getX(), location.getY(), location.getZ(), 0, 0);

          PacketPlayOutSpawnEntityLiving spawnPacket = new PacketPlayOutSpawnEntityLiving(entityWither);

          this.remove(uuid);
          this.add(uuid, entityWither.getId(), message);

          ServerMainThread.RunParallel.run(() -> {
            craftPlayer.getHandle().playerConnection.sendPacket(spawnPacket);
          });

        });

      });
  }

  @EventHandler
  public void handlePlayerQuit(PlayerQuitEvent event) {
    this.remove(event.getPlayer().getUniqueId());
  }

}
