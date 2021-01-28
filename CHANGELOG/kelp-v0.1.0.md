# v0.1.0
> Release date: 28.01.2021

**The sidebar update**:
* Rework sidebar system:
  * The old, annotation-based sidebar system using `@CreateSidebar` is not available anymore. Plugins that have been using this system will have to upgrade.
  * Rework component system (stateful & stateless)
  * Add new component `StatefulListComponent` to display dynamic lists in sidebars
  * Developers can now choose between lazy updating (manipulating team prefixes, flicker free) or normal updating (resetting the scoreboard), which was not possible with the old system.  
  * Move version dependent scoreboard code out of the core module to the version modules (scoreboard team handling, etc.)
  * Rework animation system by replacing it with a cluster-based system
  * Add new events for handling sidebars:
    * `KelpSidebarRenderEvent`
    * `KelpSidebarUpdateEvent`
    * `KelpSidebarRemoveEvent`
  * Create new event base: `KelpPlayerEvent`, which should replace the `PlayerEvent` for better integration of `KelpPlayer` into event handling. So if you create anything player-related with custom events, use this class instead.
  * Sidebar components and sidebar objects are now created using static factory methods. If you like the old approach more, you can still rely on the old factory classes such as `SidebarComponentFactory` 
  * Sidebars can now be properly removed/hidden from a player
* Documentation improvements in `KelpPlayer` and all newly added classes.  
* `TextAnimations` can now be created using static factory methods.
* Add `ksidebar` command in testing-module to demonstrate some sidebar components.
* Refactor testing-module by assigning dedicated packages to each feature to test  
* Fix bug that `TimeConverter` did not convert milliseconds <-> ticks correctly