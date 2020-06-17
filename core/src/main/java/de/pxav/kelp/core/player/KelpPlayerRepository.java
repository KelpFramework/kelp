package de.pxav.kelp.core.player;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.inventory.KelpInventoryRepository;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.sidebar.SidebarRepository;
import net.minecraft.server.v1_14_R1.PacketHandshakingInListener;
import net.minecraft.server.v1_14_R1.PacketHandshakingInSetProtocol;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_14_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class KelpPlayerRepository {

  private Map<UUID, KelpPlayer> kelpPlayers = Maps.newHashMap();

  private PlayerVersionTemplate playerVersionTemplate;
  private SidebarRepository sidebarRepository;
  private KelpInventoryRepository inventoryRepository;
  private KelpLogger logger;

  @Inject
  public KelpPlayerRepository(PlayerVersionTemplate playerVersionTemplate,
                       KelpLogger logger,
                       SidebarRepository sidebarRepository,
                       KelpInventoryRepository inventoryRepository) {
    this.playerVersionTemplate = playerVersionTemplate;
    this.logger = logger;
    this.sidebarRepository = sidebarRepository;
    this.inventoryRepository = inventoryRepository;
  }

  public void addOrUpdatePlayer(UUID uuid, KelpPlayer updatedPlayer) {
    this.kelpPlayers.put(uuid, updatedPlayer);
  }

  public KelpPlayer getKelpPlayer(UUID uuid) {
    return this.kelpPlayers.getOrDefault(uuid, null);
  }

  public KelpPlayer getKelpPlayer(Player bukkitPlayer) {
    UUID uuid = playerVersionTemplate.getUniqueId(bukkitPlayer);
    return getKelpPlayer(uuid);
  }

  public KelpPlayer newKelpPlayer(Player bukkitPlayer) {
    return new KelpPlayer(bukkitPlayer,
      playerVersionTemplate,
      sidebarRepository,
      inventoryRepository);
  }

  public KelpPlayer newKelpPlayer(UUID uuid) {
    Player bukkitPlayer = Bukkit.getPlayer(uuid);
    if (bukkitPlayer == null) {
      logger.log(LogLevel.WARNING, "Given player UUID for getting a new KelpPlayer failed." +
        "This player is not online, returning null!");
      return null;
    }
    return new KelpPlayer(bukkitPlayer,
      playerVersionTemplate,
      sidebarRepository,
      inventoryRepository);
  }

}
