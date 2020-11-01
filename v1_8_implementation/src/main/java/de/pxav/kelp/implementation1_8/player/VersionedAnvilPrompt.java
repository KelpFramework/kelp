package de.pxav.kelp.implementation1_8.player;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.material.MaterialContainer;
import de.pxav.kelp.core.inventory.material.MaterialRepository;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import de.pxav.kelp.core.player.prompt.PromptResponseType;
import de.pxav.kelp.core.player.prompt.SimplePromptResponseHandler;
import de.pxav.kelp.core.player.prompt.anvil.AnvilPromptVersionTemplate;
import de.pxav.kelp.core.version.Versioned;
import io.netty.util.internal.ConcurrentSet;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentMap;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Versioned
@Singleton
public class VersionedAnvilPrompt extends AnvilPromptVersionTemplate {

  private MaterialRepository materialRepository;
  private KelpPlayerRepository playerRepository;

  private ConcurrentMap<UUID, SimplePromptResponseHandler> promptHandlers = Maps.newConcurrentMap();
  private ConcurrentMap<UUID, Runnable> onCloseRunnables = Maps.newConcurrentMap();

  @Inject
  public VersionedAnvilPrompt(MaterialRepository materialRepository, KelpPlayerRepository playerRepository) {
    this.materialRepository = materialRepository;
    this.playerRepository = playerRepository;
  }

  @Override
  public void openPrompt(KelpPlayer player,
                         String initialText,
                         KelpMaterial sourceMaterial,
                         Runnable onClose,
                         SimplePromptResponseHandler handler) {
    CraftPlayer craftPlayer = (CraftPlayer) player.getBukkitPlayer();
    EntityHuman entityHuman = craftPlayer.getHandle();

    BlockPosition blockPosition = new BlockPosition(0, 0, 0);
    ContainerAnvil containerAnvil = new ContainerAnvil(entityHuman.inventory, entityHuman.getWorld(), blockPosition, entityHuman);

    // makes the server not check whether there really is an anvil at the given block position.
    // this would make the inventory close immediately again.
    containerAnvil.checkReachable = false;

    Inventory inventory = containerAnvil.getBukkitView().getTopInventory();
    MaterialContainer materialContainer = materialRepository.getBukkitMaterial(sourceMaterial);
    ItemStack itemStack = new ItemStack(materialContainer.getBukkitMaterial(), 1, materialContainer.getSubId());
    ItemMeta itemMeta = itemStack.getItemMeta();
    itemMeta.setDisplayName(initialText);
    itemStack.setItemMeta(itemMeta);

    inventory.setItem(0, itemStack);

    int containerId = craftPlayer.getHandle().nextContainerCounter();

    PacketPlayOutOpenWindow windowPacket = new PacketPlayOutOpenWindow(containerId,
      "minecraft:anvil",
      new ChatMessage("custom invname"),
      0);

    craftPlayer.getHandle().playerConnection.sendPacket(windowPacket);

    entityHuman.activeContainer = containerAnvil;
    entityHuman.activeContainer.windowId = containerId;
    entityHuman.activeContainer.addSlotListener(craftPlayer.getHandle());

    this.promptHandlers.put(player.getUUID(), handler);
    this.onCloseRunnables.put(player.getUUID(), onClose);

  }

  @EventHandler
  public void handleAnvilClick(InventoryClickEvent event) {
    if (!(event.getWhoClicked() instanceof Player)
      || event.getClickedInventory() == null
      || event.getCurrentItem() == null
      || event.getClickedInventory().getType() != InventoryType.ANVIL
      || !promptHandlers.containsKey(event.getWhoClicked().getUniqueId())) {
        return;
    }

    event.setCancelled(true);
    Player player = (Player) event.getWhoClicked();
    SimplePromptResponseHandler handler = this.promptHandlers.get(player.getUniqueId());

    ItemStack itemStack = event.getCurrentItem();
    String displayName = itemStack.getItemMeta().getDisplayName();
    PromptResponseType responseType = handler.accept(displayName);

    if (responseType == PromptResponseType.TRY_AGAIN) {
      KelpPlayer kelpPlayer = playerRepository.getKelpPlayer(player);
      KelpMaterial sourceMaterial;
      if (itemStack.getDurability() != 0) {
        sourceMaterial = materialRepository.getKelpMaterial(itemStack.getType().toString(), itemStack.getDurability());
      } else {
        sourceMaterial = materialRepository.getKelpMaterial(itemStack.getType().toString());
      }

      Runnable onClose = this.onCloseRunnables.get(player.getUniqueId());
      Bukkit.getScheduler().runTaskLater(KelpPlugin.getPlugin(KelpPlugin.class), () -> {
        this.openPrompt(kelpPlayer,
          displayName,
          sourceMaterial,
          onClose,
          handler);
      }, 1);

      return;
    }

    event.getClickedInventory().clear();

    this.promptHandlers.remove(player.getUniqueId());
    this.onCloseRunnables.remove(player.getUniqueId());

    player.closeInventory();
  }

  @EventHandler
  public void handleAnvilClose(InventoryCloseEvent event) {
    if (!(event.getPlayer() instanceof Player)
      || event.getInventory() == null
      || event.getInventory().getType() != InventoryType.ANVIL
      || !promptHandlers.containsKey(event.getPlayer().getUniqueId())) {
      return;
    }

    event.getInventory().clear();

    Player player = (Player) event.getPlayer();

    if (this.promptHandlers.containsKey(player.getUniqueId())) {
      Runnable onClose = this.onCloseRunnables.get(player.getUniqueId());

      Bukkit.getScheduler().runTaskLater(KelpPlugin.getPlugin(KelpPlugin.class), onClose, 1);

      this.onCloseRunnables.remove(player.getUniqueId());
      this.promptHandlers.remove(player.getUniqueId());
    }

  }

}
