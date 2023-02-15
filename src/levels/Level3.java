package levels;

import backpack.IcePistol;
import backpack.NoItem;
import backpack.SkateBoard;
import collectibles.Coin;
import enemies.Bat;
import enemies.EnemyCollision;
import game.*;
import org.jbox2d.common.Vec2;
import city.cs.engine.*;
import platforms.AirPlatform;
import platforms.GroundGameOver;
import platforms.GroundPlatform;
import view.GameTime;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Level3 extends GameLevel {



    private final Image background;
    private  int enemyX = 7;
    private int enemyY = 0;
    private final int floodY = 0;
    private Bat bat;


    public Level3(Game game){
        super(game);

        // game time of the level
        gameTime = new GameTime(this,view,40);



            currentSound = SoundManager.getLevel3Sound();
        if (!game.isMuted()){
            currentSound.loop();
        }


        // setting background
        background = new ImageIcon("data/cityBackground.gif").getImage();

        // groundTexture
        groundTexture = new BodyImage("data/Level3GroundTex.png",40f);


        // the first ground platform when world spawns
        groundPlatform = new GroundPlatform(this,0,-8);
        groundPlatform.addImage(groundTexture);


        // Collision Listeners for ground platform
        groundPlatform.setClipped(true);
        groundPlatform.getGroundFixture().setDensity(6);

        airPlatTexture = new BodyImage("data/SpookyAirplatform.png",6);

        // populates level with ground platforms
        for (int i = 0; i < 15; i++) {
            groundPlatform = new GroundPlatform(this,groundX + i * 20,groundY);
            groundPlatform.addImage(groundTexture);
            Random random = new Random();
            groundY = random.nextInt(-8,-4);
            groundX += 100;

        }
        // populates level with air platforms
        for (int i = 0; i < 5; i++) {
            airPlatform = new AirPlatform(this);
            airPlatform.changeTexture(airPlatTexture);
            airPlatform.setPosition(new Vec2(platformX, platformY));
            platformX = platformX + 10;
            Random random = new Random();
            platformY = random.nextInt(0,9);


        }

        for (int i = 0; i < 5; i++) {
            airPlatform = new AirPlatform(this);
            airPlatform.changeTexture(airPlatTexture);
            airPlatform.setPosition(new Vec2(platformX + 30, platformY));
            platformX = platformX + 10;
            Random random = new Random();
            platformY = random.nextInt(0,10);


        }
        for (int i = 0; i < 5; i++) {
            airPlatform = new AirPlatform(this);
            airPlatform.changeTexture(airPlatTexture);
            airPlatform.setPosition(new Vec2(platformX + 60, platformY));
            platformX = platformX + 10;
            Random random = new Random();
            platformY = random.nextInt(0,10);


        }

        groundGameOver = new GroundGameOver(this,game);
        groundGameOver.setAngleDegrees(90);

        // Portal level 3
        portal = new Portal(this,game);
        portal.setPosition(new Vec2(170,2));
    }

    @Override
    public void populate() {
        super.populate();
        icyGuy.setPosition(new Vec2(-5, 0));

        addBackPack();

        // populates level with bat enemies
        for (int i = 0; i < 10; i++) {
            bat = new Bat(this);
            bat.setPosition(new Vec2(enemyX, enemyY));
            enemyX = enemyX + 10;
            Random random = new Random();
            enemyX = random.nextInt(7,200);
            enemyY = random.nextInt(0,10);
            EnemyCollision enemyCollision = new EnemyCollision(icyGuy,this);
            bat.addCollisionListener(enemyCollision);
        }

        // new Coins with added collision listener
        for (int i = 0; i < 30; i++) {
            coin = new Coin(this);
            coin.setPosition(new Vec2(coinX, coinY));
            coinX = coinX + 10;
            Random random = new Random();
            coinX = random.nextInt(0,200);
            coinY = random.nextInt(0,10);
        }


    }

    @Override
    public boolean isComplete() {
        return icyGuy.getEnemysDefeated() >= 5 && icyGuy.getCoins() >= 3;
    }

    @Override
    public String getName() {
        return "Level3";
    }

    @Override
    public void addBackPack() {
        // adds backpack item to level
        icyGuy.getBackPack().addItem(new IcePistol(icyGuy));
        icyGuy.getBackPack().addItem(new SkateBoard(icyGuy));
        icyGuy.getBackPack().addItem(new NoItem(icyGuy));

    }

    @Override
    public Image getBackground() {
        return background;
    }
}
