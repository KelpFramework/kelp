package de.pxav.kelp.core.listener;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.reflect.MethodCriterion;
import de.pxav.kelp.core.reflect.MethodFinder;
import de.pxav.kelp.core.reflect.TypeFinder;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.UUID;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class KelpEventRepository {

  private TypeFinder typeFinder;
  private MethodFinder methodFinder;
  private JavaPlugin javaPlugin;
  private Injector injector;
  private KelpLogger logger;

  private Map<UUID, KelpListener> kelpListeners;
  private Map<UUID, Listener> listeners;

  @Inject
  public KelpEventRepository(TypeFinder typeFinder, MethodFinder methodFinder, JavaPlugin javaPlugin, Injector injector, KelpLogger logger) {
    this.typeFinder = typeFinder;
    this.methodFinder = methodFinder;
    this.javaPlugin = javaPlugin;
    this.injector = injector;
    this.logger = logger;

    this.kelpListeners = Maps.newHashMap();
    this.listeners = Maps.newHashMap();
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
                } else if (current.getParameters().length == 1 && current.getParameterTypes()[0].isAssignableFrom(KelpPlayer.class)) {
                  // invoke with event player as parameter
                }

              } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
              }
            },
            javaPlugin,
            false);
      }

      logger.log(LogLevel.DEBUG, "Successfully registered event @subscription for '" + current.getName() + "'");
    });
  }

  public KelpListener listen() {
    return new KelpListener(this);
  }

  UUID addListener(KelpListener kelpListener) {
    UUID uuid = UUID.randomUUID();
    for (Class<? extends Event> listenedEvent : kelpListener.getListenedEvents()) {
      Listener listenerInstance = new Listener() {};
      this.listeners.put(uuid, listenerInstance);
      Bukkit.getPluginManager()
        .registerEvent(
          (listenedEvent),
          listenerInstance,
          EventPriority.NORMAL,
          (listener, event) -> {
            kelpListener.getHandler().accept(event);
          },
          javaPlugin,
          false);
    }
    this.kelpListeners.put(uuid, kelpListener);
    return uuid;
  }

  public void removeListener(UUID listenerId) {
    KelpListener kelpListener = this.kelpListeners.get(listenerId);
    kelpListener.getListenedEvents().forEach((eventClass) -> {
      try {
        Method method = eventClass.getMethod("getHandlerList");
        HandlerList handlerList = (HandlerList) method.invoke(null);
        kelpListener.getBukkitListeners().forEach(handlerList::unregister);
      } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
        e.printStackTrace();
      }
    });
  }

  public void detectKelpEvents(String... packageNames) {

  }

}
