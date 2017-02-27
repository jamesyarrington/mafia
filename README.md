# mafia
Mafia is a game typically between two teams, the mafia & town.

... More description of the game here ...

## Classes
Below is a description of each class.
### Game
This class's methods represent game events (like killing players or advancing the game to the next phase).
### Player
In a game there will be several instances of the Player class.  Players can select an action to perform (with a target), be voted for, and can have their status changed.
### Action
Each phase, players perform an action.  Each action has an `execute` method that is performed each phase on a target.  The class `Action` is subclassed, as seen with `Vote` or `SetStatus`, typically overriding the `execute` method to have some affect on the game.
### Role
A Role is assigned when a `Player` is created.  It is created in `RoleFactory`.  It consists of several `Action`s and a `Faction`.  For example, a "Goon" is a member of the Mafia, and can 1) `Vote`, 2) `Kill`, or 3) `Do Nothing`.
