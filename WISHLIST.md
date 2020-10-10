# Kelp Wishlist

This file contains features which were requested by the community or other sources and are planned to be implemented into one of the next Kelp releases.

Before you create a new feature request, please make sure your idea has not already been posted in this wishlist to avoid duplicates.

## Features on the wishlist:

* Particle engine
* `KelpCommand#inheritSettingsFromParent()`: Settings like permissions, messages, etc. do not have to be set in every sub command but can be inherited by the main command.
* Individual command methods when executor type is set to `PLAYER_AND_CONSOLE`. Currently if you have this type set, you can only use the command method using the console sender as parameter, but in future you should be able to use both methods and the right one for the individual command sender (either player or console) is picked by kelp. So you can create personalized commands.
* Add more unit tests where possible
* Use caffeine as caching method