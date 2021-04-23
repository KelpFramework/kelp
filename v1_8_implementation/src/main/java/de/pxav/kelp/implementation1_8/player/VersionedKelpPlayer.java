package de.pxav.kelp.implementation1_8.player;

import com.google.common.collect.Sets;
import de.pxav.kelp.core.common.StringUtils;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.KelpProjectile;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.inventory.type.PlayerInventory;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.particle.type.ParticleType;
import de.pxav.kelp.core.particle.version.ParticleVersionTemplate;
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
import de.pxav.kelp.core.scheduler.synchronize.ServerMainThread;
import de.pxav.kelp.core.sidebar.SidebarRepository;
import de.pxav.kelp.core.sidebar.type.KelpSidebar;
import de.pxav.kelp.core.sound.KelpSound;
import de.pxav.kelp.core.sound.SoundRepository;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.implementation1_8.entity.type.general.VersionedHumanEntity;
import de.pxav.kelp.implementation1_8.inventory.VersionedPlayerInventory;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.net.InetSocketAddress;

public class VersionedKelpPlayer
  extends VersionedHumanEntity<KelpPlayer>
  implements KelpPlayer {

  private EntityPlayer playerHandle;
  private CraftPlayer player;
  private PermissionAttachment permissionAttachment;
  private KelpSidebar<?> currentSidebar;
  private String clientLanguage;
  private PlayerChatVisibility playerChatVisibility;
  private int clientViewDistance;
  private boolean chatColorEnabled;
  private String tabListHeader;
  private String tabListFooter;

  private KelpLogger logger;
  private BossBarLocationUpdater bossBarLocationUpdater;
  private JavaPlugin javaPlugin;
  private SoundRepository soundRepository;
  private ParticleVersionTemplate particleVersionTemplate;

  public VersionedKelpPlayer(Entity entityHandle,
                             KelpEntityType entityType,
                             Location initialLocation,
                             EntityTypeVersionTemplate entityTypeVersionTemplate,
                             ReflectionUtil reflectionUtil,
                             KelpLogger logger,
                             BossBarLocationUpdater bossBarLocationUpdater,
                             SoundRepository soundRepository,
                             ParticleVersionTemplate particleVersionTemplate,
                             JavaPlugin javaPlugin) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate, reflectionUtil);
    playerHandle = (EntityPlayer) entityHandle;
    player = (CraftPlayer) entityHandle.getBukkitEntity();
    this.logger = logger;
    this.javaPlugin = javaPlugin;
    this.bossBarLocationUpdater = bossBarLocationUpdater;
    this.soundRepository = soundRepository;
    this.particleVersionTemplate = particleVersionTemplate;
  }

  @Override
  public PlayerInventory getInventory() {
    return new VersionedPlayerInventory(player.getInventory(), this);
  }

  @Override
  public <E extends KelpProjectile<?>> E launchProjectile(Class<? extends KelpProjectile<?>> projectileType) {
    return null;
  }

  @Override
  public KelpPlayer removeScoreboard() {
    Scoreboard scoreboard = player.getScoreboard();
    if (scoreboard.getObjective(DisplaySlot.SIDEBAR) == null) {
      return this;
    }

    scoreboard.getObjective(DisplaySlot.SIDEBAR).unregister();
    player.setScoreboard(scoreboard);
    return this;
  }

  @Override
  public void setSidebarInternally(KelpSidebar<?> sidebar) {
    this.currentSidebar = sidebar;
  }

  @Override
  public KelpSidebar<?> getCurrentSidebar() {
    return this.currentSidebar;
  }

  @Override
  public KelpPlayer forceInventoryClose() {
    player.closeInventory();
    return this;
  }

  @Override
  public KelpPlayer playSound(KelpSound sound, KelpLocation from, float volume, float pitch) {
    Sound bukkitSound = Sound.valueOf(soundRepository.getSound(sound));
    player.playSound(player.getLocation(), bukkitSound, volume, pitch);
    return this;
  }

  @Override
  public KelpPlayer sendActionbar(String message) {
    PacketPlayOutChat packet =
      new PacketPlayOutChat(
        IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + message + "\"}"), (byte) 2);
    sendPacket(packet, player);
    return this;
  }

  @Override
  public KelpPlayer sendTitle(String title, String subTitle, int fadeIn, int stay, int fadeOut) {
    final IChatBaseComponent titleComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + title + "\"}");
    final IChatBaseComponent subComponent = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + subTitle + "\"}");
    final PacketPlayOutTitle titleOut = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, titleComponent, fadeIn, stay, fadeOut);
    final PacketPlayOutTitle subtitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TIMES, subComponent);
    final PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleComponent);
    final PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subComponent);

    this.sendPacket(titleOut, player);
    this.sendPacket(subtitle, player);
    this.sendPacket(titlePacket, player);
    this.sendPacket(subtitlePacket, player);
    return this;
  }

  @Override
  public KelpPlayer spawnParticle(ParticleType particleType, double x, double y, double z, float offsetX, float offsetY, float offsetZ, int count, float particleData, Object generalData) {
    particleVersionTemplate.spawnParticle(this,
      particleType,
      true,
      x,
      y,
      z,
      offsetX, offsetY,
      offsetZ,
      particleData,
      count,
      generalData);
    return this;
  }

  @Override
  public String getName() {
    return player.getName();
  }

  @Override
  public KelpPlayer setHealth(int health) {
    player.setHealth(health);
    return this;
  }

  @Override
  public KelpPlayer chat(String message) {
    player.chat(message);
    return this;
  }

  @Override
  public InetSocketAddress getSocketAddress() {
    return player.getAddress();
  }

  @Override
  public boolean mayFly() {
    return player.getAllowFlight();
  }

  @Override
  public String getDisplayName() {
    return player.getDisplayName();
  }

  @Override
  public KelpPlayer setDisplayName(String displayName) {
    player.setDisplayName(displayName);
    return this;
  }

  @Override
  public String getTabListName() {
    return player.getPlayerListName();
  }

  @Override
  public KelpPlayer setTabListName(String tabListName) {
    player.setPlayerListName(tabListName);
    return this;
  }

  @Override
  public KelpPlayer setCompassTarget(KelpLocation target) {
    player.setCompassTarget(target.getBukkitLocation());
    return this;
  }

  @Override
  public KelpLocation getCompassTarget() {
    return KelpLocation.from(player.getCompassTarget());
  }

  @Override
  public KelpPlayer kickPlayer(String kickMessage) {
    player.kickPlayer(kickMessage);
    return this;
  }

  @Override
  public boolean isSneaking() {
    return player.isSneaking();
  }

  @Override
  public KelpPlayer setSneaking(boolean sneaking) {
    player.setSneaking(sneaking);
    return this;
  }

  @Override
  public boolean isSprinting() {
    return player.isSprinting();
  }

  @Override
  public KelpPlayer setSprinting(boolean sprinting) {
    player.setSprinting(sprinting);
    return this;
  }

  @Override
  public boolean isSleepingIgnored() {
    return player.isSleepingIgnored();
  }

  @Override
  public KelpPlayer setSleepingIgnored(boolean sleepingIgnored) {
    player.setSleepingIgnored(sleepingIgnored);
    return this;
  }

  @Override
  public KelpPlayer setRelativePlayerTime(long time) {
    player.setPlayerTime(time, true);
    return this;
  }

  @Override
  public KelpPlayer setPlayerTime(long time) {
    player.setPlayerTime(time, false);
    return this;
  }

  @Override
  public long getPlayerTime() {
    return player.getPlayerTime();
  }

  @Override
  public long getPlayerTimeOffset() {
    return player.getPlayerTimeOffset();
  }

  @Override
  public boolean isPlayerTimeRelative() {
    return player.isPlayerTimeRelative();
  }

  @Override
  public KelpPlayer resetPlayerTime() {
    player.resetPlayerTime();
    return this;
  }

  @Override
  public float getExperience() {
    return player.getExp();
  }

  @Override
  public KelpPlayer setExperience(float experience) {
    player.setExp(experience);
    return this;
  }

  @Override
  public KelpPlayer setLevel(int level) {
    player.setLevel(level);
    return this;
  }

  @Override
  public int getLevel() {
    return player.getLevel();
  }

  @Override
  public int getTotalExperience() {
    return player.getTotalExperience();
  }

  @Override
  public KelpPlayer setTotalExperience(int experience) {
    player.setTotalExperience(experience);
    return this;
  }

  @Override
  public float getExhaustionLevel() {
    return player.getExhaustion();
  }

  @Override
  public KelpPlayer setExhaustionLevel(float exhaustionLevel) {
    player.setExhaustion(exhaustionLevel);
    return this;
  }

  @Override
  public KelpPlayer setSaturationLevel(float saturationLevel) {
    player.setSaturation(saturationLevel);
    return this;
  }

  @Override
  public float getSaturationLevel() {
    return player.getSaturation();
  }

  @Override
  public int getFoodLevel() {
    return player.getFoodLevel();
  }

  @Override
  public KelpPlayer setFoodLevel(int foodLevel) {
    player.setFoodLevel(foodLevel);
    return this;
  }

  @Override
  public KelpPlayer setAllowFlight(boolean allowed) {
    player.setAllowFlight(allowed);
    return this;
  }

  @Override
  public KelpPlayer hidePlayer(KelpPlayer toHide) {
    player.hidePlayer(toHide.getBukkitPlayer());
    return this;
  }

  @Override
  public KelpPlayer showPlayer(KelpPlayer toShow) {
    player.showPlayer(toShow.getBukkitPlayer());
    return this;
  }

  @Override
  public boolean canSee(KelpPlayer toCheck) {
    return false;
  }

  @Override
  public boolean isFlying() {
    return player.isFlying();
  }

  @Override
  public KelpPlayer setFlying(boolean flying) {
    player.setFlying(flying);
    return this;
  }

  @Override
  public KelpPlayer setFlySpeed(float flySpeed) {
    player.setFlySpeed(flySpeed);
    return this;
  }

  @Override
  public float getFlySpeed() {
    return player.getFlySpeed();
  }

  @Override
  public float getWalkSpeed() {
    return player.getWalkSpeed();
  }

  @Override
  public KelpPlayer setWalkSpeed(float walkSpeed) {
    player.setWalkSpeed(walkSpeed);
    return this;
  }

  @Override
  public KelpPlayer resetWalkSpeed() {
    player.setWalkSpeed(0.2F);
    return this;
  }

  @Override
  public KelpPlayer setResourcePack(String url) {
    player.setResourcePack(url);
    return this;
  }

  @Override
  public KelpPlayer setResourcePack(String url, byte[] hash) {
    player.setResourcePack(url);
    return this;
  }

  @Override
  public KelpPlayer setHealthScaled(boolean scaled) {
    player.setHealthScaled(scaled);
    return this;
  }

  @Override
  public boolean isHealthScaled() {
    return player.isHealthScaled();
  }

  @Override
  public KelpPlayer setHealthScale(double healthScale) {
    return null;
  }

  @Override
  public double getHealthScale() {
    return player.getHealthScale();
  }

  @Override
  public KelpPlayer resetTitle() {
    player.resetTitle();
    return this;
  }

  @Override
  public KelpPlayer setClientViewDistanceInternally(int clientViewDistance) {
    this.clientViewDistance = clientViewDistance;
    return this;
  }

  @Override
  public int getClientViewDistance() {
    return clientViewDistance;
  }

  @Override
  public KelpPlayer setClientLanguageInternally(String clientLanguage) {
    this.clientLanguage = clientLanguage;
    return this;
  }

  @Override
  public String getClientLanguage() {
    return clientLanguage;
  }

  @Override
  public KelpPlayer setPlayerChatVisibilityInternally(PlayerChatVisibility playerChatVisibility) {
    this.playerChatVisibility = playerChatVisibility;
    return this;
  }

  @Override
  public PlayerChatVisibility getPlayerChatVisibility() {
    return this.playerChatVisibility;
  }

  @Override
  public KelpPlayer setPlayerChatColorEnabledInternally(boolean playerChatColorEnabled) {
    this.chatColorEnabled = playerChatColorEnabled;
    return this;
  }

  @Override
  public boolean isPlayerChatColorEnabled() {
    return chatColorEnabled;
  }

  @Override
  public KelpPlayer setTabListHeaderAndFooter(String header, String footer) {
    if (header == null) header = "";
    if (footer == null) footer = "";

    IChatBaseComponent tabHeader =
      IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
    IChatBaseComponent tabFooter =
      IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");
    PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter(tabHeader);

    try {
      Field f = packet.getClass().getDeclaredField("b");
      f.setAccessible(true);
      f.set(packet, tabFooter);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      e.printStackTrace();
    }

    this.tabListFooter = footer;
    this.tabListHeader = header;
    sendPacket(packet, player);
    return this;
  }

  @Override
  public String getTabListFooter() {
    return tabListFooter;
  }

  @Override
  public String getTabListHeader() {
    return tabListHeader;
  }

  @Override
  public int getProtocolVersion() {
    // TODO implement using ViaVersion
    return 0;
  }

  @Override
  public boolean isOperator() {
    return player.isOp();
  }

  @Override
  public KelpPlayer setOperator(boolean operator) {
    player.setOp(operator);
    return this;
  }

  @Override
  public KelpPlayer grantPermission(String permission) {
    if (permissionAttachment == null) {
      this.permissionAttachment = player.addAttachment(this.javaPlugin);
    }

    permissionAttachment.setPermission(permission, true);

    return this;
  }

  @Override
  public KelpPlayer removePermission(String permission) {
    if (permissionAttachment == null) {
      return this;
    }
    permissionAttachment.unsetPermission(permission);
    return this;
  }

  @Override
  public boolean hasPermission(String permission) {
    return player.hasPermission(permission);
  }

  @Override
  public boolean isBannedByBukkit() {
    return player.isBanned();
  }

  @Override
  public boolean isWhitelisted() {
    return player.isWhitelisted();
  }

  @Override
  public KelpPlayer setWhitelisted(boolean whitelisted) {
    player.setWhitelisted(whitelisted);
    return this;
  }

  @Override
  public KelpLocation getBedSpawnLocation() {
    return KelpLocation.from(player.getBedSpawnLocation());
  }

  @Override
  public KelpPlayer sendMessage(String message) {
    player.sendMessage(message);
    return this;
  }

  @Override
  public KelpPlayer sendBossBar(String message, float health, BossBarColor barColor, BossBarStyle barStyle) {
    Vector direction = player.getLocation().getDirection();
    Location location = player.getLocation().add(direction.multiply(40));

    if (location.getY() < 1) {
      location.setY(1);
    }

    EntityWither entityWither = new EntityWither(playerHandle.getWorld());
    entityWither.setInvisible(true);
    entityWither.setCustomName((message == null ? "Custom Boss Bar Message." : message));
    entityWither.setCustomNameVisible(false);
    entityWither.setLocation(location.getX(), location.getY(), location.getZ(), 0, 0);
    entityWither.setHealth(health);

    PacketPlayOutSpawnEntityLiving spawnPacket = new PacketPlayOutSpawnEntityLiving(entityWither);
    playerHandle.playerConnection.sendPacket(spawnPacket);

    bossBarLocationUpdater.remove(player.getUniqueId());
    bossBarLocationUpdater.add(player.getUniqueId(), entityWither.getId(), health, message);

    return this;
  }

  @Override
  public KelpPlayer setBossBarProgressHealth(float health) {
    bossBarLocationUpdater.setHealth(player.getUniqueId(), health);
    return this;
  }

  @Override
  public KelpPlayer removeBossBar() {
    ServerMainThread.RunParallel.run(()
      -> bossBarLocationUpdater.remove(player.getUniqueId()));
    return this;
  }

  @Override
  public KelpPlayer sendInteractiveMessage(InteractiveMessage interactiveMessage) {
    // create the component builder retaining no formatting code information so that
    // the messages stay independent from each other.
    ComponentBuilder componentBuilder = new ComponentBuilder("")
      .retain(ComponentBuilder.FormatRetention.NONE);
    String retainedColorCode = null;

    // iterate through all components of the message to add the individual events and
    // handle the color codes correctly.
    for (MessageComponent component : interactiveMessage.getComponents()) {

      String message = component.getText();
      StringBuilder tempMessage = new StringBuilder();
      ChatColor color = ChatColor.WHITE;
      boolean colorCode = false, formattingCode = false;
      boolean bold = false, italic = false, underlined = false, strikethrough = false, obfuscated = false;

      // check whether a color code from the last component was retained
      // and eventually apply it.
      if (retainedColorCode != null) {
        appendComponentBuilder(componentBuilder,
          component,
          new StringBuilder(),
          StringUtils.getChatColor(retainedColorCode),
          false,
          false,
          false,
          false,
          false);
        retainedColorCode = null;
      }

      // if the message of the current component ends with any formatting code,
      // it is saved and applied to the next component but ignored in the current iteration.
      retainedColorCode = StringUtils.endsWithFormattingCode(message);
      if (retainedColorCode != null) {
        if (message.length() <= 2) {
          continue;
        }
        message = message.substring(0, message.length() - 2);
      }

      // iterate through each character of the current message and search for color and style codes.
      // a message is basically divided every time a new color code occurs. This has to be done as the
      // spigot component builder can only handle one color at once. Example (| is a point where the message is divided.)
      // §8[|§aYourPlugin|§8] |§7Click ... [next component]
      for (int i = 0; i < message.length() - 1; i++) {

        // if a § with a valid color code id was found, the message up to this point is
        // appended to the component builder.
        if (message.charAt(i) == '§' && StringUtils.isColorCode(message.charAt(i + 1))) {
          if (tempMessage.length() > 0) {
            appendComponentBuilder(componentBuilder, component, tempMessage, color, bold, italic, underlined, strikethrough, obfuscated);
          }
          colorCode = true;
          color = StringUtils.getChatColor(message.charAt(i + 1));
          continue;
        }

        // if a color code was detected in the last iteration, this is the
        // id of the color code (such as 1, 2, 3, ..., a, b, ...) so we can skip this
        // char as it was already handled.
        if (colorCode) {
          colorCode = false;
          continue;
        }

        // if a formatting code is detected, it is cached and applied to the message.
        if (message.charAt(i) == '§' && StringUtils.isFormattingCode(message.charAt(i + 1))) {
          formattingCode = true;
          ChatColor chatColor = StringUtils.getChatColor(message.charAt(i + 1));
          switch (chatColor) {
            case STRIKETHROUGH:
              strikethrough = true;
              break;
            case MAGIC:
              obfuscated = true;
              break;
            case UNDERLINE:
              underlined = true;
              break;
            case ITALIC:
              italic = true;
              break;
            case BOLD:
              bold = true;
              break;
          }
          continue;
        }

        // if a formatting code has been detected in the last iteration, we can skip this char.
        if (formattingCode) {
          formattingCode = false;
          continue;
        }

        // append the current char to the string builder for the temporary message
        // if this was a normal char.
        tempMessage.append(message.charAt(i));

        // if we are at the last char of the message, we can append the text to the component builder
        // and jump to the next MessageComponent if there is one.
        if (i + 1 == message.length() - 1) {
          tempMessage.append(message.charAt(i + 1));
          appendComponentBuilder(componentBuilder, component, tempMessage, color, bold, italic, underlined, strikethrough, obfuscated);
        }
      }
    }

    // finally send the message to the player via spigot api call.
    player.spigot().sendMessage(componentBuilder.create());
    return null;
  }

  @Override
  public Player getBukkitPlayer() {
    return player;
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

}
