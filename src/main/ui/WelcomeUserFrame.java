package ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.PlayerList;
import persistence.EditorJson;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class WelcomeUserFrame extends JFrame {

    int width = 400;
    int height = 500;
    JPanel welcomeUserPanel = new JPanel();

    JLabel iconMain = new JLabel(new ImageIcon("./data/iconMain.PNG"));
    JLabel welcomeMessage = new JLabel("Welcome to TryO!");

    // new user fields
    JButton openAppNewUser = new JButton("Start TryO!");

    // returning user fields
    JLabel returningUserPrompt = new JLabel("Welcome Back! Please select one of the following options:");
    JButton loadDatabaseReturningUser = new JButton("Load recent tryout");
    JButton initNewDatabaseReturningUser = new JButton("Create new tryout");
    JLabel warningMessage = new JLabel("Careful, creating a new tryout will delete your previous tryout!");


    // MODIFIES: this
    // EFFECTS: adds contents for welcomeUserPanel and makes them visible for user
    public WelcomeUserFrame() {
        super("Welcome to TryO!");
        setSize(width, height);

        welcomeUserPanel.add(iconMain);

        readFirstUserConditional();

        add(welcomeUserPanel);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // EFFECTS: sees if user is using app for first time:
    //          if so, runs initializeNewUser, otherwise, runs initReturningUserGiveOption
    private void readFirstUserConditional() {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            if (objectMapper.readValue(new File("./data/UpdateFirstUser.json"), boolean.class)) {
                initializeNewUser();
            } else {
                initReturningUserGiveOption();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // MODIFIES: this
    // EFFECTS: creates welcomeUserPanel for first time user
    public void initializeNewUser() {
        welcomeUserPanel.add(welcomeMessage);
        welcomeUserPanel.add(openAppNewUser);
        openAppNewUserButtonListener();
        setVisible(true);
    }

    // MODIFIES: this, TryOutAppGUI
    // EFFECTS: creates new instance of and opens TryOutAppGUI with new database
    private void openAppNewUserButtonListener() {
        openAppNewUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                initNewDatabase();
                setUpdateFirstUser(false);
                playStartSound();
                new TryOutAppGUI();
                welcomeUserPanel.setVisible(false);
            }
        });
    }

    // MODIFIES: this
    // EFFECTS: creates welcomeUserPanel for returning user
    private void initReturningUserGiveOption() {
        welcomeUserPanel.add(returningUserPrompt);
        welcomeUserPanel.add(loadDatabaseReturningUser);
        welcomeUserPanel.add(initNewDatabaseReturningUser);
        welcomeUserPanel.add(warningMessage);
        setVisible(true);

        processReturningUserChoice();

    }

    // EFFECTS: handles two actions based on what returning user chooses
    private void processReturningUserChoice() {
        handleLoadDatabase();

        handleInitNewDatabase();
    }

    // MODIFIES: this, TryOutGUI
    // EFFECTS: creates new instance of TryOutAppGUI with current database
    private void handleLoadDatabase() {
        loadDatabaseReturningUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new TryOutAppGUI();
                playStartSound();
                welcomeUserPanel.setVisible(false);
            }
        });
    }

    // MODIFIES: this, TryOutAppGUI
    // EFFECTS: creates new instance of TryOutAppGUi with new database
    private void handleInitNewDatabase() {
        initNewDatabaseReturningUser.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                initNewDatabase();

                playStartSound();

                new TryOutAppGUI();

                setUpdateFirstUser(false);
                welcomeUserPanel.setVisible(false);
            }
        });
    }

    // MODIFIES: playerListDatabase
    // EFFECTS: sets playerListDatabase to an empty PlayerList
    public void initNewDatabase() {

        PlayerList newPlayerList = new PlayerList();

        EditorJson.writeToJson(newPlayerList);

    }


    // MODIFIES: UpdateFirstUser
    // EFFECTS: sets value of UpdateFirstUser to passed in boolean
    private void setUpdateFirstUser(Boolean bool) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            objectMapper.writeValue(new File("./data/UpdateFirstUser.json"), bool);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // EFFECTS: plays sound effect from source file
    public void playStartSound() {
        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File("./data/startSound.wav").getAbsoluteFile());

            Clip soundClip = AudioSystem.getClip();
            soundClip.open(audioInputStream);
            soundClip.start();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    // EFFECTS: creates new instance of WelcomeUserFrame
    public static void main(String[] args) {
        WelcomeUserFrame welcomeFrameInit = new WelcomeUserFrame();
    }


}
