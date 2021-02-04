package de.pxav.kelp.core.event.listener;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.pxav.kelp.core.KelpPlugin;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * This class provides a way to create dynamic, functional listeners
 * at runtime. You don't have to predefine specific event handlers using
 * {@link org.bukkit.event.EventHandler} methods, but you can register
 * and unregister those listeners at runtime, while benefiting from
 * features such as criteria or minimum executions, etc.
 *
 * Listeners can be created via static factory method {@link KelpListener#listen(Class)}
 * and unregistered via their {@code listenerId} using {@link KelpEventRepository#removeListener(UUID)}
 *
 * @param <T> The type of event you are listening to.
 * @author pxav
 */
public class KelpListener<T extends Event> {

  private int maxExecutions = -1;
  private int minExecutions = -1;

  private int expireTime = -1;
  private TimeUnit expireTimeUnit;

  private ConcurrentMap<ConditionalExpiryTestStage, Predicate<? super T>> conditionalExpires;
  private Collection<Predicate<? super T>> criteria;

  private Consumer<? super T> handler;

  private Listener bukkitListener;

  private KelpEventRepository kelpEventRepository;

  private Class<T> eventClass;

  public KelpListener(Class<T> eventClass, KelpEventRepository eventRepository) {
    this.eventClass = eventClass;
    this.kelpEventRepository = eventRepository;
    this.conditionalExpires = Maps.newConcurrentMap();
    this.criteria = Lists.newArrayList();
  }

  /**
   * Registers a new dynamic listener for the given event.
   *
   * @param event The class of the event to listen for.
   * @param <T>    The event type used to adjust the class to your event.
   * @return An instance of the current listener for fluent builder design.
   */
  public static <T extends Event> KelpListener<T> listen(Class<T> event) {
    return new KelpListener<>(event, KelpPlugin.getInjector().getInstance(KelpEventRepository.class));
  }

  /**
   * Calls the code that has been defined in the {@link #handle(Consumer)}
   *
   * @param event The instance of the event that has been
   *              called and should be handled now.
   */
  public void triggerHandler(Event event) {
    T type = (T) event;
    this.handler.accept(type);
  }

  /**
   * Gets the instance of the bukkit listener, which calls this dynamic listener.
   *
   *
   * @return The instance of the bukkit listener, which is responsible for this dynamic listener.
   */
  Listener getBukkitListener() {
    return bukkitListener;
  }

  /**
   * Sets the internal bukkit listener instance. When registering this dynamic listener,
   *
   *
   * @param bukkitListener  The instance of the bukkit listener under
   *                        which the event is registered.
   */
  public void setBukkitListener(Listener bukkitListener) {
    this.bukkitListener = bukkitListener;
  }

  /**
   * Gets the class of the event, which is handled by this
   * listener. If the listener is handling {@link de.pxav.kelp.core.event.kelpevent.KelpPlayerLoginEvent},
   * this method will return {@code KelpPlayerLoginEvent.class}
   *
   * @return The class of the event, which is handled by this listener.
   */
  public Class<T> getEventClass() {
    return eventClass;
  }

  /**
   * Gets the maximum amount of times this event should be handled.
   * It won't be handled after the listener code has been executed
   * this specific amount of times.
   *
   * @return The maximum amount of event handles.
   */
  int getMaxExecutions() {
    return this.maxExecutions;
  }

  /**
   * Gets the minimal amount of times this event should be handled.
   * It won't expire until this number of executions has been reached.
   *
   * @return The amount of minimal executions/handles of this listener.
   */
  int getMinExecutions() {
    return minExecutions;
  }

  /**
   * Gets the time after which the listener will expire automatically.
   * The unit for this time is provided in {@link #getExpireTimeUnit()}
   *
   * @return The amount of time to pass until the listener expires.
   */
  public int getExpireTime() {
    return expireTime;
  }

  /**
   * Gets the time unit of {@link #getExpireTime()}.
   *
   * @return The time unit after which the listener will expire.
   */
  public TimeUnit getExpireTimeUnit() {
    return expireTimeUnit;
  }

  /**
   * Tests all conditions set that would let the listener expire.
   *
   * @param eventPost     The event to check the conditions against.
   * @param currentStage  The current stage of the testing process. When the event is handled
   *                      by the {@link KelpEventRepository},
   * @return  {@code true} if at least one test has failed and the listener has to be unregistered.
   *          {@code false} if all tests have passed and the listener may remain.
   */
  public boolean testConditions(Event eventPost, ConditionalExpiryTestStage currentStage) {
    boolean output = true;

    // check whether the given event is really handled by this listener
    if (eventPost.getClass() != eventClass) {
      return false;
    }

    T toCheck = (T) eventPost;

    for (Map.Entry<ConditionalExpiryTestStage, Predicate<? super T>> entry : conditionalExpires.entrySet()) {
      ConditionalExpiryTestStage testStage = entry.getKey();

      // if the current condition is not checked in the given stage, let the test pass.
      if (currentStage != testStage && testStage != ConditionalExpiryTestStage.ALWAYS) {
        return false;
      }

      // check the condition. If it is false, let all the following tests fail as well.
      if (entry.getValue().test(toCheck)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Tests all the given criteria of the listener and checks whether
   * the event should be handled right now.
   *
   * @param eventPost The event to check the criteria against.
   * @return  {@code true} if all criteria match and the event can be handled.
   *          {@code false} if at least one criterion does not match.
   */
  public boolean testCriteria(Event eventPost) {
    T eventCheck = (T) eventPost;

    for (Predicate<? super T> criterion : criteria) {
      if (!criterion.test(eventCheck)) {
        return false;
      }
    }

    return true;
  }

  /**
   * Adds an expiry condition to the listener. This method automatically selects
   * {@link ConditionalExpiryTestStage#BEFORE_HANDLER}, which means that the condition is always
   * checked before the event is handled. It won't be checked afterwards.
   *
   * If the condition is true the listener will be unregistered and not executed in the future.
   *
   * @param conditionalExpiry The condition to check for each time before the actual handler is called.
   * @return An instance of the current listener for fluent builder design.
   */
  public KelpListener<T> expireIf(Predicate<? super T> conditionalExpiry) {
    this.conditionalExpires.put(ConditionalExpiryTestStage.BEFORE_HANDLER, conditionalExpiry);
    return this;
  }

  /**
   * Adds an expiry condition to the listener. Depending on which {@code conditionalExpiryTestStage} you have
   * selected, the given condition is checked before and after the event is handled.
   * If the condition is true the listener will be unregistered and not executed in the future.
   *
   * @param conditionalExpiry             The condition to check for before/after each event.
   * @param conditionalExpiryTestStage    When the condition should be checked (before, after, always?)
   * @return An instance of the current listener for fluent builder design.
   */
  public KelpListener<T> expireIf(Predicate<? super T> conditionalExpiry, ConditionalExpiryTestStage conditionalExpiryTestStage) {
    this.conditionalExpires.put(conditionalExpiryTestStage, conditionalExpiry);
    return this;
  }

  /**
   * Adds a criterion to the listener. The listener will only be triggered
   * if all the given criteria added by this method are fulfilled. In
   * this criteria you can check basic things such as that the item
   * of an interaction is not null or a player has a specific permission,
   * etc.
   *
   * if you have criteria to check for a listener it is recommended to
   * use this method instead of cluttering all your criteria in the
   * handle method as this increases readability.
   *
   * @param condition The condition to check for before handling the event.
   * @return An instance of the current listener for fluent builder design.
   */
  public KelpListener<T> criterion(Predicate<? super T> condition) {
    this.criteria.add(condition);
    return this;
  }

  /**
   * Makes the listener expire after a specific amount of executions.
   * That means that if you set {@code maxExecutions} to 5, the event
   * will be handled 5 times and then expire. If it expires before
   * due to a conditional expiry for example, the amount provided here
   * will be ignored.
   *
   * @param maxExecutions The maximum amount of times the event should
   *                      be handled by this listener.
   * @return An instance of the current listener for fluent builder design.
   */
  public KelpListener<T> expireAfterExecutions(int maxExecutions) {
    this.maxExecutions = maxExecutions;
    return this;
  }

  /**
   * Makes the listener expire after a given amount of time.
   * The time is counted from the moment the listener is registered.
   *
   * @param time      The time to wait before letting the event handler expire.
   * @param timeUnit  The unit of the given {@code time} attribute.
   * @return An instance of the current listener for fluent builder design.
   */
  public KelpListener<T> expireAfter(int time, TimeUnit timeUnit) {
    this.expireTime = time;
    this.expireTimeUnit = timeUnit;
    return this;
  }

  /**
   * Sets the minimal amount of times this event should be handled.
   * This ignores all expire-conditions that are set (except timed expires).
   *
   * @param minExecutions The minimal amount of times this event should be handled.
   * @return An instance of the current listener for fluent builder design.
   */
  public KelpListener<T> minimalExecutions(int minExecutions) {
    this.minExecutions = minExecutions;
    return this;
  }

  /**
   * Provides the code that should be executed to handle the given event.
   * This code is only executed if all criteria match and the listener has
   * not expired before.
   *
   * @param handler The consumer consuming the event to handle.
   * @return The {@code listenerId}, which can be used later to unregister the listener from the {@link KelpEventRepository}.
   */
  public UUID handle(Consumer<? super T> handler) {
    this.handler = handler;
    return kelpEventRepository.addListener(this);
  }

}
