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
import de.pxav.kelp.core.player.prompt.PromptTimeout;
import de.pxav.kelp.core.player.prompt.SimplePromptResponseHandler;
import de.pxav.kelp.core.player.prompt.anvil.AnvilPromptVersionTemplate;
import de.pxav.kelp.core.scheduler.KelpSchedulerRepository;
import de.pxav.kelp.core.scheduler.synchronize.ServerMainThread;
import de.pxav.kelp.core.scheduler.type.SchedulerFactory;
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
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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
  private SchedulerFactory schedulerFactory;
  private KelpSchedulerRepository schedulerRepository;

  private ConcurrentMap<UUID, SimplePromptResponseHandler> promptHandlers = Maps.newConcurrentMap();
  private ConcurrentMap<UUID, Runnable> onCloseRunnables = Maps.newConcurrentMap();
  private ConcurrentMap<UUID, PromptTimeout> playerTimeouts = Maps.newConcurrentMap();

  @Inject
  public VersionedAnvilPrompt(MaterialRepository materialRepository, SchedulerFactory schedulerFactory, KelpSchedulerRepository schedulerRepository) {
    this.materialRepository = materialRepository;
    this.schedulerFactory = schedulerFactory;
    this.schedulerRepository = schedulerRepository;
  }

  @Override
  public void openPrompt(Player player,
                         String initialText,
                         KelpMaterial sourceMaterial,
                         Runnable onClose,
                         PromptTimeout timeout,
                         SimplePromptResponseHandler handler) {
    CraftPlayer craftPlayer = (CraftPlayer) player;
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

    this.promptHandlers.put(player.getUniqueId(), handler);

    if (onClose != null) {
      this.onCloseRunnables.put(player.getUniqueId(), onClose);
    } else {
      this.onCloseRunnables.put(player.getUniqueId(), () -> {});
    }


    if (timeout != null) {
      UUID task = schedulerFactory.newDelayedScheduler()
        .withDelayOf(timeout.getTimeout())
        .timeUnit(timeout.getTimeUnit())
        .async()
        .run((taskId -> {
          this.removeFromCache(player.getUniqueId());
            ServerMainThread.RunParallel.run(() -> {
              if (timeout.getOnTimeout() != null && !timeout.isAsync()) {
                timeout.getOnTimeout().run();
              }

              if (timeout.shouldCloseOnTimeout()) {
                player.getOpenInventory().getTopInventory().clear();
                player.closeInventory();
              }
            });

            if (timeout.getOnTimeout() != null && timeout.isAsync()) {
              timeout.getOnTimeout().run();
            }

        }));

      timeout.setTaskId(task);
      this.playerTimeouts.put(player.getUniqueId(), timeout);
    }

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
      KelpMaterial sourceMaterial;
      if (itemStack.getDurability() != 0) {
        sourceMaterial = materialRepository.getKelpMaterial(itemStack.getType().toString(), itemStack.getDurability());
      } else {
        sourceMaterial = materialRepository.getKelpMaterial(itemStack.getType().toString());
      }

      Runnable onClose = this.onCloseRunnables.get(player.getUniqueId());
      Bukkit.getScheduler().runTaskLater(KelpPlugin.getPlugin(KelpPlugin.class), () -> {
        UUID taskId = this.playerTimeouts.get(player.getUniqueId()).getTaskId();
        this.schedulerRepository.interruptScheduler(taskId);

        this.openPrompt(player,
          displayName,
          sourceMaterial,
          onClose,
          this.playerTimeouts.get(player.getUniqueId()),
          handler);
      }, 1);

      return;
    }

    event.getClickedInventory().clear();

    UUID taskId = this.playerTimeouts.get(player.getUniqueId()).getTaskId();
    this.schedulerRepository.interruptScheduler(taskId);
    this.removeFromCache(player.getUniqueId());

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

      this.removeFromCache(player.getUniqueId());
    }

  }

  @EventHandler
  public void handlePlayerQuit(PlayerQuitEvent event) {
    Player player = event.getPlayer();
    if (this.promptHandlers.containsKey(player.getUniqueId())) {
      UUID taskId = this.playerTimeouts.get(player.getUniqueId()).getTaskId();
      this.schedulerRepository.interruptScheduler(taskId);

      this.removeFromCache(player.getUniqueId());
    }
  }

  private void removeFromCache(UUID player) {
    this.playerTimeouts.remove(player);
    this.promptHandlers.remove(player);
    this.onCloseRunnables.remove(player);
  }

}
