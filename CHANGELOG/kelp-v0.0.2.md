# v0.0.2
> Release date: 03.08.2020

**The inventory update**:
* Add item-bound listener system
* Implement new widgets: `ToggleableWidget`, `ItemWidget`, `Pagination`
* Remove redundant bukkitSubId modifier from `KelpMaterial`
* Interactions (taking out of an inventory, ...) with `KelpItems` are now canceled by default and have to be enabled manually
* Improve/Add documentation for many classes
* Fixed bug that CPU load was on 100% when `AnimatedInventory` was opened.
* Fixed NPE when trying to get item tags of items with type `AIR`