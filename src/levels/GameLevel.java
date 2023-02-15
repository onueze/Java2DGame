package levels;

import city.cs.engine.*;
import collectibles.Coin;
import enemies.Flood;
import game.*;
import org.jbox2d.common.Vec2;
import platforms.AirPlatform;
import platforms.GroundGameOver;
import platforms.GroundPlatform;
import view.GameTime;
import view.GameView;

import java.awt.*;

public abstract class GameLevel extends World {

    // fields are protected, in order to access them in inherited classes
    public IcyGuy icyGuy;
    protected Flood flood;
    protected GroundPlatform groundPlatform;
    protected AirPlatform airPlatform;
    protected Coin coin;
    protected Portal portal;
    protected BodyImage groundTexture;
    protected BodyImage airPlatTexture;
    protected int platformX = 0;
    protected int platformY = 0;
    protected GroundGameOver groundGameOver;

    protected int coinX = 0;
    protected int coinY = 0;
    protected GameTime gameTime;

    public GameView getView() {
        return view;
    }

    protected int groundX = 100;
    protected int groundY;
    protected SoundManager soundManager;
    public SoundClip currentSound;

    public Portal getPortal() {
        return portal;
    }

    public Game getGame() {
        return game;
    }

    protected GameView view;
    protected Game game;

    public GameLevel(Game game) {
        super();
        this.game = game;
        gameTime = null;
        // ground that is not directly visible but gets activated if
        groundGameOver = new GroundGameOver(this,game);
        groundGameOver.setPosition(new Vec2(0,-18));
    }

    public void restartGameAssets(){
        // resets game assets as icy guy progresses to next level
        icyGuy.setEnemysDefeated(0);
        icyGuy.setCoins(0);
        icyGuy.setHealth(100);
        icyGuy.setLives(3);
        GameView.getHealthBar().setValue(this.icyGuy.getHealth());
    }

    public GameTime getGameTime() {
        return gameTime;
    }

    public GroundGameOver getGroundGameOver() {
        return groundGameOver;
    }

    public IcyGuy getIcyGuy() {
        return icyGuy;
    }

    public abstract Image getBackground();

    public abstract boolean isComplete();

    public SoundClip getCurrentSound() {
        return currentSound;
    }

    public abstract String getName();

    public abstract void addBackPack();



    public void populate(){ // populates the world with dynamic bodies
        icyGuy = new IcyGuy(this);


    }


}
