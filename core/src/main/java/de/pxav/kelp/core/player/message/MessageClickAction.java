package de.pxav.kelp.core.player.message;

import de.pxav.kelp.core.version.KelpVersion;
import de.pxav.kelp.core.version.SinceKelpVersion;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public enum  MessageClickAction {

  EXECUTE_COMMAND,
  SUGGEST_COMMAND,
  OPEN_URL,
  OPEN_FILE,
  CHANGE_PAGE,
  SEND_CHAT_MESSAGE,

  @SinceKelpVersion(KelpVersion.MC_1_14_0)
  COPY_TO_CLIPBOARD,

}
