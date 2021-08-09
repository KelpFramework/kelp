package de.pxav.kelp.core.world.raycast;

import com.google.common.collect.Lists;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

public class Raycast {

  private Collection<Ray> rays = Lists.newArrayList();
  private ExecutorService executorService;

  public static Raycast create() {
    return new Raycast();
  }

  public Raycast addRay(Ray ray) {
    this.rays.add(ray);
    return this;
  }

  public Raycast setRays(Collection<Ray> rays) {
    this.rays = rays;
    return this;
  }

  public void perform(Consumer<List<RaycastHit>> hits) {
    executorService = Executors.newCachedThreadPool();
    executorService.execute(() -> {
      Future<List<RaycastHit>> future = executorService.submit(() -> {
        List<RaycastHit> actualHits = Lists.newArrayList();
        for (Ray ray : this.rays) {
          actualHits.addAll(ray.compute());
        }
        return actualHits;
      });

      try {
        hits.accept(future.get());
      } catch (InterruptedException | ExecutionException e) {
        hits.accept(Lists.newArrayList());
      }
    });

  }

}
