package de.pxav.kelp.core.sidebar;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import de.pxav.kelp.core.sidebar.type.AnimatedSidebar;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.reflect.MethodCriterion;
import de.pxav.kelp.core.reflect.MethodFinder;
import de.pxav.kelp.core.sidebar.type.KelpSidebar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * This repository class is used to manage your sidebars.
 * You can open, close and update sidebars using the
 * unique identifier which is passed in the {@code CreateSidebar}
 * annotation.
 *
 * @author pxav
 */
@Singleton
public class SidebarRepository {

  // saves the methods which build the sidebar. Identifier -> Method
  private final Map<String, Method> methods = Maps.newHashMap();

  // should the sidebar be updated asynchronously? Identifier -> async?
  private final Map<String, Boolean> asyncMode = Maps.newHashMap();

  // The sidebar which is currently opened by a player. Player -> Sidebar identifier
  private final Map<Player, String> playerSidebars = Maps.newHashMap();

  // The schedulers for the title animation of animated sidebars. Identifier -> Scheduler
  private final Map<String, ScheduledExecutorService> titleScheduler = Maps.newHashMap();

  // The current state of animation for each player. Player -> State
  private final Map<Player, Integer> animationStates = Maps.newHashMap();

  // When should the next animation state be called? Identifier -> Time in millis
  private final Map<String, Integer> titleAnimationInterval = Maps.newHashMap();

  // the identifier of the scoreboard which should be set on join.
  private String defaultScoreboard = "NONE";

  private MethodFinder methodFinder;
  private KelpLogger kelpLogger;
  private Injector injector;
  private ExecutorService executorService;

  @Inject
  public SidebarRepository(MethodFinder methodFinder,
                           KelpLogger kelpLogger,
                           Injector injector,
                           ExecutorService executorService) {
    this.methodFinder = methodFinder;
    this.kelpLogger = kelpLogger;
    this.injector = injector;
    this.executorService = executorService;
  }

  /**
   * Searches for methods annotated with a {@code CreateSidebar} annotation
   * and saves as a sidebar.
   *
   * @param packageNames The packages in which you want to search.
   * @see CreateSidebar
   */
  public void loadSidebars(String... packageNames) {
    kelpLogger.log("Loading sidebars in " + Arrays.toString(packageNames));
    this.methodFinder.filter(packageNames, MethodCriterion.annotatedWith(CreateSidebar.class))
            .forEach(method -> {
              CreateSidebar annotation = method.getAnnotation(CreateSidebar.class);
              String identifier = annotation.identifier();
              if (identifier.equalsIgnoreCase("NONE")) {
                kelpLogger.log(LogLevel.ERROR, "Sidebar identifier 'NONE' is not allowed, " +
                        "because it's reserved for the system. Please choose another name.");
                return;
              }

              if (!identifierAvailable(identifier)) {
                kelpLogger.log(LogLevel.ERROR, "Sidebar identifier " + identifier
                        + " is already in use, but identifiers must be unique!" +
                        " Please change the identifier and reload the system.");
                return;
              }

              methods.put(identifier, method);
              asyncMode.put(identifier, annotation.async());
              if (annotation.titleAnimationInterval() <= 0) {
                kelpLogger.log(LogLevel.ERROR, "Animation interval of sidebar '" + identifier
                        + "' is smaller than or equal to 0. Please change the delay to at least 1.");
                return;
              }
              titleAnimationInterval.put(identifier, annotation.titleAnimationInterval());

              if (defaultScoreboard.equalsIgnoreCase("NONE")) {
                defaultScoreboard = annotation.identifier();
              }

              kelpLogger.log("Sidebar " + identifier + " successfully loaded!");
            });
    kelpLogger.log("Loaded " + methods.size() + " in total so far.");
  }

  /**
   * Starts the schedulers for the scoreboard animations.
   *
   * Each sidebar gets an own scheduler which uses the interval
   * which is passed in the {@code CreateSidebar} annotation,
   * while animation states are linked to each player individually,
   * because players can have different animations in the same sidebar
   * when for example their name is displayed in the title:
   * §apxav -> 4 states
   * §aOpi_CAN -> 7 states
   */
  public void schedule() {
    for (Map.Entry<String, Integer> entry : Maps.newHashMap(this.titleAnimationInterval).entrySet()) {
      String identifier = entry.getKey();

      // check if the current sidebar is really an animated one.
      // if not, remove it from the collection and continue with the next one.
      if (!this.isAnimated(identifier)) {
        this.titleAnimationInterval.remove(identifier);
        continue;
      }

      // create a new thread containing a new scheduler
      ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();


      // schedule using the time passed in the annotation.
      scheduledExecutorService.scheduleAtFixedRate(() -> {
        try {

          // iterate all players on the server.
          for (Player player : Bukkit.getOnlinePlayers()) {
            int state = this.animationStates.get(player);
            if (this.playerSidebars.containsKey(player)
                    && !this.playerSidebars.get(player).equalsIgnoreCase(identifier))
              return;

            // load the sidebar for the player
            AnimatedSidebar sidebar = (AnimatedSidebar) getSidebar(identifier, player);
            Preconditions.checkNotNull(sidebar);

            // update the state. If the state index it out of bounds, reset it to 0.
            animationStates.put(player, animationStates.get(player) + 1);
            if (state >= sidebar.maxStates() - 1) {
              this.animationStates.put(player, 0);
            }

            // finally update the title.
            sidebar.updateTitleOnly(player, state);

          }

        } catch (Exception e) {
          e.printStackTrace();
        }
      }, 200L, titleAnimationInterval.get(identifier), TimeUnit.MILLISECONDS);

      // save the scheduler object in the map so that it can be canceled later.
      this.titleScheduler.put(identifier, scheduledExecutorService);
    }
  }

