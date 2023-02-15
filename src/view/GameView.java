package view;

import city.cs.engine.UserView;
import city.cs.engine.World;
import game.Game;
import game.IcyGuy;
import levels.GameLevel;
import levels.Level3;
import levels.Level6;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This class represents the view of the game.
 * That is, what the user is able to see on the window frame.
 * The GameView class extends the UserView class.
 *
 * Different components of the game are added in this class as well
 * as backgrounds and foreground which change depending on the current state
 * of the game.
 *
 * Lives are drawn on the canvas as well as a JProgressBar to keep track of
 * the characters Health.
 *
 * What is more, some buttons that support the feel of the game
 * are also added to the gameView
 */

public class GameView extends UserView {
    /** field for character */
    private final IcyGuy icyGuy;

    /** Image for the lives */
    private final Image lives = new ImageIcon("data/Life.png").getImage();

    /** getter for resume button */
    public JButton getResumeButton() {
        return resumeButton;
    }

    /** @return getter for stop button */
    public JButton getStopButton() {
        return stopButton;
    }

    /** Progressbar which displays the health of the character */
    private static JProgressBar healthBar;

    /** Progressbar which displays the health of the boss */
    private static JProgressBar bossHealthBar;

    /** JPanel for the healthbar of character  */
    private final JPanel healthBarPanel;

    /** JPanel for the healthbar of boss */
    private final JPanel bossHealthBarPanel;

    /** Image icon for the coin */
    private final Image coinImage = new ImageIcon("data/coin.gif").getImage().getScaledInstance(30,30,0);

    /** field for the game time  */
    private final GameTime gameTime;

    /** field for current level  */
    private GameLevel currentLevel;

    /** field for the game  */
    private final Game game;

    /** this is the image that appears when the character dies */
    private final Image gameOverImage = new ImageIcon("data/GameOver.gif").getImage();

    /** this is the image that is used when the character dies in Level 6 */
    private final Image gameOverImageLevel6 = new ImageIcon("data/GameOverLevel6.gif").getImage();

    /** image if game is completed successfully  */
    private final Image gameWinImage = new ImageIcon("data/GameWin.gif").getImage().getScaledInstance(500,500,0);

    /** image icon that appears in the top right corner of the view for the flood enemy  */
    private final Image floodImage = new ImageIcon("data/FloodImageIcon.gif").getImage().getScaledInstance(30,30,0);

    /** image icon that appears in the top right corner of the view for the bat enemy  */
    private final Image batImage = new ImageIcon("data/BatImageIcon.gif").getImage().getScaledInstance(30,30,0);

    /** JButton for resuming the game */
    private final JButton resumeButton;

    /** JButton for pausing the game */
    private final JButton stopButton;

    /** JButton for restarting the game */
    private final JButton restartButton;

    /** boolean that determines if stop button is pressed */
    private boolean stopPressed;

    /** Image icon which is layed over the resume button */
    private final ImageIcon playButtonImage = new ImageIcon("data/playButton.png");


    /** Image icon which is layed over the stop button */
    private final ImageIcon pauseButtonImage = new ImageIcon("data/PauseButton.png");

    /** Image icon which is layed over the restart button */
    private final ImageIcon restartButtonImage = new ImageIcon("data/restart.png");


    /** @return return the health bar of the character */
    public static JProgressBar getHealthBar() {
        return healthBar;
    }

    /** @return return the health bar of the boss */
    public static JProgressBar getBossHealthBar() {
        return bossHealthBar;
    }

