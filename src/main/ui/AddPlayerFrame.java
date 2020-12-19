package ui;

import model.Player;
import persistence.EditorJson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddPlayerFrame extends JFrame {

    int width = 400;
    int height = 400;
    JPanel addPlayerPanel = new JPanel();

    JLabel playerName = new JLabel("Player Name: ");
    JTextField enterPlayerName = new JTextField();
    JLabel playerNumber = new JLabel("Player Number: ");
    JTextField enterPlayerNumber = new JTextField();
    JLabel playerPosition = new JLabel("Player Position: ");
    JTextField enterPlayerPosition = new JTextField();
    JLabel playerDescription = new JLabel("Player Description: ");
    JTextField enterPlayerDescription = new JTextField();
    JLabel playerPickStatus = new JLabel(" Pick or Cut Player: ");
    JTextField enterPickStatus = new JTextField();
    JButton submitAddPlayer = new JButton("Add player");
    JLabel returnToMain = new JLabel("Close this window to return to the main menu.");

    int playerListDBLength;

    String playerNameField;
    int playerNumberField;
    String playerPositionField;
    String playerDescriptionField;
    String playerPickStatusField;

    // MODIFIES: this, title
    // EFFECTS: creates new panel
    public AddPlayerFrame() {
        super("Add Player");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        addPlayerPanel.setLayout(new FlowLayout());
        setStylePreferences();

        addPlayerPanel.add(playerName);
        addPlayerPanel.add(enterPlayerName);
        addPlayerPanel.add(playerNumber);
        addPlayerPanel.add(enterPlayerNumber);
        addPlayerPanel.add(playerPosition);
        addPlayerPanel.add(enterPlayerPosition);
        addPlayerPanel.add(playerDescription);
        addPlayerPanel.add(enterPlayerDescription);
        addPlayerPanel.add(playerPickStatus);
        addPlayerPanel.add(enterPickStatus);
        submitButtonListener();
        addPlayerPanel.add(submitAddPlayer);

        add(addPlayerPanel);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: sets panel style preferences for font and dimensions
    private void setStylePreferences() {

        playerName.setFont(new Font("Verdana", Font.PLAIN, 18));
        playerNumber.setFont(new Font("Verdana", Font.PLAIN, 18));
        playerPosition.setFont(new Font("Verdana", Font.PLAIN, 18));
        playerDescription.setFont(new Font("Verdana", Font.PLAIN, 18));
        playerPickStatus.setFont(new Font("Verdana", Font.PLAIN, 18));

        enterPlayerName.setPreferredSize(new Dimension(100, 25));
        enterPlayerNumber.setPreferredSize(new Dimension(100, 25));
        enterPlayerPosition.setPreferredSize(new Dimension(100, 25));
        enterPlayerDescription.setPreferredSize(new Dimension(100, 25));
        enterPickStatus.setPreferredSize(new Dimension(100, 25));

    }

    // MODIFIES: this
    // EFFECTS: collects inputted data from user and logs them to variables
    public void submitButtonListener() {
        submitAddPlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerNameField = enterPlayerName.getText();
                playerNumberField = Integer.parseInt(enterPlayerNumber.getText());
                playerPositionField = enterPlayerPosition.getText();
                playerDescriptionField = enterPlayerDescription.getText();
                playerPickStatusField = enterPickStatus.getText();
                setPlayerDetailsAndAddToDatabase();
            }
        });
    }

    // MODIFIES: playerListDatabase.json
    // EFFECTS: adds player to database and sets their details
    public void setPlayerDetailsAndAddToDatabase() {
        Player newPlayer = new Player(playerNameField);
        newPlayer.setPlayerNum(playerNumberField);
        newPlayer.setPlayerPosition(playerPositionField);
        newPlayer.setPlayerDescription(playerDescriptionField);
        newPlayer.setPickStatus(playerPickStatusField);
        playerListDBLength = EditorJson.retrieveFromJson().length();
        EditorJson.addPlayerToPlayerList(newPlayer);
        successMessage();
    }

    // MODIFIES: this
    // EFFECTS: displays success message once player has been added to database
    public void successMessage() {
        JLabel playerAddedSuccess = new JLabel("Player Added Successfully!");
        if (EditorJson.retrieveFromJson().length() > playerListDBLength) {
            addPlayerPanel.add(playerAddedSuccess);
            addPlayerPanel.add(returnToMain);
            setVisible(true);
        }
    }

    // EFFECTS: instantiates new AddPlayerFrame
    public static void main(String[] args) {
        AddPlayerFrame newFrameInit = new AddPlayerFrame();
    }



}
