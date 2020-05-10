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
  private TextAnimationFactory textAnimationFactory;

  @Inject
  public KelpInventoryFactory(WindowPacketTemplate windowPacketTemplate,
                              InventoryVersionTemplate inventoryVersionTemplate,
                              TextAnimationFactory textAnimationFactory) {
    this.windowPacketTemplate = windowPacketTemplate;
    this.inventoryVersionTemplate = inventoryVersionTemplate;
    this.textAnimationFactory = textAnimationFactory;
  }

  public AnimatedInventory newAnimatedInventory() {
    return new AnimatedInventory(windowPacketTemplate, inventoryVersionTemplate, textAnimationFactory);
  }

}
