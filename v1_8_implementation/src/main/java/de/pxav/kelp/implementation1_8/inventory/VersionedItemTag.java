package de.pxav.kelp.implementation1_8.inventory;

import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import com.google.inject.Inject;
import de.pxav.kelp.core.inventory.item.ItemTagVersionTemplate;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

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
  public ItemStack tagItem(ItemStack itemStack, String key, Object value) {
    Preconditions.checkNotNull(key);
    Preconditions.checkNotNull(value);
    if (!isItemValid(itemStack)) {
      return null;
    }

    net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
    NBTTagCompound nbtTagCompound = nmsItemStack.getTag() == null
      ? new NBTTagCompound()
      : nmsItemStack.getTag();
    
    if (value instanceof Integer) {
      nbtTagCompound.setInt(key, (int) value);
    } else if (value instanceof Boolean) {
      nbtTagCompound.setBoolean(key, (Boolean) value);
    } else if (value instanceof Float) {
      nbtTagCompound.setFloat(key, (Float) value);
    } else if (value instanceof Double) {
      nbtTagCompound.setDouble(key, (Double) value);
    } else if (value instanceof Long) {
      nbtTagCompound.setLong(key, (Long) value);
    } else if (value instanceof int[]) {
      nbtTagCompound.setIntArray(key, (int[]) value);
    } else if (value instanceof Byte) {
      nbtTagCompound.setByte(key, (Byte) value);
    } else if (value instanceof byte[]) {
      nbtTagCompound.setByteArray(key, (byte[]) value);
    } else if (value instanceof Short) {
      nbtTagCompound.setShort(key, (Short) value);
    } else {
      nbtTagCompound.setString(key, String.valueOf(value));
    }
    
    nmsItemStack.setTag(nbtTagCompound);
    return CraftItemStack.asBukkitCopy(nmsItemStack);
  }

  @Override
  public ItemStack removeTag(ItemStack itemStack, String key) {
    if (!isItemValid(itemStack)) {
      return null;
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
    if (!isItemValid(itemStack)) {
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
    NBTTagCompound nbtTagCompound = getTagCompound(itemStack);
    return nbtTagCompound == null ? null : nbtTagCompound.getString(key);
  }

  @Override
  public int getIntegerValue(ItemStack itemStack, String key) {
    NBTTagCompound nbtTagCompound = getTagCompound(itemStack);
    return nbtTagCompound == null ? 0 : nbtTagCompound.getInt(key);
  }

  @Override
  public double getDoubleValue(ItemStack itemStack, String key) {
    NBTTagCompound nbtTagCompound = getTagCompound(itemStack);
    return nbtTagCompound == null ? 0 : nbtTagCompound.getDouble(key);
  }

  @Override
  public long getLongValue(ItemStack itemStack, String key) {
    NBTTagCompound nbtTagCompound = getTagCompound(itemStack);
    return nbtTagCompound == null ? 0 : nbtTagCompound.getLong(key);
  }

  @Override
  public float getFloatValue(ItemStack itemStack, String key) {
    NBTTagCompound nbtTagCompound = getTagCompound(itemStack);
    return nbtTagCompound == null ? 0 : nbtTagCompound.getFloat(key);
  }

  @Override
  public byte getByteValue(ItemStack itemStack, String key) {
    NBTTagCompound nbtTagCompound = getTagCompound(itemStack);
    return nbtTagCompound == null ? 0 : nbtTagCompound.getByte(key);
  }

  @Override
  public boolean getBooleanValue(ItemStack itemStack, String key) {
    NBTTagCompound nbtTagCompound = getTagCompound(itemStack);
    return nbtTagCompound != null && nbtTagCompound.getBoolean(key);
  }

  @Override
  public Object getAnyValue(ItemStack itemStack, String key) {
    if (!hasTagKey(itemStack, key)) {
      return null;
    }

    NBTTagCompound nbtTagCompound = getTagCompound(itemStack);
    NBTBase base = nbtTagCompound.get(key);
    if (base instanceof NBTTagInt) {
      return nbtTagCompound.getInt(key);
    } else if (base instanceof NBTTagByte) {
      byte value = nbtTagCompound.getByte(key);
      if (value == 1) {
        return true;
      } else if (value == 0) {
        return false;
      }
      return nbtTagCompound.getByte(key);
    } else if (base instanceof NBTTagByteArray) {
      return nbtTagCompound.getByteArray(key);
    } else if (base instanceof NBTTagDouble) {
      return nbtTagCompound.getDouble(key);
    } else if (base instanceof NBTTagFloat) {
      return nbtTagCompound.getFloat(key);
    } else if (base instanceof NBTTagIntArray) {
      return nbtTagCompound.getIntArray(key);
    } else if (base instanceof NBTTagLong) {
      return nbtTagCompound.getLong(key);
    } else if (base instanceof NBTTagShort) {
      return nbtTagCompound.getShort(key);
    } else if (base instanceof NBTTagString) {
      return nbtTagCompound.getString(key);
    }
    return null;
  }

  @Override
  public Set<String> getTagKeys(ItemStack itemStack) {
    if (!isItemValid(itemStack)) {
      return Sets.newHashSet();
    }

    net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);

    if (nmsItemStack.getTag() == null || nmsItemStack.getTag().isEmpty()) {
      return Sets.newHashSet();
    }

    NBTTagCompound nbtTagCompound = nmsItemStack.getTag();
    Map<String, NBTBase> tags = (Map<String, NBTBase>) reflectionUtil.getValue(nbtTagCompound, "map");
    return tags.keySet();
  }

  /**
   * Gets the {@link NBTTagCompound tag compound} of an item stack
   * and automatically checks if the item stack can hold tags at all.
   *
   * The tag compound holds all nbt tag data of the item.
   *
   * @param itemStack The item stack to get the tag compound of.
   * @return The {@link NBTTagCompound} of the given item stack.
   */
  private NBTTagCompound getTagCompound(ItemStack itemStack) {
    if (!isItemValid(itemStack)) {
      return null;
    }

    net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
    return nmsItemStack.getTag();
  }

  /**
   * Checks whether the given item stack is not null and if it is
   * not of type {@link de.pxav.kelp.core.inventory.material.KelpMaterial#AIR air}.
   *
   * @param itemStack The item stack to check.
   * @return Whether the item stack can hold an item tag.
   */
  private boolean isItemValid(ItemStack itemStack) {
    Preconditions.checkNotNull(itemStack);
    if (itemStack.getType() == Material.AIR) {
      logger.log(LogLevel.DEBUG, "Cannot get item tags of items with type AIR.");
      return false;
    }
    return true;
  }

}
