package platforms;

import city.cs.engine.*;
import levels.GameLevel;
import projectiles.Ice;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class IceBox extends StaticBody implements StepListener,SensorListener {

    private static final BodyImage iceBoxImage = new BodyImage("data/IceBoxTexture.png");

    private static final BoxShape iceBox =  new BoxShape(1f, 0.4f);

    private int counter;

    private static SoundClip boxPlaceSound; // sound when ice box is placed

    private final Sensor iceBoxSensor;

    static {
        try {
            boxPlaceSound = new SoundClip("data/BlockSet.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }


    public static SoundClip getBoxPlaceSound() {
        return boxPlaceSound;
    }

    public IceBox(GameLevel w) {
        // constructor
        super(w,iceBox);
        iceBoxSensor = new Sensor(this,iceBox);
        iceBoxSensor.addSensorListener(this);
        addImage(iceBoxImage);
        counter = 180;
        w.addStepListener(this);
        this.setClipped(true);
        setAlwaysOutline(true);
        if(!w.getGame().isMuted()) {
            boxPlaceSound.play();
        }

    }

    @Override
    // counter -- reduces the counter by 60
    public void preStep(StepEvent stepEvent) {
        counter--;
        if(counter == 0){
            this.destroy();
        }

    }

    @Override
    public void postStep(StepEvent stepEvent) {
            }

    @Override
    public void beginContact(SensorEvent sensorEvent) {
        if ( sensorEvent.getContactBody() instanceof Ice){
            this.destroy();
            sensorEvent.getContactBody().destroy();
            sensorEvent.getSensor().destroy();
        }

    }

    @Override
    public void endContact(SensorEvent sensorEvent) {

    }
}
