package projectiles;

import city.cs.engine.*;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Ice extends DynamicBody implements  SensorListener{
    // shape of the iceBall projectile
    private static final Shape iceBallShape = new PolygonShape(-1.75f,0.53f,
            -1.26f,1.62f,
            1.51f,0.94f,
            1.7f,-0.88f,
            -0.52f,-1.81f,
            -1.77f,-0.55f);


    // Body images for the iceBall
    private static final BodyImage iceBallLeft = new BodyImage("data/IceBallLeft.gif",4f);

    private static final BodyImage iceBallRight = new BodyImage("data/IceBallRight.gif",4f);

    // iceBall fixture
    private final Fixture iceBallFixture;

    private static SoundClip IceSound;

    private final Sensor iceSensor;




    static {
        try {
            IceSound = new SoundClip("data/IcePistolSound.wav");
            IceSound.setVolume(0.3);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public Ice(World world) {
        // constructor
        super(world);
        iceSensor = new Sensor(this,iceBallShape);
        iceSensor.addSensorListener(this);
        iceBallFixture = new SolidFixture(this,iceBallShape);
        addImage(iceBallLeft);
        setGravityScale(0);



    }

    @Override
    public void destroy()
    {
        super.destroy();
    }

    public static SoundClip getIceSound(){
        return IceSound;
    }

    public Shape getIceBallShape(){
        return iceBallShape;
    }

    public Fixture getIceBallFixture(){
        return iceBallFixture;
    }


    public BodyImage getIceBallRight(){
        return iceBallRight;
    }

    public BodyImage getIceBallLeft(){
        return iceBallLeft;
    }

    @Override
    public void beginContact(SensorEvent sensorEvent) {
        // when ice makes contact with another ice projectile, both get destroyed
        if( sensorEvent.getContactBody() instanceof Ice){
            this.destroy();
            sensorEvent.getContactBody().destroy();
        }

    }

    @Override
    public void endContact(SensorEvent sensorEvent) {

    }
}
