package de.pxav.kelp.core.player;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.entity.version.EntityVersionTemplate;
import de.pxav.kelp.core.entity.version.LivingEntityVersionTemplate;
import de.pxav.kelp.core.inventory.KelpInventoryRepository;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.particle.version.ParticleVersionTemplate;
import de.pxav.kelp.core.sidebar.SidebarRepository;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

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
  private Map<UUID, KelpPlayer> kelpPlayers = Maps.newHashMap();

  // This map stores the NMS entity objects of players.
  // Those are required by the entity classes and version
  // specific, thus they are stored as a simple object in the map.
  // As an application developer you likely won't need information
  // provided by this map.
  private Map<UUID, Object> playerEntities = Maps.newHashMap();

  private PlayerVersionTemplate playerVersionTemplate;
  private SidebarRepository sidebarRepository;
  private KelpInventoryRepository inventoryRepository;
  private KelpLogger logger;
  private EntityVersionTemplate entityVersionTemplate;
  private LivingEntityVersionTemplate livingEntityVersionTemplate;
  private ParticleVersionTemplate particleVersionTemplate;

  @Inject
  public KelpPlayerRepository(PlayerVersionTemplate playerVersionTemplate,
                       KelpLogger logger,
                       SidebarRepository sidebarRepository,
                       KelpInventoryRepository inventoryRepository,
                       EntityVersionTemplate entityVersionTemplate,
                       LivingEntityVersionTemplate livingEntityVersionTemplate,
                       ParticleVersionTemplate particleVersionTemplate) {
    this.playerVersionTemplate = playerVersionTemplate;
    this.logger = logger;
    this.sidebarRepository = sidebarRepository;
    this.inventoryRepository = inventoryRepository;
    this.entityVersionTemplate = entityVersionTemplate;
    this.livingEntityVersionTemplate = livingEntityVersionTemplate;
    this.particleVersionTemplate = particleVersionTemplate;
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
    UUID uuid = playerVersionTemplate.getUniqueId(bukkitPlayer);
    return getKelpPlayer(uuid);
  }

  /**
   * Creates a new {@code KelpPlayer} instance based on the
   * player's bukkit object.
   *
   * Generally it is not recommended to create new KelpPlayer
   * instances as an application developer, because this is handled
   * by the version templates. They intercept settings packets when
   * a player sends them and put this information into a map.
   *
   * If you always create new instances it is not guaranteed that
   * this information is accessible for you, because it has never
   * been set. If you try to query it, exceptions might be thrown.
   *
   * If you do not know what you're doing, use {@code #getKelpPlayer}
   * methods instead.
   *
   * @param bukkitPlayer  The bukkit player object of the player you
   *                      want to create an instance of.
   * @return  The final {@code KelpPlayer} object. If the player is not
   *          online, {@code null} will be returned.
   */
  public KelpPlayer newKelpPlayer(Player bukkitPlayer) {
    return this.newKelpPlayerFrom(bukkitPlayer);
  }

  /**
   * Creates a new {@code KelpPlayer} instance based on the
   * player's UUID.
   *
   * Generally it is not recommended to create new KelpPlayer
   * instances as an application developer, because this is handled
   * by the version templates. They intercept settings packets when
   * a player sends them and put this information into a map.
   *
   * If you always create new instances it is not guaranteed that
   * this information is accessible for you, because it has never
   * been set. If you try to query it, exceptions might be thrown.
   *
   * If you do not know what you're doing, use {@code #getKelpPlayer}
   * methods instead.
   *
   * @param uuid The UUID of the player you want to create an
   *             instance of.
   * @return  The final {@code KelpPlayer} object. If the player is not
   *          online, {@code null} will be returned.
   */
  public KelpPlayer newKelpPlayer(UUID uuid) {
    Player bukkitPlayer = Bukkit.getPlayer(uuid);
    if (bukkitPlayer == null) {
      logger.log(LogLevel.WARNING, "Given player UUID for getting a new KelpPlayer failed." +
        "This player is not online, returning null!");
      return null;
    }
    return this.newKelpPlayerFrom(bukkitPlayer);
  }

  /**
   * Creates a new {@code KelpPlayer} instance and automatically
   * injects all dependencies needed.
   *
   * @param bukkitPlayer The bukkit player object you want to create
   *                     the {@code KelpPlayer} from.
   * @return The final {@code KelpPlayer} object.
   */
  private KelpPlayer newKelpPlayerFrom(Player bukkitPlayer) {
    return new KelpPlayer(bukkitPlayer,
      playerVersionTemplate,
      sidebarRepository,
      inventoryRepository,
      this,
      particleVersionTemplate,
      entityVersionTemplate,
      livingEntityVersionTemplate,
      playerVersionTemplate.getUniqueId(bukkitPlayer),
      entityVersionTemplate.getLocation(bukkitPlayer),
      entityVersionTemplate.getEntityId(bukkitPlayer)
    );
  }

}
