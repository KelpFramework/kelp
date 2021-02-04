# v0.1.1
> Release date: 04.02.2021

**Events & commands changes**:
* Rework dynamic listener system:
  * Dynamic listeners are now built with generic types, meaning that they can adjust to your event, and you don't have to cast your event anymore when handling it.
  * Add timed/scheduled expires to dynamic listeners: You can now give a specific time in which the listener will expire. 
  * Add minimal executions: You can now give a specific amount of minimal executions of the listener, which means that it has to be executed `n` amount of times, before it expires, even though other conditional expiry checks would lead to an expiry. Scheduled expires have a higher priority and will unregister the listener even though the event has not been handled `n` times.
  * Add event criteria: You can now add unlimited criteria to an event. Only if all criteria are fulfilled, the event will be handled. Those criteria can be used to check whether the action of a specific interaction is valid, etc.
  * Add default event criteria in `EventCriteria`. Common criteria such as checking if the player has a specific permissions are included here to avoid typing the predicates yourself every time.
* Custom Kelp events now use the new event supertype `KelpPlayerEvent` added in the last release:
  * `KelpInventoryCloseEvent`
  * `KelpInventoryOpenEvent`
  * `KelpInventoryUpdateEvent`
  * `KelpInventoryCloseEvent`
  * `KelpPlayerLoginEvent`
  * `KelpPlayerUpdateSettingsEvent`
  * `KelpInventoryCloseEvent`
  * `KelpInventoryCloseEvent`
  * `KelpInventoryCloseEvent`
* Add 'smart inject' to listeners using the `@Subscribes` annotation. If your event is a `PlayerEvent` or `KelpPlayerEvent` and has a single parameter with the player of this event, the player parameter will be injected automatically.  
* Documentation improvements for text animation classes
* Add `delegatePlayerToConsole(bool)` method to `KelpCommand`, which is a command property that can be assigned in `onCommandRegister`. It executes the `onCommand` method of the console sender if the executor type is set to `PLAYER_AND_CONSOLE` and a player executes the command. If your command has the same behaviour for players and consoles, this is useful to avoid duplicated command handlers.
* Add `inheritFromMainCommand(bool)` property to `KelpCommand`, which can be used in sub commands to inherit basic properties such as permission and messages from the main command (`@CreateCommand`) to the sub command (`@CreateSubCommand`). So you don't have to assign the properties manually for every command anymore.
* Fix bug in sidebar cluster system that caused a `NullPointerException` when a player quit during a server reload.
* Schedulers can now be produced via static factories