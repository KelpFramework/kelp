package de.pxav.kelp.core.sidebar.component;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.sidebar.SidebarUtils;
import org.bukkit.ChatColor;

/**
 * This class is used to easily create sidebar components.
 * These depend on certain classes that have to be injected
 * each time you call the constructor.
 *
 * To avoid these redundant code snippets, use this factory class.
 *
 * @author pxav
 */
@Singleton
public final class SidebarComponentFactory {

  private KelpLogger kelpLogger;
  private SidebarUtils sidebarUtils;

  @Inject
  public SidebarComponentFactory(KelpLogger kelpLogger, SidebarUtils sidebarUtils) {
    this.kelpLogger = kelpLogger;
    this.sidebarUtils = sidebarUtils;
  }

  public SimpleTextComponent simpleTextComponent() {
    return new SimpleTextComponent(sidebarUtils, kelpLogger);
  }

  public SimpleTextComponent simpleTextComponent(String text) {
    return new SimpleTextComponent(sidebarUtils, kelpLogger)
            .text(text);
  }

  public SimpleTextComponent simpleTextComponent(String text, int line) {
    return new SimpleTextComponent(sidebarUtils, kelpLogger)
            .text(text)
            .line(line);
  }

  public EmptyLineComponent emptyLineComponent() {
    return new EmptyLineComponent(sidebarUtils);
  }

  public EmptyLineComponent emptyLineComponent(int line) {
    return new EmptyLineComponent(sidebarUtils).score(line);
  }

  public LineSeparatorComponent lineSeparatorComponent() {
    return new LineSeparatorComponent(sidebarUtils);
  }

  public LineSeparatorComponent lineSeparatorComponent(int line) {
    return new LineSeparatorComponent(sidebarUtils).score(line);
  }

  public LineSeparatorComponent lineSeparatorComponent(char symbol) {
    return new LineSeparatorComponent(sidebarUtils).symbol(symbol);
  }

  public LineSeparatorComponent lineSeparatorComponent(int line, char symbol) {
    return new LineSeparatorComponent(sidebarUtils)
            .symbol(symbol)
            .score(line);
  }

  public LineSeparatorComponent lineSeparatorComponent(char symbol, ChatColor... colors) {
    return new LineSeparatorComponent(sidebarUtils).symbol(symbol).color(colors);
  }

  public LineSeparatorComponent lineSeparatorComponent(int line, char symbol, ChatColor... colors) {
    return new LineSeparatorComponent(sidebarUtils)
            .symbol(symbol)
            .color(colors)
            .score(line);
  }

  public LineSeparatorComponent lineSeparatorComponent(ChatColor... colors) {
    return new LineSeparatorComponent(sidebarUtils)
            .color(colors);
  }

  public LineSeparatorComponent lineSeparatorComponent(int line, ChatColor... colors) {
    return new LineSeparatorComponent(sidebarUtils)
            .color(colors)
            .score(line);
  }

  public LineSeparatorComponent lineSeparatorComponent(char symbol, int length, ChatColor... colors) {
    return new LineSeparatorComponent(sidebarUtils)
            .color(colors)
            .symbol(symbol)
            .length(length);
  }

  public LineSeparatorComponent lineSeparatorComponent(int line, char symbol, int length, ChatColor... colors) {
    return new LineSeparatorComponent(sidebarUtils)
            .color(colors)
            .symbol(symbol)
            .length(length)
            .score(line);
  }

  public LineSeparatorComponent lineSeparatorComponent(int line, int length) {
    return new LineSeparatorComponent(sidebarUtils)
            .score(line)
            .length(length);
  }

}