  /**
   * Iterates through all sidebars and cancels the animation
   * scheduler if existing.
   */
  public void interruptAnimations() {
    for (Map.Entry<String, ScheduledExecutorService> entry : this.titleScheduler.entrySet()) {
      entry.getValue().shutdown();
    }
  }

  /**
   * Open the given sidebar for the given player.
   *
   * @param identifier The identifier of the desired sidebar.
   * @param player The player who should see the sidebar.
   */
  public void openSidebar(String identifier, Player player) {
    checkAvailability(identifier);

    KelpSidebar sidebar = getSidebar(identifier, player);
    Preconditions.checkNotNull(sidebar);

    playerSidebars.put(player, identifier);
    animationStates.put(player, 0);
    sidebar.renderAndOpenSidebar(player);
  }

  /**
   * Updates the sidebar of a player.
   * The type ((a-)sync) depends on the value
   * passed in the {@code CreateSidebar} annotation
   * and will be selected automatically.
   *
   * @param player The player whose sidebar you want to update.
   */
  public void updateSidebar(Player player) {
    String identifier = playerSidebars.get(player);
    if (isAsync(identifier)) {
      this.updateSidebarAsynchronously(player);
    } else {
      this.updateSidebarSynchronously(player);
    }
  }

  /**
   * Updates the sidebar of the given player inside the
   * main thread of the server.
   *
   * @param player The player whose sidebar you want to update.
   */
  public void updateSidebarSynchronously(Player player) {
    String identifier = playerSidebars.get(player);
    checkAvailability(identifier);

    KelpSidebar kelpSidebar = getSidebar(identifier, player);
    Preconditions.checkNotNull(kelpSidebar);
    kelpSidebar.update(player);
  }

  /**
   * Updates the sidebar of the given player in a
   * separate thread.
   *
   * @param player The player whose sidebar you want to update.
   */
  public void updateSidebarAsynchronously(Player player) {
    this.executorService.execute(() -> {
      String identifier = playerSidebars.get(player);
      checkAvailability(identifier);

      KelpSidebar kelpSidebar = getSidebar(identifier, player);
      Preconditions.checkNotNull(kelpSidebar);
      kelpSidebar.update(player);
    });
  }

  /**
   * Invokes the creation method of the sidebar with the
   * given identifier and returns the result.
   *
   * @param identifier The identifier of the sidebar you want to get.
   * @param player Each sidebar method needs a player as parameter
   *               to also load player-specific data as well.
   *               So you need to give the player who should be passed
   *               as parameter.
   * @return The final sidebar object.
   */
  private KelpSidebar getSidebar(String identifier, Player player) {
    if (this.identifierAvailable(identifier)) return null;

    try {
      Method method = this.methods.get(identifier);
      return (KelpSidebar) method.invoke(injector.getInstance(method.getDeclaringClass()), player);
    } catch (IllegalAccessException | InvocationTargetException ignore) {}
    return null;
  }

  /**
   * Checks whether the requested sidebar is animated.
   * This means if the sidebar is of type {@code AnimatedSidebar}
   * ano not just {@code SimpleSidebar} for example.
   *
   * @param identifier The identifier of the sidebar you want to check.
   * @return {@code true} if the sidebar is of type {@code AnimatedSidebar}.
   * @see AnimatedSidebar
   */
  private boolean isAnimated(String identifier) {
    checkAvailability(identifier);
    Method method = this.methods.get(identifier);
    return method.getReturnType() == AnimatedSidebar.class;
  }

  /**
   * Checks if the requested identifier exits in the cache.
   * If this is false an error message is sent to the log.
   *
   * @param identifier The identifier you want to check.
   */
  private void checkAvailability(String identifier) {
    if (identifierAvailable(identifier)) {
      kelpLogger.log(LogLevel.ERROR, "Cannot access sidebar: " +
              " Sidebar with identifier " + identifier + " does not exist.");
    }
  }

  /**
   * @param identifier The identifier you want to check.
   * @return {@code true} if the identifier is not in use already.
   */
  private boolean identifierAvailable(String identifier) {
    return !methods.containsKey(identifier);
  }

  /**
   * @param identifier The identifier of the sidebar you want to check.
   * @return {@code true} if the sidebar should be handled asynchronously.
   */
  private boolean isAsync(String identifier) {
    return this.asyncMode.get(identifier);
  }

  public String getDefaultScoreboard() {
    return defaultScoreboard;
  }
}
