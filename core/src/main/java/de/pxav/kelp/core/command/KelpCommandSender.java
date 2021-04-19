package de.pxav.kelp.core.command;

import java.util.Collection;

public interface KelpCommandSender<T extends KelpCommandSender> {
  String getName();
  T sendMessage(String message);
}
