package levels;

import backpack.IcePistol;
import backpack.NoItem;
import collectibles.Coin;
import enemies.EnemyCollision;
import enemies.Flood;
import game.*;
import org.jbox2d.common.Vec2;
import platforms.AirPlatform;
import platforms.GroundPlatform;
import view.GameTime;


import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Level1 extends GameLevel {
    private final Image background;
    private  int floodX = 7;
    private final int floodY = 0;


    public Level1(Game game){
        super(game);

        currentSound = SoundManager.getLevel1Sound();
        if (!game.isMuted()){
            currentSound.loop();
        }


        // texture of the ground
        groundTexture = GroundPlatform.getGroundImage();
        groundPlatform = new GroundPlatform(this,0,-8);
        groundPlatform.addImage(groundTexture);

        gameTime = new GameTime(this,view,50);


        background = new ImageIcon("data/Level1Background.png").getImage();

        // ground platforms created in loop
        for (int i = 0; i < 2; i++) {
            groundPlatform = new GroundPlatform(this,groundX + i * 20,groundY);
            groundPlatform.addImage(groundTexture);
            Random random = new Random();
            groundY = random.nextInt(-8,-4);
            groundX += 100;

        }

        airPlatTexture = AirPlatform.getPlatformImage();


        // air platforms created in loop
        for (int i = 0; i < 5; i++) {
            airPlatform = new AirPlatform(this);
            airPlatform.addImage(airPlatTexture);
            airPlatform.setPosition(new Vec2(platformX, platformY));
            platformX = platformX + 10;
            Random random = new Random();
            platformY = random.nextInt(0,9);


        }

        // air platforms created in loop
        for (int i = 0; i < 5; i++) {
            airPlatform = new AirPlatform(this);
            airPlatform.addImage(airPlatTexture);
            airPlatform.setPosition(new Vec2(platformX + 30, platformY));
            platformX = platformX + 10;
            Random random = new Random();
            platformY = random.nextInt(0,10);


        }
        // air platforms created in loop
        for (int i = 0; i < 5; i++) {
            airPlatform = new AirPlatform(this);
            airPlatform.addImage(airPlatTexture);
            airPlatform.setPosition(new Vec2(platformX + 60, platformY));
            platformX = platformX + 10;
            Random random = new Random();
            platformY = random.nextInt(0,10);
        }








    }

    @Override
    public void populate(){
        // method to place dynamic bodies
        super.populate();
        icyGuy.setPosition(new Vec2(-7, 0));

        addBackPack();

        // creates portal and sets position
        portal = new Portal(this,game);
        portal.setPosition(new Vec2(100,4));

        // creates flood enemies in a loop at random positions
        for (int i = 0; i < 15; i++) {
            flood = new Flood(this);
            flood.setPosition(new Vec2(floodX, 10));
            floodX = floodX + 10;
            Random random = new Random();
            floodX = random.nextInt(7,200);
            EnemyCollision enemyCollision = new EnemyCollision(icyGuy,this);
            flood.addCollisionListener(enemyCollision);
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
    public void addBackPack(){
        // adds items to icy Guys backpack
        icyGuy.getBackPack().addItem(new IcePistol(icyGuy));
        icyGuy.getBackPack().addItem(new NoItem(icyGuy));

    }

    @Override
    public Image getBackground() {
        return background;
    }

    @Override
    public boolean isComplete() {
        return icyGuy.getEnemysDefeated() >= 3;
    }

    @Override
    public String getName() {
        return "Level1";
    }


}