    /** Game view constructor.
     * All the visuals of the screen are initialised here.
     *
     * Buttons and the progressbar are placed in appropriate positions.
     *
     * The image icon of the enemy also changes accordingly as the character progresses to the next level
     * JPanels such as health bar for the character and for the enemy are placed on the game view
     * @param  game  class is passed in
     * @param w the currentLevel passed in
     * @param i the icy guy character passed in
     * @param width width of the game view
     * @param height height of the game view*/
    public GameView(Game game, GameLevel w, IcyGuy i, int width, int height) {
        super(w, width, height);
        this.setLayout(null);
        icyGuy = i;
        currentLevel = w;
        gameTime = new GameTime(w,this, currentLevel.getGameTime().getTimer());
        stopPressed = false;
        this.game = game;
        resumeButton = new JButton(playButtonImage);
        resumeButton.setBackground(Color.gray);
        resumeButton.setOpaque(true);
        resumeButton.setBorderPainted(false);
        resumeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                currentLevel.start();
                if (!game.isMuted()) {
                    currentLevel.getCurrentSound().loop();
                }

                resumeButton.setBackground(Color.gray);
                resumeButton.setOpaque(true);
                resumeButton.setBorderPainted(false);

                stopButton.setBackground(Color.cyan);
                stopButton.setOpaque(true);
                stopButton.setBorderPainted(false);
                stopPressed = false;
            }
        });
        stopButton = new JButton(pauseButtonImage);
        stopButton.setBackground(Color.cyan);
        stopButton.setOpaque(true);
        stopButton.setBorderPainted(false);
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentLevel.stop();
                currentLevel.getCurrentSound().stop();

                stopButton.setBackground(Color.gray);
                stopButton.setOpaque(true);
                stopButton.setBorderPainted(false);

                resumeButton.setBackground(Color.CYAN);
                resumeButton.setOpaque(true);
                resumeButton.setBorderPainted(false);
                stopPressed = true;
            }
        });
        restartButton = new JButton(restartButtonImage);
        restartButton.setBackground(Color.cyan);
        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    game.startNewGame();

            }
        });
        resumeButton.setBounds(240,5,30,30);
        stopButton.setBounds(290,5,30,30);
        restartButton.setBounds(245,5,50,50);
        this.add(resumeButton);
        this.add(stopButton);


        // healthbar for icy Guy
        healthBarPanel = new JPanel();
        healthBarPanel.setPreferredSize(new Dimension(200,30));
        healthBarPanel.setOpaque(false);
        healthBarPanel.repaint();
        healthBarPanel.setLocation(10, 55);
        this.add(healthBarPanel);
        healthBar = new JProgressBar(0,100);
        healthBar.setPreferredSize(new Dimension(200,30));
        healthBarPanel.add(healthBar);
        healthBar.setValue(currentLevel.getIcyGuy().getHealth());
        healthBarPanel.setBounds(10, 30,200,30);


        // health bar for final boss
        bossHealthBarPanel = new JPanel();
        bossHealthBarPanel.setBackground(Color.RED);
        bossHealthBarPanel.setPreferredSize(new Dimension(200,30));
        bossHealthBarPanel.setOpaque(false);
        bossHealthBarPanel.repaint();
        bossHealthBarPanel.setLocation(10, 55);
        bossHealthBar = new JProgressBar(0, 200);
        bossHealthBar.setBackground(Color.RED);
        bossHealthBar.setPreferredSize(new Dimension(200, 30));
        bossHealthBarPanel.add(bossHealthBar);
        bossHealthBarPanel.setBounds(700, 30, 200, 30);
        if(currentLevel instanceof Level6) {
            this.add(bossHealthBarPanel);
            bossHealthBar.setValue(((Level6) currentLevel).getBoss().getHealth());
        }


    }

    /** updates the view to be on the current Level.
     * @param w passes in the current level*/
    @Override
    public void setWorld(World w) {
        super.setWorld(w);
        currentLevel = (GameLevel) w;
    }



    /** @param g the Graphics2D class has properties like painting images on the canvas
      */
    @Override
    protected void paintBackground(Graphics2D g) {
        g.drawImage(currentLevel.getBackground(), 0, 0, this);
    }

    /** @param g the Graphics2D class has properties like painting images on the canvas
     */
    @Override
    protected void paintForeground(Graphics2D g) {
        this.repaint();
        g.setColor(Color.RED);
//        g.drawString("IcyGuy Health: " + currentLevel.getIcyGuy().getHealth(), 10, 55);
        g.drawString("x  " + currentLevel.getIcyGuy().getEnemysDefeated(), 55, 67);
        g.drawString("x  " + currentLevel.getIcyGuy().getCoins(), 55, 93);
        g.drawImage(coinImage,10,75,this);
        g.drawImage(currentLevel.getIcyGuy().getBackPack().getCurrentItem().itemImage(), 10, 100,this);

        // if the level is level 6 it places the time accordingly
        if(currentLevel  instanceof Level6 ){
            g.drawString("Time left: " + currentLevel.getGameTime().getTimer() / 60 + " sec", 780, 15);
        }else {
            g.drawString("Time left: " + currentLevel.getGameTime().getTimer() / 60 + " sec", 390, 15);
        }

        // changes the display of enemy icon to view
        if(currentLevel instanceof Level3){
            g.drawImage(batImage,10,50,this);
        }else{
            g.drawImage(floodImage,10,50,this);
        }



        // loop draws 3 hearts at designated coordinates and displays game over on
        // the screen, once 0 lives are reached
        for (int i = 0; i < currentLevel.getIcyGuy().getLives(); i++) {
            g.drawImage(lives, 10 + i * 50 , 5, 40, 40, this);
            for (int j = currentLevel.getIcyGuy().getLives(); j < 0; j--) {
                if (currentLevel.getIcyGuy().getHealth() == 0) {
                    currentLevel.getIcyGuy().setLives(currentLevel.getIcyGuy().getLives() - 1);

                }

            }

        }
        // if the lives are 0, game over is set to true
        if (currentLevel.getIcyGuy().getLives() == 0) {
            game.setGameOver(true);
        }
        // if game time runs out, game over is set to true
        else if(currentLevel.getGameTime().getTimer() <= 0){
            game.setGameOver(true);
        }

        // draws the lives of the boss in level 6
        if(currentLevel instanceof Level6) {
                for (int i = 0; i < ((Level6) currentLevel).getBoss().getLives(); i++) {
                    g.drawImage(lives, 750 + i * 50, 20, 40, 40, this);
                    for (int j = currentLevel.getIcyGuy().getLives(); j < 0; j--) {
                        if (((Level6) currentLevel).getBoss().getHealth() == 0) {
                            ((Level6) currentLevel).getBoss().setLives(((Level6) currentLevel).getBoss().getLives() - 1);

                    }

                }
            }
        }

        // if game is over, health bar is removed and buttons are removed
        if(game.isGameOver()){
            this.remove(healthBarPanel);
            if(currentLevel instanceof Level6){
                g.drawImage(gameOverImageLevel6,0,0,this);
                this.setLayout(null);
                this.add(restartButton);
                restartButton.setBounds(500,15,0,0);
            } else {
                g.drawImage(gameOverImage, 0, 0, this);
                this.add(restartButton); // option for resetting the game
            }


        }
        else if (game.isGameWin()){
            this.remove(healthBarPanel);
            this.remove(bossHealthBarPanel);
            g.setPaint(Color.black);
            g.drawImage(gameWinImage,200,100,this);
            this.add(restartButton);

        }
    }


}


