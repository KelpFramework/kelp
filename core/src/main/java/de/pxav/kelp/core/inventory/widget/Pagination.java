package de.pxav.kelp.core.inventory.widget;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.math.DoubleMath;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.inventory.KelpInventoryRepository;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.player.KelpPlayer;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * This widget is used to crate pagination in your inventories.
 * It allows you to insert a collection of items, which have
 * to be sorted across different pages. It also provides an
 * option to integrate navigation buttons (previous & next page).
 *
 * You can use multiple pagination widgets per inventory.
 *
 * @author pxav
 */
public class Pagination implements GroupedWidget {

  private KelpPlayer player;

  // the slots which should be used for the pages
  private List<Integer> contentSlots;

  // all items which have to be spread across the pages
  private Collection<KelpItem> contentItems;

  // navigation buttons
  private KelpItem nextButton;
  private KelpItem previousButton;

  private KelpInventoryRepository inventoryRepository;

  Pagination(KelpInventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
    this.contentSlots = Lists.newArrayList();
    this.contentItems = Lists.newArrayList();
  }

  public static Pagination create() {
    return new Pagination(
      KelpPlugin.getInjector().getInstance(KelpInventoryRepository.class)
    );
  }

  /**
   * Sets the content slots of the pagination widget.
   *
   * This method overrides all slots which have been set before
   * and only saves the ones provided by this method.
   *
   * Content slots are all slots which are available for the
   * pageable items to be displayed. The page is only displayed
   * at the given slots and you can freely choose the location
   * of your pagination widget.
   *
   * @param contentSlot The content slots which should be set.
   * @return The current instance of the widget.
   */
  public Pagination contentSlots(int... contentSlot)  {
    this.contentSlots.clear();
    for (int slot : contentSlot) {
      this.contentSlots.add(slot);
    }
    return this;
  }

  /**
   * Sets the items you want to be spread across the different pages.
   *
   * This method overrides all slots which have been set before
   * and only saves the ones provided by this method.
   *
   * @param contentItem An array of items you want to be spread across
   *                    the different pages.
   * @return The current instance of the widget.
   */
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

  /**
   * Calculates the maximum amount of pages needed to display all
   * items based on the amount of items and amount of available slots.
   *
   * @return The amount of maximum pages needed.
   */
  private int getMaxPage() {
    double d = ((double) contentItems.size()) / contentSlots.size();
    if (DoubleMath.isMathematicalInteger(d)) {
      return (int) d;
    }
    return ((int) d + 1);
  }

  /**
   * Converts all the given information and customization
   * and generates a collection of items out of it.
   *
   * @return The collection of items representing the widget.
   */
  @Override
  public Collection<KelpItem> render() {
    Collection<KelpItem> output = Lists.newArrayList();

    // Iterate all items and check to which page they belong
    // Map pattern: page id -> items for that page
    Multimap<Integer, KelpItem> pages = HashMultimap.create();
    for (KelpItem item : contentItems) {
      int currentPage = pages.isEmpty() ? 0 : pages.keySet().size() - 1;
      if (pages.get(currentPage).size() >= contentSlots.size()) {
        currentPage++;
      }
      pages.put(currentPage, item);
    }

    // spread the items of the current page across all available slots
    int currentPlayerPage = inventoryRepository.getPlayerPages().getOrDefault(player.getUUID(), Maps.newHashMap()).getOrDefault(this, 0);
    int slotIndex = 0;
    for (KelpItem item : pages.get(currentPlayerPage)) {
      output.add(item.slot(contentSlots.get(slotIndex)));
      slotIndex++;
    }

    // check if the navigation buttons have been set and put them into the inventory
    if (nextButton != null) {
      output.add(nextButton);
    }

    if (previousButton != null) {
      output.add(previousButton);
    }

    return output;
  }

  /**
   * Sets the player to whom the current widget is currently dedicated.
   *
   * @param player The player you want to choose.
   * @return The current instance of the widget.
   */
  @Override
  public Pagination player(KelpPlayer player) {
    this.player = player;
    return this;
  }

  /**
   * Gets the player to whom the current widget is dedicated.
   *
   * @return The {@code KelpPlayer} - "owner" of the widget.
   */
  @Override
  public KelpPlayer getPlayer() {
    return this.player;
  }

}
