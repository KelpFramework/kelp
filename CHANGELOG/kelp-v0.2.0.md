# v0.2.0
> Release date: 15.02.2021

**The NPC update**:
* Many changes and enhancements in NPC system:
  * You can now hook into the NPC heartbeat (custom NPC tick system) by creating an `NpcActivity`.
  * Methods like `followHeadRotation()` or `imitateSneaking()` are now replaced with activities (`LookToActivity`, `SneakingActivity`), which also allows you to change the player the NPC is imitating the sneaking behavior of, etc. This makes NPC much more customizable and extensible.
  * NPC heartbeat is now using asynchronous Kelp scheduler library (`RepeatingScheduler`)
  * Add basic walking mechanics to NPCs with `WalkToDirectionActivity` and `WalkToTargetActivity`
  * NPCs can now sprint, which also has effects on how fast they can walk (calculated with `MovementSpeed`)
  * You can now modify the title lines of an NPC during its lifetime and change the content as well as the amount of the individual lines.
  * An NPC is now bound to a specific player. This player has to be assigned before you spawn it for the first time. So you cannot use the same NPC instance for multiple players anymore.
  * You can now teleport NPCs freely (`teleport(Location)`) and move their head with `lookTo(Location)` without having to refresh the metadata.
  * You don't have to refresh your NPC anymore when you want to make it sneak/burn/invisible anymore. Refresh is now done automatically.
  * You can now hide/show NPC custom names (remove the empty title line above the NPC's head)
  * You can now set a custom tab name for an NPC that varies from its custom name and toggle whether the tab list name should be visible or invisible for other players.
  * With `AutoSpawnActivity` you can now fix that NPCs despawn when they are out of range and become invisible for a player.
  * You can now equip an NPC with armor and change the item in hand during NPC lifetime.
  * You can now play animations on NPCs (such as a damage animation, swing arm, etc.)
  * NPCs can now sleep inside a bed or act as a corpse on the ground (still experimental and not 100% optimized)
  * It is no longer distinguished between `spawnLocation` and `currentLocation` of an NPC. You can only modify its current `location`, which will then be used to spawn and move the npc later.
  * Add static factory for `KelpNpc`
  * Add some NPC-related custom events:
    * `NpcSpawnEvent`
    * `NpcDespawnEvent`
    * `NpcToggleSneakEvent`
    * `NpcInteractEvent` (you can also listen to NPC interactions with an inline interaction listener `KelpNpc#onInteract(NpcInteractEvent)`)
* Create static factory for `KelpItem`, all inventory types and all default Kelp widgets
* Add `rows(int)` method to `AnimatedInventory` to make inventory sizes more readable
* Fix NullPointerException when creating a `KelpItem` with type `AIR`