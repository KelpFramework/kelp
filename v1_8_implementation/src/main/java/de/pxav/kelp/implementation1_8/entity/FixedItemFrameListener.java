package de.pxav.kelp.implementation1_8.entity;

import com.google.common.collect.Sets;
import de.pxav.kelp.core.entity.type.ItemFrameEntity;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import javax.inject.Singleton;
import java.util.Collection;
import java.util.Set;

@Singleton
public class FixedItemFrameListener {

  private Set<ItemFrameEntity> fixedItemFrames = Sets.newHashSet();

  @EventHandler
  public void handleItemFrameInteract(PlayerInteractEntityEvent event) {
    if (!(event.getRightClicked() instanceof ItemFrame)) {
      return;
    }

    //todo implement logic

  }

  public boolean isItemFrameFixed(ItemFrameEntity itemFrameEntity) {
    return fixedItemFrames.contains(itemFrameEntity);
  }

  public void fixItemFrame(ItemFrameEntity itemFrameEntity) {
    this.fixedItemFrames.add(itemFrameEntity);
  }

  public void unfixItemFrame(ItemFrameEntity itemFrameEntity) {
    this.fixedItemFrames.remove(itemFrameEntity);
  }

}
