package de.pxav.kelp.implementation1_8.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import de.pxav.kelp.core.inventory.item.ItemTagVersionTemplate;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_8_R3.NBTBase;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
public class VersionedItemTag extends ItemTagVersionTemplate {

  private KelpLogger logger;
  private ReflectionUtil reflectionUtil;

  @Inject
  public VersionedItemTag(KelpLogger logger, ReflectionUtil reflectionUtil) {
    this.logger = logger;
    this.reflectionUtil = reflectionUtil;
  }

  @Override
  public ItemStack tagItem(ItemStack itemStack, String key, String value) {
    Preconditions.checkNotNull(itemStack);
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(value);
    if (itemStack.getType() == Material.AIR) {
      this.logAirError();
      return itemStack;
    }

    net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
    NBTTagCompound nbtTagCompound = nmsItemStack.getTag() == null
      ? new NBTTagCompound()
      : nmsItemStack.getTag();
    nbtTagCompound.setString(key, value);
    nmsItemStack.setTag(nbtTagCompound);
    return CraftItemStack.asBukkitCopy(nmsItemStack);
  }

  @Override
  public ItemStack removeTag(ItemStack itemStack, String key) {
    Preconditions.checkNotNull(itemStack);

    if (itemStack.getType() == Material.AIR) {
      this.logAirError();
      return itemStack;
    }

    net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
    NBTTagCompound nbtTagCompound = nmsItemStack.getTag() == null
      ? new NBTTagCompound()
      : nmsItemStack.getTag();
    nbtTagCompound.remove(key);
    return CraftItemStack.asBukkitCopy(nmsItemStack);
  }

  @Override
  public boolean hasTagKey(ItemStack itemStack, String key) {
    Preconditions.checkNotNull(itemStack);
    if (itemStack.getType() == Material.AIR) {
      this.logAirError();
      return false;
    }

    net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
    if (nmsItemStack.getTag() == null || nmsItemStack.getTag().isEmpty()) {
      return false;
    }
    return nmsItemStack.getTag().hasKey(key);
  }

  @Override
  public String getStringValue(ItemStack itemStack, String key) {
    Preconditions.checkNotNull(itemStack);
    if (itemStack.getType() == Material.AIR) {
      this.logAirError();
      return null;
    }

    net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
    NBTTagCompound nbtTagCompound = nmsItemStack.getTag();
    return nbtTagCompound.getString(key);
  }

  @Override
  public Set<String> getTagKeys(ItemStack itemStack) {
    net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
    if (nmsItemStack.getTag() == null || nmsItemStack.getTag().isEmpty()) {
      return Sets.newHashSet();
    }
    NBTTagCompound nbtTagCompound = nmsItemStack.getTag();
    Map<String, NBTBase> tags = (Map<String, NBTBase>) reflectionUtil.getValue(nbtTagCompound, "map");
    return tags.keySet();
  }

  private void logAirError() {
    logger.log(LogLevel.DEBUG, "Cannot get item tags of items with type AIR.");
  }

}
