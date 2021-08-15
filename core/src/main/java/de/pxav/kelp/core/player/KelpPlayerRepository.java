package de.pxav.kelp.core.player;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.inventory.KelpInventoryRepository;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.particle.version.ParticleVersionTemplate;
import de.pxav.kelp.core.player.prompt.anvil.AnvilPromptVersionTemplate;
import de.pxav.kelp.core.player.prompt.chat.ChatPromptVersionTemplate;
import de.pxav.kelp.core.player.prompt.sign.SignPromptVersionTemplate;
import de.pxav.kelp.core.sidebar.SidebarRepository;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * This repository class basically handles the creation and
 * conversion of player objects. You can create new KelpPlayer
 * instances or get them by an identifier such as their UUID
 * or bukkit {@code Player} object.
 *
 * @author pxav
 */
@Singleton
public class KelpPlayerRepository {

  // This map stored the KelpPlayer objects of all players by their
  // UUID. This is needed to avoid new instances to be created
  // (see factory methods)
  private ConcurrentMap<UUID, KelpPlayer> kelpPlayers = Maps.newConcurrentMap();

  // This map stores the NMS entity objects of players.
  // Those are required by the entity classes and version
  // specific, thus they are stored as a simple object in the map.
  // As an application developer you likely won't need information
  // provided by this map.
  private ConcurrentMap<UUID, Object> playerEntities = Maps.newConcurrentMap();

  private KelpInventoryRepository inventoryRepository;
  private ParticleVersionTemplate particleVersionTemplate;
  private SignPromptVersionTemplate signPromptVersionTemplate;
  private AnvilPromptVersionTemplate anvilPromptVersionTemplate;
  private ChatPromptVersionTemplate chatPromptVersionTemplate;

  @Inject
  public KelpPlayerRepository(KelpInventoryRepository inventoryRepository,
                              ParticleVersionTemplate particleVersionTemplate,
                              SignPromptVersionTemplate signPromptVersionTemplate,
                              AnvilPromptVersionTemplate anvilPromptVersionTemplate,
                              ChatPromptVersionTemplate chatPromptVersionTemplate) {
    this.inventoryRepository = inventoryRepository;
    this.particleVersionTemplate = particleVersionTemplate;
    this.signPromptVersionTemplate = signPromptVersionTemplate;
    this.anvilPromptVersionTemplate = anvilPromptVersionTemplate;
    this.chatPromptVersionTemplate = chatPromptVersionTemplate;
  }

  /**
   * Sets the NMS entity object of the given player.
   *
   * @param uuid    The uuid of the player whose entity object
   *                you want to set.
   * @param entity  The actual object you want to set.
   */
  public void playerEntityObject(UUID uuid, Object entity) {
    this.playerEntities.put(uuid, entity);
  }

  /**
   * Gets the NMS entity object of the requested player.
   *
   * The output can be casted to an {@code Entity} class of the version
   * used on the server but as this makes the application version
   * dependent, it is recommended to only do this inside version
   * implementations.
   *
   * @param uuid The uuid of the player whose entity object you want to get.
   * @return The NMS entity of the player.
   */
  public Object getMinecraftEntity(UUID uuid) {
    return this.playerEntities.get(uuid);
  }

  /**
   * Adds a new {@code KelpPlayer} to the map or updates it if it
   * already exists.
   *
   * @param uuid            The UUID of the player you want to add/update.
   * @param updatedPlayer   The instance you want to store.
   */
  public void addOrUpdatePlayer(UUID uuid, KelpPlayer updatedPlayer) {
    this.kelpPlayers.put(uuid, updatedPlayer);
  }

  /**
   * Gets the stored {@code KelpPlayer} instance of the given
   * player UUID.
   *
   * It is recommended to use this method instead of creating new instances
   * if you are a normal application developer.
   *
   * @param uuid  The bukkit instance of the player you want to get.
   * @return      The final {@code KelpPlayer} object. If the player is not
   *              online, does not exist or has no data available,
   *              {@code null} will be returned.
   */
  public KelpPlayer getKelpPlayer(UUID uuid) {
    return this.kelpPlayers.getOrDefault(uuid, null);
  }

  /**
   * Gets the stored {@code KelpPlayer} instance of the given
   * bukkit player object.
   *
   * It is recommended to use this method instead of creating new instances
   * if you are a normal application developer.
   *
   * @param bukkitPlayer  The bukkit instance of the player you want to get.
   * @return              The final {@code KelpPlayer} object. If the player is not
   *                      online, does not exist or has no data available,
   *                      {@code null} will be returned.
   */
  public KelpPlayer getKelpPlayer(Player bukkitPlayer) {
    return getKelpPlayer(bukkitPlayer.getUniqueId());
  }

  /**
   * Removes the player with the given {@link UUID} from the
   * cache.
   *
   * @param uuid of the player to be removed.
   */
  public void removeKelpPlayer(UUID uuid) {
    this.playerEntities.remove(uuid);
    this.kelpPlayers.remove(uuid);
  }

}
