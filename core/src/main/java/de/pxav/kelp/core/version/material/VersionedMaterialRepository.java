package de.pxav.kelp.core.version.material;

import com.google.common.collect.Maps;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import de.pxav.kelp.core.logger.KelpLogger;
import de.pxav.kelp.core.logger.LogLevel;
import de.pxav.kelp.core.reflect.TypeCriterion;
import de.pxav.kelp.core.reflect.TypeFinder;
import de.pxav.kelp.core.version.KelpVersion;
import de.pxav.kelp.core.version.ServerInformation;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A class description goes here.
 *
 * @author pxav
 */
@Singleton
public class VersionedMaterialRepository {

  private Injector injector;
  private TypeFinder typeFinder;
  private KelpLogger logger;
  private ServerInformation serverInformation;

  private Map<Collection<KelpVersion>, VersionedSpigotMaterial> versionedMaterials = Maps.newHashMap();

  @Inject
  public VersionedMaterialRepository(Injector injector,
                                     TypeFinder typeFinder,
                                     KelpLogger logger,
                                     ServerInformation serverInformation) {
    this.injector = injector;
    this.typeFinder = typeFinder;
    this.logger = logger;
    this.serverInformation = serverInformation;
  }

  public void loadAll(String... packages) {
    this.typeFinder
            .filter(
                    packages,
                    TypeCriterion.subclassOf(VersionedSpigotMaterial.class),
                    TypeCriterion.annotatedWith(VersionedMaterial.class))
            .forEach(
                    versionClass -> {
                      VersionedMaterial annotation = versionClass.getAnnotation(VersionedMaterial.class);
                      VersionedSpigotMaterial implementation = (VersionedSpigotMaterial) injector.getInstance(versionClass);

                      implementation.versionMaterials();
                      Collection<KelpMaterial> validateResult = implementation.validate();
                      validateResult.removeAll(KelpMaterial.aboveVersion(KelpVersion.highestVersionOf(annotation.value())));

                      logger.log("Detected MATERIAL implementation for versions " + Arrays.toString((annotation.value()))
                              .replace("[", "")
                              .replace("]", ""));

                      if (validateResult.isEmpty()) {
                        logger.log("=> Validated material implementation. Result: OK, NO TYPES MISSING (#200)");
                      } else {
                        logger.log(LogLevel.WARNING, "=> Validated material implementation. Result: NOT OK, "
                                + validateResult.size() + " ENTRY(S) MISSING!",
                                "The implementation can be loaded, but might not work properly.");
                      }

                      versionedMaterials.put(Arrays.asList(annotation.value()), implementation);
                      System.out.println("=> Loaded implementation successfully.");
                    });

    AtomicBoolean found = new AtomicBoolean(false);
    versionedMaterials.forEach(((kelpVersions, implementation) -> {
      if (kelpVersions.contains(serverInformation.getKelpVersion())) {
        found.set(true);
        logger.log("Found material implementation for current version (" + serverInformation.getKelpVersion() + ")",
                "Applying materials to cache...");
        serverInformation.setMaterialsAvailable(implementation.materials);
      }
    }));

    if (!found.get()) {
      logger.log(LogLevel.ERROR,
              "No material implementation detected for version " + serverInformation.getKelpVersion());
    }
  }

  public String getMaterial(KelpMaterial kelpMaterial) {
    for (MaterialData current : serverInformation.getMaterialsAvailable()) {
      if (kelpMaterial == current.getKelpMaterial()) {
        return current.getVersionedName();
      }
    }
    return null;
  }

}
