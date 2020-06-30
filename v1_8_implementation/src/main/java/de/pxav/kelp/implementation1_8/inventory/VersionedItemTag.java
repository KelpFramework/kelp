package de.pxav.kelp.implementation1_8.inventory;

import de.pxav.kelp.core.inventory.item.ItemTagVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedItemTag extends ItemTagVersionTemplate {

  @Override
  public ItemStack tagItem(ItemStack itemStack, String key, String value) {
    net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
    NBTTagCompound nbtTagCompound = nmsItemStack.getTag();
    nbtTagCompound.setString(key, value);
    return CraftItemStack.asBukkitCopy(nmsItemStack);
  }

  @Override
  public ItemStack removeTag(ItemStack itemStack, String key) {
    net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
    NBTTagCompound nbtTagCompound = nmsItemStack.getTag();
    nbtTagCompound.remove(key);
    return CraftItemStack.asBukkitCopy(nmsItemStack);
  }

  @Override
  public boolean hasTagKey(ItemStack itemStack, String key) {
    net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
    if (nmsItemStack.getTag() == null || nmsItemStack.getTag().isEmpty()) {
      return false;
    }
    return nmsItemStack.getTag().hasKey(key);
  }

  @Override
  public String getStringValue(ItemStack itemStack, String key) {
    net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
    NBTTagCompound nbtTagCompound = nmsItemStack.getTag();
    return nbtTagCompound.getString(key);
  }

}
