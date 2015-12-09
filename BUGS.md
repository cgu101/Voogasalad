#Active
* When two other keys are already being pressed, pressing the space bar key does not work properly (but other keys still do).  Pressing the space bar key does work when one or fewer other keys are being pressed.

* Collision circles are far larger than their actors, presumably becuase they depend on the deprecated "size" attribute/

* In the asteroids example, holding down spacebar fires every frame.

*? The turning does not work the same way it does in arcade asteroids.

* The actor editor (when not on the interaction pane) is useless and possibly doesnt have a purpose at all.

* Selected interaction trigger in actor editor does not immediately change display when it is edited.

* LoseGame action set to a key trigger from the authoring environment not working.

* Actor editor displays xLocation, yLocation for actor groups which are irrelevant (for a group, they only matter for an individual.)

* Deprecated property size needs to be removed.

* When in the authoring environment, editing an individual actor by right clicking and changing properties does not change its representation on the map accordingly.

* Load Game File from authoring environment doesnt work


#Considerations

* Make Parameter Objects Immutable

#Resolved
* Sprites are not centered over their location.  Instead, the location is the top left corner of the sprite.

* It is possible to create and save a game in the creator that cannot be loaded by the Player (crashed the Player). To reproduce: create multiple levels with actors, create a blank splash screen, then save the game and try to load it with the Player.

* Exception raised at the end of a drag and drop by LevelMap.java:59

* When the left arrow key is pressed in the player, the minimap decreases in size.  When the right arrow key is pressed in the player, the minimap increases in size.
