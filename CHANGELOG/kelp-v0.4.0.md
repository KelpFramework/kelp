# v0.4.0
> Release date: 24.05.2021

**The entity update**:

* Make methods of `StringUtils` and `ReflectionUtil` static.
* You can now invoke methods using `ReflectionUtils.invokeMethod(Object, String, Object...)` and `ReflectionUtils.invokeStaticMethod(Class<?>, String name, Object... parameters)`  
* Move `isInWater` and `isInCobweb` method from `KelpPlayer` to `KelpEntity` as this is a more general method that can be applied to all entities.
* `KelpPlayer` is now an interface instead of a class for better integration into the entity system
  * Although it has ever been a sub-type of `LivingKelpEntity`, it was not handled as such. The `EntityTypeVersionTemplate` could not convert it. Now it is exactly handled as an entity with the only logical exceptions that you cannot create/spawn new ones (use `KelpNpc`), and you cannot remove players from the server (kick them instead).
* Complete rework of the entity library:
    * Old entity classes and methods are not supported anymore. They have been removed from the code.
    * Entities are now represented as interfaces in the core module instead of classes.
    * Those interfaces are implemented by the version modules - similar to bukkit. All entity types for MC1.8 have been implemented
    * Add new entity types to `KelpEntityType` enum that have been added in 1.15 and 1.16.
* With the entity rework, other features that entities depend on had to be implemented as well:
  * Add potion API allowing you to add potion effects 
    * Add `@MinecraftPotion` annotation, and a class for each potion effect type. The Kelp-Binder can now check for custom implementations of a potion effect type: If you run a 1.8 server, but want to use the levitation effect, this is no problem because the levitation effect can be reproduced by Kelp (just as you would create a version template and implementation). However, no effect has been emulated yet - only the mechanics exist and have been tested.
  * Add entity constants for all entities that exist in 1.8. You can now convert horse types, villager professions, etc. across different versions
  * Add `StorageInventory` interface, which represents a general inventory just like bukkit's `Inventory` interface. 
    * Add `HorseInventory` and `AbstractHorseInventory` allowing you to easily access a horse's saddle or armor.
    * `PlayerInventory` now also is a subtype of `StorageInventory` and has been converted to an interface as well.
* Add 1.16-1.16.5 versions to `KelpVersion` enum
* Add `getEntities()` method to `KelpWorld` returning a list of all entities in the given world.  
* Fix bug that entities could not be damaged by left click interaction when Kelp was installed. This was due to a bug that when an interaction packet was intercepted, it was not passed on to the server afterwards.
