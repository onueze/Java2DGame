package platforms;

import city.cs.engine.*;
import game.IcyGuy;
import game.IcyGuyController;
import levels.GameLevel;
import levels.Level2;
import org.jbox2d.common.Vec2;
import projectiles.Ice;

public class GroundPlatform extends StaticBody implements SensorListener {

    private static final Shape groundShape = new BoxShape(50, 0.5f);
    private static final BodyImage groundImage = new BodyImage("data/IceGround.png",40f);

    public static BodyImage getGroundImage() {
        return groundImage;
    }

    private final Fixture groundFixture;

    private final Sensor sensor;

    private boolean contact;

    private final GameLevel currentLevel;

    // image is added to ground platform
    public GroundPlatform(GameLevel world, float posX, float posY){
        super(world);
        sensor = new Sensor(this,groundShape);
        sensor.addSensorListener(this);
        groundFixture = new SolidFixture(this,groundShape);
        currentLevel = world;
        setClipped(true);
        setPosition(new Vec2(posX,posY));
        setAlwaysOutline(true);
        contact = false;

    }

    public Fixture getGroundFixture(){
        return groundFixture;
    }

    //Want to change the ground image
    public void changeTexture(BodyImage image){
        this.removeAllImages();
        this.addImage(image);
        setClipped(true);
    }


    @Override
    public void beginContact(SensorEvent sensorEvent) {
        // when contact is made, the ground has different properties
        contact = true;
        if (sensorEvent.getContactBody() instanceof IcyGuy && contact && currentLevel instanceof Level2) {
            // ground of level 2 reduces icy guys speed when contact is made
            IcyGuyController.setWalkingSpeed(2);
            IcyGuyController.setSprintingSpeed(6);


        }
        else if (sensorEvent.getContactBody() instanceof IcyGuy && contact && !(currentLevel instanceof Level2)) {
            IcyGuyController.setWalkingSpeed(5);
            IcyGuyController.setSprintingSpeed(9);
        }

        else if(sensorEvent.getContactBody() instanceof Ice){
            // when ice hits ground, ice projectile is destroyed
            sensorEvent.getContactBody().destroy();
        }
    }

    @Override
    public void endContact(SensorEvent sensorEvent) {
        contact = false;
        // reset walking speed when no contact with the ground is made
        if (sensorEvent.getContactBody() instanceof IcyGuy && !contact && currentLevel instanceof Level2) {
            IcyGuyController.setWalkingSpeed(5);
            IcyGuyController.setSprintingSpeed(9);
        }

        }

}
