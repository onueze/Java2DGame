package gui;

import game.Game;
import game.GameSaverLoader;
import game.SoundManager;
import levels.GameLevel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class GameLoadingPanel{
    public JPanel mainPanel;
    private JButton saveButton;
    private JButton loadLastCheckpointButton;
    private JButton backButton;
    private final Game game;


    public GameLoadingPanel(Game game) {
        this.game = game;
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!game.isMuted()) {
                    SoundManager.getButtonPressSound().play();
                }
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File((".")));
                int response = fileChooser.showSaveDialog(null); // select file to open
                if (response == JFileChooser.APPROVE_OPTION) { // response value is either 0 for selected file or 1 if window was closed
                    try {
                        GameSaverLoader.save(fileChooser.getSelectedFile().getAbsolutePath(), game.getCurrentLevel());
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }

            }
        });


        loadLastCheckpointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!game.isMuted()) {
                    SoundManager.getButtonPressSound().play();
                }
                JFileChooser fileChooser = new JFileChooser(); // new jFile chooser object
                fileChooser.setCurrentDirectory(new File(("."))); // sets current directory to game directory
                int response = fileChooser.showOpenDialog(null); // select file to open
                if(response == JFileChooser.APPROVE_OPTION ) { // response value is either 0 for selected file or 1 if window was closed
                    GameLevel loadLevel;
                    try {
                        loadLevel = GameSaverLoader.load(fileChooser.getSelectedFile().getAbsolutePath(), game);
                        game.setLevel(loadLevel);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
                game.transitionGameLoadToMain();
                game.getCurrentLevel().stop();
                game.getCurrentLevel().getCurrentSound().stop();



            }
        });
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(!game.isMuted()){
                    SoundManager.getButtonPressSound().play();
                }game.transitionGameLoadToMain();
            }
        });
    }
}
