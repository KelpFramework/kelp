package de.pxav.kelp.core.sidebar.component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * This component is used to automate list displays in sidebars.
 * Imagine a JumpLeague plugin for example where a list of top players
 * and their progression is displayed in the sidebar. Then it would be
 * useful to be able to <br>
 *  - limit your displayed list to a specific size (to avoid having a cluttered
 *  scoreboard when you have 20+ players in a round) <br>
 *  - display dynamic content (using a {@link Supplier} for example) instead of static text <br>
 *  - keeping the list size constant for a more convenient design (when players quit for example) <br>
 *
 *  All this is supported by this component.
 *
 *  By default this component does not support {@code lazyUpdates}, because
 *  the number of lines set by it may vary. To avoid this use a limiter
 *  {@link #limitTo(int)} and {@link #enableAutoFill()}.
 *
 * @author pxav
 */
public class StatefulListComponent extends SidebarComponent {

  private Supplier<List<String>> list;
  private int startLine;
  private int maxLine;
  private boolean enableLimiter;
  private boolean ascendingOrder;
  private boolean autoFill;

  public StatefulListComponent() {
    // set default values
    this.list = Lists::newArrayList;
    this.startLine = 0;
    this.maxLine = 0;
    this.enableLimiter = false;
    this.ascendingOrder = false;
  }

  public static StatefulListComponent create() {
    return new StatefulListComponent();
  }

  /**
   * The list to be displayed in the sidebar. The order of your list
   * should be in the order of how they should be finally displayed.
   * depending on whether you sort them with {@link #ascendingOrder()} or
   * {@link #descendingOrder()}.
   *
   * @param listSupplier The supplier providing the list to be displayed.
   * @return Instance of the current component for more fluent builder design.
   */
  public StatefulListComponent list(Supplier<List<String>> listSupplier) {
    this.list = listSupplier;
    return this;
  }

  /**
   * Defines the line in the scoreboard where the first list element should
   * be placed and from where the list items should be placed. If you selected
   * {@link #ascendingOrder()} the number for the next list item will be incremented
   * and if you choose {@link #descendingOrder()} the number will be decremented.
   *
   * Please note that the given line ids are absolute and you should check whether
   * no components are in range of your list items, which could cause weird behaviour.
   *
   * @param startLine The line from where the list should start.
   * @return Instance of the current component for more fluent builder design.
   */
  public StatefulListComponent startFrom(int startLine) {
    this.startLine = startLine;
    return this;
  }

  /**
   * Enables the line limiter for the list. So there will be only as many
   * list items in the sidebar as you input here. Generally this is recommended
   * if you are not completely sure how many list items will be displayed. Then
   * you can make sure that those list items do not interfere with any other
   * items.
   *
   * Please make sure that the number is reachable if you set it. If your
   * list order is ascending and this number is smaller than your start line,
   * this number will have no effect.
   *
   * @param maxLine The last line where a list item can be displayed.
   * @return Instance of the current component for more fluent builder design.
   */
  public StatefulListComponent limitTo(int maxLine) {
    this.maxLine = maxLine;
    this.enableLimiter = true;
    return this;
  }

  /**
   * Normally, this component is not compatible with {@code lazyUpdating}, because
   * the amount of lines covered by it may vary with each update. To avoid this, enable
   * the limiter with {@link #limitTo(int)} and enable auto fill by calling this method.
   *
   * Auto fill makes sure that if the amount of list items is smaller than the given
   * maximum, more empty lines are inserted to keep the total line amount constant.
   * This allows you to create flicker free sidebars using this component.
   *
   * @return Instance of the current component for more fluent builder design.
   */
  public StatefulListComponent enableAutoFill() {
    this.autoFill = true;
    return this;
  }

  /**
   * Disables the auto fill feature described in {@link #enableAutoFill()}.
   *
   * @return Instance of the current component for more fluent builder design.
   */
  public StatefulListComponent disableAutoFill() {
    this.autoFill = false;
    return this;
  }

  /**
   * Disables the item limiter described in {@link #limitTo(int)}. If you have
   * auto fill enabled, this will also disable auto fill!
   *
   * @return Instance of the current component for more fluent builder design.
   */
  public StatefulListComponent disableLimiter() {
    this.enableLimiter = false;
    return this;
  }

  /**
   * Sets the list item order to {@code ascending}. This means that
   * if your start line is 10 for example, the second item will be placed
   * on line 11, the third one on 12, and so one.
   *
   * @return Instance of the current component for more fluent builder design.
   */
  public StatefulListComponent ascendingOrder() {
    this.ascendingOrder = true;
    return this;
  }

  /**
   * Sets the list item order to {@code descending}. This means that
   * if your start line is 10 for example, the second item will be placed
   * on line 9, the third one on 8, and so one.
   *
   * @return Instance of the current component for more fluent builder design.
   */
  public StatefulListComponent descendingOrder() {
    this.ascendingOrder = false;
    return this;
  }

  /**
   * Renders all the information provided in the component
   * to a map containing the final information to be rendered
   * to the sidebar (the lines where the text should be placed and
   * the text to write there).
   *
   * @return A map, where the key is the absolute line where
   *         the component should be placed in the sidebar and
   *         the value is the actual text for that line.
   */
  @Override
  public Map<Integer, String> render() {
    Map<Integer, String> output = Maps.newHashMap();
    List<String> lines = list.get();

    Iterator<String> iterator = lines.iterator();

    // before iterating through the list, the plugin checks whether
    // the list should be placed in ascending or descending order, which
    // is important to check whether indexes have to be decremented or incremented.
    if (ascendingOrder) {
      for (int i = startLine; i < Integer.MAX_VALUE; i++) {

        if (!iterator.hasNext()) {
          // if the iterator has arrived at the last item it has to check
          // whether there are enough lines to fill the minimum required area
          // if such an area is set with autoFill. It will then add any missing
          // lines.
          if (enableLimiter && autoFill && i <= maxLine) {
            for (int fillIndex = i; fillIndex <= maxLine; fillIndex++) {
              output.put(fillIndex, " ");
            }
            return output;
          }
          return output;
        }
        if (enableLimiter && i == maxLine) {
          return output;
        }
        output.put(i, iterator.next());
      }
    } else {
      for (int i = startLine; i < Integer.MAX_VALUE; i--) {

        // here you can find pretty much the same as described above
        // but for the descending list order. The major difference is that
        // the numbers are inverted here. (++ -> -- for example)
        if (!iterator.hasNext()) {
          if (enableLimiter && autoFill && i >= maxLine) {
            for (int fillIndex = i; fillIndex >= maxLine; fillIndex--) {
              output.put(fillIndex, " ");
            }
            return output;
          }
          return output;
        }
        if (enableLimiter && i == maxLine) {
          return output;
        }
        output.put(i, iterator.next());
      }
    }

    // if the list is empty, there have been no checks whether it is
    // necessary to fill up empty lines. So this is done here. If the
    // limiter and auto fill mode are enabled, the plugin will add as
    // many lines as needed to fill the space.
    if (output.isEmpty() && enableLimiter && autoFill) {
      if (ascendingOrder) {
        for (int fillIndex = startLine; fillIndex <= maxLine; fillIndex++) {
          output.put(fillIndex, " ");
        }
      } else {
        for (int fillIndex = startLine; fillIndex >= maxLine; fillIndex--) {
          output.put(fillIndex, " ");
        }
      }

    }

    return output;
  }

}
