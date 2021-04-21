package de.pxav.kelp.implementation1_8.entity.type;

import com.google.common.collect.ImmutableList;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.ArrowEntity;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffect;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedAbstractArrow;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;

import java.util.List;

public class VersionedArrow extends VersionedAbstractArrow<ArrowEntity> implements ArrowEntity {

  public VersionedArrow(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
  }

  @Override
  public ArrowEntity addCustomEffect(Class<? extends KelpPotionEffect> effect, int tier) {
    return this;
  }

  @Override
  public boolean hasCustomEffect(Class<? extends KelpPotionEffect> effect) {
    return false;
  }

  @Override
  public ArrowEntity removeCustomEffect(Class<? extends KelpPotionEffect> effect) {
    return this;
  }

  @Override
  public ArrowEntity setColor(Color color) {
    return this;
  }

  @Override
  public Color getColor() {
    return Color.CHAT_DARK_RED;
  }

  @Override
  public List<Class<? extends KelpPotionEffect>> getCustomEffects() {
    return ImmutableList.of();
  }

  @Override
  public boolean hasCustomEffects() {
    return false;
  }

  @Override
  public int getCustomEffectTier(Class<? extends KelpPotionEffect> effect) {
    return 0;
  }

}
