#Active
* When two other keys are already being pressed, pressing the space bar key does not work properly (but other keys still do).  Pressing the space bar key does work when one or fewer other keys are being pressed.

* It is possible to create and save a game in the creator that cannot be loaded by the Player (crashed the Player). To reproduce: create multiple levels with actors, create a blank splash screen, then save the game and try to load it with the Player.

* Exception raised at the end of a drag and drop by LevelMap.java:59

* When the left arrow key is pressed in the player, the minimap decreases in size.  When the right arrow key is pressed in the player, the minimap increases in size.

#Considerations

* Make Parameter Objects Immutable
* Why do keycode triggers use "keycode" as the key to the parameter? Please use ParametersKey type + index

#Resolved
* Sprites are not centered over their location.  Instead, the location is the top left corner of the sprite.


