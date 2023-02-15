package levels;

import backpack.IcePistol;
import backpack.JetPack;
import backpack.NoItem;
import collectibles.Bubble;
import collectibles.Coin;
import enemies.Angel;
import enemies.EnemyCollision;
import game.*;
import org.jbox2d.common.Vec2;
import city.cs.engine.*;
import platforms.AirPlatform;
import platforms.CloudPlatform;
import view.GameTime;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Level5 extends GameLevel {


    private final Image background;
    private final int floodX = 7;
    private final int floodY = 0;
    private int bubbleX = 7;
    private int bubbleY = 0;
    private  int enemyX = 7;
    private int enemyY = 10;
    private Bubble bubble;
    private CloudPlatform cloud;
    private Angel angel;

    public Angel getAngel() {
        return angel;
    }

    public Level5(Game game) {
        super(game);

        gameTime = new GameTime(this, view, 200);

        currentSound = SoundManager.getLevel5Sound();
        if (!game.isMuted()){
            currentSound.loop();
        }


        if (!game.isMuted()){
            currentSound.loop();
        }


        // setting background
        background = new ImageIcon("data/CloudBackground.jpg").getImage();

        // groundTexture
        groundTexture = new BodyImage("data/WaterTexture.gif", 100f);

        // populates level with air platforms
        for (int i = 0; i < 22; i++) {
            cloud = new CloudPlatform(this,platformX,platformY);
            platformY = platformY + 9;
            Random random = new Random();
            platformX = random.nextInt(-10, 10);

        }


        airPlatform = new AirPlatform(this);
        airPlatform.setPosition(new Vec2(0,-5));
        airPlatTexture = AirPlatform.getPlatformImage();
        airPlatform.addImage(airPlatTexture);

        // Portal level 5
        portal = new Portal(this,game);
        portal.setPosition(new Vec2(0,180));


    }

    @Override
    public void populate() {
        super.populate();


        icyGuy.setPosition(new Vec2(0, 0));
        icyGuy.setGravityScale(0.9f);
        addBackPack();



        // populates level with bat enemies
        for (int i = 0; i < 7; i++) {
            angel = new Angel(this);
            angel.setPosition(new Vec2(enemyX, enemyY));
            enemyX = enemyX + 10;
            Random random = new Random();
            enemyY = random.nextInt(15,200);
            enemyX = random.nextInt(-10,10);
            EnemyCollision enemyCollision = new EnemyCollision(icyGuy,this);
            angel.addCollisionListener(enemyCollision);
        }

        // populates level with bubbles
        for (int i = 0; i < 10; i++) {
            bubble = new Bubble(this);
            bubble.setPosition(new Vec2(bubbleX, bubbleY));
            bubbleX = bubbleX + 10;
            Random bubbleRand = new Random();
            bubbleY = bubbleRand.nextInt(30,200);
            bubbleX = bubbleRand.nextInt(-10,10);
        }
        // new Coins with added collision listener
        for (int i = 0; i < 30; i++) {
            coin = new Coin(this);
            coin.setPosition(new Vec2(coinX, coinY));
            coinX = coinX + 10;
            Random random = new Random();
            coinY = random.nextInt(0,200);
            coinX = random.nextInt(-10,10);}


    }

    @Override
    public void addBackPack() {
        // adds backpack item to level
        icyGuy.getBackPack().addItem(new IcePistol(icyGuy));
        icyGuy.getBackPack().addItem(new JetPack(icyGuy));
        icyGuy.getBackPack().addItem(new NoItem(icyGuy));

    }

    @Override
    public boolean isComplete() {
        return icyGuy.getEnemysDefeated() >= 5;
    }

    @Override
    public String getName() {
        return "Level5";
    }


    @Override
    public Image getBackground() {
        return background;
    }
}
