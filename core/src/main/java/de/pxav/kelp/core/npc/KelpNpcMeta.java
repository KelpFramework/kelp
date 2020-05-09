package de.pxav.kelp.core.npc;

import com.mojang.authlib.GameProfile;

/**
 * A class description goes here.
 *
 * @author pxav
 */
public class KelpNpcMeta {

  private int entityId;
  private GameProfile gameProfile;
  private String overHeadDisplayName;

  public KelpNpcMeta(int entityId, GameProfile gameProfile, String overHeadDisplayName) {
    this.entityId = entityId;
    this.gameProfile = gameProfile;
    this.overHeadDisplayName = overHeadDisplayName;
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
}
