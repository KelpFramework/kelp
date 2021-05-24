package de.pxav.kelp.implementation1_8.entity.type;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.ThrownPotionEntity;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.PotionVersionTemplate;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedProjectile;
import net.minecraft.server.v1_8_R3.Entity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftThrownPotion;
import org.bukkit.potion.PotionEffect;

import java.util.Collection;

public class VersionedThrownPotion extends VersionedProjectile<ThrownPotionEntity> implements ThrownPotionEntity {

  private CraftThrownPotion craftPotion;
  private PotionVersionTemplate potionVersionTemplate;

  public VersionedThrownPotion(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate, PotionVersionTemplate potionVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.potionVersionTemplate = potionVersionTemplate;
    this.craftPotion = (CraftThrownPotion) entityHandle.getBukkitEntity();
  }

  @Override
  public Collection<KelpPotionEffectType> getPotionEffects() {
    Collection<KelpPotionEffectType> output = Lists.newArrayList();
    for (PotionEffect effect : craftPotion.getEffects()) {
      output.add(potionVersionTemplate.getKelpPotion(effect.getType()));
    }
    return output;
  }

  @Override
  public KelpItem getItem() {
    return KelpItem.from(craftPotion.getItem());
  }

  @Override
  public ThrownPotionEntity setItem(KelpItem item) {
    craftPotion.setItem(item.getItemStack());
    return this;
  }

}
