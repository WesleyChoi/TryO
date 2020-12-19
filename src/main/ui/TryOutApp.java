package ui;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.PlayerAlreadyExistsException;
import model.Player;
import model.PlayerList;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.stream.Stream;

import static persistence.EditorJson.customPickedReturn;
import static persistence.EditorJson.togglePickStatusExistingPlayer;

// JsonAutoDetect bug fixed using following StackOverflow link as reference:
// https://stackoverflow.com/questions/8367312/serializing-with-jackson-json-getting-no-serializer-found

public class TryOutApp extends PlayerList {

    // JSON reader and writer methods based on json serialization demo:
    // https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git

    private static final String JSON_STORE = "./data/playerListDatabase.json";
    // Scanner implementation based on Teller java project
    private Scanner scanner;
    private PlayerList listPlayer = new PlayerList();
    private Player newPlayer;

    // EFFECTS: runs tryout app
    public TryOutApp() {

        scanner = new Scanner(System.in);

        runTryOut();
    }

    // MODIFIES: this
    // EFFECTS: sets program states and processes input commands from user
    private void runTryOut()  {
        boolean programRunning = true;
        String inputCommand;

        System.out.println("Welcome to TryO!");

        init();

        while (programRunning) {

            initWelcomeUser();

            displayMenu();
            inputCommand = scanner.next();
            inputCommand = inputCommand.toLowerCase();

            if (inputCommand.equals("end")) {
                programRunning = false;
                System.out.println("Ended tryout!");
            } else {
                executeCommand(inputCommand);
            }
        }
    }

    // MODIFIES: this
    // EFFECTS: initializes new scanner and new playerList
    private void init() {
        scanner = new Scanner(System.in);
    }

    // MODIFIES: UpdateFirstUser.json
    // EFFECTS: initializes json file for new user, if they are a returning user, then loads saved player list
    public void initWelcomeUser() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if (objectMapper.readValue(new File("./data/UpdateFirstUser.json"), boolean.class)) {
                initializePlayerList();
            } else {
                initLoadReturningUser();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // MODIFIES: this(listPlayer)
    // EFFECTS: retrieves previously saved player list and sets it to listPlayer
    public void initLoadReturningUser() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        PlayerList retrievedPlayerList = new PlayerList();

        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);

        String jsonData = readFile("./data/playerListDatabase.json");

        JSONObject jsonObject = new JSONObject(jsonData);

        JSONArray jsonArray = jsonObject.getJSONArray("listPlayers");

        ArrayList<Player> arraylistPlayer;
        arraylistPlayer = objectMapper.readValue(jsonArray.toString(), new TypeReference<ArrayList<Player>>(){});

        retrievedPlayerList.setListPlayers(arraylistPlayer);

        listPlayer = retrievedPlayerList;

    }

    // EFFECTS: reads file from json source and converts into string
    private static String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s));
        }

        return contentBuilder.toString();
    }

    // MODIFIES: playerListDatabase, UpdateFirstUser
    // EFFECTS: for a new user, initializes a new PlayerList inside playerListDatabase,
    //          and also sets UpdateFirstUser to false
    public static void initializePlayerList() {

        PlayerList thisPlayerList = new PlayerList();
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);


        try {
            objectMapper.writeValue(new File("./data/playerListDatabase.json"), thisPlayerList);
            objectMapper.writeValue(new File("./data/UpdateFirstUser.json"), false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // EFFECTS: executes given input by user.
    private void executeCommand(String inputCommand) {
        if (inputCommand.equals("new")) {
            initNewPlayer();
            try {
                listPlayer.addPlayer(newPlayer);
            } catch (PlayerAlreadyExistsException e) {
                e.printStackTrace();
            }
        } else if (inputCommand.equals("view")) {
            viewPlayers();
        } else if (inputCommand.equals("remove")) {
            String name;
            System.out.println("What's the player's name?");
            name = scanner.next();
            deletePlayer(name);
        } else if (inputCommand.equals("picked")) {
            viewPickedPlayers();
        } else if (inputCommand.equals("switch")) {
            switchPlayerStatus();
        } else {
            System.out.println("Selection not valid...");
        }
    }

    private void initNewPlayer() {
        newPlayerName();
        setPlayerNum();
        setPlayerPos();
        setPlayerDescription();
        setPlayerPickStatus();
    }


    // EFFECTS: displays command options to user
    private void displayMenu() {
        System.out.println("\nSelect one of the following options:");
        System.out.println("\tnew     -> Enter a new player");
        System.out.println("\tview    -> View list of ALL players");
        System.out.println("\tremove  -> Removes a player from list of players");
        System.out.println("\tpicked  -> View list of all PICKED players");
        System.out.println("\tswitch  -> Toggle a player from cut to pick or vice versa");
        System.out.println("\tend     -> End program");
    }


    // MODIFIES: this
    // EFFECTS: adds a new player and sets their name
    private void newPlayerName() {
        System.out.println("Enter the player's name:");
        String name = scanner.next();
        newPlayer = new Player(name);
    }

    // REQUIRES: entered value is positive integer
    // MODIFIES: this
    // EFFECTS: sets player's name
    private void setPlayerNum() {
        System.out.println("Enter the player's number:");
        Integer num = scanner.nextInt();
        newPlayer.setPlayerNum(num);
    }

    // REQUIRES: position cannot include spaces
    // MODIFIES: this
    // EFFECTS: sets player's position
    private void setPlayerPos() {
        System.out.println("Enter the player's position:");
        String position = scanner.next();
        newPlayer.setPlayerPosition(position);
    }

    // REQUIRES: description cannot include spaces
    // MODIFIES: this
    // EFFECTS: sets player's description
    private void setPlayerDescription() {
        System.out.println("Enter the player's description:");
        String description = scanner.next();
        newPlayer.setPlayerDescription(description);
    }


    // REQUIRES: entered value must be integer
    // MODIFIES: this
    // EFFECTS: sets player's pickStatus
    private void setPlayerPickStatus() {
        while (!(scanner.equals(1) || scanner.equals(0))) {
            System.out.println("Enter 1 to pick the player and 0 to cut the player:");
            Integer pick = scanner.nextInt();
            if (pick == 1) {
                newPlayer.setPickStatus("Pick");
                System.out.println("Player added to list!");
            } else if (pick == 0) {
                newPlayer.setPickStatus("Cut");
                System.out.println("Player added to list!");
            } else {
                System.out.println("Failed selection");
            }
            break;
        }
    }

    // MODIFIES: Player in playerListDatabase
    // EFFECTS: sets existing player's pickStatus to newChoice
    private void switchPlayerStatus() {
        String name;
        int newChoice;
        System.out.println("What is the player's name?");
        name = scanner.next();
        System.out.println("Enter 1 to pick the player and 0 to cut the player:");
        newChoice = scanner.nextInt();
        togglePickStatusExistingPlayer(name, newChoice);
    }

    // MODIFIES: this
    // EFFECTS: produces list of all players in playerList
    private void viewPlayers() {

        System.out.println(listPlayer.returnList());

    }

    // EFFECTS: allows user to see pickedPlayers
    private void viewPickedPlayers() {

        System.out.println(customPickedReturn());

    }

}
