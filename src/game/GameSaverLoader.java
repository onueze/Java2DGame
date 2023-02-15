package game;

import city.cs.engine.DynamicBody;
import collectibles.Bubble;
import collectibles.Coin;
import enemies.Angel;
import enemies.Bat;
import enemies.EnemyCollision;
import enemies.Flood;
import levels.*;
import org.jbox2d.common.Vec2;
import view.GameView;
import view.TrackerX;
import view.TrackerY;

import javax.swing.plaf.IconUIResource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Class for saving and loading the game
 * This class can save the current state of the game.
 *
 * That is, keeping track of the level, keeping track of game assets, like how many enemies were killed
 * how many coins were collected within that specific level.
 *
 * The loading method enables to update the game to the saved file which is stored in a .txt
 * */

public class GameSaverLoader {

    /**
     * Method for saving
     * The file writer writes specified objects to a txt file. such as game level
     * and all the other assets of the game
     * @param fileName : the file which the file writer writes to
     * @param currentLevel : the current level that may be saved
     * @throws IOException */
    public static void save(String fileName, GameLevel currentLevel) throws IOException {

        boolean append = false; // file will be overridden
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName, append);


            writer.write(currentLevel.getName() + "\n"); // level name

            writer.write("timer," + currentLevel.getGameTime().getTimer() + "\n"); // keeps track of timer



            for(int i=0; i<currentLevel.getDynamicBodies().size();i++) {
                // iterates through all the dynamic bodies and stores the position and other properties

                DynamicBody b = currentLevel.getDynamicBodies().get(i);

                if (b instanceof Coin) {
                    writer.write("Coin," + b.getPosition().x + "," + b.getPosition().y + "\n");


                } else if (b instanceof IcyGuy) {
                    writer.write("IcyGuy," + b.getPosition().x + "," + b.getPosition().y + ","
                            + ((IcyGuy) b).getHealth() + "," + ((IcyGuy) b).getLives() + "," + ((IcyGuy) b).getEnemysDefeated() + "," +  ((IcyGuy) b).getCoins() + "\n");


                } else if (b instanceof Flood) {
                    writer.write("Flood," + b.getPosition().x + "," + b.getPosition().y + "\n");


                } else if (b instanceof Bat) {
                    writer.write("Bat," + b.getPosition().x + "," + b.getPosition().y + "\n");


                } else if (b instanceof Bubble) {
                    writer.write("Bubble," + b.getPosition().x + "," + b.getPosition().y + "\n");
                }
                else if (b instanceof SandStorm) {
                    writer.write("Sandstorm," + b.getPosition().x + "," + b.getPosition().y + "\n");
                }
                else if (b instanceof Portal) {
                    writer.write("Portal," + b.getPosition().x + "," + b.getPosition().y + "\n");
                }
                else if (b instanceof Angel) {
                    writer.write("Angel," + b.getPosition().x + "," + b.getPosition().y + "\n");
                }

            }

