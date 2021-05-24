package de.pxav.kelp.implementation1_8.entity.type;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.FishHookEntity;
import de.pxav.kelp.core.entity.util.FishHookState;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedProjectile;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;

import java.util.List;

public class VersionedFishHook extends VersionedProjectile<FishHookEntity> implements FishHookEntity {

  private EntityFishingHook fishingHook;

  public VersionedFishHook(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.fishingHook = (EntityFishingHook) entityHandle;
  }

  @Override
  public KelpEntity<?> getOwner() {
    return entityTypeVersionTemplate.getKelpEntity(fishingHook.owner.getBukkitEntity());
  }

  @Override
  public int getWaitTimeInTicks() {
    return (int) ReflectionUtil.getValue(fishingHook, "aw");
  }

  @Override
  public FishHookEntity setWaitTime(int waitTicks) {
    ReflectionUtil.setValue(fishingHook, "aw", waitTicks);
    return this;
  }

  // cannot be changed in 1.8, hardcoded there
  @Override
  public int getMinWaitTime() {
    return 100;
  }

  // cannot be changed in 1.8, hardcoded there
  @Override
  public FishHookEntity setMinWaitTime(int minWaitTime) {
    return this;
  }

  // hardcoded by minecraft :(
  @Override
  public int getMaxWaitTime() {
    return 900;
  }

  @Override
  public FishHookEntity setMaxWaitTime(int maxWaitTime) {
    return this;
  }

  @Override
  public boolean hasLure() {
    int lureLevel = EnchantmentManager.h(fishingHook.owner);
    return lureLevel > 0;
  }

  @Override
  public FishHookEntity setApplyLure(boolean applyLure) {
    ItemStack itemInHand = fishingHook.owner.bZ();

    if (applyLure && !hasLure()) {
      itemInHand.addEnchantment(Enchantment.LURE, 1);
    } else if (!applyLure && hasLure()) {
      NBTTagList nbttaglist = itemInHand.getTag().getList("ench", 10);
      List<NBTBase> list = (List<NBTBase>) ReflectionUtil.getValue(nbttaglist, "list");
      for (int i = 0; i < list.size(); i++) {
        NBTTagCompound compound = (NBTTagCompound) list.get(i);
        if (compound.getShort("id") == (short) Enchantment.LURE.id) {
          nbttaglist.a(i);
        }
      }
    }

    return this;
  }

  // not possible yet
  @Override
  public boolean isInOpenWater() {
    return false;
  }

  @Override
  public KelpEntity<?> getHookedEntity() {
    return entityTypeVersionTemplate.getKelpEntity(fishingHook.hooked.getBukkitEntity());
  }

  @Override
  public FishHookEntity setHookedEntity(KelpEntity<?> hookedEntity) {
    fishingHook.hooked = ((CraftEntity) (hookedEntity.getBukkitEntity())).getHandle();
    return this;
  }

  @Override
  public FishHookEntity pullHookedEntity() {
    double deltaX = fishingHook.owner.locX - fishingHook.locX;
    double deltaY = fishingHook.owner.locY - fishingHook.locY;
    double deltaZ = fishingHook.owner.locZ - fishingHook.locZ;
    double magnitude = MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ);
    double multiplier = 0.1D;

    Entity hookedEntity = fishingHook.hooked;
    hookedEntity.motX += deltaX * multiplier;
    hookedEntity = fishingHook.hooked;
    hookedEntity.motY += deltaY * multiplier + (double)MathHelper.sqrt(magnitude) * 0.08D;
    hookedEntity = fishingHook.hooked;
    hookedEntity.motZ += deltaZ * multiplier;

    return this;
  }

  @Override
  public FishHookState getFishHookState() {

    if (fishingHook.hooked != null) {
      return FishHookState.HOOKED_ENTITY;
    }

    WorldServer worldServer = (WorldServer) fishingHook.world;
    int ax = (int) ReflectionUtil.getValue(fishingHook, "ax");
    float ay = (float) ReflectionUtil.getValue(fishingHook, "ay");
    float f3 = ay * 0.017453292F;
    float f5 = MathHelper.sin(f3);
    float f4 = MathHelper.cos(f3);
    double d8 = fishingHook.locX + (double)(f5 * (float)ax * 0.1F);
    double d12 = (float)MathHelper.floor(fishingHook.getBoundingBox().b) + 1.0F;
    double d11 = fishingHook.locZ + (double)(f4 * (float)ax * 0.1F);
    Block block = worldServer.getType(new BlockPosition((int)d8, (int)d12 - 1, (int)d11)).getBlock();
    if (block == Blocks.WATER || block == Blocks.FLOWING_WATER) {
      return FishHookState.BOBBING;
    }

    return FishHookState.UNHOOKED;
  }

}
