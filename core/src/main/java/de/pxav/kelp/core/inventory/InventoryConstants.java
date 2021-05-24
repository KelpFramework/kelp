package de.pxav.kelp.core.inventory;

import de.pxav.kelp.core.inventory.widget.GroupedWidget;

import java.util.function.Predicate;

public class InventoryConstants {

  public static final int NOT_RENDERED_SIMPLE_WIDGET = -1;

  public static final Predicate<GroupedWidget> NOT_RENDERED_GROUPED_WIDGET =
    groupedWidget -> groupedWidget.getCoveredSlots().isEmpty() || groupedWidget.getCoveredSlots() == null;

}
