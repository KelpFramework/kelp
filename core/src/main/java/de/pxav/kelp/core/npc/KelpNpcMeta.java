package de.pxav.kelp.core.npc;

import com.mojang.authlib.GameProfile;

import java.util.Collection;

/**
 * A class description goes here.
 *
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

  public int getEntityId() {
    return entityId;
  }

  public GameProfile getGameProfile() {
    return gameProfile;
  }

  public String getOverHeadDisplayName() {
    return overHeadDisplayName;
  }

  public Collection<Integer> getArmorStandEntityIds() {
    return armorStandEntityIds;
  }

}
