# v0.5.0
> Release date: unknown

**The hologram and raycasting update**:

* Change `KelpLogger` to be more compact and simple: 
  * Remove own logging files and folder. During development, this custom folder has not proven as useful and just ate resources that could have been used better.
  * `KelpLogger` is now a subclass of `java.util.logger.Logger` and therefore Java's default logging methods are used.
  * Remove `LogLevel` enum as Java's log levels are now used, where `FINE`, `FINER`, `FINEST`, and `CONFIG` are only shown in debug mode.
  * Properly link loggers to their corresponding kelp applications: `KelpLogger.of(YourPluginMainClass.class).info(...)`
  * When logging from the core module, use `KelpLogger.of(KelpApplication.class)...`
  * This way you don't have to let Guice inject the logger for you, reducing boilerplate dependencies. 
* Add Raycasting library. You can create a new Raycast using `Raycast.create()`. For more info, check the corresponding wiki page.
* You can now insert components at a specific index in an `InteractiveMessage`, which required to change the component list type from `Collection<...>` to `List<...>`. **If your plugin used `getComponents()`, then you will have to recompile it to update the method signature!** 
* The raw text (excluding color codes) of `InteractiveMessages` can now be queried using `getRawText()` 
* Add `ImageMessage` allowing you to display image files in the chat.
* Gravity can now be disabled for all entities. This is a native feature in newer versions, for 1.8 a workaround has been made by spawning no-gravity armor stands and setting the entity as passenger.  
* Fix bug that `SlimeEntity` could not be spawned because it could not be cast to a mob/creature in `1.8`.
* Add `getEntities()` method to `KelpChunk`  
* Fix bug that `getPlayers()` method of `KelpChunk` threw a `NoSuchFieldError` 
  * This was because the CraftBukkit API and the NMS code of the spigot server differed and had different method signatures of `entitySlice` (the way how entities within a chunk are represented)
  * So now the `entitySlice` is accessed via reflection, which might negatively impact performance of methods depending on it (currently `getPlayers` and `getEntities`)
* Add debug tool to particle library: `ParticleVisualizable`
  * It can be implemented by any class that can be visualized in the in-game world with particles such as regions or raycasts.
  * You can apply different color schemes by using another `ParticleVisualizerProfile`
* Rename `getOuterCorners()` of `KelpRegion` to `getCuboidOuterCorners()`
  * The cuboid outer corners now have a defined order used by the `visualize` methods of regions
  * Therefore, there should be no confusion with the cuboid outer corners and custom implementations such as in a `MergerdRegion`
* You can now get an entities hitbox/bounding box as a `CuboidRegion`
* You can now get a block's collision or selection bounding box as `CuboidRegion`  
* Particle effects such as `ParticleLineEffect` are now cloneable and have static factories instead of a separate factory class
* Remove `CardinalDirection` and replace it with `KelpBlockFace` wherever it was used. It was basically the same as `KelpBlockFace` with lower accuracy and is therefore considered redundant.
* Create own `Vector3` class offering a version-independent alternative to bukkit's normal `Vector` class.
* Add a method (`setValue(Class, Object, String, Object)`) to `ReflectionUtil` allowing to set the value of a field declared in any parent class of the given object.
* You can now compare two entities using `KelpEntity#sameEntity(Object)` offering an alternative to the `equals()` method, which can not be overridden due to language limitations. 























