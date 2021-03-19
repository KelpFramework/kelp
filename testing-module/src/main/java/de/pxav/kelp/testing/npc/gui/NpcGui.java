package de.pxav.kelp.testing.npc.gui;

import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.type.AnimatedInventory;
import de.pxav.kelp.core.inventory.widget.ItemWidget;
import de.pxav.kelp.core.inventory.widget.Pagination;
import de.pxav.kelp.core.npc.KelpNpc;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.prompt.PromptResponseType;
import de.pxav.kelp.core.scheduler.type.DelayedScheduler;
import org.bukkit.ChatColor;

import java.util.concurrent.TimeUnit;

public class NpcGui {

  private static final String descriptionLine = "§8§m----------------------------";

  public void open(KelpNpc npc) {
    KelpPlayer player = npc.getPlayer();
    AnimatedInventory inventory = AnimatedInventory.create();

    inventory.rows(4);

    inventory.addWidget(ItemWidget.create()
      .player(player)
      .item(KelpItem.create()
        .displayName("§6§lEDITING NPC")
        .material(KelpMaterial.OAK_SIGN_ITEM)
        .slot(4)
        .addItemDescription(
          "§8§m----------------------------",
          "§7Name§8: §e" + npc.getCustomName(),
          "§7Tab-Name§8: §e" + npc.getTabListName()
        )));

    inventory.addWidget(Pagination.create()
      .player(player)
      .contentSlots(
        10, 11, 12, 13, 14, 15, 16,
        19, 20, 21, 22, 23, 24, 25)
      .contentItems(
        KelpItem.create()
          .displayName("§eEdit Custom Name")
          .material(KelpMaterial.NAME_TAG)
          .addItemDescription(
            descriptionLine,
            "§7Change the NPC's custom name")
          .addListener(player, event -> {
            player.closeInventory();
            player.openAnvilPrompt()
            .initialText(npc.getCustomName().replace("§", "&"))
            .sourceMaterial(KelpMaterial.NAME_TAG)
            .withAsyncTimeout(60, TimeUnit.SECONDS, () -> {}, true)
            .handle(response -> {
              if (response.length() >= 16) {
                player.sendMessage("§cGiven name is too long. Limit is 16 chars.");
                return PromptResponseType.TRY_AGAIN;
              }

              npc.customName(ChatColor.translateAlternateColorCodes('&', response));
              player.sendMessage("§aCustom name has been changed successfully.");
              return PromptResponseType.ACCEPTED;
            });
          }),
        KelpItem.create()
          .displayName("§cNPC is attackable")
          .material(KelpMaterial.IRON_SWORD)
          .addItemDescription(
            descriptionLine,
            "§7Toggle whether the NPC is attackable or not.",
            "§c(coming soon)"
          ),
        KelpItem.create()
          .displayName("§cChange title lines")
          .material(KelpMaterial.PAINTING)
          .addItemDescription(
            descriptionLine,
            "§7Change the NPC's text lines above its head"
          ),
        KelpItem.create()
          .displayName("§eMake the NPC walk")
          .material(KelpMaterial.RAIL)
          .addItemDescription(
            descriptionLine,
            "§7Let the npc walk to a given target",
            "§7or direction. No physics are implemented",
            "§7yet."
          )
      )
      .previousButton(KelpItem.create()
          .displayName("§6§lPrevious page")
          .slot(27)
          .material(KelpMaterial.ARROW),
        () -> {}) // do nothing when on first page
      .nextButton(KelpItem.create()
        .displayName("§6§lNext page")
        .slot(35)
        .material(KelpMaterial.ARROW),
        () -> {}) // do nothing when on last page
    );

    player.openInventory(inventory);
  }

}
