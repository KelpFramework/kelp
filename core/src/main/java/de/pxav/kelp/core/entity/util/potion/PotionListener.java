package de.pxav.kelp.core.entity.util.potion;

import de.pxav.kelp.core.entity.LivingKelpEntity;
import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;

/**
 * This class is responsible for properly executing the {@link KelpPotionEffectType#onRemove(LivingKelpEntity)
 * remove} method of custom potion effects. When a potion effect is emulated for an older server
 * version, bukkit does not automatically remove it when a player drinks milk for example, so
 * Kelp has to check for that manually. This is important to remove the effect properly and avoid
 * memory leaks, etc.
 *
 * @author pxav
 */
public class PotionListener {

  /**
   * This method is called whenever the player 'consumes' an item such
   * as a potion or a bucket of milk.
   *
   * @param event The event to listen for.
   */
  @EventHandler(priority = EventPriority.HIGHEST)
  public void handleMilkConsume(PlayerItemConsumeEvent event) {

    // check if the player consumed a milk bucket as this is the only way
    // to manually remove a potion effect again.
    // it also checks if the event has not been canceled yet. If it is canceled,
    // another plugin has determined that the player should not be able to remove
    // their effect - Kelp won't interfere with this decision.
    if (event.getItem().getType() != Material.MILK_BUCKET || event.isCancelled()) {
      return;
    }

    KelpPlayer player = KelpPlayer.from(event.getPlayer());

    // go through all effects the player has and only remove those, which are
    // emulated (so the ones, which are no bukkit effects) and check if they are
    // removable with milk at all.
    for (KelpPotionEffectType effectType : player.getActivePotionEffectTypes()) {
      if (!effectType.isBukkitEffect() && effectType.isRemovableWithMilk()) {
        player.removePotionEffect(effectType);
      }
    }
  }

}
