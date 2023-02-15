package platforms;

import city.cs.engine.*;
import view.GameView;
import projectiles.Ice;
import game.IcyGuy;
import levels.GameLevel;
import levels.Level3;
import levels.Level4;

// air platform class to place air platform into the game world
public class AirPlatform extends StaticBody implements SensorListener {


    private static final Shape platformShape = new BoxShape(3, 0.5f);

    private static final BodyImage platformImage = new BodyImage("data/IceAirplatform.png",6);

    private final Fixture fixture;

    private final Sensor sensor;

    private boolean contact;

    private final GameLevel currentLevel;

    public static BodyImage getPlatformImage() {
        return platformImage;
    }

    public AirPlatform(GameLevel w) {
        super(w);
        // sensor added since class extends sensor listener
        sensor = new Sensor(this,platformShape);
        sensor.addSensorListener(this);
        // fixture for the platform
        fixture = new SolidFixture(this,platformShape);
        currentLevel = w;
        // clips the image of platform
        this.setClipped(true);
        setAlwaysOutline(true);

    }

    //Want to change the ground image
    public void changeTexture(BodyImage image){
        this.removeAllImages();
        this.addImage(image);
        setClipped(true);
    }

    @Override
    public void beginContact(SensorEvent sensorEvent) {
        // if icy guy makes contact with platform, life gets decreased/ increased by one depending on the level
        contact = true;
        if (sensorEvent.getContactBody() instanceof IcyGuy && contact && currentLevel instanceof Level3){
            // decreases icy guys health by 1
            ((IcyGuy) sensorEvent.getContactBody()).setHealth(((IcyGuy) sensorEvent.getContactBody()).getHealth()-1);
            GameView.getHealthBar().setValue(currentLevel.getIcyGuy().getHealth());
            if(!currentLevel.getGame().isMuted()) {
                IcyGuy.getIcyGuyHurt().play();
            }
            currentLevel.getIcyGuy().healthLoss();

        }else if (sensorEvent.getContactBody() instanceof IcyGuy && contact && currentLevel instanceof Level4) {
            // increases icy guys health by 1
            if(((IcyGuy) sensorEvent.getContactBody()).getHealth() < 100) {
                if(!currentLevel.getGame().isMuted()) {
                    IcyGuy.getIcyGuyYes().play();
                }
                ((IcyGuy) sensorEvent.getContactBody()).setHealth(((IcyGuy) sensorEvent.getContactBody()).getHealth() + 1);
                GameView.getHealthBar().setValue(currentLevel.getIcyGuy().getHealth());
            }
        }
        else if(sensorEvent.getContactBody() instanceof Ice){
            sensorEvent.getContactBody().destroy();
        }

    }

    @Override
    public void endContact(SensorEvent sensorEvent) {
        contact = false;

    }
}
