# TryO: An organizer for sports tryouts

### Proposal

TryO is a coaching tool meant to help evaluators and coaches record player data during a tryout process.
Oftentimes during a tryout, whether it may be for basketball, volleyball, or other sports, it is hard to keep
track of the number of participants all the while shuffling loose pieces of paper to record down information 
and testing results. TryO helps with this evaluation process, making it possible to track players by their ID, view
all the players, their descriptions, and their statistics.

TryO can be used by evaluators or coaches for any sports that involve tryouts. Having been part of tryouts
both as a player and as a coach, a tool like TryO would make it much easier to track player information, 
especially if there were numerous evaluators. Having a digitalized tool also means that the annoyances of using
pen and paper go away, with no more loss of papers, pens running out of ink, and papers getting wet or blowing
away in outdoor sports. 

## User Stories

<h3> Phase One: </h3>

- As a user, I want to be able to add and delete a player to a list of players
- As a user, I want to be able to write in descriptions about a player, including their
name, number, description, and position
- As a user, I want to be able to toggle a player's status between "pick" and "cut"
- As a user, I want to be able to view a list of all players in my list of players

<h3> Phase Two: </h3>

- As a user, I want to be able to save my list of players to a database
- As a user, I want to be able to load and view my list of players from the database
- As a user, I want the database to be updated whenever I add a player
- As a user, I want the database to be updated whenever I remove a player
- As a user, I want to be able to get a player from an existing player list and change their pick status from "pick" 
to "cut" and vice versa

<h4> Phase Three: </h4>
<ul>
<li>For phase three, I created a GUI for the TryOutApp. Running Main in the ui package will run the application. </li>
</ul>

<h3> Phase Four: </h3>
<h4> Task One </h4>
<ul>
<li>The GUI/Phase 3 is complete and has been pushed.</li></ul>
<h4> Task Two </h4>
For Task Two of Phase Four, I decided to make the PlayerList class robust. I added two of my own custom Exceptions, 
PlayerAlreadyExistsException, and PlayerDoesNotExistException. <br />
<ul>
<li>The PlayerAlreadyExistsException checks if the playerList already contains a player that wants to be added. If the 
exception is thrown (inside the addPlayer method in playerList), a message informs the user that the player they are 
trying to add already exists in the system. To view its tests, run TestEditorJson.</li>
<li>The PlayerDoesNotExistException checks if a player that wants to be removed does not exist in the database. If the 
user's input for the player they would like to remove does not match up with any of the players in the 
playerListDatabase, the exception is thrown. The user is informed that the player they would like to remove does not 
exist. To view its tests, run TestEditorJson.</li></ul> <br />


<h4> Task Three </h4>
If I had more time to work on the project, I would make the following changes: <br><br>

1. **Consistency for pickStatus data type**
    <p>Description: The field pickStatus that defines if a player is picked or cut was initially created with a 
    binary input; the user inputs either 0 or 1, where 1 would signify a player being picked and a 0 would signify
    a player being cut. However, the representation of this data changed into a string, from either "Pick" or "Cut", 
    representing the two options. The user would then manually type in which selection they desired. </p>
    <p>Changes I'd make: This is not the most ideal, as two different data types used to represent the same concept.
    A solution would be to make the data type consistent throughout; it could be made possible for the user to input
    a player's pickStatus in a different way, for example, if radio buttons were added that were associated with both
    pick and cut options. These buttons could then be interpreted as 1 or 0 values, as radio buttons only allow for 
    one of the possible inputs to be selected. </p>
2. **Duplication in code/Design refactoring**  
    <p>Description: There are some methods that seem to be duplicated or can be refactored to improve design. Though
    methods all adhere to the Single Responsibility Principle, an example of improvable design can be found in the 
     deletePlayer method from the PlayerLsit class. Here, the deletePlayer method calls the removePlayerFromPlayerList 
     method in EditorJson. In the long run, this could potentially lead to inefficiency in terms of time complexity, 
     and lead to unnecessarily weak coupling.</p>
    <p>Changes I'd make: In order to improve coupling and have a better design, I would need to first rethink what 
    methods require which appropriate methods in other classes. I could then avoid redundancy by directly calling the
     method that I require (removePlayerFromPlayerList) instead of calling deletePlayer.</p>
3. **Independent code**
    <p>Description: Coupling in the design of the application could be improved by moving the location of the method
    getListPlayers inside PlayerList. This method is used to help convert and process the data retrieved from the 
    playerListDatabase, however, it is only called inside the EditorJson class and results in lower cohesion. </p>
    <p>Changes I'd make: In order to improve coupling, I could create a new method inside EditorJson that can handle
    getting the PlayerList from the retrieved data, called processListPlayers(). Since the presence, but not the
     instantiation, of the getter that is included in PlayerList must be present for Jackson to work properly in 
     EditorJson, it would be better for the design of the application to create a new method. </p>
 4. **Implementing Iterable pattern**
    <p>Description: Since PlayerList is a class that contains a set of players, an iterable pattern could be implemented
    to have cleaner code and decrease the possibility of running into errors if the collection type of the collection 
    of players needed to be changed.</p>
    <p>Changes I'd make: By introducing an iterable pattern, the application will not run into a problem if the 
    collection type of PlayerList needed to be changed. To outline what would need to be done, PlayerList would need to 
    implement Iterable with Player as its parameter, which would make it easier to iterate over the players in a given
    playerList whenever needed (for example, for the return players loop).</p>