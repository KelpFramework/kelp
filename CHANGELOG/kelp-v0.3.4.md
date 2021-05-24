# v0.3.4
> Release date: 21.04.2021

**The declarative command update**:
* Add [`KommonCommand`](https://github.com/DSeeLP/Kommon/tree/development/command) to the kelp codebase (`de.dseelp.kommon.command`)
  * This adds a Brigadier-like API to write declarative commands instead of using the old command system. Brigadier is the command library developed by Mojang and used in the Minecraft Source Code.
  * The old command system has not been removed yet, but will experience several changes in the future to encourage developers using the new API.
* Add `KelpCommandSender` interface implemented by all users who can execute commands (`KelpConsoleSender` and `KelpPlayer`)
* Add `@CreateDeclarativeCommand` annotation marking a class containing a declarative command. The corresponding interface `DeclarativeKelpCommand` also has to be implemented by every command class.
* Add example command with the new system to the testing module (`KTestCommand`)
* Change build settings in `core/pom.xml` so that Kotlin can now be used in core code. This has been done to compile and use the Kommon classes, but can also be used to write Kotlin for normal Core functionality. But please don't use kotlin-only features inside the core as there will be a separate Kotlin extension for such purposes in the future.

Credits for the declarative command implementation go to [@DSeeLP](https://github.com/DSeeLP), who developed this update and the underlying project KommonCommand.