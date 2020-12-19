package ui;

import exceptions.PlayerDoesNotExistException;
import persistence.EditorJson;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RemoveExistingPlayerFrame extends JFrame {


    int width = 500;
    int height = 300;

    JPanel removeExistingPlayerPanel = new JPanel();
    JLabel removePlayerTitle = new JLabel("Enter the name of the player you want to remove:");
    JTextArea enterPlayerNameToRemove = new JTextArea();
    JLabel closeReminder = new JLabel("Close this window to return to main menu.", JLabel.CENTER);
    JButton removePlayerButton = new JButton("Remove Player");

    String playerToRemove;
    int playerListSize;

    // MODIFIES: this
    // EFFECTS: adds contents for removeExistingPlayerPanel and makes them visible for user
    public RemoveExistingPlayerFrame() {
        super("Remove a player");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        removePlayerTitle.setFont(new Font("Verdana", Font.PLAIN, 18));
        removeExistingPlayerPanel.add(removePlayerTitle);

        enterPlayerNameToRemove.setPreferredSize(new Dimension(100, 25));
        removeExistingPlayerPanel.add(enterPlayerNameToRemove);

        removePlayerButton.setFont(new Font("Verdana", Font.PLAIN, 18));
        removeExistingPlayerPanel.add(removePlayerButton);

        removeButtonListener();

        add(removeExistingPlayerPanel);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    // MODIFIES: this, playerListDatabase
    // EFFECTS: collects inputted data from user and handles its removal from database
    public void removeButtonListener() {
        removePlayerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playerToRemove = enterPlayerNameToRemove.getText();
                playerListSize = EditorJson.retrieveFromJson().length();
                removePlayerFromDatabase(playerToRemove);
            }
        });
    }

    // MODIFIES: playerListDatabase
    // EFFECTS: removes corresponding player with name of given string from playerListDatabase
    protected void removePlayerFromDatabase(String playerToRemove) {
        try {
            EditorJson.removePlayerFromPlayerList(playerToRemove);
        } catch (PlayerDoesNotExistException e) {
            e.printStackTrace();
        }
        playerRemoveMessage();
    }

    // MODIFIES: this
    // EFFECTS: displays successMessage if removal was successful, failMessage otherwise
    protected void playerRemoveMessage() {
        JLabel successMessage = new JLabel("Player removed successfully");
        JLabel failMessage = new JLabel("Player does not exist in list");
        if (playerListSize > EditorJson.retrieveFromJson().length()) {
            removeExistingPlayerPanel.add(successMessage);
            removeExistingPlayerPanel.add(closeReminder);
            setVisible(true);
        } else {
            removeExistingPlayerPanel.add(failMessage);
            setVisible(true);
        }
    }

    // EFFECTS: instantiates new RemoveExistingPlayerFrame
    public static void main(String[] args) {
        RemoveExistingPlayerFrame newFrameInit = new RemoveExistingPlayerFrame();
    }


}
