package de.pxav.kelp.implementation1_8.packet;

import com.google.common.collect.Lists;
import com.google.inject.Inject;
import de.pxav.kelp.core.KelpPlugin;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.KelpPlayerRepository;
import de.pxav.kelp.core.player.PlayerChatVisibility;
import de.pxav.kelp.core.player.prompt.sign.SignPromptResponseHandler;
import de.pxav.kelp.core.player.prompt.PromptResponseType;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.implementation1_8.player.VersionedSignPrompt;
import io.netty.channel.*;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayInSettings;
import net.minecraft.server.v1_8_R3.PacketPlayInUpdateSign;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.List;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class GlobalPacketListener {

  private KelpPlayerRepository playerRepository;
  private ReflectionUtil reflectionUtil;
  private VersionedSignPrompt signPrompt;

  @Inject
  public GlobalPacketListener(KelpPlayerRepository playerRepository, ReflectionUtil reflectionUtil, VersionedSignPrompt signPrompt) {
    this.playerRepository = playerRepository;
    this.reflectionUtil = reflectionUtil;
    this.signPrompt = signPrompt;
  }

  @EventHandler
  public void handlePlayerJoin(PlayerJoinEvent event) {
    injectPacketListener(event.getPlayer());
  }

  @EventHandler
  public void handlePlayerQuit(PlayerQuitEvent event) {
    removePacketListener(event.getPlayer());
  }

  /**
   * Creates a new packet listener.
   *
   * @param player
   */
  public void injectPacketListener(Player player) {
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

        if (packet instanceof PacketPlayInUpdateSign && signPrompt.isChecked(player.getUniqueId())) {

          PacketPlayInUpdateSign updatePacket = (PacketPlayInUpdateSign) packet;
          IChatBaseComponent[] rawLines = (IChatBaseComponent[]) reflectionUtil.getValue(updatePacket, "b");
          List<String> input = Lists.newArrayList();

          for (IChatBaseComponent line : rawLines) {
            input.add(line.getText());
          }

          SignPromptResponseHandler handler = signPrompt.getHandler(player.getUniqueId());
          PromptResponseType responseType = handler.accept(input);

          if (responseType == PromptResponseType.TRY_AGAIN) {
            signPrompt.resetBlockAndRemove(player.getUniqueId());
            Bukkit.getScheduler().runTaskLater(KelpPlugin.getPlugin(KelpPlugin.class), () -> {
              signPrompt.openSignPrompt(playerRepository.getKelpPlayer(player), input, handler);
            }, 1);
            return;
          }

          signPrompt.resetBlockAndRemove(player.getUniqueId());

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

  public void removePacketListener(Player player) {
    Channel channel = ((CraftPlayer)player).getHandle().playerConnection.networkManager.channel;
    channel.eventLoop().submit(() -> {
      channel.pipeline().remove(player.getName());
      return null;
    });
  }

}
