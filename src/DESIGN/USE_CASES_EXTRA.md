# Use Cases: #

> In another file, USE_CASES_EXTRA.md, write at least 40 new use cases for your project that describe specific features you expect to complete during the next sprint. Note, they can be similar to those given in the assignment specification, but should include more details based on your project's or genre's features and goals.

> It may be helpful to look at the following resource for help writing use cases: http://www.cs.duke.edu/courses/compsci308/fall15/readings/cockburn_use_cases.pdf

> Let’s have it so that everyone comes up with 4 use cases each, coming up with a total of 40, which is the requirement. Make sure that your use cases don’t overlap with anyone else’s. Write your use cases below in your corresponding name section before 10 AM tomorrow, so I can compile this into a markdown file and push it to master.

### Use Case 1: ###

#### Goals/Objectives: Determining level transitions based on global and local conditions ####

**Brief Description:**

The scope of determining level transitions spans primarily between the Game Engine and the Game Authoring Environment. The user must be able to manually define the necessary win/lose conditions based on editable conditions, either through actor-actor interaction (e.g. a spaceship colliding with an asteroid) or through global interaction conditions (e.g. running out of time in a time-constrained game). These conditions and objectives must be able to be represented in an actual game, with the engine determining when a level changes, or when a game ends.

**Types of users/classes that can engage in above activities:**

The CreatorScreen will be the class that engages with creating the specifics for the level transitions. The user would be able to define objectives either through a drag/drop interface, enterable values, or through a scripting language via Groovy.

The interaction tree needs to be able to accommodate conditions and objectives. The team was thinking maybe of using functional interfaces for these, and using predicates and consumers.

**Pre-conditions:**

The user can assume the following to be true when determining level transitions, and realizing goals/objectives:

- Levels are well-defined and are encapsulated in a larger class.
- A non-linear progression of levels. This means that levels can jump forward, backward, and can skip around.
- Triggerable interfaces exist to assist determining whether an objective is complete, is inactive, or is currently in progress.
- Interfaces exist to link objectives to an interaction tree node so that with each iteration of the game loop, the conditions are checked.

**Basic Flow:**

The user inputs a condition and a reaction and links this with a global behavior. On the engine side, every iteration of the game loop checks the condition, and if it is met, then the reaction action is done, which may include changing levels.

**Post Conditions:**

The condition lambda and object needs to be added to the object conducting game logic and which contains all the levels.

----------


### Use Case 2: ###

#### Create exception hierarchies that catch runtime errors and displays to the user when such an error occurs (e.g. assigning “infinite-loop” actions to a certain actor) ####

**Brief Description:**

We want to be able create a centralized hierarchy which can display errors to the user. For example, if the user tries to add a property that is forbidden to a certain actor, we want to be able to throw an exception and visually display it.

**Types of users/classes that can engage in above activities:**

The classes that can perform this are the exception classes. One possible solution is to create 4 abstract exception classes for each major portion of the project: AuthoringException, EngineException, PlayerException, and DataException. Each instance of these will extend one of the four specified. These are thrown wherever errors could possibly occur. These would be displayed by the classes in view.screen.

**Pre-conditions:**

The user can assume that the following are true when throwing errors:

- There is a tangible link between front-end and back-end errors.
- Every error thrown can be expressed using a dialog box in front-end classes.

**Basic Flow:**

When a user encounters an error, exceptions are thrown. For example, for an engine exception, a signal is generated and processed as a “heartbeat” to the PlayerController, which tells front-end classes to open a dialog box while, at the same time, pausing the game loop. Depending on the type of error, once the user has successfully closed to dialog, the game loop resumes.

**Post Conditions:**

We can assume that after processing the exception, any error encounter does not break the program.


----------


### Use Case 3: ###

#### User is playing a game, and decides he wants to change the behavior of a certain actor (e.g. modify an asteroid so that it doesn’t split on laser collision but rather just explodes). Create a pause-modification interface to do this. ####

**Brief Description:**

