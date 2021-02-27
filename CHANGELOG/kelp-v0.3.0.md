# v0.3.0
> Release date: 27.02.2021

**The world update**:
* Add custom library to for world and location management:
  * Add `KelpWorld` class replacing the normal `World` class by bukkit. It still does not offer all methods of a default bukkit world, but offers integration with other kelp world classes.
  * Add `KelpLocation` offering a replacement for the normal bukkit `Location`. It offers some more methods for vector calculation for example.
  * Add `KelpChunk`, which is a replacement for the normal bukkit `Chunk`. On top of the normal bukkit methods, it offers methods to work with neighbouring chunks.
  * Add `KelpBlock`
  * All the mentioned classes port methods from newer versions to older versions for example bone meal application for `KelpBlock` or checking whether a `KelpChunk` is a slime chunk. More method ports will follow.
  * Add some util methods used by the kelp world classes:
    * `WorldType` expresses whether a world is overworld, nether or the end
    * `CardinalDirection` expresses in which direction a player or relative block is facing. It simplifies the work wit `yaw` 
    * `ExplosionPower` is basically just a wrapper for a float, but it contains some default values for default minecraft explosion types, which makes the code more readable. Instead of `4F`, you would write `ExplosionPower.TNT`
  * Add functionality to force load chunks
* The `KelpPlayer`, `KelpEntity`, `KelpNpc` class as well as the particle library are now using the new location system instead of the normal bukkit system.
* Add `KelpDummyPlugin` which can be used to emulate a plugin (`KelpApplication`) when developing core features where providing a plugin instance is needed.   
* Fix some material sub ids of the above mentioned ones.
* Add `MathUtils` class providing some useful mathematical functions
* When tagging an item of type `AIR`, the method does not return `null` anymore but the original item stack without a tag. This should avoid unexpected NPEs in the future.
* Documentation improvements
* Fix #45: Item description of `KelpItem` was not rendered.















