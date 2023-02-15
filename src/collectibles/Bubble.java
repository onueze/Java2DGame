package collectibles;

import city.cs.engine.*;
import view.GameView;
import game.IcyGuy;
import levels.GameLevel;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

//enemy class
public class Bubble extends DynamicBody implements SensorListener{
    // circle shape for the enemy
    private static final Shape bubbleShape = new CircleShape(0.5f);

    // Body image for the enemy
    private static final BodyImage bubbleImage = new BodyImage("data/bubble.gif", 1f);

    private static SoundClip bubblePop; // sound if bubble gets collected

    public static SoundClip getBubblePop() {
        return bubblePop;
    }

    static {
        try {
            bubblePop = new SoundClip("data/BubblePop.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    private final Sensor bubbleSensor; // sensor for the bubble
    private final GhostlyFixture bubbleFixture; // bubble as ghostly fixture

    private final GameLevel currentLevel;

    private final int timer;



    public Bubble(GameLevel currentLevel) {
        super(currentLevel);
        this.currentLevel = currentLevel;
        bubbleSensor = new Sensor(this,bubbleShape); // sensor
        bubbleSensor.addSensorListener(this); // sensor added to body
        bubbleFixture = new GhostlyFixture(this,bubbleShape); // ghostly fixture
        addImage(bubbleImage); //image added
        setGravityScale(0); // gravity set to 0 to avoid falling of bubble
        timer = 7 * 60;

    }

    @Override
    public void destroy() {
        // destroys bubble object with sound if game is not muted
        if(!currentLevel.getGame().isMuted()) { // asks if game is muted
            bubblePop.play();
        }
        super.destroy();
    }

    @Override
    public void beginContact(SensorEvent sensorEvent) {
        // If icy guy makes contact with the bubble, his health increases by 5
        if (sensorEvent.getContactBody() instanceof IcyGuy){
            this.destroy();
            if(!currentLevel.getGame().isMuted()) {
                IcyGuy.getIcyGuyYes().play();
            }
            if(((IcyGuy) sensorEvent.getContactBody()).getHealth() <= 95) {
                ((IcyGuy) sensorEvent.getContactBody()).setHealth(((IcyGuy) sensorEvent.getContactBody()).getHealth() + 5);
                GameView.getHealthBar().setValue(currentLevel.icyGuy.getHealth());
            }

        }

    }

    @Override
    public void endContact(SensorEvent sensorEvent) {

    }
}