#Active
* When two other keys are already being pressed, pressing the space bar key does not work properly (but other keys still do).  Pressing the space bar key does work when one or fewer other keys are being pressed.

* It is possible to create and save a game in the creator that cannot be loaded by the Player (crashed the Player). To reproduce: create multiple levels with actors, create a blank splash screen, then save the game and try to load it with the Player.

* ActorBrowser.properties has an unresolved merge conflict in it.

*? Changing the filename in ActorMonitorCell.properties does not change anything.

* Exception raised at the end of a drag and drop by LevelMap.java:59

#Considerations

* Make Parameter Objects Immutable

#Resolved


