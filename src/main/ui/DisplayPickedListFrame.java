package ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.PlayerList;
import persistence.EditorJson;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class DisplayPickedListFrame extends JFrame {

    int width = 500;
    int height = 400;
    JPanel clipboardBackground = new JPanel();
    JPanel pickedPlayerListPanel = new JPanel();
    JLabel playerListTitle = new JLabel("Names of Picked Players: ");
    JTextArea displayPlayerList = new JTextArea();
    JLabel closeReminder = new JLabel("Close this window to return to main menu.", JLabel.CENTER);

    // MODIFIES: this
    // EFFECTS: adds contents for pickedPlayerListPanel and makes them visible for user
    public DisplayPickedListFrame() {
        super("Picked Players:");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        playerListTitle.setFont(new Font("Verdana", Font.PLAIN, 18));
        pickedPlayerListPanel.add(playerListTitle);

        displayPlayerList.setFont(new Font("Verdana", Font.PLAIN, 18));
        pickedPlayerListPanel.add(displayPlayers(displayPlayerList));

        closeReminder.setFont(new Font("Verdana", Font.PLAIN, 12));
        pickedPlayerListPanel.add(closeReminder);

        add(clipboardBackground);
        add(pickedPlayerListPanel);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: returns names for all picked players
    private JTextArea displayPlayers(JTextArea displayPlayerList) {

        StringBuilder stringBuilderPlayers;
        stringBuilderPlayers = EditorJson.customPickedReturn();

        displayPlayerList.append(stringBuilderPlayers.toString());

        return displayPlayerList;
    }

    // EFFECTS: creates new instance of DisplayPickedListFrame
    public static void main(String[] args) {
        DisplayPickedListFrame newFrameInit = new DisplayPickedListFrame();
    }



}