This use case integrates the authoring environment and the game engine. We want to be able to seamlessly modify actor components, change conditions/behaviors/objectives, and have the game still function properly.

Types of users/classes that can engage in above activities:
This use case would primarily involve classes in the game engine package as well as classes in the authoring environment classes.

**Pre-conditions:**

We can assume that the engine is fully functional, and can play a game by itself, without being modified.

**Basic Flow:**

PlayerController starts a game, and begins the game loop. When the user wants to modify parameters and states, the game loop is paused, and a connection is made between the authoring environment and the game engine. After state, parameter, and behavior modification, the link between the authoring environment and the engine environment is broken, and the game loop resumes.

**Post Conditions:**

After game modifications, we can assume that the modified game functions properly.


----------


### Use Case 4: ###

#### User decides while playing a level that he isn’t doing so well, and wants to restart the level at the beginning of the level. We want to create an interface to persistently save the state of the level. ####

**Brief Description:**

We want to be able to allow the user to be able to choose which level he is on. This means that every level needs to be able to have an initial state that is persistently saved. As the user progresses, his current state as well as the initial level state needs to be saved.

Types of users/classes that can engage in above activities:
The game engine classes as well as perhaps classes in the data package would need to be used to engage in the above activity.

**Pre-conditions:**

We can assume that game state can be dynamically saved throughout the timeline of the game loop.

**Basic Flow:**

PlayerController begins the game loop, but at each level, the user has the option of restarting the level, which then forces the level to reinitialize to its previous “level” state.

**Post Conditions:**

Post conditions include the objectives must be able to be reset, as well as any behavioral aspects of Actors need to be reset as well. Given that the game and levels need to be saved into memory, the persistent “initial levels” need to be serialized using XStream.


----------


### Use Case 5: ###

#### The user loads a savestate that is from a different type of game. The engine compares the ID of the savestate to that of the current game to determine whether the state can be accepted. If they differ, the engine throws an exception for the player to handle.  ####


----------


### Use Case 6: ###

#### The user saves a particular HUD configuration to use across different games. ####


----------


### Use Case 7: ###

#### The user creates HUD elements in the authoring environment, such as a timer or level metadata.  They should be customizable like actors. HUD elements may have to watch the state of specific actors. ####


----------

### Use Case 8: ###

#### The user should be able to set events to happen on level transition, and specify which level should be the next one (rather than going linearly through the levels according to their order in the authoring environment). ####


----------

### Use Case 9: ###

#### Filler ####


----------


### Use Case 10: ###

#### Filler ####


----------

### Use Case 11: ###

#### Filler ####


----------

### Use Case 12: ###

#### Filler ####


----------

### Use Case 13: ###

#### The user places an instance of an actor on the screen but decides to delete it or move it to another position.  A new AbstractDockElement will allow the user to modify individual actors and turn them in certain directions.  It was also allow the user to decide if this particular actor should be visible in the ActorMonitor and if it should be different in some way from the rest of the actors of its type. ####


----------


### Use Case 14: ###

#### The user should now be able to add new actors, new properties for actors, and new trigger-based behaviors.  This will require and implementation on the back end and new methods for modifying properties on the front end. ####


----------

### Use Case 15: ###

#### The user can graphically visualize the connections that an actor is involved in (through externaltriggers) in a graph like settings.  When the user opens up this visualization interface (accessible somewhere in the browser or editor windows) the user will see an image of the selected actor, connected by arrows to all other actors it is involved with.  This will allow the user to easily monitor the relationships between actors, or modify them as desired from this screen. ####


----------

### Use Case 16: ###

#### The user is able to pull up a graphical representation of a keyboard that shows all keys that have been assigned to actors (like a library of sorts that that keeps track of which actors have checked out which keys).  Thus, the user will be able to tell which keys control which actors and ensure that the same key is not intentionally assigned twice. ####


----------

### Use Case 17: ###

#### Actions perform a number of changes on different Properties based on what Properties the Actor has (essentially multiple actions are linked together). Because Actions are now also associated with a set of Properties that they affect. ####


