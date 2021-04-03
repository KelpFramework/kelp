package de.pxav.kelp.testing.npc.gui;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.inventory.material.KelpMaterial;
import de.pxav.kelp.core.inventory.type.SimpleInventory;
import de.pxav.kelp.core.inventory.util.SlotArea;
import de.pxav.kelp.core.inventory.widget.Pagination;
import de.pxav.kelp.core.inventory.widget.SimpleWidget;
import de.pxav.kelp.core.inventory.widget.StatelessItemWidget;
import de.pxav.kelp.core.npc.KelpNpc;

import java.util.Collection;
import java.util.List;

public class TitleLineGui {

  private NpcGui npcGui;

  @Inject
  public void setNpcGui(NpcGui npcGui) {
    this.npcGui = npcGui;
  }

  public void openTitleLineEditor(KelpNpc npc) {
    SimpleInventory inventory = SimpleInventory.create();

    inventory.rows(4);
    inventory.title("§7Edit§8: §eNPC");

    inventory.addWidget(StatelessItemWidget.create()
      .item(KelpItem.create()
        .material(KelpMaterial.BARRIER)
        .slot(31)
        .displayName("§cBack to main menu")
        .addGlobalListener(event -> {
          npcGui.open(npc);
        })));

    inventory.addWidget(Pagination.create()
      .contentSlots(SlotArea.rectangle(0, 26))
      .contentWidgets(() -> getLineWidgets(npc))
      .previousButton(KelpItem.create()
          .displayName("§6§lPrevious page")
          .slot(27)
          .material(KelpMaterial.ARROW),
        () -> {}) // do nothing when on first page
      .nextButton(KelpItem.create()
          .displayName("§6§lNext page")
          .slot(35)
          .material(KelpMaterial.ARROW),
        () -> {})); // do nothing when on last page

    npc.getPlayer().openInventory(inventory);
  }

  private List<SimpleWidget> getLineWidgets(KelpNpc npc) {
    List<SimpleWidget> output = Lists.newArrayList();
    List<String> titles = npc.getTitles().get();

    titles.forEach(titleLine -> {
      output.add(StatelessItemWidget.create()
        .item(KelpItem.create()
          .material(KelpMaterial.PAPER)
          .displayName("§r" + titleLine)
          .itemDescription(
            "§9Left click to edit",
            "§cRight click to delete"
          )));
    });

    output.add(StatelessItemWidget.create()
      .item(KelpItem.create()
        .material(KelpMaterial.EMERALD)
        .displayName("§aAdd a title line").itemDescription("§7Click here to add a new title line.")));

    return output;
  }

}
