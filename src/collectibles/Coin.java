package collectibles;

import city.cs.engine.*;
import game.IcyGuy;
import levels.GameLevel;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Coin extends DynamicBody implements SensorListener{
    // shape for the coin
    private static final Shape coinShape = new CircleShape(0.5f);

    private static final BodyImage coinImage = new BodyImage("data/coin.gif",1f); // image of coin

    private static SoundClip CoinSound; // sound when coin is collected

    private final Sensor coinSensor;
    private final GhostlyFixture coinFixture;

    static {
        try {
            CoinSound = new SoundClip("data/CoinCollectSound.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    private final GameLevel currentLevel;


    public Coin(GameLevel currentLevel) {
        super(currentLevel);
        this.currentLevel = currentLevel;
        coinSensor = new Sensor(this,coinShape); // added sensor
        coinSensor.addSensorListener(this);
        coinFixture = new GhostlyFixture(this,coinShape); // coin as ghostly fixture
        addImage(coinImage);
        setGravityScale(0);

    }


    public static SoundClip getCoinSound(){
        return CoinSound;
    }

    @Override

    public void destroy(){
        if(!currentLevel.getGame().isMuted()) { // asks if game is muted
            CoinSound.play(); // plays coin sound
            CoinSound.setVolume(0.1f);
        }
        super.destroy();
    }

    @Override
    public void beginContact(SensorEvent sensorEvent) {
        // adds +1 to icy guys collected coins
        if(sensorEvent.getContactBody() instanceof IcyGuy){
            this.destroy();
            ((IcyGuy) sensorEvent.getContactBody()).setCoins(((IcyGuy) sensorEvent.getContactBody()).getCoins()+1);
        }

    }

    @Override
    public void endContact(SensorEvent sensorEvent) {

    }
}
