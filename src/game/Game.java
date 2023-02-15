package game;

import city.cs.engine.DebugViewer;
import gui.*;
import levels.*;
import view.GameView;
import view.GiveFocus;
import view.TrackerX;
import view.TrackerY;

import javax.swing.*;
import java.awt.*;

/**
 * Your main game entry point
 */
public class Game {


    private GameLevel currentLevel;
    private static GameView view;
    private final IcyGuyController controller;
    private TrackerX trackerX;
    private TrackerY trackerY;
    private int levelNumber = 1;
    private SoundManager soundManager;
    private final IceBoxPlacer iceBoxPlacer;
    private boolean gameOver;
    private boolean menuVisible;
    private final ControlPanel controlPanel;
    private static JFrame frame;
    private final SettingsPanel settingsPanel;
    private final GameLoadingPanel gameloadingPanel;
    private boolean gameLoadingVisible;
    private final TutorialPanel tutorialPanel;
    private boolean tutorialVisible;
    private boolean gameWin;

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    private boolean settingsVisible;
    private final ChooseLevelPanel chooseLevelPanel;
    private boolean chooseLevelVisible;

    private boolean muted;

    public GameLevel getCurrentLevel() {
        return currentLevel;
    }

    public IcyGuyController getController() {
        return controller;
    }

    public TrackerY getTrackerY() {
        return trackerY;
    }

    public TrackerX getTrackerX() {
        return trackerX;
    }

    /** Initialise a new Game. */
    public Game() {



        //1. make a game world
        currentLevel = new Level1(this);
        currentLevel.populate();

        // sets gameOver to false
        gameOver = false;
        gameWin = false;
        menuVisible = false;
        muted = false;
        settingsVisible = false;
        chooseLevelVisible = false;
        gameLoadingVisible = false;
        tutorialVisible = false;

        // icy Guy
        IcyGuy icyGuy = currentLevel.getIcyGuy();


        // make a view to look into the game world
        view = new GameView(this,currentLevel, icyGuy,500, 500);

        // create tracker to keep track of the character
        trackerX = new TrackerX(view, currentLevel.getIcyGuy(), currentLevel,0);

        // adding a MouseListener to the view
        view.addMouseListener(new GiveFocus(view));


        // adds Mouse listener to game for IceBoxPlacer
        iceBoxPlacer = new IceBoxPlacer(view,currentLevel);
        view.addMouseListener(iceBoxPlacer);




        // optional: draw a 1-metre grid over the view
        // view.setGridResolution(1);


        // IcyGuy controller
        controller = new IcyGuyController(this,currentLevel.getIcyGuy());

        // add key Listener for controlling icy Guy
        view.addKeyListener(controller);




        //3. create a Java window (frame) and add the game
        //  view to it
        frame = new JFrame("City Game");
        frame.add(view);


        controlPanel = new ControlPanel(this);
        settingsPanel = new SettingsPanel(this);
        chooseLevelPanel = new ChooseLevelPanel(this);
        gameloadingPanel = new GameLoadingPanel(this);
        tutorialPanel = new TutorialPanel(this);




        // enable the frame to quit the application
        // when the x button is pressed
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationByPlatform(true);
        // don't let the frame be resized
        frame.setResizable(false);
        // size the frame to fit the world view
        frame.pack();
        // finally, make the frame visible
        frame.setVisible(true);


        //optional: uncomment this to make a debugging view
         JFrame debugView = new DebugViewer(currentLevel, 500, 500);

        // start our game world simulation!
        currentLevel.start();
    }



