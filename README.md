# Online Multiplayer JRPG

This game was built in the time alloted (24 hours) for Hackpoly 2017 by our team members from Random 5.

**Status**: The basic game mechanics are just about completed.

* There's a few bugs
with the multiplayer, and there is no multiplayer in _Combat_ / _Battlefield_.
* _World_ and _Player_ are rendered, and player can move and interact with enemies and terrain.
* In _Battlefield_, the player can start the attack or fireball animations by clicking
the corresponding buttons. There is no change in any health values or any way to
leave the _Combat_ mode once entered.


In the game we're able to join another player who is running a server by entering their IP address and port number of the server. This brings us to the _Lobby_ where we're able to ready up and go in game to the game _World_. In the _World_ we have the background map and character images drawn along with blocked zones which prevent the player from walking through the zone. If the player walks near an enemy in the _World_ map, then he'll be sent into *Combat* or the _Battlefield_ in which they fight the enemy. The _Battlefield_ has enemy and player attack and fireball spell animations implemented, and the animations will play when they attack.


### Install

1. Download or clone this repository
2. Generate default game project using the _lib gdx setup jar_ that's downloaded from their site
3. Copy local files (including hidden files) from this project into the generated project overwriting changes
4. Download [google protocol buffer v3.1.0](https://mvnrepository.com/artifact/com.google.protobuf/protobuf-java/3.1.0)
5. Add this downloaded external jar file to project core folder in Eclipse
6. Add workspace directory in Eclipse by going to Run -> Run Configurations -> Arguments -> Working Directory -> Other -> Workspaces... -> select ./core/assets folder

You might need to refactor -> rename the package name to match the package name determined when the
libgdx project was generated.


### Run

Follow the installation instructions and run the game using the `DesktopLauncher`
(it hasn't been tested on the other platforms).


### Screenshots

![Login](http://i.imgur.com/X6HQhek.png)

![World](http://imgur.com/RjLhkMN.png)

![Combat3](http://i.imgur.com/sLg0fN0.png)

![Combat1](http://i.imgur.com/xMrHlLy.png)

![Combat2](http://imgur.com/JI0Xaf8.png)


### Random 5

* Alex
* Hao
* Francisco
* Jerry
* James
