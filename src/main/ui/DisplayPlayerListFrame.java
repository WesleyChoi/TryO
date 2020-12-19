package ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Player;
import model.PlayerList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DisplayPlayerListFrame extends JFrame {

    int width = 800;
    int height = 600;
    JPanel playerListPanel = new JPanel();
    JLabel playerListTitle = new JLabel("Players:");
    JTextArea displayPlayerList = new JTextArea();

    // MODIFIES: this
    // EFFECTS: adds contents for playerListPanel and makes them visible for user
    public DisplayPlayerListFrame() {
        super("List of Players:");
        setSize(width, height);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        playerListTitle.setFont(new Font("Verdana", Font.PLAIN, 18));
        playerListPanel.add(playerListTitle);

        displayPlayerList.setFont(new Font("Verdana", Font.PLAIN, 18));
        playerListPanel.add(displayPlayers(displayPlayerList));

        add(playerListPanel);

        setLocationRelativeTo(null);
        setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: retrieves PlayerList from playerListDatabase and returns it in StringBuilder
    private JTextArea displayPlayers(JTextArea displayPlayerList) {
        ObjectMapper objectMapper = new ObjectMapper();

        PlayerList playerList = null;

        try {
            playerList = objectMapper.readValue(new File("./data/playerListDatabase.json"),
                    PlayerList.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        StringBuilder stringBuilderPlayers;
        assert playerList != null;
        stringBuilderPlayers = playerList.returnList();


        displayPlayerList.append(stringBuilderPlayers.toString());

        return displayPlayerList;
    }

    // EFFECTS: creates new instance of DisplayPlayerListFrame
    public static void main(String[] args) {
        DisplayPlayerListFrame newFrameInit = new DisplayPlayerListFrame();
    }


}