----------


### Use Case 18: ###

#### The user can pick different types of Actions based on different triggers that are selected. ####


----------

### Use Case 19: ###

#### The user can pick different parameters for the Actions based on what type of Action was selected. ####


----------

### Use Case 20: ###

#### The user can specify properties and values that generic Actions (like changeBy...) can act on on a certain trigger. (Future) ####


----------

### Use Case 21: ###

#### The user can create time-based triggers and actions, perhaps through interactions with a special timer actor. ####


----------


### Use Case 22: ###

#### Multiple separate trigger conditions for an actor are satisfied, each causing a different action that tries to modify the same property.  For some properties, such as location, the action of highest precedence should be chosen, while for other properties, such as health, multiple actions can take effect in the same iteration (get shot -> lose health, drink potion -> increase health). ####


----------

### Use Case 23: ###

#### After creating and placing several actors of one type onto a level in the authoring environment, the user decides to change the name of that actor type.  All actors of this type already created must update their identifiers.  Additionally, the old group should be deleted from ActorGroups, and the actors should be re-added under their new group name. ####


----------

### Use Case 24: ###

#### The user specifies an action to occur only when two or more triggers are satisfied at once.  This may involve restructuring how our interaction tree is built and read. ####


----------

### Use Case 25: ###

#### The user can place Actors onto the display area of the map by dragging and dropping actors from the side pane, and can remove those Actors by deleting them. The Map keeps track of all the Nodes (Actors) that were placed onto it, and can delete them when necessary. ####


----------


### Use Case 26: ###

#### User can zoom in and out of the Map’s display area using the slider bar underneath the map, and the map and actors placed on the map will scale accordingly. Currently, dragging the slider to its maximum value will fit the map’s width within the bounds allocated to the map. ####


----------

### Use Case 27: ###

#### (To be implemented) User can see a minimap on the bottom of the screen that visually indicates the boundaries of the map display area they are currently looking at relative to the entire map’s area. The minimap might be transparent and occupy the bottom right corner of the map’s display area. Dragging the slider and thus changing the area of the screen the user can see will be reflected within the minimap.
 ####


----------

### Use Case 28: ###

#### When the user chooses to go into full-screen mode, the Map, its display area, and all the actors within it will resize and reposition themselves correctly. ####


----------

### Use Case 29: ###

#### User can set up characteristics, features, and triggers for actors in one level and then access those same actors in all the other levels, while still being able to create new ones whenever the user desires. ####


----------


### Use Case 30: ###

#### User can set their own image to be the background image for a level. In fact, the user can tile smaller images to be a single large background image (this may be necessary for tower defense levels in which there is a set path that the user creates.) ####


----------

### Use Case 31: ###

#### User can directly go from the authoring environment to the player and play the game he/she has just created, without saving it to a file. ####


----------

### Use Case 32: ###

#### Saved games will also include data like high score, and prompt the user to give a name to go with that high score, if they have reached the high score.  ####


----------

### Use Case 33: ###

#### Filler ####


----------


### Use Case 34: ###

#### Filler ####


----------

### Use Case 35: ###

#### Filler ####


----------

### Use Case 36: ###

#### Filler ####


----------

### Use Case 37: ###

#### The user loads a game and selects to show the Actor Monitor. This actor monitor will display individual and/or groups of actors and their important properties. These can be refreshed within the game loop or by hand. ####


----------


### Use Case 38: ###

#### The user loads a game and selects to show the HUD. This dynamic HUD, specific to the type of game created, will show relevant stats to the game state that are from Global parameters that were selected in the authoring environment. ####


----------

### Use Case 39: ###

#### A user will be able to modify game preferences (by selecting to show the preferences component) These preferences will be specific to the type of game created that will be used to control the overall game settings (not specific to a particular level). ####


----------

### Use Case 40: ###

#### A user will be able to view the ‘about’ page from the Main Menu to see details about the project as well as short bios about each team member. ####

