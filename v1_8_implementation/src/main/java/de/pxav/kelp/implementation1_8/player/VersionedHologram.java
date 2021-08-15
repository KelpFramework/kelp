package de.pxav.kelp.implementation1_8.player;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.pxav.kelp.core.player.hologram.*;
import de.pxav.kelp.core.player.hologram.line.EmptyHologramLine;
import de.pxav.kelp.core.player.hologram.line.HologramLine;
import de.pxav.kelp.core.player.hologram.line.ItemHologramLine;
import de.pxav.kelp.core.player.hologram.line.TextHologramLine;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Versioned
public class VersionedHologram extends HologramVersionTemplate {

  private static double TITLE_LINE_HEIGHT = 0.25;

  @Inject
  private HologramRepository hologramRepository;

  @Override
  public void spawnHologram(KelpHologram hologram) {
    List<Entity> entities = Lists.newArrayList();
    Map<EntityItem, EntityArmorStand> itemBases = Maps.newHashMap();
    CraftPlayer craftPlayer = (CraftPlayer) hologram.getPlayer().getBukkitPlayer();
    WorldServer worldServer = ((CraftWorld) (craftPlayer.getWorld())).getHandle();

    for (HologramLine<?> line : hologram.getLines()) {
      if (line instanceof ItemHologramLine) {
        ItemHologramLine itemComponent = (ItemHologramLine) line;
        ItemStack craftItemStack = itemComponent.display().getItemStack();
        net.minecraft.server.v1_8_R3.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(craftItemStack);
        EntityItem itemEntity = new EntityItem(worldServer);
        itemEntity.setCustomNameVisible(false);
        itemEntity.setItemStack(nmsItemStack);

        EntityArmorStand holoBase = new EntityArmorStand(worldServer);
        holoBase.setGravity(false);
        holoBase.setCustomNameVisible(false);
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInt("DisabledSlots", 2039589); // All slots are disabled to prevent client glitches
        holoBase.a(tag);

        int armorStandId = randomEntityId();
        int itemId = randomEntityId();
        ReflectionUtil.setValue(Entity.class, holoBase, "id", armorStandId);
        ReflectionUtil.setValue(Entity.class, itemEntity, "id", itemId);
        hologram.addEntityId(armorStandId);
        itemComponent.setItemEntityId(itemId);

        itemBases.put(itemEntity, holoBase);
        entities.add(itemEntity);
      }

      if (line instanceof TextHologramLine) {
        TextHologramLine textComponent = (TextHologramLine) line;

        EntityArmorStand holoBase = new EntityArmorStand(worldServer);
        holoBase.setInvisible(true);
        holoBase.setArms(false);
        holoBase.setGravity(false);
        holoBase.setSmall(true);
        holoBase.setCustomNameVisible(true);
        holoBase.setCustomName(textComponent.display());
        hologram.addEntityId(holoBase.getId());
        entities.add(holoBase);

      }

      if (line instanceof EmptyHologramLine) {
        entities.add(null);
      }

    }

    Collections.reverse(entities);

    double previousHeight = hologram.getLocation().getY();
    boolean previousItem = false;

    for (Entity entity : entities) {

      if (entity == null) {
        if (previousItem) {
          previousItem = false;
          previousHeight += TITLE_LINE_HEIGHT * 3.5 * hologram.getLineSpaceModifier();
        } else {
          previousHeight += TITLE_LINE_HEIGHT * hologram.getLineSpaceModifier();
        }
        continue;
      }

      if (entity instanceof EntityArmorStand) {
        if (previousItem) {
          previousItem = false;
          previousHeight += TITLE_LINE_HEIGHT * 3.5 * hologram.getLineSpaceModifier();
        } else {
          previousHeight += TITLE_LINE_HEIGHT * hologram.getLineSpaceModifier();
        }
        EntityArmorStand holoBase = (EntityArmorStand) entity;
        entity.setLocation(hologram.getLocation().getX(), previousHeight, hologram.getLocation().getZ(), 0, 0);
        PacketPlayOutSpawnEntityLiving spawnPacket = new PacketPlayOutSpawnEntityLiving(holoBase);
        craftPlayer.getHandle().playerConnection.sendPacket(spawnPacket);
      } else if (entity instanceof EntityItem) {
        if (previousItem) {
          previousHeight += .50 * hologram.getLineSpaceModifier();
        } else {
          previousHeight += 0.04 * hologram.getLineSpaceModifier();
        }
        previousItem = true;
        EntityItem itemEntity = (EntityItem) entity;
        EntityArmorStand holoBase = itemBases.get(itemEntity);
        holoBase.setLocation(hologram.getLocation().getX(), previousHeight, hologram.getLocation().getZ(), 0, 0);
        itemEntity.setLocation(holoBase.locX, holoBase.locY, holoBase.locZ, 0f, 0f);

        PacketPlayOutSpawnEntity spawnPacket = new PacketPlayOutSpawnEntity(itemEntity, 2);
        PacketPlayOutSpawnEntityLiving armorStandSpawnPacket = new PacketPlayOutSpawnEntityLiving(holoBase);
        PacketPlayOutEntityMetadata metadataPacket = new PacketPlayOutEntityMetadata(itemEntity.getId(), itemEntity.getDataWatcher(), true);
        PacketPlayOutEntityMetadata armorStandMetadataPacket = new PacketPlayOutEntityMetadata(holoBase.getId(), holoBase.getDataWatcher(), true);

        PacketPlayOutAttachEntity passengerPacket = new PacketPlayOutAttachEntity(0, itemEntity, holoBase);

        craftPlayer.getHandle().playerConnection.sendPacket(spawnPacket);
        craftPlayer.getHandle().playerConnection.sendPacket(metadataPacket);
        craftPlayer.getHandle().playerConnection.sendPacket(armorStandSpawnPacket);
        craftPlayer.getHandle().playerConnection.sendPacket(passengerPacket);

        holoBase.setInvisible(true);
        craftPlayer.getHandle().playerConnection.sendPacket(armorStandMetadataPacket);
      }

    }

  }

  @Override
  public void despawnHologram(KelpHologram hologram) {
    CraftPlayer player = (CraftPlayer) hologram.getPlayer().getBukkitPlayer();

    for (HologramLine<?> line : hologram.getSpawnedLines()) {
      if (line instanceof ItemHologramLine) {
        ItemHologramLine itemLine = (ItemHologramLine) line;
        Packet<?> destroyPacket = new PacketPlayOutEntityDestroy(itemLine.getItemEntityId());
        player.getHandle().playerConnection.sendPacket(destroyPacket);
      }
    }

    for (Integer entityId : hologram.getEntityIds()) {
      Packet<?> destroyPacket = new PacketPlayOutEntityDestroy(entityId);
      player.getHandle().playerConnection.sendPacket(destroyPacket);
    }

    hologram.getSpawnedLines().clear();

  }

  @Override
  public void updateHologram(KelpHologram hologram) {


  }

  private int randomEntityId() {
    return ThreadLocalRandom.current().nextInt(20_000, 60_000);
  }

}
