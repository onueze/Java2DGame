package platforms;

import city.cs.engine.*;
import projectiles.Ice;
import levels.GameLevel;
import org.jbox2d.common.Vec2;

public class CloudPlatform extends StaticBody implements StepListener,SensorListener {

    private final BodyImage cloudImage = new BodyImage("data/CloudPlatform.png",4f); // image of cloud
    private final Shape cloudShape = new BoxShape(2f,0.5f); // cloud shape

    private float movingRate; // moving rate of the cloud platform

    private final Vec2 pos0; // vector to make platform move around x value (0)

    private final GameLevel currentLevel;

    private final Fixture fixture;

    private final Sensor sensor;

    public CloudPlatform(GameLevel currentLevel, float posX, float posY) {
        super(currentLevel);
        this.currentLevel = currentLevel;
        fixture = new SolidFixture(this,cloudShape);
        this.addImage(cloudImage);
        this.setPosition(new Vec2(posX,posY)); // sets position of cloud
        currentLevel.addStepListener(this);
        pos0 = new Vec2(0,0);
        movingRate = 0.08f;
        sensor = new Sensor(this,cloudShape); // added sensor
        sensor.addSensorListener(this);

    }

    @Override
    public void preStep(StepEvent stepEvent) {
        // cloud moves back and forth on x-axis
        if(this.getPosition().x > pos0.x + 10){
            movingRate *= -1;
        } else if (this.getPosition().x < pos0.x - 10){
            movingRate *= -1;
        }
        this.setPosition(new Vec2(this.getPosition().x + movingRate, this.getPosition().y)); // changes direction

    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }

    @Override
    public void beginContact(SensorEvent sensorEvent) {
        if(sensorEvent.getContactBody() instanceof Ice){ // if ice collides with cloud, ice gets destroyed
            sensorEvent.getContactBody().destroy();
        }
    }

    @Override
    public void endContact(SensorEvent sensorEvent) {

    }
}
