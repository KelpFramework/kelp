package de.pxav.kelp.implementation1_8.player;

import net.minecraft.server.v1_8_R3.BiomeBase;
import net.minecraft.server.v1_8_R3.EntityInsentient;
import net.minecraft.server.v1_8_R3.EntityTypes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * This class is used to register custom entities to the server. In this
 * case, a custom entity is an NMS entity that extends from {@link EntityInsentient}.
 * This can be used to create non-movable villagers for example or change the item
 * required to lure an animal.
 *
 * To achieve this you take your entity, for example {@link net.minecraft.server.v1_8_R3.EntityChicken}
 * and create a new class that extends from this entity class (all normal NMS entity classes extend
 * from {@link EntityInsentient}).
 *
 * Finally you have to register your new entity using this class, which has not been written by
 * the kelp developer team, but is copied from a resource on the
 * <a href="https://bukkit.org/threads/tutorial-register-your-custom-entities-nms-reflection.258542/">bukkit dev forums</a>.
 *
 * Kelp does not claim it owns the code of this class. If you have a problem that Kelp
 * uses it, please contact the developer (pxav.studios@gmail.com)
 *
 * @author pxav (documentation)
 * @author bigteddy98 (code)
 */
public class NMSRegistry {

  public void registerEntity(String name, int id, Class<? extends EntityInsentient> nmsClass, Class<? extends EntityInsentient> customClass) {
    try {

      /*
       * First, we make a list of all HashMap's in the EntityTypes class
       * by looping through all fields. I am using reflection here so we
       * have no problems later when minecraft changes the field's name.
       * By creating a list of these maps we can easily modify them later
       * on.
       */
      List<Map<?, ?>> dataMaps = new ArrayList<Map<?, ?>>();
      for (Field f : EntityTypes.class.getDeclaredFields()) {
        if (f.getType().getSimpleName().equals(Map.class.getSimpleName())) {
          f.setAccessible(true);
          dataMaps.add((Map<?, ?>) f.get(null));
        }
      }

      /*
       * since minecraft checks if an id has already been registered, we
       * have to remove the old entity class before we can register our
       * custom one
       *
       * map 0 is the map with names and map 2 is the map with ids
       */
      if (dataMaps.get(2).containsKey(id)) {
        dataMaps.get(0).remove(name);
        dataMaps.get(2).remove(id);
      }

      /*
       * now we call the method which adds the entity to the lists in the
       * EntityTypes class, now we are actually 'registering' our entity
       */
      Method method = EntityTypes.class.getDeclaredMethod("a", Class.class, String.class, int.class);
      method.setAccessible(true);
      method.invoke(null, customClass, name, id);

      /*
       * after doing the basic registering stuff , we have to register our
       * mob as to be the default for every biome. This can be done by
       * looping through all BiomeBase fields in the BiomeBase class, so
       * we can loop though all available biomes afterwards. Here, again,
       * I am using reflection so we have no problems later when minecraft
       * changes the fields name
       */
      for (Field f : BiomeBase.class.getDeclaredFields()) {
        if (f.getType().getSimpleName().equals(BiomeBase.class.getSimpleName())) {
          if (f.get(null) != null) {

            /*
             * this peace of code is being called for every biome,
             * we are going to loop through all fields in the
             * BiomeBase class so we can detect which of them are
             * Lists (again, to prevent problems when the field's
             * name changes), by doing this we can easily get the 4
             * required lists without using the name (which probably
             * changes every version)
             */
            for (Field list : BiomeBase.class.getDeclaredFields()) {
              if (list.getType().getSimpleName().equals(List.class.getSimpleName())) {
                list.setAccessible(true);
                @SuppressWarnings("unchecked")
                List<BiomeBase.BiomeMeta> metaList = (List<BiomeBase.BiomeMeta>) list.get(f.get(null));

                /*
                 * now we are almost done. This peace of code
                 * we're in now is called for every biome. Loop
                 * though the list with BiomeMeta, if the
                 * BiomeMeta's entity is the one you want to
                 * change (for example if EntitySkeleton matches
                 * EntitySkeleton) we will change it to our
                 * custom entity class
                 */
                for (BiomeBase.BiomeMeta meta : metaList) {
                  Field clazz = BiomeBase.BiomeMeta.class.getDeclaredFields()[0];
                  if (clazz.get(meta).equals(nmsClass)) {
                    clazz.set(meta, customClass);
                  }
                }
              }
            }

          }
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
