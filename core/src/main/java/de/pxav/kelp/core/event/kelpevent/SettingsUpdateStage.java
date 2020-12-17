package de.pxav.kelp.core.event.kelpevent;

/**
 * Describes when an update of client settings has been received.
 * There are two ways the server can receive information about
 * the player's client settings:
 *  - By loading default settings on each plugin startup
 *  - By catching the packet sent by the client during the server runtime.
 *
 * This enum is used by {@link KelpPlayerUpdateSettingsEvent} to provide information
 * about when the settings have changed.
 *
 * @author pxav
 */
public enum SettingsUpdateStage {

  /**
   * When the player has updated its settings during the server runtime
   * and the server received an incoming settings packet. This packet is
   * sent when the player presses the {@code Done} button in the video settings
   * tab for example.
   */
  PACKET_PLAY_IN,

  /**
   * When the server automatically updates the settings values for the player
   * on every plugin startup (relevant for server reloads for example).
   */
  PLUGIN_STARTUP

}