            if (currentLevel.getName().equals("Level3") || currentLevel.getName().equals("Level4")) {
                writer.write("TrackerX," + currentLevel.getGame().getTrackerX().getCounter() + "\n"); // keeps track of trackerX
                currentLevel.getGame().getTrackerX().getCounter();

            } else if (currentLevel.getName().equals("Level5")){
                writer.write("TrackerY," + currentLevel.getGame().getTrackerY().getCounter() + "\n"); // keeps track of trackerY
                currentLevel.getGame().getTrackerY().getCounter();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close(); // closes writer when writer reaches end of file
            }
        }
    }

    /** method for loading
     * * The file reader reads specified objects from a txt file. such as game level
     *  and all the other assets of the game
     *  @param fileName : the file which the file writer writes to
     *  @param game : reads the game that should be loaded into
     *  @throws IOException */
    public static GameLevel load(String fileName, Game game) throws IOException{
        FileReader fr = null;
        BufferedReader reader = null;
        GameLevel currentLevel = game.getCurrentLevel();

        // reads the levels and sets the level number
        try {
            System.out.println("Reading " + fileName + " ...");
            fr = new FileReader(fileName);
            reader = new BufferedReader(fr);

            String line = reader.readLine();
            if(line.equals("Level1")){
                currentLevel = new Level1(game);
                game.setLevelNumber(1);

            }
            else if(line.equals("Level2")){
                currentLevel = new Level2(game);
                game.setLevelNumber(2);

            }

            else if(line.equals("Level3")){
                currentLevel = new Level3(game);
                game.setLevelNumber(3);
            }

            else if(line.equals("Level4")){
                currentLevel = new Level4(game);
                game.setLevelNumber(4);
            }
            else if(line.equals("Level5")){
                currentLevel = new Level5(game);
                game.setLevelNumber(5);
            }

            line = reader.readLine();
            // iterates through all specified dynamic bodies of the saving method and places them in the loaded world
            while (line != null) {
                // file is assumed to contain one name, score pair per line
                String[] tokens = line.split(",");


                if(tokens[0].equals("IcyGuy")){
                    currentLevel.icyGuy = new IcyGuy(currentLevel);
                    currentLevel.getIcyGuy().setPosition(new Vec2(Float.parseFloat(tokens[1]),Float.parseFloat(tokens[2])));
                    currentLevel.icyGuy.setHealth(Integer.parseInt(tokens[3]));
                    GameView.getHealthBar().setValue(currentLevel.icyGuy.getHealth());
                    currentLevel.icyGuy.setLives(Integer.parseInt(tokens[4]));
                    currentLevel.icyGuy.setEnemysDefeated(Integer.parseInt(tokens[5]));
                    currentLevel.icyGuy.setCoins(Integer.parseInt(tokens[6]));
                    game.getController().updateIcyGuy(currentLevel.icyGuy);
                    currentLevel.addBackPack();
                    currentLevel.currentSound = currentLevel.getCurrentSound();


                }

                else if(tokens[0].equals("Coin")){
                    Coin coin = new Coin(currentLevel);
                    coin.setPosition(new Vec2(Float.parseFloat(tokens[1]),Float.parseFloat(tokens[2])));
                }
                else if(tokens[0].equals("Bubble")){
                    Bubble bubble = new Bubble(currentLevel);
                    bubble.setPosition(new Vec2(Float.parseFloat(tokens[1]),Float.parseFloat(tokens[2])));
                }
                else if(tokens[0].equals("Flood")){
                    Flood flood = new Flood(currentLevel);
                    flood.setPosition(new Vec2(Float.parseFloat(tokens[1]),Float.parseFloat(tokens[2])));
                    EnemyCollision enemyCollision = new EnemyCollision(currentLevel.icyGuy,currentLevel);
                    flood.addCollisionListener(enemyCollision);
                }
                else if(tokens[0].equals("Bat")){
                    Bat bat = new Bat(currentLevel);
                    bat.setPosition(new Vec2(Float.parseFloat(tokens[1]),Float.parseFloat(tokens[2])));
                    EnemyCollision enemyCollision = new EnemyCollision(currentLevel.icyGuy,currentLevel);
                    bat.addCollisionListener(enemyCollision);
                }
                else if(tokens[0].equals("Sandstorm")){
                    SandStorm sandStorm = new SandStorm(currentLevel);
                    sandStorm.setPosition(new Vec2(Float.parseFloat(tokens[1]),Float.parseFloat(tokens[2])));
                }
                else if(tokens[0].equals("Portal")){
                    Portal portal = new Portal(currentLevel,game);
                    portal.setPosition(new Vec2(Float.parseFloat(tokens[1]),Float.parseFloat(tokens[2])));

                } else if (tokens[0].equals("timer")){
                    currentLevel.getGameTime().setTimer(Integer.parseInt(tokens[1]));
                }
                else if(tokens[0].equals("TrackerX")){
                    TrackerX trackerX = new TrackerX(currentLevel.getGame().getView(),currentLevel.icyGuy,currentLevel,Float.parseFloat(tokens[1]));
                    currentLevel.getGame().getTrackerX().updateTracker(currentLevel.getGame().getView(),currentLevel.icyGuy,currentLevel,Float.parseFloat(tokens[1]));
                    trackerX.setCounter(Float.parseFloat(tokens[1]));


                }
                else if(tokens[0].equals("TrackerY")){
                    TrackerY trackerY = new TrackerY(currentLevel.getGame().getView(),currentLevel.icyGuy,currentLevel, Float.parseFloat(tokens[1]));
                    currentLevel.getGame().getTrackerY().updateTracker(currentLevel.getGame().getView(),currentLevel.icyGuy,currentLevel,Float.parseFloat(tokens[1]));
                    trackerY.setCounter(Float.parseFloat(tokens[1]));
                }

                else if(tokens[0].equals("Angel")){
                    Angel angel = new Angel(currentLevel);
                    angel.setPosition(new Vec2(Float.parseFloat(tokens[1]),Float.parseFloat(tokens[2])));
                }

                line = reader.readLine();
            }
            return currentLevel;
        } finally {
            if (reader != null) {
                reader.close();
            }
            if (fr != null) {
                fr.close();
            }
        }
    }
}
