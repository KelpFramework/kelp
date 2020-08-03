# Kelp Changelog

This file contains the changes made in the different releases of the KelpFramework.

The headlines with `MR <number>` stand for the major release. The following headlines mark the minor releases/versions.

## MR 0

#### v0.0.0 (28.09.2020)
* **Beginning of Kelp development**
* First commit to the Git repo

#### v0.0.1 (30.06.2020)
* **First release of Kelp**
* Add entity system
* Add command system
* Add NPC system
* Add configuration system
* Add Netty-Wrapper
* Add sidebar system
* Add entity system
* Add 1.8 support

#### v0.0.2 (03.08.2020)
* **"The inventory update"**
* Add item-bound listener system
* Implement new widgets: `ToggleableWidget`, `ItemWidget`, `Pagination`
* Remove redundant bukkitSubId modifier from `KelpMaterial`
* Interactions (taking out of an inventory, ...) with `KelpItems` are now canceled by default and have to be enabled manually
* Improve/Add documentation for many classes
* Fixed bug that CPU load was on 100% when `AnimatedInventory` was opened.
* Fixed NPE when trying to get item tags of items with type `AIR`

