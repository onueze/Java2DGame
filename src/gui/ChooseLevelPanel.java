package gui;

import game.Game;
import game.SoundManager;
import levels.GameLevel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChooseLevelPanel {
    public JPanel mainPanel;
    private JPanel level1Panel;
    private JPanel level2Panel;
    private JPanel level3Panel;
    private JPanel level4Panel;
    private JRadioButton level1Button;
    private JRadioButton level2Button;
    private JRadioButton level3Button;
    private JRadioButton level4Button;
    private JButton backButton;
    private JLabel chooseLevelLabel;
    private JRadioButton level5Button;
    private JPanel level5Panel;
    private JRadioButton level6Button;
    private JPanel level6Panel;
    private final Game game;
    private GameLevel currentLevel;
    private final ButtonGroup radioButton;


    public ChooseLevelPanel(Game g) {
        game = g;
        radioButton = new ButtonGroup(); // button group is used to unselect radio button when pressed
        radioButton.add(level1Button);
        radioButton.add(level2Button);
        radioButton.add(level3Button);
        radioButton.add(level4Button);
        radioButton.add(level5Button);
        radioButton.add(level6Button);

        // each level has their own image in the menu and when selected, the level starts running
        level1Panel.add(new ChooseLevelImage("data/Level1backgroundIcon.png"));
        level1Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.removeControlLevelPanel();
                if(!game.isMuted()){
                    SoundManager.getButtonPressSound().play();
                }

                game.setLevelNumber(0);
                game.goToNextLevel();
                radioButton.clearSelection();

            }
        });
        level2Panel.add(new ChooseLevelImage("data/Level2BackgroundIcon.jpeg"));
        level2Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.removeControlLevelPanel();
                if(!game.isMuted()){
                    SoundManager.getButtonPressSound().play();
                }
                game.setLevelNumber(1);
                game.goToNextLevel();
                radioButton.clearSelection();

            }
        });
        level3Panel.add(new ChooseLevelImage("data/Level3BackgroundIcon.jpg"));
        level3Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.removeControlLevelPanel();
                if(!game.isMuted()){
                    SoundManager.getButtonPressSound().play();
                }
                game.setLevelNumber(2);
                game.goToNextLevel();
                radioButton.clearSelection();


            }
        });
        level4Panel.add(new ChooseLevelImage("data/Level4BackgroundIcon.jpg"));
        level4Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.removeControlLevelPanel();
                if(!game.isMuted()){
                    SoundManager.getButtonPressSound().play();
                }
                game.setLevelNumber(3);
                game.goToNextLevel();
                radioButton.clearSelection();


            }
        });
        level5Panel.setPreferredSize(new Dimension(100,100));
        level5Panel.add(new ChooseLevelImage("data/CloudBackgroundIcon.jpg"));
        level5Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.removeControlLevelPanel();
                if(!game.isMuted()){
                    SoundManager.getButtonPressSound().play();
                }
                game.setLevelNumber(4);
                game.goToNextLevel();
                radioButton.clearSelection();

            }
        });
        level6Panel.add(new ChooseLevelImage("data/SpaceBackGroundIcon.jpg"));
        level6Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.removeControlLevelPanel();
                if(!game.isMuted()){
                    SoundManager.getButtonPressSound().play();
                }
                game.setLevelNumber(5);
                game.goToNextLevel();
                radioButton.clearSelection();

            }
        });

        // back button to get back to main menu
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.transitionLevelToMain();
                if(!game.isMuted()){
                    SoundManager.getButtonPressSound().play();
                }

            }
        });

    }

}
