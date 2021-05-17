# Apache Flink® SQL Assignment

**This repository provides a training for Flink's SQL API.**

**This repository contains the Flink SQL Assignment.**

The assignment will cover:

- SQL queries on Streams
- Filtering, Projection and Aggregation
- Grouping and Windowing 
- Joins

### Requirements

You **only need [Docker](https://www.docker.com/)** to run this assignment. </br>
You don't need Java, Scala, or an IDE.

### Setting Up the Environment 

1. Clone this repo 
  ```bash
  git clone https://github.com/semlanghi/sql-training
  ```
2. In the cloned repo, run `docker-compose`
- Linux & MacOS
  ```bash
  docker-compose up -d
  ```
- Windows
  ```bash
  set COMPOSE_CONVERT_WINDOWS_PATHS=1
  docker-compose up -d
  ```
  
## Setting

Two NBA games are held in the related cities. The NBA enabled the tracking of the games' actions through a combination of cameras in the arenas and sensors on players' shirts. Sensors are held only by on-field players, that, when substituted, give the sensor to their companions. The tracking results in 3 different streams of data:

- _Passages_: contains the stream of passages made by a player with a given sensor, and it contains the sender and receiver's sensor ids (`senderId` and `receiverId`), the timestamp `gameTime`, the id of current action in the game (`actionId`, by action we mean a set of passages between players of the same team, and an optional final shot), the game identifier `gameId`
- _Shots_: contains the stream of shots made by a player with a given sensor and it contains the shooter's sensor id (`playerId`), the timestamp `gameTime`, the id of current action in the game (`actionId`, by action we mean a set of passages between players of the same team, and an optional final shot), the game identifier `gameId`, and a boolean indicating if the shot was made (`isMade`)
- _Substitutions_: contains the stream of substitutions represented by the name of the player who is entering the game `playerName`, the sensor that he is inheriting (`playerId`), and a timestamp `substitutionTime` 


### Functions

- `team(long sensorId)`: a function that given a sensor id returns the team that is using the sensor 

### Query 1

The query should return the stream of Passages from the Lakers team. 

### Query 2

The query should return the number of lost balls (wrong passages), per each team. 

### Query 3 

The query should return the average number of shots made across the four quarters, per each team. Every quarter of the game is 12 minutes (assume no break between quarters). 

### Query 4 

The query should return the number of assists (passages immediately before a made shot) per each team. Assume in this case, that no player touches the ball more than once in the same action. 

### Query 5

The query should return the number of points per each player, reporting the player name. Assume that only 2-point shots are allowed.

N.B.: for the purpose of this query, consider the stream of Substitution *finite*.

### Optional (Query 4 Refinement)

The query should return the first and last passages of a successful action, i.e., a series of passages followed by a made shot. 



----

*Apache Flink, Flink®, Apache®, the squirrel logo, and the Apache feather logo are either registered trademarks or trademarks of The Apache Software Foundation.*
