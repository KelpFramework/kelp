package de.pxav.kelp.core.inventory.widget;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.math.DoubleMath;
import de.pxav.kelp.core.inventory.KelpInventoryRepository;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.player.KelpPlayer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class Pagination implements GroupedWidget {

  private KelpPlayer player;
  private List<Integer> contentSlots;
  private Collection<KelpItem> contentItems;
  private KelpItem nextButton;
  private KelpItem previousButton;

  private KelpInventoryRepository inventoryRepository;

  public Pagination(KelpInventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
    this.contentSlots = Lists.newArrayList();
    this.contentItems = Lists.newArrayList();
  }

  public Pagination contentSlots(int... contentSlot)  {
    this.contentSlots.clear();
    for (int slot : contentSlot) {
      this.contentSlots.add(slot);
    }
    return this;
  }

  public Pagination contentItems(KelpItem... contentItem) {
    this.contentItems.clear();
    this.contentItems.addAll(Arrays.asList(contentItem));
    return this;
  }

  public Pagination nextButton(KelpItem nextButton, Runnable onLastPage) {
    this.nextButton = nextButton;
    nextButton.addListener(player, event -> {
      Map<Pagination, Integer> playerPages = inventoryRepository.getPlayerPages().getOrDefault(player.getUUID(), Maps.newHashMap());
      int currentPage = playerPages.getOrDefault(this, 0);
      if (currentPage >= this.getMaxPage() - 1) {
        playerPages.put(this, currentPage);
        onLastPage.run();
        inventoryRepository.getPlayerPages().put(player.getUUID(), playerPages);
        return;
      }

      playerPages.put(this, currentPage + 1);
      inventoryRepository.getPlayerPages().put(player.getUUID(), playerPages);
      player.updateKelpInventory();
    });
    return this;
  }

  public Pagination previousButton(KelpItem previousButton, Runnable onFirstPage) {
    this.previousButton = previousButton;
    previousButton.addListener(player, event -> {
      Map<Pagination, Integer> playerPages = inventoryRepository.getPlayerPages().getOrDefault(player.getUUID(), Maps.newHashMap());
      int currentPage = playerPages.getOrDefault(this, 0);
      if (currentPage == 0) {
        playerPages.put(this, currentPage);
        onFirstPage.run();
        inventoryRepository.getPlayerPages().put(player.getUUID(), playerPages);
        return;
      }
      playerPages.put(this, currentPage - 1);
      player.updateKelpInventory();
      inventoryRepository.getPlayerPages().put(player.getUUID(), playerPages);
    });
    return this;
  }

  private int getMaxPage() {
    double d = ((double) contentItems.size()) / contentSlots.size();
    if (DoubleMath.isMathematicalInteger(d)) {
      return (int) d;
    }
    return ((int) d + 1);
  }

  @Override
  public Collection<KelpItem> render() {
    Collection<KelpItem> output = Lists.newArrayList();

    // page -> items
    Multimap<Integer, KelpItem> pages = HashMultimap.create();
    for (KelpItem item : contentItems) {
      int currentPage = pages.isEmpty() ? 0 : pages.keySet().size() - 1;
      if (pages.get(currentPage).size() >= contentSlots.size()) {
        currentPage++;
      }
      pages.put(currentPage, item);
    }

    int currentPlayerPage = inventoryRepository.getPlayerPages().getOrDefault(player.getUUID(), Maps.newHashMap()).getOrDefault(this, 0);
    int slotIndex = 0;
    for (KelpItem item : pages.get(currentPlayerPage)) {
      output.add(item.slot(contentSlots.get(slotIndex)));
      slotIndex++;
    }

    if (nextButton != null) {
      output.add(nextButton);
    }

    if (previousButton != null) {
      output.add(previousButton);
    }

    return output;
  }

  @Override
  public Pagination player(KelpPlayer player) {
    this.player = player;
    return this;
  }

  @Override
  public KelpPlayer getPlayer() {
    return this.player;
  }

}
