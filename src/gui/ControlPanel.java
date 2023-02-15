package gui;

import game.Game;
import game.SoundManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ControlPanel {
    public JPanel mainPanel;
    private JButton newGameButton;
    private JButton exitButton;
    private JButton settingsButton;
    private JButton chooseLevelButton;
    private JButton gameloadingButton;
    private JButton tutorialButton;
    private JLabel icyGuyLabel;


    private final Game game;
    public ControlPanel(Game game) {
        this.game = game;
        // control panel has menu options
        settingsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.transitionToSettings();
                if(!game.isMuted()){
                    SoundManager.getButtonPressSound().play();
                }
            }
        });
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) { if(!game.isMuted()){
                SoundManager.getButtonPressSound().play();
            }System.exit(0);}
        });
        newGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!game.isMuted()){
                    SoundManager.getButtonPressSound().play();
                }game.startNewGame();
            }
        });
        chooseLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!game.isMuted()){
                    SoundManager.getButtonPressSound().play();
                }game.transitionToControlLevel();
            }
        });
        gameloadingButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!game.isMuted()){
                    SoundManager.getButtonPressSound().play();
                }game.transitionToGameLoading();
            }
        });
        tutorialButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!game.isMuted()){
                    SoundManager.getButtonPressSound().play();
                }game.transitionToTutorial();
            }
        });
    }
}
