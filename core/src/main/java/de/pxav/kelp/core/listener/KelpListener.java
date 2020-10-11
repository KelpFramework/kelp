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
  private Collection<Predicate<?>> criteria = Lists.newArrayList();
  private Collection<Predicate<?>> conditionalExpires = Lists.newArrayList();
  private Consumer<Event> handler;

  private Collection<Listener> bukkitListeners = Lists.newArrayList();

  private KelpEventRepository kelpEventRepository;

  public Collection<Class<? extends Event>> getListenedEvents() {
    return listenedEvents;
  }

  public Consumer<Event> getHandler() {
    return this.handler;
  }

  public Collection<Listener> getBukkitListeners() {
    return bukkitListeners;
  }

  public KelpListener(KelpEventRepository kelpEventRepository) {
    this.kelpEventRepository = kelpEventRepository;
  }

  public void addBukkitListener(Listener listener) {
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

  public KelpListener addCriterion(Predicate<?> criterion) {
    criteria.add(criterion);
    return this;
  }

  public UUID handle(Consumer<Event> handler) {
    this.handler = handler;
    return kelpEventRepository.addListener(this);
  }

}