    public void goToNextLevel() {
        // method for jumping to another level
        currentLevel.stop();
        currentLevel.getCurrentSound().stop();
        levelNumber++;
        if (levelNumber == 1) {
            // updates statistics to new level
            currentLevel.restartGameAssets();
            currentLevel = new Level1(this);
            currentLevel.populate();
            // removes IceBox mouse listener
            view.removeMouseListener(iceBoxPlacer);
            // show new world in the view
            view.setWorld(currentLevel);
            // update tracker for level 1
            trackerX.updateTracker(view, currentLevel.getIcyGuy(), currentLevel,0);
            // enable controls for world 1
            controller.updateIcyGuy(currentLevel.getIcyGuy());
            // start of simulation
            currentLevel.start();
        } else if (levelNumber == 2) {
            // updates statistics to new level
            currentLevel.restartGameAssets();
            currentLevel = new Level2(this);
            currentLevel.populate();
            // removes IceBox mouselistener
            view.removeMouseListener(iceBoxPlacer);
            // show new world in the view
            view.setWorld(currentLevel);
            // update tracker for level 2
            trackerX.updateTracker(view, currentLevel.getIcyGuy(), currentLevel,0);
            // enable controls for world 2
            controller.updateIcyGuy(currentLevel.getIcyGuy());
            // start of simulation
            currentLevel.start();

        } else if (levelNumber == 3) {
            // updates statistics to new level
            currentLevel.restartGameAssets();
            currentLevel = new Level3(this);
            currentLevel.populate();
            // show new world in the view
            view.setWorld(currentLevel);
            // update tracker for level 5
            trackerX.updateTracker(view, currentLevel.getIcyGuy(), currentLevel,0);
            // enable controls for world 5
            controller.updateIcyGuy(currentLevel.getIcyGuy());
            // update mouseListener
            view.addMouseListener(new IceBoxPlacer(view, currentLevel));
            // start of simulation
            currentLevel.start();

        } else if (levelNumber == 4) {
            currentLevel.restartGameAssets();
            currentLevel = new Level4(this);
            currentLevel.populate();
            // updates statistics to new level
            currentLevel.restartGameAssets();
            // show new world in the view
            view.setWorld(currentLevel);
            // update tracker for level 5
            trackerX.updateTracker(view, currentLevel.getIcyGuy(), currentLevel,0);
            // enable controls for world 5
            controller.updateIcyGuy(currentLevel.getIcyGuy());
            // update mouseListener
            view.addMouseListener(new IceBoxPlacer(view, currentLevel));
            // start of simulation
            currentLevel.start();

        } else if (levelNumber == 5) {
            // updates statistics to new level
            currentLevel.restartGameAssets();
            currentLevel = new Level5(this);
            currentLevel.populate();
            // show new world in the view
            view.setWorld(currentLevel);
            // update tracker for level 5
            trackerY = new TrackerY(view, currentLevel.getIcyGuy(), currentLevel,0);
            // enable controls for world 5
            controller.updateIcyGuy(currentLevel.getIcyGuy());
            // update mouseListener
            view.addMouseListener(new IceBoxPlacer(view, currentLevel));
            // start of simulation
            currentLevel.start();


        } else if (levelNumber == 6) {
            currentLevel = new Level6(this);
            view.setLayout(null);

            // this block of code changes the size of the frame and places buttons accordingly
            frame.remove(view);
            view.remove(view.getResumeButton());
            view.remove(view.getStopButton());
            frame.setPreferredSize(new Dimension(900,700));
            frame.add(view);
            view.add(view.getResumeButton());
            view.add(view.getStopButton());
            view.getResumeButton().setBounds(450,5,30,30);
            view.getStopButton().setBounds(500,5,30,30);


            trackerY = new TrackerY(view, currentLevel.getIcyGuy(), currentLevel,0);
            // show new world in the view
            view.setWorld(currentLevel);
            view.repaint();
            frame.pack();
            frame.repaint();



            currentLevel.populate();
            // updates statistics to new level
            currentLevel.restartGameAssets();
            // enable controls for world 6
            controller.updateIcyGuy(currentLevel.getIcyGuy());
            // update mouseListener
            view.addMouseListener(new IceBoxPlacer(view, currentLevel));
            // start of simulation
            currentLevel.start();

        }

        else if(levelNumber == 7){
            setGameWin(true); // game is won
        }
    }



    /** Run the game. */
    public static void main(String[] args) {
        new Game();

    }

    public boolean isGameOver(){
        return gameOver;
    }

    public void setGameOver(boolean gameOver){
        this.gameOver = gameOver;
        currentLevel.stop();
        view.remove(view.getResumeButton()); // removes resume button
        view.remove(view.getStopButton()); // removes stop button
        currentLevel.stop();
        currentLevel.getCurrentSound().stop();
        if(!this.isMuted()) {
            SoundManager.getGameOverSound().play();
        }
        view.repaint();

    }

    public boolean isGameWin() {
        return gameWin;
    }

    public void setGameWin(boolean gameWin) {
        this.gameWin = gameWin;
        currentLevel.stop();
        view.remove(view.getResumeButton());
        view.remove(view.getStopButton());
        currentLevel.stop();
        currentLevel.getCurrentSound().stop();
        if(!this.isMuted()) {
            SoundManager.getGameWinSound().play();
        }
        view.repaint();

    }

    public void toggleMenu() {
        if (menuVisible && !settingsVisible && !chooseLevelVisible && !gameLoadingVisible) {
            //hide menu
            frame.remove(controlPanel.mainPanel);
            frame.pack();
            if(!this.isMuted()){
                currentLevel.getCurrentSound().loop();
            }
            menuVisible = false;
            currentLevel.start();

        } else if (!menuVisible && !settingsVisible && !chooseLevelVisible && !gameLoadingVisible) {
            // show main menu
            frame.remove(view);
            frame.add(controlPanel.mainPanel,BorderLayout.WEST);
            frame.add(view);
            view.requestFocus();
            frame.pack();
            frame.repaint();
            menuVisible = true;
            currentLevel.stop();

        } else if (!menuVisible && settingsVisible && !chooseLevelVisible && !gameLoadingVisible){
            transitionToMain();
        }

    }

