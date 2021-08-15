package de.pxav.kelp.core.world.raycast;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.player.KelpPlayer;
import de.pxav.kelp.core.world.KelpLocation;
import de.pxav.kelp.core.world.KelpWorld;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

// todo explain whitelists and ignore, etc. in class header
public class EntityRay extends Ray<EntityRay> {

  private Collection<Predicate<KelpEntity<?>>> scanCriteria = Sets.newHashSet();
  private double accuracy = 0.1d;

  public EntityRay addScanCriterion(Predicate<KelpEntity<?>> scanCriterion) {
    this.scanCriteria.add(scanCriterion);
    return this;
  }

  public EntityRay accuracy(double accuracy) {
    this.accuracy = accuracy;
    return this;
  }

  @Override
  @NotNull
  public List<RaycastHit> compute() {
    List<RaycastHit> hits = Lists.newArrayList();
    KelpWorld world = this.startLocation.getWorld();
    KelpLocation currentLocation = this.startLocation.clone();

    this.direction.normalize();

    distanceIncrement: for (double d = 1.0; d < maxLength; d += accuracy) {
      double x = direction.getX() * d;
      double y = direction.getY() * d;
      double z = direction.getZ() * d;
      currentLocation.add(x, y, z);

      boolean anyHit = false;
      if (!world.isChunkLoaded(currentLocation)) {
        //todo optimize and skip entire chunk
        continue distanceIncrement;
      }
      entityIterator: for (KelpEntity<?> entity : world.getChunkAt(currentLocation).getEntities()) {

        // if whitelist is enabled and does not contain the current entity type, skip it.
        for (Predicate<KelpEntity<?>> scanCriterion : this.scanCriteria) {
          if (!scanCriterion.test(entity)) {
            continue entityIterator;
          }
        }

        if (entity.getBoundingBox().contains(currentLocation)) {
          anyHit = true;
          if (visualize) {
            for (KelpPlayer kelpPlayer : visualizeTo) {
              kelpPlayer.spawnParticle(visualizerProfile.secondary(), currentLocation);
            }
          }

          double hitDistance = startLocation.distance(currentLocation);

          hits.add(new RaycastHit() {
            @Override
            public Ray<?> getCollidingRay() {
              return EntityRay.this;
            }

            @Override
            public Object getCollider() {
              return entity;
            }

            @Override
            public double getHitDistance() {
              return hitDistance;
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
          kelpPlayer.spawnParticle(visualizerProfile.primary(), currentLocation);
        }
      }
    }
    return hits;
  }

}
