package gui;

import game.Game;
import game.SoundManager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.spi.MixerProvider;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SettingsPanel {
    public JPanel mainPanel;
    private JButton volumeUpButton;
    private JButton volumeDownButton;
    private JButton backButton;
    private JButton muteButton;
    private JButton unmuteButton;
    private boolean volumeUpPressed;

    private final Game game;

    public SettingsPanel(Game game) {
        this.game = game;
        // settings such as volume control
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!game.isMuted()){
                    SoundManager.getButtonPressSound().play();
                }game.transitionToMain();
            }
        });
        volumeDownButton.setBackground(Color.gray);
        volumeDownButton.setOpaque(true);
        volumeDownButton.setBorderPainted(false);

        volumeDownButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 1; i < 2; i++) {
                    game.getCurrentLevel().getCurrentSound().setVolume(i);
                }
                volumeUpPressed = true;
                volumeUpButton.setBackground(Color.white);
                volumeDownButton.setBackground(Color.gray);
            }
        });
        volumeUpButton.setOpaque(true);
        volumeUpButton.setBorderPainted(false);
        volumeUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(int i = 2; i > 1; i--) {
                    game.getCurrentLevel().getCurrentSound().setVolume(i);

                }
                volumeUpPressed = false;
                volumeDownButton.setBackground(Color.white);
                volumeUpButton.setBackground(Color.gray);
            }
        });
        muteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!game.isMuted()) {
                    game.setMuted(true);
                    game.getCurrentLevel().getCurrentSound().stop();
                    muteButton.setText("Unmute");

                }
                else if (game.isMuted()){
                    game.setMuted(false);
                    game.getCurrentLevel().getCurrentSound().loop();
                    muteButton.setText("Mute");

                }
            }
        });


    }
}
