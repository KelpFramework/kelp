package de.pxav.kelp.testing.npc.gui;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.inject.Inject;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.type.AnimatedInventory;
import de.pxav.kelp.core.inventory.type.SimpleInventory;
import de.pxav.kelp.core.inventory.util.SlotArea;
import de.pxav.kelp.core.inventory.widget.PlaceholderWidget;
import de.pxav.kelp.core.inventory.widget.StatelessItemWidget;
import de.pxav.kelp.core.inventory.widget.Pagination;
import de.pxav.kelp.core.inventory.widget.ToggleableWidget;
import de.pxav.kelp.core.npc.KelpNpc;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.prompt.PromptResponseType;
import org.bukkit.ChatColor;

import java.util.concurrent.TimeUnit;

public class NpcGui {

  private static final String descriptionLine = "§8§m----------------------------";

  private TitleLineGui titleLineGui;

  @Inject
  public void injectSubMenus(TitleLineGui titleLineGui) {
    this.titleLineGui = titleLineGui;
  }

  public void open(KelpNpc npc) {
    KelpPlayer player = npc.getPlayer();
    SimpleInventory inventory = SimpleInventory.create();

    inventory.rows(4);

    inventory.addWidget(PlaceholderWidget.create().addSlots(0, 1, 2, 3));
    inventory.addWidget(PlaceholderWidget.create().addSlots(5, 6, 7, 8));
    inventory.addWidget(PlaceholderWidget.create().addSlots(SlotArea.lowerLine(36)));

    inventory.addWidget(StatelessItemWidget.create()
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
      .contentSlots(SlotArea.rectangle(9, 26))
      .contentWidgets(() -> ImmutableList.of(
        StatelessItemWidget.create()
          .item(KelpItem.create()
            .displayName("§eEdit Custom Name")
            .material(KelpMaterial.NAME_TAG)
            .addItemDescription(
              descriptionLine,
              "§7Change the NPC's custom name")
            .addGlobalListener(event -> {
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
                npc.updateCustomName();
                player.sendMessage("§aCustom name has been changed successfully.");
                return PromptResponseType.ACCEPTED;
              });
            })),
        ToggleableWidget.create()
          .condition(npc::isCustomNameShown)
          .whenTrue(KelpItem.create()
            .material(KelpMaterial.ENDER_EYE)
            .displayName("§3Custom name is shown")
            .itemDescription(
              descriptionLine,
              "§7Custom name is currently shown.",
              "§7Click here to hide.")
            .addGlow(),
            npc::hideCustomName)
          .whenFalse(KelpItem.create()
            .material(KelpMaterial.ENDER_EYE)
            .displayName("§cCustom name is hidden")
            .itemDescription(
              descriptionLine,
              "§7Custom name is currently hidden.",
              "§7Click here to show."),
            npc::showCustomName),
        StatelessItemWidget.create()
          .item(KelpItem.create()
            .material(KelpMaterial.ARMOR_STAND)
            .displayName("§eEdit title lines")
            .itemDescription(
              descriptionLine,
              "§7Click to add/remove title lines",
              "§c§lStill work in progress!")
            .addGlobalListener(event -> titleLineGui.openTitleLineEditor(npc)))
      ))
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
