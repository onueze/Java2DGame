package projectiles;

import city.cs.engine.*;
import view.GameView;
import platforms.IceBox;
import game.IcyGuy;
import levels.GameLevel;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class EnergyBall extends DynamicBody implements  SensorListener{
    // shape of the energyBall projectile
    private static final Shape energyBallShape = new CircleShape(0.5f);

    private static final BodyImage energyBallImage = new BodyImage("data/EnergyBall.gif",4f);

    private static SoundClip energySound;

    private final Sensor energySensor;

    private final GhostlyFixture ghostlyFixture;

    private final GameLevel currentLevel;






    static {
        try {
            energySound = new SoundClip("data/EnergyBallSound.wav");
            energySound.setVolume(0.1f);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public EnergyBall(GameLevel currentLevel) {
        super(currentLevel);
        this.currentLevel = currentLevel;
        energySensor = new Sensor(this,energyBallShape);
        energySensor.addSensorListener(this); // added sensor listener
        ghostlyFixture = new GhostlyFixture(this,energyBallShape);
        addImage(energyBallImage); // added image
        setGravityScale(0); // gravity is 0



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
        if( sensorEvent.getContactBody() instanceof Ice){
            sensorEvent.getContactBody().destroy(); // if energy-ball collides with ice, both get destroyed
            this.destroy();

            // if energy ball collides with icebox, energy ball gets destroyed
        } else if( sensorEvent.getContactBody() instanceof IceBox){
            this.destroy();
        }
        // if energy ball collides with icy guy, energy ball gets destroyed and icy guy loses 30 health
        else if(sensorEvent.getContactBody() instanceof IcyGuy){
            this.destroy();
            ((IcyGuy) sensorEvent.getContactBody()).setHealth(((IcyGuy) sensorEvent.getContactBody()).getHealth()-30);
            IcyGuy.getIcyGuyHurt().play(); // plays sound when icy guy gets hurt
            GameView.getHealthBar().setValue(currentLevel.icyGuy.getHealth()); // updates health bar
            currentLevel.icyGuy.healthLoss();

        }

    }

    @Override
    public void endContact(SensorEvent sensorEvent) {

    }
}
