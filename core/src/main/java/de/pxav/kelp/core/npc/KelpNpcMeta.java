package de.pxav.kelp.core.npc;

import com.mojang.authlib.GameProfile;

import java.util.Collection;

/**
 * {@code KelpNpcMeta} is a way to identify NPCs between
 * the version templates and the core module. It offers
 * a way to communicate basic entity data.
 *
 * Saving this data is important, because entities can only be
 * identified by their entityId and/or {@code GameProfile}, which
 * is not saved in the {@code KelpNpc} class directly for
 * version independence. The version templates use this data
 * to create update packets for example.
 *
 * @see GameProfile
 * @see KelpNpc
 * @see de.pxav.kelp.core.npc.version.NpcVersionTemplate
 * @author pxav
 */
public class KelpNpcMeta {

  private int entityId;
  private GameProfile gameProfile;
  private String overHeadDisplayName;
  private Collection<Integer> armorStandEntityIds;

  public KelpNpcMeta(int entityId,
                     GameProfile gameProfile,
                     String overHeadDisplayName,
                     Collection<Integer> armorStandEntityIds) {
    this.entityId = entityId;
    this.gameProfile = gameProfile;
    this.overHeadDisplayName = overHeadDisplayName;
    this.armorStandEntityIds = armorStandEntityIds;
  }

  /**
   * @return The entity id of the NPC entity. The value range differs depending
   *         on which version you are running Kelp.
   */
  public int getEntityId() {
    return entityId;
  }

  /**
   * @return The {@code GameProfile} of the NPC, which is used to control
   *         the display name or tab visibility.
   */
  public GameProfile getGameProfile() {
    return gameProfile;
  }

  /**
   * @return The display name of the NPC, which is displayed directly above its head.
   *         This is required by some info packets.
   */
  public String getOverHeadDisplayName() {
    return overHeadDisplayName;
  }

  /**
   * @return A list of all entity ids of the armor stands containing the
   *         title lines of the NPC.
   */
  public Collection<Integer> getArmorStandEntityIds() {
    return armorStandEntityIds;
  }

  public void setArmorStandEntityIds(Collection<Integer> armorStandEntityIds) {
    this.armorStandEntityIds = armorStandEntityIds;
  }

}
