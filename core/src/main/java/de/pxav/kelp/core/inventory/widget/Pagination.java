package de.pxav.kelp.core.inventory.widget;

import com.google.common.collect.*;
import com.google.common.math.DoubleMath;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.common.ConcurrentListMultimap;
import de.pxav.kelp.core.common.ConcurrentMultimap;
import de.pxav.kelp.core.inventory.KelpInventoryRepository;
import de.pxav.kelp.core.inventory.item.KelpItem;
import de.pxav.kelp.core.player.KelpPlayer;

import java.util.*;
import java.util.function.Supplier;

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
public class Pagination extends AbstractWidget<Pagination> implements GroupedWidget {

  // the slots which should be used for the pages
  private List<Integer> contentSlots;

  // all items which have to be spread across the pages
  private Supplier<List<SimpleWidget>> contentWidgets;

  // widgets that are currently displayed independent from the output
  // that would be returned by the supplier right now.
  private List<SimpleWidget> currentContentWidgets = ImmutableList.of();

  // if the widget has at least been rendered once.
  private boolean rendered = false;

  // navigation buttons
  private KelpItem nextButton;
  private KelpItem previousButton;

  private KelpInventoryRepository inventoryRepository;

  Pagination(KelpInventoryRepository inventoryRepository) {
    this.inventoryRepository = inventoryRepository;
    this.contentSlots = Lists.newArrayList();
    this.contentWidgets = Lists::newArrayList;
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
   * @param slots The content slots which should be set.
   * @return The current instance of the widget.
   */
  public Pagination contentSlots(Set<Integer> slots)  {
    this.contentSlots.clear();
    this.contentSlots.addAll(slots);
    return this;
  }

  /**
   * Sets the widgets you want to be spread across the different pages.
   *
   * This method overrides all widgets which have been set before
   * and only saves the ones provided by this method.
   *
   * @param contentWidgets A supplier containing the list with all content
   *                       widgets you want to set.
   * @return The current instance of the widget.
   */
  public Pagination contentWidgets(Supplier<List<SimpleWidget>> contentWidgets) {
    this.contentWidgets = contentWidgets;
    return this;
  }

  public Pagination nextButton(KelpItem nextButton, Runnable onLastPage) {
    this.nextButton = nextButton;

    addClickListener(nextButton, event -> {
      KelpPlayer player = event.getPlayer();
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

    addClickListener(previousButton, event -> {
      KelpPlayer player = event.getPlayer();
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
    double d = ((double) currentContentWidgets.size()) / contentSlots.size();
    if (DoubleMath.isMathematicalInteger(d)) {
      return (int) d;
    }
    return ((int) d + 1);
  }

  @Override
  public boolean isStateful() {
    return true;
  }

  /**
   * Converts all the given information and customization
   * and generates a collection of items out of it.
   *
   * @return The collection of items representing the widget.
   */
  @Override
  public Collection<KelpItem> render(KelpPlayer player) {
    player = this.player != null ? this.player : player;
    this.currentContentWidgets = contentWidgets.get();

    Collection<KelpItem> output = Lists.newArrayList();

    // Iterate all items and check to which page they belong
    // Map pattern: page id -> items for that page
    ConcurrentMultimap<Integer, SimpleWidget> pages = ConcurrentListMultimap.create();
    for (SimpleWidget widget : currentContentWidgets) {
      int currentPage = pages.isEmpty() ? 0 : pages.keySet().size() - 1;
      if (pages.getOrEmpty(currentPage).size() >= contentSlots.size()) {
        currentPage++;
      }
      pages.put(currentPage, widget);
    }

    // spread the items of the current page across all available slots
    int currentPlayerPage = inventoryRepository.getPlayerPages().getOrDefault(player.getUUID(), Maps.newHashMap()).getOrDefault(this, 0);
    int slotIndex = 0;
    for (SimpleWidget widget : pages.get(currentPlayerPage)) {
      int slot = contentSlots.get(slotIndex);
      output.add(widget.render().slot(slot));
      slotIndex++;
    }

    // check if the navigation buttons have been set and put them into the inventory
    if (nextButton != null) {
      output.add(nextButton);
    }

    if (previousButton != null) {
      output.add(previousButton);
    }

    rendered = true;

    return output;
  }

  @Override
  public void onRemove() {
    currentContentWidgets.forEach(Widget::onRemove);
  }

  @Override
  public Set<Integer> getCoveredSlots() {
    if (!rendered) {
      return Sets.newHashSet();
    }
    Set<Integer> output = Sets.newHashSet(contentSlots);
    output.add(nextButton.getSlot());
    output.add(previousButton.getSlot());
    return output;
  }

}
