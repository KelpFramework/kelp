package de.pxav.kelp.core.world.raycast;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.KelpWorld;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class EntityRay extends Ray {

  private Collection<KelpEntityType> ignoredTypes = Sets.newHashSet();
  private Collection<KelpEntityType> whitelistedTypes = Sets.newHashSet();
  private Collection<KelpEntity<?>> ignoredEntities = Sets.newHashSet();

  @Override
  @NotNull
  public List<RaycastHit> compute() {
    List<RaycastHit> hits = Lists.newArrayList();
    KelpWorld world = this.startLocation.getWorld();
    KelpLocation currentLocation = this.startLocation.clone();

    this.direction.normalize();

    distanceIncrement: for (double d = 1.0; d < maxLength; d += 0.1d) {
      double x = direction.getX() * d;
      double y = direction.getY() * d;
      double z = direction.getZ() * d;
      currentLocation.add(x, y, z);

      boolean anyHit = false;
      for (KelpEntity<?> entity : world.getChunkAt(currentLocation).getEntities()) {

        if (entity.getBoundingBox().contains(currentLocation)) {
          anyHit = true;
          if (visualize) {
            for (KelpPlayer kelpPlayer : visualizeTo) {
              kelpPlayer.spawnParticle(visualizerProfile.secondary(), currentLocation);
            }
          }
          hits.add(new RaycastHit() {
            @Override
            public Object getCollider() {
              return entity;
            }

            @Override
            public double getHitDistance() {
              return startLocation.distance(currentLocation);
            }

            @Override
            public KelpLocation getPoint() {
              return currentLocation;
            }
          });

          if (this.maxCollisions == hits.size()) {
            break distanceIncrement;
          }
        }
      }

      if (visualize && !anyHit) {
        for (KelpPlayer kelpPlayer : visualizeTo) {
          kelpPlayer.spawnParticle(visualizerProfile.primary(), startLocation);
        }
      }
    }
    return hits;
  }

}
