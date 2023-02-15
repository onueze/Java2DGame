package gui;

import game.Game;
import game.SoundManager;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TutorialPanel {
    public JPanel mainPanel;
    private JLabel SpaceLabel;
    private JLabel ChangeItemLabel;
    private JLabel RunningLabel;
    private JLabel JumpingLabel;
    private JLabel MovingLabel;
    private JLabel MenuLabel;
    private JButton backButton;
    private JLabel CrouchLabel;
    private final Game game;



    public TutorialPanel(Game game) {
        this.game = game;
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!game.isMuted()){
                    SoundManager.getButtonPressSound().play();
                }
                game.transitionTutorialToMain();
            }
        });
    }
}
