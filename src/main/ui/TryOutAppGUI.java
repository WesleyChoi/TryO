package ui;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class TryOutAppGUI extends JFrame {

    JPanel mainPanel = new JPanel();
    int width = 500;
    int height = 500;
    JButton addPlayer = new JButton("Add Player");
    JButton viewPlayers = new JButton("View Players");
    JButton viewPickedPlayers = new JButton("View Picked Players");
    JButton removeExistingPlayer = new JButton("Remove Player");
    JLabel iconMain = new JLabel(new ImageIcon("./data/iconMain.PNG"));

    // MODIFIES: this
    // EFFECTS: adds contents for mainPanel and makes them visible for user
    public TryOutAppGUI() {
        super("TryO");
        setSize(width, height);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainPanel.add(iconMain);

        setStyle();
        mainPanel.add(addPlayer);
        mainPanel.add(viewPlayers);
        mainPanel.add(viewPickedPlayers);
        mainPanel.add(removeExistingPlayer);

        add(mainPanel);
        buttonListeners();

        setLocationRelativeTo(null);
        setVisible(true);

    }

    // MODIFIES: this
    // EFFECTS: sets font for JButtons
    private void setStyle() {
        addPlayer.setFont(new Font("Verdana", Font.PLAIN, 18));
        viewPlayers.setFont(new Font("Verdana", Font.PLAIN, 18));
        viewPickedPlayers.setFont(new Font("Verdana", Font.PLAIN, 18));
        removeExistingPlayer.setFont(new Font("Verdana", Font.PLAIN, 18));
    }

    // EFFECTS: handles actions for each button on mainPanel
    public void buttonListeners() {
        addPlayerButtonListener();

        viewPlayersButtonListener();

        viewPickedPlayersButtonListener();

        removeExistingPlayerButtonListener();
    }

    // MODIFIES: RemoveExistingPlayerFrame
    // EFFECTS: creates new instance of RemoveExistingPlayerFrame and makes it visible
    private void removeExistingPlayerButtonListener() {
        removeExistingPlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playButtonClickSound();
                new RemoveExistingPlayerFrame().setVisible(true);
            }
        });
    }

    // MODIFIES: DisplayPickedListFrame
    // EFFECTS: creates new instance of DisplayPickedListFrame and makes it visible
    private void viewPickedPlayersButtonListener() {
        viewPickedPlayers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playButtonClickSound();
                new DisplayPickedListFrame().setVisible(true);
            }
        });
    }

    // MODIFIES: DisplayPlayerListFrame
    // EFFECTS: creates new instance of DisplayPlayerListFrame and makes it visible
    private void viewPlayersButtonListener() {
        // Makes DisplayPlayerListFrame visible after clicking viewPlayers button
        viewPlayers.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playButtonClickSound();
                new DisplayPlayerListFrame().setVisible(true);
            }
        });
    }

    // MODIFIES: AddPlayerFrame
    // EFFECTS: creates new instance of AddPlayerFrame and makes it visible
    private void addPlayerButtonListener() {
        // Makes AddPlayerFrame visible after clicking addPlayer button
        addPlayer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                playButtonClickSound();
                new AddPlayerFrame().setVisible(true);
            }
        });
    }

    // EFFECTS: plays sound effect from source file
    public void playButtonClickSound() {

        try {
            AudioInputStream audioInputStream =
                    AudioSystem.getAudioInputStream(new File("./data/ClickSound.wav").getAbsoluteFile());

            Clip soundClip = AudioSystem.getClip();
            soundClip.open(audioInputStream);
            soundClip.start();

        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    // EFFECTS: creates new instance of TryOutAppGUI
    public static void main(String[] args) {
        TryOutAppGUI newFrameInit = new TryOutAppGUI();
    }

}
