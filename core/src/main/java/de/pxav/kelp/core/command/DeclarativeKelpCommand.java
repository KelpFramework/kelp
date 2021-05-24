package de.pxav.kelp.core.command;

import de.dseelp.kommon.command.CommandNode;
import org.jetbrains.annotations.NotNull;

/**
 * @author DSeeLP
 * @param <T> Describes the Type of CommandSenders the Command accept.
 */
public interface DeclarativeKelpCommand<T extends KelpCommandSender<?>> {
  @NotNull
  CommandNode<T> getCommandNode();

  @NotNull
  default String getNoConsoleMessage() {
    return "Default no Console Message";
  }

  @NotNull
  default String getNoPlayerMessage() {
    return "Default no Player Message";
  }
}
