package levels;

import backpack.IcePistol;
import backpack.JetPack;
import backpack.NoItem;
import collectibles.Coin;
import enemies.EnemyCollision;
import enemies.Flood;
import game.*;
import org.jbox2d.common.Vec2;
import city.cs.engine.*;
import platforms.AirPlatform;
import platforms.GroundPlatform;
import view.GameTime;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Level2 extends GameLevel {

    private final Image background;
    private  int floodX = 7;
    private SandStorm sandStorm;


    public Level2(Game game){
        super(game);

        gameTime = new GameTime(this,view,60);


            currentSound = SoundManager.getLevel2Sound();
        if (!game.isMuted()){
            currentSound.loop();
        }


        // setting background
        background = new ImageIcon("data/Level2Background.jpeg").getImage();


        // groundTexture
        groundTexture = new BodyImage("data/SandTexture.jpeg",40f);



        // the first ground platform when world spawns
        groundPlatform = new GroundPlatform(this,0,-8);
        groundPlatform.addImage(groundTexture);

        // Collision Listeners for ground platform
        groundPlatform.setClipped(true);
        groundPlatform.getGroundFixture().setDensity(6);

        airPlatTexture = new BodyImage("data/SandAirplatform.png",6);

        // populates level with ground platforms
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 12; j++) {
                groundPlatform = new GroundPlatform(this, groundX + i * 20, groundY);
                groundPlatform.addImage(groundTexture);
                sandStorm = new SandStorm(this);
                sandStorm.setPosition(new Vec2(groundX + i * 20, groundY + 3));
                Random random = new Random();
                groundY = random.nextInt(-8, -4);
                groundX += 100;
            }

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

        // air platforms created in loop
        for (int i = 0; i < 5; i++) {
            airPlatform = new AirPlatform(this);
            airPlatform.changeTexture(airPlatTexture);
            airPlatform.setPosition(new Vec2(platformX + 30, platformY));
            platformX = platformX + 10;
            Random random = new Random();
            platformY = random.nextInt(0,10);


        }

        // air platforms created in loop
        for (int i = 0; i < 5; i++) {
            airPlatform = new AirPlatform(this);
            airPlatform.changeTexture(airPlatTexture);
            airPlatform.setPosition(new Vec2(platformX + 60, platformY));
            platformX = platformX + 10;
            Random random = new Random();
            platformY = random.nextInt(0,10);
            System.out.println(airPlatform.getPosition());


        }

    }

    @Override
    public void populate() {
        super.populate();

        icyGuy.setPosition(new Vec2(-7, 0));

        // adds back pack
        addBackPack();

        // Portal level 2
        portal = new Portal(this,game);
        portal.setPosition(new Vec2(150,2));

        //Sandstorm obstacle
        sandStorm = new SandStorm(this);
        sandStorm.setPosition(new Vec2(0,-5));

        // populates level with flood enemies
        for (int i = 0; i < 10; i++) {
            flood = new Flood(this);
            flood.setPosition(new Vec2(floodX, 10));
            floodX = floodX + 10;
            Random random = new Random();
            floodX = random.nextInt(15,200);
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
    public void addBackPack() {
        // adds backpack item to level
        icyGuy.getBackPack().addItem(new IcePistol(icyGuy));
        icyGuy.getBackPack().addItem(new JetPack(icyGuy));
        icyGuy.getBackPack().addItem(new NoItem(icyGuy));

    }

    @Override
    public boolean isComplete() {
        return icyGuy.getEnemysDefeated() >= 3 && icyGuy.getCoins() >= 1;
    }

    @Override
    public String getName() {
        return "Level2";
    }



    @Override
    public Image getBackground() {
        return background;
    }
}
