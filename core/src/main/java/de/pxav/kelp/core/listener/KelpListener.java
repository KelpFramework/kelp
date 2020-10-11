package de.pxav.kelp.core.listener;

import com.google.common.collect.Lists;
import org.bukkit.event.Event;
import org.bukkit.event.Listener;

import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpListener {

  private Collection<Class<? extends Event>> listenedEvents = Lists.newArrayList();
  private int maxExecutions = -1;
  private Collection<Predicate<?>> criteria = Lists.newArrayList();
  private Collection<Predicate<?>> conditionalExpires = Lists.newArrayList();
  private Consumer<Event> handler;

  private Collection<Listener> bukkitListeners = Lists.newArrayList();

  private KelpEventRepository kelpEventRepository;

  Collection<Class<? extends Event>> getListenedEvents() {
    return listenedEvents;
  }

  Consumer<Event> getHandler() {
    return this.handler;
  }

  Collection<Listener> getBukkitListeners() {
    return bukkitListeners;
  }

  int getMaxExecutions() {
    return this.maxExecutions;
  }

  KelpListener(KelpEventRepository kelpEventRepository) {
    this.kelpEventRepository = kelpEventRepository;
  }

  void addBukkitListener(Listener listener) {
    this.bukkitListeners.add(listener);
  }

  public KelpListener to(Class<? extends Event> event) {
    this.listenedEvents.add(event);
    return this;
  }

  public KelpListener expireIf(Predicate<?> conditionalExpiry) { // todo: add test stage!!!!
    conditionalExpires.add(conditionalExpiry);
    return this;
  }

  public KelpListener expireAfterExecutions(int maxExecutions) {
    this.maxExecutions = maxExecutions;
    return this;
  }

  public KelpListener addCriterion(Predicate<?> criterion) {
    criteria.add(criterion);
    return this;
  }

  public UUID handle(Consumer<Event> handler) {
    this.handler = handler;
    return kelpEventRepository.addListener(this);
  }

}
