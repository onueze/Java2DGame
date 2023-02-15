package levels;

import backpack.IcePistol;
import backpack.JetPack;
import backpack.NoItem;
import collectibles.Bubble;
import collectibles.Coin;
import enemies.Angel;
import enemies.Bat;
import enemies.Boss;
import enemies.EnemyCollision;
import game.*;
import org.jbox2d.common.Vec2;
import platforms.CloudPlatform;
import platforms.FinalLevelGround;
import view.GameTime;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Level6 extends GameLevel {


    private final Image background;
    private final int bossX = 7;
    private final int bossY = 0;
    private int bubbleX = 7;
    private int bubbleY = 0;
    private  int enemyX = 7;
    private int enemyY = 10;
    private Bubble bubble;
    private CloudPlatform cloud;
    private Angel angel;
    private FinalLevelGround finalLevelGround;
    private Bat bat;
    private Boss boss;

    public Boss getBoss() {
        return boss;
    }

    public Angel getAngel() {
        return angel;
    }

    public Level6(Game game) {
        super(game);

        gameTime = new GameTime(this, view, 300);

        currentSound = SoundManager.getLevel6Sound();


        if (!game.isMuted()){
            currentSound.loop();
        }


        // setting background
        background = new ImageIcon("data/SpaceBackGround.jpg").getImage();

        finalLevelGround = new FinalLevelGround(this);
        finalLevelGround.setPosition(new Vec2(0,-10));

        finalLevelGround = new FinalLevelGround(this);
        finalLevelGround.setPosition(new Vec2(0,18));



        // populates level with air platforms
        for (int i = 0; i < 2; i++) {
            cloud = new CloudPlatform(this,platformX,platformY);
            platformY = platformY + 9;
            Random random = new Random();
            platformX = random.nextInt(-10, 10);

        }


    }

    @Override
    public void populate() {
        super.populate();


        icyGuy.setPosition(new Vec2(-15, -9));
        icyGuy.setGravityScale(0.9f);
        addBackPack();


        // populates level with bat enemies
        for (int i = 0; i < 3; i++) {
            bat = new Bat(this);
            bat.setPosition(new Vec2(enemyX, enemyY));
            enemyX = enemyX + 10;
            Random random = new Random();
            enemyX = random.nextInt(7,20);
            enemyY = random.nextInt(-1,15);
            EnemyCollision enemyCollision = new EnemyCollision(icyGuy,this);
            bat.addCollisionListener(enemyCollision);
        }

        // populates level with bubbles
        for (int i = 0; i < 10; i++) {
            bubble = new Bubble(this);
            bubble.setPosition(new Vec2(bubbleX, bubbleY));
            bubbleX = bubbleX + 10;
            Random bubbleRand = new Random();
            bubbleY = bubbleRand.nextInt(0,10);
            bubbleX = bubbleRand.nextInt(-10,10);
        }
        // new Coins with added collision listener
        for (int i = 0; i < 15; i++) {
            coin = new Coin(this);
            coin.setPosition(new Vec2(coinX, coinY));
            coinX = coinX + 10;
            Random random = new Random();
            coinY = random.nextInt(0,10);
            coinX = random.nextInt(-10,10);}

        // boss
        boss = new Boss(this);
        boss.setPosition(new Vec2(18, -9));
        EnemyCollision enemyCollision = new EnemyCollision(icyGuy, this);
        boss.addCollisionListener(enemyCollision);





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
        return boss.getLives() == 0;
    }

    @Override
    public String getName() {
        return "Level6";
    }


    @Override
    public Image getBackground() {
        return background;
    }
}
