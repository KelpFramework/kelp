package de.pxav.kelp.core.world.raycast;

import com.google.common.collect.Lists;
import de.pxav.kelp.core.world.KelpBlock;
import org.bukkit.craftbukkit.v1_16_R3.block.CraftBlock;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BlockRay extends Ray {

  @NotNull
  @Override
  public List<RaycastHit> compute() {
    List<RaycastHit> hits = Lists.newArrayList();

    return hits;
  }

}
