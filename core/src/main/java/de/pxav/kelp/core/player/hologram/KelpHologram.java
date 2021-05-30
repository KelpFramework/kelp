package de.pxav.kelp.core.player.hologram;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.application.KelpApplication;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.hologram.component.HoloItemComponent;
import de.pxav.kelp.core.player.hologram.component.HoloTextComponent;
import de.pxav.kelp.core.player.hologram.component.HologramComponent;
import de.pxav.kelp.core.world.KelpLocation;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public class KelpHologram {

  private double lineSpaceModifier = 1;
  private KelpLocation location;
  private List<HologramComponent<?>> components = Lists.newArrayList();
  private Collection<Integer> entityIds = Lists.newArrayList();

  private HologramVersionTemplate hologramVersionTemplate;

  public static KelpHologram create() {
    return new KelpHologram(KelpPlugin.getInjector().getInstance(HologramVersionTemplate.class));
  }

  private KelpHologram(HologramVersionTemplate hologramVersionTemplate) {
    this.hologramVersionTemplate = hologramVersionTemplate;
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

  public KelpHologram show(KelpPlayer player) {
    if (location == null) {
      KelpLogger.of(KelpApplication.class).warning("Cannot spawn hologram without location! " +
        "Please check that you pass a location that is not null.");
      return this;
    }
    hologramVersionTemplate.spawnHologram(player, this);
    return this;
  }

  public KelpHologram update(KelpPlayer player) {
    hologramVersionTemplate.updateHologram(player, this);
    return this;
  }

  public KelpHologram hide(KelpPlayer player) {
    hologramVersionTemplate.despawnHologram(player, this);
    return this;
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

}
