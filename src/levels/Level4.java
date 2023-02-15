package levels;

import backpack.IcePistol;
import backpack.JetPack;
import backpack.NoItem;
import backpack.SkateBoard;
import collectibles.Bubble;
import collectibles.Coin;
import enemies.EnemyCollision;
import enemies.Flood;
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

public class Level4 extends GameLevel {


    private final Image background;
    private Image idleBackground;
    private int floodX = 7;
    private final int floodY = 0;
    private int bubbleX = 7;
    private int bubbleY = 0;
    private Bubble bubble;


    public Level4(Game game) {
        super(game);

        gameTime = new GameTime(this, view, 75);



        currentSound = SoundManager.getLevel4Sound();

        if (!game.isMuted()){
            currentSound.loop();
    }


        // setting background
            background = new ImageIcon("data/parallaxLevel4.gif").getImage();

        // groundTexture
        groundTexture = new BodyImage("data/WaterTexture.gif", 100f);


        // the first ground platform when world spawns
        groundPlatform = new GroundPlatform(this, 0, -8);
        groundPlatform.addImage(groundTexture);


        // Collision Listeners for ground platform
        groundPlatform.setClipped(true);
        groundPlatform.getGroundFixture().setDensity(6);

        airPlatTexture = new BodyImage("data/LightAirPlatform.png", 6);

        // populates level with ground platforms
        for (int i = 0; i < 15; i++) {
            groundPlatform = new GroundPlatform(this, groundX + i * 20, groundY);
            groundPlatform.addImage(groundTexture);
            Random random = new Random();
            groundY = random.nextInt(-8, -4);
            groundX += 100;
        }


        // populates level with air platforms
        for (int i = 0; i < 5; i++) {
            airPlatform = new AirPlatform(this);
            airPlatform.changeTexture(airPlatTexture);
            airPlatform.setPosition(new Vec2(platformX, platformY));
            platformX = platformX + 10;
            Random random = new Random();
            platformY = random.nextInt(0, 9);

        }

        for (int i = 0; i < 5; i++) {
            airPlatform = new AirPlatform(this);
            airPlatform.changeTexture(airPlatTexture);
            airPlatform.setPosition(new Vec2(platformX + 30, platformY));
            platformX = platformX + 10;
            Random random = new Random();
            platformY = random.nextInt(0, 10);

        }
        for (int i = 0; i < 5; i++) {
            airPlatform = new AirPlatform(this);
            airPlatform.changeTexture(airPlatTexture);
            airPlatform.setPosition(new Vec2(platformX + 60, platformY));
            platformX = platformX + 10;
            Random random = new Random();
            platformY = random.nextInt(0, 10);

        }

        groundGameOver = new GroundGameOver(this,game);
        groundGameOver.setAngleDegrees(90);

        // Portal level 4
        portal = new Portal(this,game);
        portal.setPosition(new Vec2(220,2));

    }

    @Override
    public void populate() {
        super.populate();



        icyGuy.setPosition(new Vec2(-5, 0));
        addBackPack();


        // populates level with flood enemies
        for (int i = 0; i < 10; i++) {
            flood = new Flood(this);
            flood.setPosition(new Vec2(floodX, 10));
            floodX = floodX + 10;
            Random random = new Random();
            floodX = random.nextInt(7,200);
            EnemyCollision enemyCollision = new EnemyCollision(icyGuy,this);
            flood.addCollisionListener(enemyCollision);
        }

        // populates level with bubbles
        for (int i = 0; i < 5; i++) {
            bubble = new Bubble(this);
            bubble.setPosition(new Vec2(bubbleX, bubbleY));
            bubbleX = bubbleX + 10;
            Random bubbleRand = new Random();
            bubbleX = bubbleRand.nextInt(30,200);
            bubbleY = bubbleRand.nextInt(0,10);
        }
        // new Coins with added collision listener
        for (int i = 0; i < 30; i++) {
            coin = new Coin(this);
            coin.setPosition(new Vec2(coinX, coinY));
            coinX = coinX + 10;
            Random random = new Random();
            coinX = random.nextInt(0,200);
            coinY = random.nextInt(0,10);}

    }

    @Override
    public void addBackPack() {
        // adds backpack item to level
        icyGuy.getBackPack().addItem(new IcePistol(icyGuy));
        icyGuy.getBackPack().addItem(new JetPack(icyGuy));
        icyGuy.getBackPack().addItem(new SkateBoard(icyGuy));
        icyGuy.getBackPack().addItem(new NoItem(icyGuy));

    }

    @Override
    public boolean isComplete() {
        return icyGuy.getEnemysDefeated() >= 5 && icyGuy.getCoins() >= 10;
    }

    @Override
    public String getName() {
        return "Level4";
    }


    @Override
    public Image getBackground() {
        return background;
    }
}
