package de.pxav.kelp.core.event.listener;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.event.kelpevent.KelpPlayerEvent;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import de.pxav.kelp.core.reflect.MethodCriterion;
import de.pxav.kelp.core.reflect.MethodFinder;
import de.pxav.kelp.core.scheduler.type.DelayedScheduler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class KelpEventRepository {

  private MethodFinder methodFinder;
  private JavaPlugin javaPlugin;
  private Injector injector;
  private KelpPlayerRepository playerRepository;

  private Map<UUID, KelpListener<?>> kelpListeners;
  private Map<UUID, Integer> timesCalled;

  @Inject
  public KelpEventRepository(KelpPlayerRepository playerRepository, MethodFinder methodFinder, JavaPlugin javaPlugin, Injector injector) {
    this.playerRepository = playerRepository;
    this.methodFinder = methodFinder;
    this.javaPlugin = javaPlugin;
    this.injector = injector;

    this.kelpListeners = Maps.newHashMap();
    this.timesCalled = Maps.newHashMap();
  }

  public void detectSubscriptions(String... packageNames) {
    methodFinder.filter(packageNames, MethodCriterion.annotatedWith(Subscribes.class)).forEach(current -> {

      Subscribes annotation = current.getAnnotation(Subscribes.class);
      for (Class<? extends Event> eventType : annotation.value()) {
        Bukkit.getPluginManager()
          .registerEvent(
            (eventType),
            new Listener() {},
            EventPriority.NORMAL,
            (listener, event) -> {
              try {
                if (current.getParameters().length == 0) {
                  current.invoke(injector.getInstance(current.getDeclaringClass()));
                  return;
                }

                // auto inject for specific parameters:

                if (current.getParameters().length == 1
                    && current.getParameterTypes()[0].isAssignableFrom(KelpPlayer.class)) {
                  if (event instanceof PlayerEvent) {
                    PlayerEvent playerEvent = (PlayerEvent) event;
                    KelpPlayer kelpPlayer = playerRepository.getKelpPlayer(playerEvent.getPlayer());
                    current.invoke(injector.getInstance(current.getDeclaringClass()), kelpPlayer);
                  } else if (event instanceof KelpPlayerEvent) {
                    KelpPlayerEvent playerEvent = (KelpPlayerEvent) event;
                    current.invoke(injector.getInstance(current.getDeclaringClass()), playerEvent.getPlayer());
                  }
                }
                if (event instanceof PlayerEvent
                  && current.getParameters().length == 1
                  && current.getParameterTypes()[0].isAssignableFrom(Player.class)) {
                  PlayerEvent playerEvent = (PlayerEvent) event;
                  current.invoke(injector.getInstance(current.getDeclaringClass()), playerEvent.getPlayer());
                }

              } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
              }
            },
            javaPlugin,
            false);
      }

      KelpLogger.of(KelpApplication.class).fine("Successfully registered event @subscription for '" + current.getName() + "'");
    });
  }

  /**
   * Adds/Registers a new dynamic listener. This method automatically
   * creates the bukkit event handler, which then checks for expires
   * or criteria of the listener. This method is non-api. The listener
   * id is also returned by {@link KelpListener#handle(Consumer)} if you need
   * it.
   *
   * A listener can be unregistered with {@link KelpEventRepository#removeListener(UUID)}
   *
   * @param kelpListener The listener you want to register.
   * @return The id of the listener, which can be used later to remove it.
   */
  UUID addListener(KelpListener<?> kelpListener) {
    // generate listener id
    UUID uuid = UUID.randomUUID();

    Listener listenerInstance = new Listener() {};
    kelpListener.setBukkitListener(listenerInstance);

    Bukkit.getPluginManager()
      .registerEvent(
        (kelpListener.getEventClass()),
        listenerInstance,
        EventPriority.NORMAL,
        (listener, event) -> {

          int timesCalled = this.timesCalled.getOrDefault(uuid, 0);

          // if it has to be executed according to the minimal execution times, ignore the
          // expire conditions.

          // if min executions are ignored          or      min executions are not relevant
          if (kelpListener.getMinExecutions() == -1 || timesCalled >= kelpListener.getMinExecutions()) {
            boolean expire = kelpListener.testConditions(event, ConditionalExpiryTestStage.BEFORE_HANDLER);

            if (expire) {
              removeListener(uuid);
              return;
            }
          }

          // if one or more criteria do not match, don't handle the event.
          if (!kelpListener.testCriteria(event)) {
            return;
          }

          // if all criteria match and the listener did not expire, call the handler.
          kelpListener.triggerHandler(event);

          // check if max executions are enabled and have been reached
          if (kelpListener.getMaxExecutions() != -1
            && (timesCalled + 1) >= kelpListener.getMaxExecutions()) {
              removeListener(uuid);
              return;
          }
          this.timesCalled.put(uuid, timesCalled + 1);

          // test post-handler conditions and let the listener expire eventually.
          boolean expire = kelpListener.testConditions(event, ConditionalExpiryTestStage.AFTER_HANDLER);

          if (expire) {
            removeListener(uuid);
          }

        },
        javaPlugin,
        false);

    // scheduled expiry if enabled
    if (kelpListener.getExpireTime() != -1) {
      DelayedScheduler.create()
        .async()
        .withDelayOf(kelpListener.getExpireTime())
        .timeUnit(kelpListener.getExpireTimeUnit())
        .run(taskId -> {
          if (this.kelpListeners.containsKey(uuid)) {
            removeListener(uuid);
          }
        });
    }

    this.kelpListeners.put(uuid, kelpListener);
    return uuid;
  }

  /**
   * Removes/unregisters a dynamic listener with a specific listener id.
   * When registering a new dynamic listener using either {@link KelpListener#listen(Class)}
   * or {@link KelpEventRepository#addListener(KelpListener)}, you are returned
   * an id. This id can be used to unregister the listener, so that it won't be
   * called again. This method is called automatically if the listener expires due to
   * timeout or certain conditions you set using {@link KelpListener#expireIf(Predicate)} for example.
   *
   * @param listenerId The id of the listener you want to remove.
   */
  public void removeListener(UUID listenerId) {
    // check if there is anything to rremove.
    if (!this.kelpListeners.containsKey(listenerId)) {
      KelpLogger.of(KelpApplication.class).warning("Cannot remove non-existing listener (id: " + listenerId + ")");
      return;
    }

    KelpListener<?> kelpListener = this.kelpListeners.get(listenerId);
    try {
        // remove the listener instance saved in the KelpListener object from the event's handler list.
        Method method = kelpListener.getEventClass().getMethod("getHandlerList");
        HandlerList handlerList = (HandlerList) method.invoke(null);
        handlerList.unregister(kelpListener.getBukkitListener());
      } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    this.kelpListeners.remove(listenerId);
  }

}
