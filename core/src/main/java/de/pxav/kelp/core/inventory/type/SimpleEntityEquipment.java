package de.pxav.kelp.core.inventory.type;

import de.pxav.kelp.core.inventory.item.KelpItem;

public interface SimpleEntityEquipment extends StorageInventory<SimpleEntityEquipment> {

  /**
   * Sets the helmet the player is wearing to the given item.
   * Specifically for helmets, you can not only use the normal armor items
   * such as leather helmet, gold helmet, etc. but also blocks, heads or banners
   * to set them as a player head.
   *
   * @param helmet The item of the helmet you want to set. This can be
   *               a normal armor helmet or any banner, head or even some blocks.
   * @return An instance of the current inventory for fluent builder design.
   */
  PlayerInventory setHelmet(KelpItem helmet);

  /**
   * Sets the chest plate of the player owning this inventory.
   * Unlike in the helmet method, you cannot pass other item materials
   * than chest plates here.
   *
   * @param chestPlate The chest plate item you want to set.
   * @return An instance of the current inventory for fluent builder design.
   */
  PlayerInventory setChestPlate(KelpItem chestPlate);

  /**
   * Sets the leggings of the player owning this inventory.
   * Unlike in the helmet method, you cannot pass other item materials
   * than leggings here.
   *
   * @param leggings The leggings item you want to set.
   * @return An instance of the current inventory for fluent builder design.
   */
  PlayerInventory setLeggings(KelpItem leggings);

  /**
   * Sets the boots of the player owning this inventory.
   * Unlike in the helmet method, you cannot pass other item materials
   * than boots here.
   *
   * @param boots The chest plate item you want to set.
   * @return An instance of the current inventory for fluent builder design.
   */
  PlayerInventory setBoots(KelpItem boots);

  /**
   * Gets the helmet the player is currently wearing.
   * Unlike the other armor parts, the helmet can also be of
   * materials other than normal helmets, but also banners and some
   * blocks.
   *
   * If the player has no helmet, {@code null} will be returned.
   *
   * @return The item representing the player's helmet.
   */
  KelpItem getHelmet();

  /**
   * Gets the chest plate the player is currently wearing.
   *
   * If the player has no chest plate, {@code null} will be returned.
   *
   * @return The item representing the player's chest plate.
   */
  KelpItem getChestPlate();

  /**
   * Gets the leggings the player is currently wearing.
   *
   * If the player has no leggings, {@code null} will be returned.
   *
   * @return The item representing the player's chest plate.
   */
  KelpItem getLeggings();

  /**
   * Gets the boots the player is currently wearing.
   *
   * If the player has no boots, {@code null} will be returned.
   *
   * @return The item representing the player's chest plate.
   */
  KelpItem getBoots();

  /**
   * Gets the item that is currently stored in the player's
   * main hand (the player's right hand).
   *
   * If the player has no item in hand, {@code null} will be returned.
   *
   * @return The item the player is holding right now.
   */
  KelpItem getItemInHand();

  /**
   * Sets the item in the player's main hand (the player's right hand).
   *
   * Unlike the off hand, the main hand can be used in all server versions.
   *
   * @param item The item to set in the player's main hand.
   * @return An instance of the current inventory for fluent builder design.
   */
  PlayerInventory setItemInHand(KelpItem item);

  /**
   * Gets the item that is currently in the player's off hand
   * (the player's left hand).
   *
   * Please note that off hands were introduced in {code MC 1.9} and are
   * therefore not available on 1.8 servers. But this method won't throw
   * an error if you do it anyways.
   *
   * If the player has no item in his off hand, {@code null} will be returned.
   *
   * @return The item that is stored in the player's off hand right now.
   */
  KelpItem getItemInOffHand();

  /**
   * Sets the item in the player's off hand (the player's left hand).
   *
   * Please note that off hands were introduced in {code MC 1.9} and are
   * therefore not available on 1.8 servers. But this method won't throw
   * an error if you do it anyways.
   *
   * @param item The item to set in the player's off hand.
   * @return An instance of the current inventory for fluent builder design.
   */
  PlayerInventory setItemInOffHand(KelpItem item);

}
