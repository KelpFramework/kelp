package de.pxav.kelp.core.sidebar.component;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class StatefulListComponent extends SidebarComponent {

  private Supplier<List<String>> list;
  private int startLine;
  private int maxLine;
  private boolean enableLimiter;
  private boolean ascendingOrder;
  private boolean autoFill;

  public StatefulListComponent() {
    this.list = Lists::newArrayList;
    this.startLine = 0;
    this.maxLine = 0;
    this.enableLimiter = false;
    this.ascendingOrder = false;
  }

  public static StatefulListComponent create() {
    return new StatefulListComponent();
  }

  public StatefulListComponent list(Supplier<List<String>> listSupplier) {
    this.list = listSupplier;
    return this;
  }

  public StatefulListComponent startFrom(int startLine) {
    this.startLine = startLine;
    return this;
  }

  public StatefulListComponent limitTo(int maxLine) {
    this.maxLine = maxLine;
    this.enableLimiter = true;
    return this;
  }

  public StatefulListComponent enableAutoFill() {
    this.autoFill = true;
    return this;
  }

  public StatefulListComponent disableAutoFill() {
    this.autoFill = false;
    return this;
  }

  public StatefulListComponent disableLimiter() {
    this.enableLimiter = false;
    return this;
  }

  public StatefulListComponent ascendingOrder() {
    this.ascendingOrder = true;
    return this;
  }

  public StatefulListComponent descendingOrder() {
    this.ascendingOrder = false;
    return this;
  }

  @Override
  public Map<Integer, String> render() {
    Map<Integer, String> output = Maps.newHashMap();
    List<String> lines = list.get();

    Iterator<String> iterator = lines.iterator();
    if (ascendingOrder) {
      for (int i = startLine; i < Integer.MAX_VALUE; i++) {
        if (!iterator.hasNext()) {
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

  private void fill() {

  }

}
