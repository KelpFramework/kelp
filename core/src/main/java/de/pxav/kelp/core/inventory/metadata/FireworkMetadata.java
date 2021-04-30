package de.pxav.kelp.core.inventory.metadata;

import com.google.common.collect.Lists;
import org.bukkit.FireworkEffect;

import java.util.Arrays;
import java.util.Collection;

public class FireworkMetadata implements ItemMetadata {

  private int height = 20;
  private Collection<FireworkEffect> effects = Lists.newArrayList();

  public static FireworkMetadata create() {
    return new FireworkMetadata();
  }

  public Collection<FireworkEffect> getEffects() {
    return effects;
  }

  public int getHeight() {
    return height;
  }

  public boolean hasAnyEffects() {
    return !effects.isEmpty();
  }

  public FireworkMetadata height(int height) {
    this.height = height;
    return this;
  }

  public FireworkMetadata addEffect(FireworkEffect... effectsToAdd) {
    effects.addAll(Arrays.asList(effectsToAdd));
    return this;
  }

  public FireworkMetadata removeEffect(FireworkEffect toRemove) {
    effects.remove(toRemove);
    return this;
  }

  public FireworkMetadata clearEffects() {
    this.effects.clear();
    return this;
  }

}
