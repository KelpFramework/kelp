# v0.0.5
> Release date: 30.12.2020

**The player interaction update**:
* Add `KelpServer` class, which serves as a kind of kelp wrapper for the `Bukkit` class:
  * Broadcast messages to `KelpPlayers` (instead of bukkit players)
  * Fetch all online `KelpPlayers` of the server
* You can now send centered chat messages using the `KelpPlayer` as well as the `KelpServer` class.
* Add player prompts accessible via the `KelpPlayer` class:
  * `AnvilPrompt`: Use an anvil GUI to query text input from a player (default texts possible)
  * `SignPrompt`: Use the sign editor to query multiple lines of text from a player (default lines are possible)
  * `ChatPrompt`: Simple utility to use the chat as a method to query text input (default input not possible yet).
* Add methods to display a `boss bar` to a player (including process modification and boss bar removal)
* Add new custom event `KelpPlayerChangeSettingsEvent` that is triggered when a player changes their settings
* Fix bug that custom kelp inventory events crashed the plugin when they were actually used in an event handler
* Add `KelpPlayerLoginEvent` which is called on login of a player but after it has been initialized by kelp, so that calls to the `KelpPlayerRepository` are safe. If you want to handle logins now, it is recommended to use this event instead of the normal bukkit event.
* You can now send interactive messages to KelpPlayers. You can assign messages that are displayable when you hover over them and define events which are triggered when a player clicks on a message. You can combine unlimited components per message unlike the normal `TextComponent#addExtra` method which is wide spread across the spigot community.
* Documentation improvements in the code as well as in the GitHub wiki
* Create empty kelp sql module