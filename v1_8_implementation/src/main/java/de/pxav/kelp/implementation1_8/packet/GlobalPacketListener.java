package de.pxav.kelp.implementation1_8.packet;

import com.google.inject.Inject;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import de.pxav.kelp.core.player.PlayerChatVisibility;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelPromise;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class GlobalPacketListener {

  private KelpPlayerRepository playerRepository;
  private ReflectionUtil reflectionUtil;

  @Inject
  public GlobalPacketListener(KelpPlayerRepository playerRepository, ReflectionUtil reflectionUtil) {
    this.playerRepository = playerRepository;
    this.reflectionUtil = reflectionUtil;
  }

  @EventHandler
  public void handlePlayerJoin(PlayerJoinEvent event) {
    injectPacketListener(event.getPlayer());
  }

  /**
   * Creates a new packet listener.
   *
   * @param player
   */
  private void injectPacketListener(Player player) {
    ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {

      @Override
      public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
        if (packet instanceof PacketPlayInSettings) {

          PacketPlayInSettings settingsPacket = (PacketPlayInSettings) packet;
          String language = String.valueOf(reflectionUtil.getValue(settingsPacket, "a"));
          int viewDistance = Integer.parseInt(String.valueOf(reflectionUtil.getValue(settingsPacket, "b")));
          boolean chatColorEnabled = Boolean.parseBoolean(String.valueOf(reflectionUtil.getValue(settingsPacket, "d")));
          PlayerChatVisibility chatVisibility = PlayerChatVisibility.SHOW_ALL_MESSAGES;
          switch (String.valueOf(reflectionUtil.getValue(settingsPacket, "c"))) {
            case "FULL":
              chatVisibility = PlayerChatVisibility.SHOW_ALL_MESSAGES;
              break;
            case "SYSTEM":
              chatVisibility = PlayerChatVisibility.COMMANDS_ONLY;
              break;
            case "HIDDEN":
              chatVisibility = PlayerChatVisibility.HIDDEN;
              break;
          }

          KelpPlayer kelpPlayer = playerRepository.getKelpPlayer(player);
          kelpPlayer
            .setClientLanguageInternally(language)
            .setClientViewDistanceInternally(viewDistance)
            .setPlayerChatColorEnabledInternally(chatColorEnabled)
            .setPlayerChatVisibilityInternally(chatVisibility);
          playerRepository.addOrUpdatePlayer(kelpPlayer.getUUID(), kelpPlayer);

          return;
        }
        super.channelRead(channelHandlerContext, packet);
      }

      @Override
      public void write(ChannelHandlerContext channelHandlerContext, Object packet, ChannelPromise channelPromise) throws Exception {
        super.write(channelHandlerContext, packet, channelPromise);
      }

    };

    ChannelPipeline pipeline = ((CraftPlayer)player).getHandle().playerConnection.networkManager.channel.pipeline();
    pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
  }

}
