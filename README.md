# VOOGASalad - Game Authoring Engine Project

Final Project for CompSci 308 Fall 2015

Name: Connor Usry

Due Date: December 12, 2015

Hours worked: ~100

> This is the link to the Analysis Description: [VOOGASalad](http://www.cs.duke.edu/courses/cps108/current/assign/04_voogasalad/)

##Specifications

My team, using JavaFX, designed a game authoring environment that allows game designers, people with no programming skills, to build arcade-style 2D video games of a specific genre, using a variety of visual tools and requiring minimal programming. These games should be saved in a format that allows them the be re-loaded by the program for additional editing or deployed, loaded into a different program to be played by a user. The game player might be located on the same machine as the editor or it might get access to the game files via the web.


**At a high level, this program can easily be divided into the following four components:**

1. **Game Authoring Environment:** program of visual tools for placing, specifying, editing, and combining general game elements together to make a particular game.

2. **Game Engine:** framework of general Java classes to support any kind of game within a specific game genre.

3. **Game Player:** program that loads the game data and uses the game engine to run a particular game.

4. **Game Data:** files, assets, preferences, and code that represent a particular game.


-----

###Graphical Authoring Environment

No modern game hard-codes individual levels into the source code. Instead, the programing team actually codes general game action but the levels and characters are built by game designers that place every obstacle, monster, powerup, etc. To make this possible, designers need an interactive environment that lets them:

* Place game elements (i.e., starting positions for a level)
* Determine the order of advancement (i.e., what level or stage follows the current one)
* Setup graphical elements (i.e., the level background or images used for each of the monsters)
* Assign reactions to collisions (i.e., what happens when a monster collides with the player)
* Assign reactions to interaction (i.e., what happens when a key is pressed or the mouse is moved)
* Set instructions, splash screen, player setup, level bonuses, etc.
* Tweak settings (i.e., monster hit-points or projectile speed or animation timing)
* Load previously created games to be edited again or nodded

### Game Engine

Games have many common characteristics that can be shared in a framework so that creating a new game requires only creating things specific to it. The engine provides a set of Java classes that handle these common tasks in a general way that is easily tailored to any particular game. Common aspects to consider include:

* Setting up, running, and completing levels or stages
* Rules, interactions, or events
* Goals, both small and large
* Progression through the game
* Behaviors or animations
* Game status information
* Game genre specific concepts


###Game Player

This separate program will load the data for a particular game and, using our game engine, allow a user to play the loaded game. Additionally, it allows the user to:

* Keep track of games' high scores through successive runs of the program until the user clears it
* Display dynamically updated game status information (i.e., heads up display (HUD))
* Replay the game repeatedly without quitting
* Switch games repeatedly without quitting
* See which games are available, including at least each game's name, image, and description
* Save their progress in the game to restart later (perhaps only at specific save points)
* Set preferences specific to each game played
