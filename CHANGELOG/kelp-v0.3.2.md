# v0.3.2
> Release date: 19.03.2021

**The regions update**:
* Add basic region library to the kelp world library. 
  * Add supertype for regions `KelpRegion`
  * Add region implementation `CuboidRegion` representing a cuboid area of blocks
  * Add region implementation `EllipsoidRegion` representing ellipsoids including spheres and sphereoids
  * Add new event type `KelpRegionEvent`
    * Add `PlayerEnterRegionEvent` triggered when a player enters a region
    * Add `PlayerLeaveRegionEvent` triggered when a player leaves a region
* Add new multimap type `ConcurrentMultimap` offering multimaps with thread-safety.
  * `ConcurrentSetMultimap` using a set in the background
  * `ConcurrentListMultimap` using a normal list in the background
* Add a custom implementation of bukkits block face: `KelpBlockFace`  
* Add documentation to `KelpChunk`
* Add basic NPC and region tests to `testing-module`