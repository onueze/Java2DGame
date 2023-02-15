package game;

import city.cs.engine.*;
import levels.GameLevel;
import levels.Level4;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Portal extends DynamicBody implements SensorListener {

    private static final Shape portalShape = new CircleShape(1f);
    private static final BodyImage portalImage = new BodyImage("data/Portal.gif",6f);

    private final GameLevel currentLevel;
    private final Game game;

    private static SoundClip portalSound;
    private boolean contact;

    public static SoundClip getPortalSound() {
        return portalSound;
    }

    static {
        try {
            portalSound = new SoundClip("data/PortalEnter.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    private final GhostlyFixture portalFixture;
    private final Sensor portalSensor;


    public Portal(GameLevel w, Game g) {
        super(w);
        this.game = g;
        currentLevel = w;
        portalSensor = new Sensor(this,portalShape); // portal as sensor
        portalSensor.addSensorListener(this); // sensor listener added
        portalFixture = new GhostlyFixture(this,portalShape); // portal fixture
        this.addImage(portalImage);
        setGravityScale(0);
        contact = false;

    }

    @Override
    public void beginContact(SensorEvent sensorEvent) {
        contact = true;
        if (sensorEvent.getContactBody() instanceof IcyGuy){
            if (currentLevel.isComplete()){  // if level is complete, icy guy is able to make contact with portal
                game.goToNextLevel();
                if(!game.isMuted()) {
                    Portal.getPortalSound().play();
                }

            }
            contact = false;

        }


    }

    @Override
    public void endContact(SensorEvent sensorEvent) {
        contact = false;

    }
}
