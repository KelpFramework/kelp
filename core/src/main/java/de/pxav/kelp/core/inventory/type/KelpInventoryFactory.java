package de.pxav.kelp.core.inventory.type;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.animation.TextAnimation;
import de.pxav.kelp.core.animation.TextAnimationFactory;
import de.pxav.kelp.core.inventory.version.InventoryVersionTemplate;
import de.pxav.kelp.core.inventory.version.WindowPacketTemplate;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class KelpInventoryFactory {

  private WindowPacketTemplate windowPacketTemplate;
  private InventoryVersionTemplate inventoryVersionTemplate;

  @Inject
  public KelpInventoryFactory(WindowPacketTemplate windowPacketTemplate,
                              InventoryVersionTemplate inventoryVersionTemplate) {
    this.windowPacketTemplate = windowPacketTemplate;
    this.inventoryVersionTemplate = inventoryVersionTemplate;
  }

  public AnimatedInventory newAnimatedInventory() {
    return new AnimatedInventory(windowPacketTemplate, inventoryVersionTemplate);
  }

}
