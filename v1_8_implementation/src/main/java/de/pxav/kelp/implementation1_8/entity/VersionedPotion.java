package de.pxav.kelp.implementation1_8.entity;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffect;
import de.pxav.kelp.core.entity.util.potion.PotionVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import org.bukkit.potion.PotionEffectType;

@Versioned
public class VersionedPotion extends PotionVersionTemplate {

  @Override
  public PotionEffectType getBukkitPotion(Class<? extends KelpPotionEffect> effectClass) {
    return getPotionMappings().get(effectClass);
  }

  @Override
  public Class<? extends KelpPotionEffect> getKelpPotion(PotionEffectType effectType) {
    return getPotionMappings().inverse().get(effectType);
  }

  private BiMap<Class<? extends KelpPotionEffect>, PotionEffectType> getPotionMappings() {
    BiMap<Class<? extends KelpPotionEffect>, PotionEffectType> output = HashBiMap.create();

    output.put(KelpPotionEffect.ABSORPTION, PotionEffectType.ABSORPTION);

    return output;
  }

}
