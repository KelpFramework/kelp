package de.pxav.kelp.implementation1_8.entity.type.general;

import de.pxav.kelp.core.entity.KelpEntity;
import de.pxav.kelp.core.entity.KelpEntityType;
import de.pxav.kelp.core.entity.type.general.AnimalEntity;
import de.pxav.kelp.core.entity.version.EntityTypeVersionTemplate;
import de.pxav.kelp.core.reflect.ReflectionUtil;
import javassist.tools.reflect.CannotInvokeException;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPig;

public class VersionedAnimalEntity<T extends AnimalEntity<T>>
  extends VersionedMobileEntity<T> implements AnimalEntity<T> {

  private EntityAnimal animalHandle;
  private ReflectionUtil reflectionUtil;

  public VersionedAnimalEntity(Entity entityHandle, KelpEntityType entityType, Location initialLocation, EntityTypeVersionTemplate entityTypeVersionTemplate, ReflectionUtil reflectionUtil) {
    super(entityHandle, entityType, initialLocation, entityTypeVersionTemplate);
    this.reflectionUtil = reflectionUtil;
    this.animalHandle = (EntityAnimal) entityHandle;
  }

  @Override
  public boolean isInLove() {
    return animalHandle.isInLove();
  }

  @Override
  public T setInLove(boolean inLove) {
    if (inLove) {
      setLoveModeTicks(600);
    } else {
      setLoveModeTicks(0);
    }
    return (T) this;
  }

  @Override
  public KelpEntity<?> getBreeder() {
    return entityTypeVersionTemplate.getKelpEntity(animalHandle.cq().getBukkitEntity());
  }

  @Override
  public T setBreeder(KelpEntity<?> breeder) {
    EntityHuman nmsHuman = ((CraftHumanEntity) breeder.getBukkitEntity()).getHandle();
    reflectionUtil.setValue(animalHandle, "bo", nmsHuman);
    return (T) this;
  }

  @Override
  public int getLoveModeTicks() {
    return (int) reflectionUtil.getValue(animalHandle, "bm");
  }

  @Override
  public T setLoveModeTicks(int loveModeTicks) {
    reflectionUtil.setValue(animalHandle, "bm", loveModeTicks);
    return (T) this;
  }

}
