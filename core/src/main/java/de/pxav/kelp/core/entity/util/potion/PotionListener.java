package de.pxav.kelp.core.entity.util.potion;

import de.pxav.kelp.core.player.KelpPlayer;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerItemConsumeEvent;

public class PotionListener {

  @EventHandler(priority = EventPriority.HIGHEST)
  public void handleMilkConsume(PlayerItemConsumeEvent event) {
    if (event.getItem().getType() != Material.MILK_BUCKET || event.isCancelled()) {
      return;
    }

    KelpPlayer player = KelpPlayer.from(event.getPlayer());

    for (KelpPotionEffectType effectType : player.getActivePotionEffectTypes()) {
      if (!effectType.isBukkitEffect() && effectType.isRemovableWithMilk()) {
        player.removePotionEffect(effectType);
      }
    }

  }

}