    public void transitionToSettings(){
        // adds settings menu
        frame.remove(controlPanel.mainPanel);
        frame.remove(view);
        frame.add(settingsPanel.mainPanel,BorderLayout.WEST);
        frame.add(view);
        view.requestFocus();
        frame.pack();
        frame.repaint();
        settingsVisible = true;
        menuVisible = false;

        chooseLevelVisible = false;

        gameLoadingVisible = false;


    }

    public void transitionToMain(){
        // transition settings menu to main menu
        frame.remove(settingsPanel.mainPanel);
        frame.remove(view);
        frame.add(controlPanel.mainPanel,BorderLayout.WEST);
        frame.add(view);
        view.requestFocus();
        frame.pack();
        frame.repaint();
        settingsVisible = false;
        menuVisible = true;

        gameLoadingVisible = false;

        chooseLevelVisible = false;

    }

    public void transitionLevelToMain(){
        // transition choose level menu to main menu
        frame.remove(chooseLevelPanel.mainPanel);
        frame.remove(view);
        frame.setPreferredSize(new Dimension(500,500));
        frame.add(controlPanel.mainPanel,BorderLayout.WEST);
        frame.add(view);
        view.requestFocus();
        frame.pack();
        frame.repaint();
        chooseLevelVisible = false;
        menuVisible = true;

        gameLoadingVisible = false;

        settingsVisible = false;



    }

    public void transitionToControlLevel(){
        // transition main menu to choose level menu
        frame.remove(controlPanel.mainPanel);
        frame.remove(view);
        frame.setPreferredSize(new Dimension(700,500));
        frame.add(chooseLevelPanel.mainPanel);
        frame.pack();
        frame.repaint();
        chooseLevelVisible = true;
        menuVisible = false;

        settingsVisible = false;

        gameLoadingVisible = false;



    }

    // this is when a level is chosen it will remove the control level menu in  order to play the game
    public void removeControlLevelPanel(){
        frame.remove(chooseLevelPanel.mainPanel);
        frame.remove(view);
        frame.setPreferredSize(new Dimension(500,500));
        frame.add(view);
        view.requestFocus();
        frame.pack();
        frame.repaint();
        chooseLevelVisible = false;
        menuVisible = false;

        gameLoadingVisible = false;
        settingsVisible = false;


    }

    public void transitionToGameLoading() {
        // when game is loaded, it will remove all the panels
        frame.remove(controlPanel.mainPanel);
        frame.remove(view);
        gameloadingPanel.mainPanel.setPreferredSize(new Dimension(500,500));
        frame.add(gameloadingPanel.mainPanel);
        frame.pack();
        frame.repaint();
        gameLoadingVisible = true;
        menuVisible = false;

        settingsVisible = false;
        chooseLevelVisible = false;

    }

    public void transitionGameLoadToMain() {
        // transition from game saving/loading menu to main menu
        frame.remove(gameloadingPanel.mainPanel);
        frame.remove(view);
        frame.add(controlPanel.mainPanel,BorderLayout.WEST);
        frame.add(view);
        view.requestFocus();
        frame.pack();
        frame.repaint();
        gameLoadingVisible = false;
        menuVisible = true;

        settingsVisible = false;

        chooseLevelVisible = false;

    }

    public void transitionToTutorial() {
        // transition from main menu to tutorial menu
        frame.remove(controlPanel.mainPanel);
        frame.remove(view);
        tutorialPanel.mainPanel.setPreferredSize(new Dimension(500,500));
        frame.add(tutorialPanel.mainPanel);
        frame.pack();
        frame.repaint();
        tutorialVisible = true;
        menuVisible = false;

        settingsVisible = false;

        chooseLevelVisible = false;
    }

    public void transitionTutorialToMain() {
        // transition from tutorial menu to main menu
        frame.remove(tutorialPanel.mainPanel);
        frame.remove(view);
        frame.add(controlPanel.mainPanel,BorderLayout.WEST);
        frame.add(view);
        view.requestFocus();
        frame.pack();
        frame.repaint();
        menuVisible = true;
        tutorialVisible = false;
        settingsVisible = false;
        chooseLevelVisible = false;
    }


    public GameView getView() {
        return view;
    }

    public void setLevel (GameLevel l) {
        currentLevel.getCurrentSound().stop();
        currentLevel.stop();
        currentLevel = l;
        currentLevel.getCurrentSound().loop();
        // removes IceBox mouse listener
        view.removeMouseListener(iceBoxPlacer);
        // show new world in the view
        view.setWorld(currentLevel);
        if (currentLevel instanceof Level1 || currentLevel instanceof Level2){
            trackerX = new TrackerX(view, currentLevel.getIcyGuy(), currentLevel, 0);
        }
        // enable controls for world 2
        controller.updateIcyGuy(currentLevel.getIcyGuy());
        // start of simulation
        currentLevel.start();


    }



    public void startNewGame(){
        currentLevel.getCurrentSound().stop();
        frame.dispose();
        new Game();
    }

    public boolean isMuted() {
        return muted;
    }

    public void setMuted(boolean muted) {
        this.muted = muted;
    }


}
