package de.pxav.kelp.implementation1_8.player;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import de.pxav.kelp.core.common.ConcurrentMultimap;
import de.pxav.kelp.core.common.ConcurrentSetMultimap;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.hologram.HologramVersionTemplate;
import de.pxav.kelp.core.player.hologram.KelpHologram;
import de.pxav.kelp.core.player.hologram.component.EmptyTextComponent;
import de.pxav.kelp.core.player.hologram.component.HoloItemComponent;
import de.pxav.kelp.core.player.hologram.component.HoloTextComponent;
import de.pxav.kelp.core.player.hologram.component.HologramComponent;
import de.pxav.kelp.core.version.Versioned;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Versioned
public class VersionedHologram extends HologramVersionTemplate {

  private static double TITLE_LINE_HEIGHT = 0.25;
  private static ConcurrentMultimap<KelpHologram, Integer> entityIds = ConcurrentSetMultimap.create();

  @Override
  public void spawnHologram(KelpHologram hologram) {
    List<Entity> entities = Lists.newArrayList();
    Map<EntityItem, EntityArmorStand> itemBases = Maps.newHashMap();
    CraftPlayer craftPlayer = (CraftPlayer) hologram.getPlayer().getBukkitPlayer();
    WorldServer worldServer = ((CraftWorld) (craftPlayer.getWorld())).getHandle();

    for (HologramComponent<?> component : hologram.getComponents()) {
      if (component instanceof HoloItemComponent) {
        HoloItemComponent itemComponent = (HoloItemComponent) component;
        ItemStack craftItemStack = itemComponent.getItem().getItemStack();
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
        
        itemBases.put(itemEntity, holoBase);
        entities.add(itemEntity);
      }

      if (component instanceof HoloTextComponent) {
        HoloTextComponent<?> textComponent = (HoloTextComponent<?>) component;

        if (component instanceof EmptyTextComponent) {
          entities.add(null);
          continue;
        }

        for (String line : textComponent.getLines()) {
          EntityArmorStand holoBase = new EntityArmorStand(worldServer);
          holoBase.setInvisible(true);
          holoBase.setArms(false);
          holoBase.setGravity(false);
          holoBase.setSmall(true);
          holoBase.setCustomNameVisible(true);
          holoBase.setCustomName(line);
          entities.add(holoBase);
        }
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
        entityIds.put(hologram, holoBase.getId());
      }

      entityIds.put(hologram, entity.getId());
    }

  }

  @Override
  public void despawnHologram(KelpHologram hologram) {
    CraftPlayer player = (CraftPlayer) hologram.getPlayer().getBukkitPlayer();
    for (Integer entityId : entityIds.get(hologram)) {
      Packet<?> destroyPacket = new PacketPlayOutEntityDestroy(entityId);
      player.getHandle().playerConnection.sendPacket(destroyPacket);
    }
    entityIds.removeAll(hologram);
  }

  @Override
  public void updateHologram(KelpHologram hologram) {

  }

}
