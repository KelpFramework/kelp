package de.pxav.kelp.sidebar;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import de.pxav.kelp.logger.KelpLogger;
import de.pxav.kelp.logger.LogLevel;
import de.pxav.kelp.reflect.MethodCriterion;
import de.pxav.kelp.reflect.MethodFinder;
import de.pxav.kelp.sidebar.type.AnimatedSidebar;
import de.pxav.kelp.sidebar.type.KelpSidebar;
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
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class SidebarRepository {

  private final Map<String, Method> methods = Maps.newHashMap();
  private final Map<String, Boolean> asyncMode = Maps.newHashMap();
  private final Map<Player, String> playerSidebars = Maps.newHashMap();
  private final Map<String, ScheduledExecutorService> titleScheduler = Maps.newHashMap();
  private final Map<Player, Integer> animationStates = Maps.newHashMap();
  private final Map<String, Integer> titleAnimationInterval = Maps.newHashMap();

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

  public void loadSidebars(String... packageNames) {
    kelpLogger.log("Loading sidebars in " + Arrays.toString(packageNames));
    this.methodFinder.filter(packageNames, MethodCriterion.annotatedWith(CreateSidebar.class))
            .forEach(method -> {
              CreateSidebar annotation = method.getAnnotation(CreateSidebar.class);
              String identifier = annotation.identifier();

              if (!identifierAvailable(identifier)) {
                kelpLogger.log(LogLevel.ERROR, "Sidebar identifier " + identifier
                        + " is already in use, but identifiers must be unique!" +
                        " Please change the identifier and reload the system.");
                return;
              }

              methods.put(identifier, method);
              asyncMode.put(identifier, annotation.async());
              titleAnimationInterval.put(identifier, annotation.titleAnimationInterval());
              kelpLogger.log("Sidebar " + identifier + " successfully loaded!");
            });
    kelpLogger.log("Loaded " + methods.size() + " in total so far.");
  }

  public void schedule() {
    for (Map.Entry<String, Integer> entry : this.titleAnimationInterval.entrySet()) {
      ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
      String identifier = entry.getKey();

      scheduledExecutorService.scheduleAtFixedRate(() -> {
        try {
          for (Player player : Bukkit.getOnlinePlayers()) {
            int state = this.animationStates.get(player);
            if (this.playerSidebars.containsKey(player)
                    && !this.playerSidebars.get(player).equalsIgnoreCase(identifier))
              return;

            KelpSidebar rawSidebar = getSidebar(identifier, player);
            AnimatedSidebar sidebar;
            if (!(rawSidebar instanceof AnimatedSidebar)) {
              break;
            }

            sidebar = (AnimatedSidebar) getSidebar(identifier, player);
            Preconditions.checkNotNull(sidebar);

            animationStates.put(player, animationStates.get(player) + 1);
            if (state >= sidebar.maxStates() - 1) {
              this.animationStates.put(player, 0);
            }

            sidebar.updateTitleOnly(player, state);

          }

        } catch (Exception e) {
          e.printStackTrace();
        }
      }, 200L, titleAnimationInterval.get(identifier), TimeUnit.MILLISECONDS);

      this.titleScheduler.put(identifier, scheduledExecutorService);
    }
  }

  public void interruptAnimations() {
    for (Map.Entry<String, ScheduledExecutorService> entry : this.titleScheduler.entrySet()) {
      entry.getValue().shutdown();
    }
  }

  public void openSidebar(String identifier, Player player) {
    checkAvailability(identifier);

    KelpSidebar sidebar = getSidebar(identifier, player);
    Preconditions.checkNotNull(sidebar);

    playerSidebars.put(player, identifier);
    animationStates.put(player, 0);
    sidebar.renderAndOpenSidebar(player);
  }

  public void updateSidebar(Player player) {
    String identifier = playerSidebars.get(player);
    if (isAsync(identifier)) {
      this.updateSidebarAsynchronously(player);
    } else {
      this.updateSidebarSynchronously(player);
    }
  }

  public void updateSidebarSynchronously(Player player) {
    String identifier = playerSidebars.get(player);
    checkAvailability(identifier);

    KelpSidebar kelpSidebar = getSidebar(identifier, player);
    Preconditions.checkNotNull(kelpSidebar);
    kelpSidebar.update(player);
  }

  public void updateSidebarAsynchronously(Player player) {
    this.executorService.execute(() -> {
      String identifier = playerSidebars.get(player);
      checkAvailability(identifier);

      KelpSidebar kelpSidebar = getSidebar(identifier, player);
      Preconditions.checkNotNull(kelpSidebar);
      kelpSidebar.update(player);
    });
  }

  private KelpSidebar getSidebar(String identifier, Player player) {
    if (this.identifierAvailable(identifier)) return null;

    try {
      Method method = this.methods.get(identifier);
      return (KelpSidebar) method.invoke(injector.getInstance(method.getDeclaringClass()), player);
    } catch (IllegalAccessException | InvocationTargetException ignore) {}
    return null;
  }

  private void checkAvailability(String identifier) {
    if (identifierAvailable(identifier)) {
      kelpLogger.log(LogLevel.ERROR, "Cannot access sidebar: " +
              " Sidebar with identifier " + identifier + " does not exist.");
    }
  }

  private boolean identifierAvailable(String identifier) {
    return !methods.containsKey(identifier);
  }

  private boolean isAsync(String identifier) {
    return this.asyncMode.get(identifier);
  }

}
