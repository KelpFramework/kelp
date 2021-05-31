package de.pxav.kelp.core.player.hologram;

import com.google.common.collect.Lists;
import com.google.common.hash.HashCode;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.hologram.component.HoloItemComponent;
import de.pxav.kelp.core.player.hologram.component.HoloTextComponent;
import de.pxav.kelp.core.player.hologram.component.HologramComponent;
import de.pxav.kelp.core.world.KelpLocation;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class KelpHologram {

  // unique identifier for the hologram. Do not change.
  private UUID hologramId = UUID.randomUUID();

  private KelpPlayer player;
  private double lineSpaceModifier = 1;
  private KelpLocation location;
  private List<HologramComponent<?>> components = Lists.newArrayList();
  private Collection<Integer> entityIds = Lists.newArrayList();
  private boolean spawned;
  private boolean activelyHidden;

  private HologramVersionTemplate hologramVersionTemplate;
  private HologramRepository hologramRepository;

  public static KelpHologram create() {
    return new KelpHologram(KelpPlugin.getInjector().getInstance(HologramVersionTemplate.class),
      KelpPlugin.getInjector().getInstance(HologramRepository.class));
  }

  private KelpHologram(HologramVersionTemplate hologramVersionTemplate, HologramRepository hologramRepository) {
    this.hologramVersionTemplate = hologramVersionTemplate;
    this.hologramRepository = hologramRepository;
  }

  public KelpHologram player(KelpPlayer player) {
    this.player = player;
    return this;
  }

  public KelpHologram location(KelpLocation location) {
    this.location = location;
    return this;
  }

  public KelpHologram addComponent(HologramComponent<?> component) {
    if (component.getIndex() == -1) {
      this.components.add(component);
    } else {
      this.components.add(component.getIndex(), component);
    }
    return this;
  }

  public KelpHologram removeComponent(int index) {
    this.components.remove(index);
    return this;
  }

  public KelpHologram lineSpacingModifier(double lineSpaceModifier) {
    this.lineSpaceModifier = lineSpaceModifier;
    return this;
  }

  public KelpHologram show() {
    if (location == null) {
      KelpLogger.of(KelpApplication.class).warning("Cannot spawn hologram without location! " +
        "Please check that you pass a location that is not null.");
      return this;
    }
    hologramVersionTemplate.spawnHologram(this);
    this.hologramRepository.addHologram(this);
    this.activelyHidden = false;
    this.spawned = true;
    return this;
  }

  public KelpHologram update() {
    if (activelyHidden || !spawned) {
      return this;
    }
    hologramVersionTemplate.despawnHologram(this);
    hologramVersionTemplate.spawnHologram(this);
    return this;
  }

  public KelpHologram hide() {
    this.spawned = false;
    this.activelyHidden = true;
    hologramVersionTemplate.despawnHologram(this);
    return this;
  }

  public KelpHologram despawn() {
    this.spawned = false;
    hologramVersionTemplate.despawnHologram(this);
    return this;
  }

  public KelpHologram remove() {
    spawned = false;
    hologramVersionTemplate.despawnHologram(this);
    this.hologramRepository.removeHologram(this);
    return this;
  }

  public void doTick() {

    if (player.getLocation().distanceSquared(location) < 40 * 40 && !spawned && !activelyHidden) {
      show();
    } else if (player.getLocation().distanceSquared(location) > 40 * 40 && spawned) {
      despawn();
    }

  }

  public KelpLocation getLocation() {
    return location;
  }

  public Collection<HologramComponent<?>> getComponents() {
    return components;
  }

  public Collection<Integer> getEntityIds() {
    return entityIds;
  }

  public void setEntityIds(Collection<Integer> entityIds) {
    this.entityIds = entityIds;
  }

  public double getLineSpaceModifier() {
    return lineSpaceModifier;
  }

  public KelpPlayer getPlayer() {
    return player;
  }

  @Override
  public boolean equals(Object other) {
    if (!(other instanceof KelpHologram)) {
      return false;
    }

    KelpHologram hologram = (KelpHologram) other;
    return hologram.hologramId.equals(this.hologramId);
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(37, 17).append(hologramId).toHashCode();
  }

}
