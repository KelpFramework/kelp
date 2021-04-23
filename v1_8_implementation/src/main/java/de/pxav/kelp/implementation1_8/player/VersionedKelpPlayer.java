package de.pxav.kelp.implementation1_8.player;

import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.KelpProjectile;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.type.PlayerInventory;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.particle.type.ParticleType;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.player.PlayerChatVisibility;
import de.pxav.kelp.core.player.bossbar.BossBarColor;
import de.pxav.kelp.core.player.bossbar.BossBarStyle;
import de.pxav.kelp.core.player.message.InteractiveMessage;
import de.pxav.kelp.core.player.message.MessageClickAction;
import de.pxav.kelp.core.player.message.MessageComponent;
import de.pxav.kelp.core.player.message.MessageHoverAction;
import de.pxav.kelp.core.player.prompt.sign.SignPromptVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import de.pxav.kelp.core.sidebar.type.KelpSidebar;
import de.pxav.kelp.core.sound.KelpSound;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedHumanEntity;
import de.pxav.kelp.implementation1_8.inventory.VersionedPlayerInventory;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.projectiles.ProjectileSource;

import java.net.InetSocketAddress;

public class VersionedKelpPlayer
  extends VersionedHumanEntity<KelpPlayer>
  implements KelpPlayer {

  EntityPlayer playerHandle;
  CraftPlayer craftPlayer;

  public VersionedKelpPlayer(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate, ReflectionUtil reflectionUtil) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate, reflectionUtil);
  }

  @Override
  public PlayerInventory getInventory() {
    return new VersionedPlayerInventory(craftPlayer.getInventory(), this);
  }

  /**
   * Appends a message to the given {@link ComponentBuilder}. Please note that every message you append
   * using this method may only have one chat color and the formatting code applies for the entire message.
   *
   * @param componentBuilder    The {@link ComponentBuilder} the message should be appended to.
   * @param component           The {@link MessageComponent} that produced this message.
   * @param tempMessage         The actual message to send.
   * @param color               The color of the message.
   * @param bold                Whether the message should be printed bold.
   * @param italic              Whether the message should be printed italic.
   * @param underlined          Whether the message should be underlined.
   * @param strikethrough       Whether the message should have a strikethrough.
   * @param obfuscated          Whether the message should be obfuscated/magic.
   */
  private void appendComponentBuilder(ComponentBuilder componentBuilder,
                                      MessageComponent component,
                                      StringBuilder tempMessage,
                                      ChatColor color,
                                      boolean bold,
                                      boolean italic,
                                      boolean underlined,
                                      boolean strikethrough,
                                      boolean obfuscated) {
    componentBuilder
      .append(tempMessage.toString())
      .reset()
      .color(color);
    if (bold) {
      componentBuilder.bold(true);
    } else if (italic) {
      componentBuilder.italic(true);
    } else if (underlined) {
      componentBuilder.underlined(true);
    } else if (strikethrough) {
      componentBuilder.strikethrough(true);
    } else if (obfuscated) {
      componentBuilder.obfuscated(true);
    }
    if (tempMessage.length() > 0) {
      applyEvents(component, componentBuilder);
    }
    tempMessage.setLength(0);
  }

  /**
   * Applies the hover and click events contained in the {@link MessageComponent} to the {@link ComponentBuilder}.
   *
   * @param component   The {@link MessageComponent} containing the events you want to apply.
   * @param builder     The {@link ComponentBuilder} the events should be applied to.
   * @return The newly built {@link ComponentBuilder} containing the event data.
   */
  private ComponentBuilder applyEvents(MessageComponent component, ComponentBuilder builder) {
    if (component.getClickAction() == MessageClickAction.EXECUTE_COMMAND) {
      builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/" + component.getClickValue().toString()));
    }

    if (component.getClickAction() == MessageClickAction.SEND_CHAT_MESSAGE) {
      builder.event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, component.getClickValue().toString()));
    }

    if (component.getClickAction() == MessageClickAction.CHANGE_PAGE) {
      try {
        // The click value is converted to an integer first in order to check if the given
        // value really is an integer and then converted back into a string to be passed
        // as a parameter.
        builder.event(new ClickEvent(ClickEvent.Action.CHANGE_PAGE, String.valueOf(Integer.parseInt(component.getClickValue().toString()))));
      } catch (NumberFormatException e) {
        logger.log(LogLevel.ERROR, "Error converting click value to type INTEGER. If you selected click action CHANGE_PAGE, the click value has to be an integer.");
      }
    }

    if (component.getClickAction() == MessageClickAction.COPY_TO_CLIPBOARD) {
      // In Spigot 1.8, this feature is not available.
    }

    if (component.getClickAction() == MessageClickAction.OPEN_FILE) {
      builder.event(new ClickEvent(ClickEvent.Action.OPEN_FILE, component.getClickValue().toString()));
    }

    if (component.getClickAction() == MessageClickAction.OPEN_URL) {
      builder.event(new ClickEvent(ClickEvent.Action.OPEN_URL, component.getClickValue().toString()));
    }

    if (component.getClickAction() == MessageClickAction.SUGGEST_COMMAND) {
      builder.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, component.getClickValue().toString()));
    }

    if (component.getHoverAction() == MessageHoverAction.SHOW_MESSAGE) {
      builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
        TextComponent.fromLegacyText(component.getHoverValue().toString())));
    }

    return builder;
  }

  /**
   * Sends the given packet to the given player.
   *
   * @param packet The packet you want to send.
   * @param player The player who should receive the packet.
   */
  private void sendPacket(Packet<?> packet, Player player) {
    ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
  }

  @Override
  public <E extends KelpProjectile<?>> E launchProjectile(Class<? extends KelpProjectile<?>> projectileType) {
    return null;
  }

  @Override
  public KelpPlayer removeScoreboard() {
    return null;
  }

  @Override
  public void setSidebarInternally(KelpSidebar<?> sidebar) {

  }

  @Override
  public KelpSidebar<?> getCurrentSidebar() {
    return null;
  }

  @Override
  public KelpPlayer forceInventoryClose() {
    return null;
  }

  @Override
  public KelpPlayer playSound(KelpSound sound, KelpLocation from, float volume, float pitch) {
    return null;
  }

  @Override
  public KelpPlayer sendActionbar(String message) {
    return null;
  }

  @Override
  public KelpPlayer sendTitle(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
    return null;
  }

  @Override
  public KelpPlayer spawnParticle(ParticleType particleType, double x, double y, double z, float offsetX, float offsetY, float offsetZ, int count, float particleData, Object generalData) {
    return null;
  }

  @Override
  public String getName() {
    return null;
  }

  @Override
  public KelpPlayer setHealth(int health) {
    return null;
  }

  @Override
  public boolean isInCobweb() {
    return false;
  }

  @Override
  public boolean isInWater() {
    return false;
  }

  @Override
  public KelpPlayer chat(String message) {
    return null;
  }

  @Override
  public InetSocketAddress getSocketAddress() {
    return null;
  }

  @Override
  public boolean mayFly() {
    return false;
  }

  @Override
  public String getDisplayName() {
    return null;
  }

  @Override
  public KelpPlayer setDisplayName(String displayName) {
    return null;
  }

  @Override
  public String getTabListName() {
    return null;
  }

  @Override
  public KelpPlayer setTabListName(String tabListName) {
    return null;
  }

  @Override
  public KelpPlayer setCompassTarget(KelpLocation target) {
    return null;
  }

  @Override
  public KelpPlayer getCompassTarget() {
    return null;
  }

  @Override
  public KelpPlayer kickPlayer(String kickMessage) {
    return null;
  }

  @Override
  public boolean isSneaking() {
    return false;
  }

  @Override
  public KelpPlayer setSneaking(boolean sneaking) {
    return null;
  }

  @Override
  public boolean isSprinting() {
    return false;
  }

  @Override
  public KelpPlayer setSprinting(boolean sprinting) {
    return null;
  }

  @Override
  public boolean isSleepingIgnored() {
    return false;
  }

  @Override
  public KelpPlayer setSleepingIgnored(boolean sleepingIgnored) {
    return null;
  }

  @Override
  public KelpPlayer setRelativePlayerTime(long time) {
    return null;
  }

  @Override
  public KelpPlayer setPlayerTime(long time) {
    return null;
  }

  @Override
  public long getPlayerTime() {
    return 0;
  }

  @Override
  public long getPlayerTimeOffset() {
    return 0;
  }

  @Override
  public boolean isPlayerTimeRelative() {
    return false;
  }

  @Override
  public KelpPlayer resetPlayerTime() {
    return null;
  }

  @Override
  public float getExperience() {
    return 0;
  }

  @Override
  public KelpPlayer setExperience(float experience) {
    return null;
  }

  @Override
  public KelpPlayer setLevel(int level) {
    return null;
  }

  @Override
  public int getLevel() {
    return 0;
  }

  @Override
  public int getTotalExperience() {
    return 0;
  }

  @Override
  public KelpPlayer setTotalExperience(int experience) {
    return null;
  }

  @Override
  public float getExhaustionLevel() {
    return 0;
  }

  @Override
  public KelpPlayer setExhaustionLevel(float exhaustionLevel) {
    return null;
  }

  @Override
  public KelpPlayer setSaturationLevel(float saturationLevel) {
    return null;
  }

  @Override
  public float getSaturationLevel() {
    return 0;
  }

  @Override
  public int getFoodLevel() {
    return 0;
  }

  @Override
  public KelpPlayer setFoodLevel(int foodLevel) {
    return null;
  }

  @Override
  public KelpPlayer setAllowFlight(boolean allowed) {
    return null;
  }

  @Override
  public KelpPlayer allowFlying() {
    return null;
  }

  @Override
  public KelpPlayer disallowFlying() {
    return null;
  }

  @Override
  public KelpPlayer hidePlayer(KelpPlayer toHide) {
    return null;
  }

  @Override
  public KelpPlayer showPlayer(KelpPlayer toShow) {
    return null;
  }

  @Override
  public boolean canSee(KelpPlayer toCheck) {
    return false;
  }

  @Override
  public boolean isFlying() {
    return false;
  }

  @Override
  public KelpPlayer setFlying(boolean flying) {
    return null;
  }

  @Override
  public KelpPlayer setFlySpeed(float flySpeed) {
    return null;
  }

  @Override
  public float getFlySpeed() {
    return 0;
  }

  @Override
  public float getWalkSpeed() {
    return 0;
  }

  @Override
  public KelpPlayer setWalkSpeed(float walkSpeed) {
    return null;
  }

  @Override
  public KelpPlayer resetWalkSpeed() {
    return null;
  }

  @Override
  public KelpPlayer setResourcePack(String url) {
    return null;
  }

  @Override
  public KelpPlayer setResourcePack(String url, byte[] hash) {
    return null;
  }

  @Override
  public KelpPlayer setHealthScaled(boolean scaled) {
    return null;
  }

  @Override
  public boolean isHealthScaled() {
    return false;
  }

  @Override
  public KelpPlayer setHealthScale(double healthScale) {
    return null;
  }

  @Override
  public double getHealthScale() {
    return 0;
  }

  @Override
  public KelpPlayer resetTitle() {
    return null;
  }

  @Override
  public KelpPlayer setClientViewDistanceInternally(int clientViewDistance) {
    return null;
  }

  @Override
  public int getClientViewDistance() {
    return 0;
  }

  @Override
  public KelpPlayer setClientLanguageInternally(String clientLanguage) {
    return null;
  }

  @Override
  public String getClientLanguage() {
    return null;
  }

  @Override
  public KelpPlayer setPlayerChatVisibilityInternally(PlayerChatVisibility playerChatVisibility) {
    return null;
  }

  @Override
  public PlayerChatVisibility getPlayerChatVisibility() {
    return null;
  }

  @Override
  public KelpPlayer setPlayerChatColorEnabledInternally(boolean playerChatColorEnabled) {
    return null;
  }

  @Override
  public boolean isPlayerChatColorEnabled() {
    return false;
  }

  @Override
  public KelpPlayer setTabListHeaderAndFooter(String header, String footer) {
    return null;
  }

  @Override
  public String getTabListFooter() {
    return null;
  }

  @Override
  public String getTabListHeader() {
    return null;
  }

  @Override
  public int getProtocolVersion() {
    return 0;
  }

  @Override
  public boolean isOperator() {
    return false;
  }

  @Override
  public KelpPlayer setOperator(boolean operator) {
    return null;
  }

  @Override
  public KelpPlayer grantPermission(String permission) {
    return null;
  }

  @Override
  public KelpPlayer removePermission(String permission) {
    return null;
  }

  @Override
  public boolean hasPermission(String permission) {
    return false;
  }

  @Override
  public boolean isBannedByBukkit() {
    return false;
  }

  @Override
  public boolean isWhitelisted() {
    return false;
  }

  @Override
  public KelpPlayer setWhitelisted(boolean whitelisted) {
    return null;
  }

  @Override
  public KelpPlayer sendMessage(String message) {
    return null;
  }

  @Override
  public KelpPlayer sendBossBar(String message, float health, BossBarColor barColor, BossBarStyle barStyle) {
    return null;
  }

  @Override
  public KelpPlayer setBossBarProgressHealth(float health) {
    return null;
  }

  @Override
  public KelpPlayer removeBossBar() {
    return null;
  }

  @Override
  public KelpPlayer sendInteractiveMessage(InteractiveMessage interactiveMessage) {
    return null;
  }

  @Override
  public Player getBukkitPlayer() {
    return null;
  }
}
