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
import java.util.function.Consumer;
import java.util.function.Predicate;

public class KelpListener<T extends Event> {

  private int maxExecutions = -1;
  private int minExecutions = -1;
  private ConcurrentMap<ConditionalExpiryTestStage, Predicate<? super T>> conditionalExpires;
  private Collection<Predicate<? super T>> filters;
  private Consumer<? super T> handler;

  private Listener bukkitListener;

  private KelpEventRepository kelpEventRepository;

  private Class<T> eventClass;

  public KelpListener(Class<T> eventClass, KelpEventRepository eventRepository) {
    this.eventClass = eventClass;
    this.kelpEventRepository = eventRepository;
    this.conditionalExpires = Maps.newConcurrentMap();
    this.filters = Lists.newArrayList();
  }

  public static <T extends Event> KelpListener<T> listen(Class<T> event) {
    return new KelpListener<>(event, KelpPlugin.getInjector().getInstance(KelpEventRepository.class));
  }

  ConcurrentMap<ConditionalExpiryTestStage, Predicate<? super T>> getConditionalExpires() {
    return this.conditionalExpires;
  }

  public void triggerHandler(Event event) {
    T type = (T) event;
    this.handler.accept(type);
  }

  Listener getBukkitListener() {
    return bukkitListener;
  }

  public void setBukkitListener(Listener bukkitListener) {
    this.bukkitListener = bukkitListener;
  }

  public Class<T> getEventClass() {
    return eventClass;
  }

  int getMaxExecutions() {
    return this.maxExecutions;
  }

  int getMinExecutions() {
    return minExecutions;
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
   * Tests all the given filters of the listener and checks whether
   * the event should be handled right now.
   *
   * @param eventPost The event to check the filters against.
   * @return  {@code true} if all filters match and the event can be handled.
   *          {@code false} if at least one filter does not match.
   */
  public boolean testFilters(Event eventPost) {
    T eventCheck = (T) eventPost;

    for (Predicate<? super T> filter : filters) {
      if (!filter.test(eventCheck)) {
        return false;
      }
    }

    return true;
  }

  public KelpListener<T> expireIf(Predicate<? super T> conditionalExpiry) {
    this.conditionalExpires.put(ConditionalExpiryTestStage.BEFORE_HANDLER, conditionalExpiry);
    return this;
  }

  public KelpListener<T> expireIf(Predicate<? super T> conditionalExpiry, ConditionalExpiryTestStage conditionalExpiryTestStage) {
    this.conditionalExpires.put(conditionalExpiryTestStage, conditionalExpiry);
    return this;
  }

  public KelpListener<T> filter(Predicate<? super T> condition) {
    this.filters.add(condition);
    return this;
  }

  public KelpListener<T> expireAfterExecutions(int maxExecutions) {
    this.maxExecutions = maxExecutions;
    return this;
  }

  public KelpListener<T> minimalExecutions(int minExecutions) {
    this.minExecutions = minExecutions;
    return this;
  }

  public UUID handle(Consumer<? super T> handler) {
    this.handler = handler;
    return kelpEventRepository.addListener(this);
  }

}
