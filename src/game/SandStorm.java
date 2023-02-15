package game;

import city.cs.engine.*;
import levels.GameLevel;
import view.GameView;

public class SandStorm extends DynamicBody implements SensorListener {

    private final BodyImage sandStormImage = new BodyImage("data/SandStorm.gif",7f);

    private static final Shape sandStormShape = new PolygonShape(-0.36f,-1.95f,
            0.41f,-1.97f,
            0.87f,0.97f,
            -0.97f,1.06f);
    private final Sensor sandStormSensor;
    private final GhostlyFixture stormFixture;
    private final GameLevel currentLevel;

    public SandStorm(GameLevel w) {
        super(w);
        currentLevel = w;
        sandStormSensor = new Sensor(this,sandStormShape);
        sandStormSensor.addSensorListener(this);
        stormFixture = new GhostlyFixture(this,sandStormShape);
        addImage(sandStormImage);
        setGravityScale(0);
    }

    @Override
    public void beginContact(SensorEvent sensorEvent) {
        // if contact is made, sandstorm reduces health of icy guy by 20
        if (sensorEvent.getContactBody() instanceof IcyGuy){
           currentLevel.getIcyGuy().setHealth(currentLevel.getIcyGuy().getHealth()-20);
            GameView.getHealthBar().setValue(currentLevel.getIcyGuy().getHealth());
            if(!currentLevel.getGame().isMuted()) { // checks if game is muted
                IcyGuy.getIcyGuyHurt().play();
            }
           currentLevel.getIcyGuy().healthLoss();
        }

    }

    @Override
    public void endContact(SensorEvent sensorEvent) {

    }
}
