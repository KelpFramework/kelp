package de.pxav.kelp.core.version;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.version.material.MaterialData;

import java.util.Collection;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class ServerInformation {

  private final KelpLogger logger;

  private String serverVersion;
  private KelpVersion kelpVersion;
  private Collection<MaterialData> materialsAvailable;

  @Inject
  public ServerInformation(KelpLogger logger) {
    this.logger = logger;
  }

  public String getServerVersion() {
    return serverVersion;
  }

  public ServerInformation setServerVersion(String serverVersion) {
    this.serverVersion = serverVersion;
    logger.log("Changed server version to " + serverVersion);
    return this;
  }

  public KelpVersion getKelpVersion() {
    return kelpVersion;
  }

  public ServerInformation setKelpVersion(KelpVersion kelpVersion) {
    this.kelpVersion = kelpVersion;
    logger.log("Changed kelp api version to " + kelpVersion);
    return this;
  }

  public Collection<MaterialData> getMaterialsAvailable() {
    return materialsAvailable;
  }

  public ServerInformation setMaterialsAvailable(Collection<MaterialData> materialsAvailable) {
    this.materialsAvailable = materialsAvailable;
    logger.log("Changed available materials. Now " + materialsAvailable.size() + " are ready to use..");
    return this;
  }

}
