package de.pxav.kelp.core.entity.util.potion;

import de.pxav.kelp.core.version.KelpVersion;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface MinecraftPotion {

  KelpVersion since();

}
