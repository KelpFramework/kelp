package de.pxav.kelp.core.entity.util.potion.minecraft;

import de.pxav.kelp.core.entity.util.potion.KelpEffectRating;
import de.pxav.kelp.core.entity.util.potion.KelpPotionEffectType;
import de.pxav.kelp.core.entity.util.potion.MinecraftPotion;
import de.pxav.kelp.core.inventory.metadata.Color;
import de.pxav.kelp.core.version.KelpVersion;

/**
 * Players receive gifts and discounts from villager trades while under the effect.
 *
 * Level I Hero of the Village decreases the cost of the first item in a trade by 30%;
 * each additional level decreases the price by an additional 6.25%.
 *
 * Hero of the Village XII gives a discount of 98.75%, decreasing all prices to 1.
 * No matter how large the discount is, the final item count in the trade is
 * always at least one, never zero. In other cases, the decrement is the discount
 * ratio multiplied by the original count rounded down or rounded up if the decrement
 * is less than 1.
 *
 * Example: Level III would give a 42.5% discount. For trade with 14 emeralds as the cost,
 * the discount would be 5 emeralds (rounded down from 5.95 emeralds), for a final price of 9 emeralds.
 *
 * The formula can be written as:
 * {@code Discounted price = Initial price - max[1, Initial Price × (.0625 × (Level - 1) + 0.3)]}
 *
 * @author pxav
 */
@MinecraftPotion(since = KelpVersion.MC_1_14_0)
public class HeroOfTheVillagePotionEffect extends KelpPotionEffectType {

  @Override
  public String getName() {
    return "Hero of the Village";
  }

  @Override
  public boolean isInstant() {
    return false;
  }

  @Override
  public Color getColor() {
    return Color.fromHEX("44FF44");
  }

  @Override
  public KelpEffectRating getRating() {
    return KelpEffectRating.POSITIVE;
  }

  @Override
  public boolean isBukkitEffectUnsafe(KelpVersion version) {
    return version.isHigherThanOrEqualTo(KelpVersion.MC_1_14_0);
  }

}
