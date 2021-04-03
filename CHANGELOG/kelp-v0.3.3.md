# v0.3.3
> Release date: unknown

**Small inventory patches**:
* Item click listeners now use a `Consumer<KelpClickEvent>` instead of the custom `ClickListener` interface.
* You can now use data types other than String to tag a `KelpItem`
* Item tags can now be retrieved from `KelpItem` class directly using `getIntTag(String key)` for example. This fixes issue #50.
* Remove `@CreateInventory` annotation. Annotation-based inventories won't be supported.  
* `ItemStack` can now be converted to `KelpItem` via `KelpItem.from(ItemStack)` as well.
* `KelpInventory` now has a `onClose` method that can be overridden if needed to stop things like schedulers when the inventory is closed 
* Now the `KelpInventory` abstract class is holding all widgets instead of letting this do by each individual implementation.
* Add `PlayerInventory` class allowing for better integration of `KelpItem` in player inventories
  * This allows you to do things like `KelpPlayer#getInventory()#setItem(slot, KelpItem)`
  * You can add normal `Widgets` to a player inventory and create a pagination of lobby servers like that for example
  * Fix duplication bug when item click was canceled inside a creative inventory 
* Fix bug that converting a bukkit `ItemStack` to `KelpItem` did not convert item tags as well.
* Change inventory state management:
  * Remove `SimpleStatelessInventory` as Kelp does now not focus on completely stateless inventories anymore. It is considered more useful to only make specific widgets stateless and exclude them from the update process.
  * Replace `ItemWidget` with `StatelessItemWidget` (static content) and `StatefulItemWidget` (updatable content)
  * Every widget now has `isStateful()` method indicating if it contains updatable content. When an inventory is updated, not all slots are cleared and replaced anymore, but only those of stateful widgets.
* You can now check if a player has a `KelpInventory` open using `KelpPlayer#hasKelpInventory()` 
* Write an implementation for `SimpleInventory`, which can now finally be used just as `AnimatedInventory`
* Add properties to `KelpMaterial`: You can now check whether a specific material is an item or a block and if so which blast resistance and which hardness it has, etc.
  * Add item groups such as armor, tools, melee weapons, etc. 
* Add `SlotArea` class calculating slot areas like lines or rectangles based on the inventory size. This makes it easier to define the areas of a pagination or a placeholder.
* Add `PlaceholderWidget` which can be used to add placeholder items (such as glass panes, etc.) to any inventory.
* Add `ItemMetadata` for `KelpItems`:
  * You can now give any KelpItem a metadata depending on which material it has. This enables you to use colored armor, custom head textures, etc.
  * `LeatherArmorMetadata`: Give color to leather armor parts 
    * Add `Color` class representing RGB colors and offering some minecraft default colors 
  * `SkullMetadata`: Give either custom or player textures to skull items (`PLAYER_HEAD`)
    * Add `HeadTexture` class for conversion between custom skins and their base64-URL needed by minecraft to display the skins.
* Documentation improvements for some NPC classes and other item classes
* Add `getRomanNumber(int)` method to `MathUtils` allowing to get the roman notation for a decimal number
* Add custom enchantments:
  * You can now define default minecraft enchantments as `KelpEnchantment` for version independence (`KelpItem#enchant(KelpEnchantment class, level)`)
  * You can create your own custom enchantments by extending from `KelpEnchantment`
  * Implement glow effect of `KelpItem`: There is now a custom enchantment `ItemGlowEnchantment` which can be applied to simply make an item glow without actually giving special abilities.
* A `Pagination` widget now takes a supplier of widgets instead of a normal list allowing the amount of items inside a pagination to be changed.
* The `Pagination` widget now accepts `SimpleWidgets` instead of only normal Kelp items.
* Add `ORB_PICKUP` to `KelpSound` enum















