package projectiles;

import city.cs.engine.*;
import view.GameView;
import platforms.IceBox;
import game.IcyGuy;
import levels.GameLevel;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class EnergyStrike extends DynamicBody implements  SensorListener{
    // shape of the energy strike projectile
    private static final Shape energyBallShape = new CircleShape(1f); // energy strike shape

    private static final BodyImage energyBallImage = new BodyImage("data/EnergyStrike.gif",6f); // energy strike image

    private static SoundClip energySound; // sound when energy strike gets shot

    private final Sensor energySensor; // sensor for energy strike

    private final GhostlyFixture ghostlyFixture;

    private final GameLevel currentLevel;

    private int hits; // number of hits energy strike can take from ice ball






    static {
        try {
            energySound = new SoundClip("data/EnergyBallSound.wav");
            energySound.setVolume(0.1f);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public EnergyStrike(GameLevel currentLevel) {
        super(currentLevel);
        this.currentLevel = currentLevel;
        energySensor = new Sensor(this,energyBallShape);
        energySensor.addSensorListener(this);
        ghostlyFixture = new GhostlyFixture(this,energyBallShape);
        addImage(energyBallImage);
        setGravityScale(0);
        hits = 3; // 3 hits from ice all and energy strike gets destroyed


    }

    @Override
    public void destroy()
    {
        super.destroy();
    }

    public static SoundClip getEnergySound(){
        return energySound;
    }

    public Shape getEnergyBallShape(){
        return energyBallShape;
    }

    public GhostlyFixture getEnergyFixture(){
        return ghostlyFixture;
    }


    public BodyImage getEnergyBallImage(){
        return energyBallImage;
    }


    @Override
    public void beginContact(SensorEvent sensorEvent) {
        if(sensorEvent.getContactBody() instanceof Ice){
            sensorEvent.getContactBody().destroy();
            hits--;
            if(hits == 0) { // when hit 3 times by ice , it gets destroyed
                this.destroy();
            }

        } else if(sensorEvent.getContactBody() instanceof IceBox){ // when it hits ice box , it gets destroyed
            this.destroy();

        }
        else if(sensorEvent.getContactBody() instanceof IcyGuy){
            // reduces health of icy guy by 30
            this.destroy();
            ((IcyGuy) sensorEvent.getContactBody()).setHealth(((IcyGuy) sensorEvent.getContactBody()).getHealth()-30);
            IcyGuy.getIcyGuyHurt().play();
            GameView.getHealthBar().setValue(currentLevel.icyGuy.getHealth()); // update of health bar
            currentLevel.icyGuy.healthLoss();

        }

    }

    @Override
    public void endContact(SensorEvent sensorEvent) {

    }
}
