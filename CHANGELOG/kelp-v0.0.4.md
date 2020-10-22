# v0.0.4
> Release date: 22.10.2020

**The event scheduling update**:
* Add `@Subscribes` annotation which allows to call certain methods on an event but still use them in the code
* Add custom kelp listeners, which can be added and removed dynamically using the `KelpEventRepository`
* Change name of default bukkit listener registration to `EventHandlerRegistration`. This has to be updated in your application code!
* Add scheduler system: `RepeatingScheduler` and `DelayedScheduler`
* Add thread synchronization utils in `ServerMainThread`, which is accessible via a static factory
* Implement feature suggested in #29: Add some custom events for kelp inventories (open, update, close)
* Fix bug #27: Command was not always called when executor type `PLAYER_AND_CONSOLE` was set. If you want to use this executor type now, you can use both `onCommand` methods and kelp picks the right method depending on who is executing the command.
* Fix bug #31: Fix some exceptions which occurred when players quit during a server reload.
* Change changelog structure from a single file to one file per version. 
* Documentation improvements