# v0.3.1
> Release date: 04.03.2021

**Independent Widgets**:
* Widgets _can_ now independent from players (#44)
  * Added an additional KelpPlayer parameter to the following methods:
    - `KelpInventory.render(KelpPlayer)`
    - `GroupedWidget.render(KelpPlayer)`
* To summarize and cleanup all `Widget` implementations, the abstract class `AbstractWidget<W extends AbstractWidget<?>>` was added. It's responsible for player-specific actions like adding a `ClickListener` to a certain item.