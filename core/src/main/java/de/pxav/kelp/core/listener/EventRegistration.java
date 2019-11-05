package de.pxav.kelp.core.listener;

import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import de.pxav.kelp.core.reflect.MethodCriterion;
import de.pxav.kelp.core.reflect.MethodFinder;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.InvocationTargetException;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class EventRegistration {

  private final MethodFinder methodSearcher;
  private final JavaPlugin javaPlugin;
  private final Injector injector;

  @Inject
  public EventRegistration(MethodFinder methodSearcher,
                           JavaPlugin javaPlugin,
                           Injector injector) {
    this.methodSearcher = methodSearcher;
    this.javaPlugin = javaPlugin;
    this.injector = injector;
  }

  public void initialize(String... packageNames) {
    Preconditions.checkNotNull(packageNames);
    this.methodSearcher
            .filter(packageNames, MethodCriterion.annotatedWith(EventHandler.class))
            .forEach(
                    method -> {
                      System.out.println("registered eventhandler " + method.getName());
                      EventHandler handler = method.getAnnotation(EventHandler.class);
                      Bukkit.getPluginManager()
                              .registerEvent(
                                      ((Class<? extends Event>) method.getParameterTypes()[0]),
                                      new Listener() {},
                                      handler.priority(),
                                      (listener, event) -> {
                                        try {
                                          if (method.getParameterTypes()[0].isAssignableFrom(event.getClass())) {
                                            method.invoke(injector.getInstance(method.getDeclaringClass()), event);
                                          }
                                        } catch (IllegalAccessException | InvocationTargetException e) {
                                          e.printStackTrace();
                                        }
                                      },
                                      javaPlugin,
                                      handler.ignoreCancelled());
                    });
  }

}
