package platforms;

import city.cs.engine.*;
import game.Game;
import game.IcyGuy;
import levels.GameLevel;
import levels.Level3;
import levels.Level4;
import levels.Level5;
import org.jbox2d.common.Vec2;

public class GroundGameOver extends StaticBody implements SensorListener,StepListener{

    private final Shape groundGameOverShape = new BoxShape(1000, 0.5f); // shape of ground
    private final Sensor sensor;
    private final Game game;
    private final GameLevel currentLevel;

    public GroundGameOver(GameLevel currentLevel, Game game) {
        // constructor
        super(currentLevel);
        this.currentLevel = currentLevel;
        this.game = game;
        sensor = new Sensor(this,groundGameOverShape);
        sensor.addSensorListener(this);
        currentLevel.addStepListener(this);

    }

    @Override
    public void beginContact(SensorEvent sensorEvent) {
        // when icy guy makes contact, the game is over
        if(sensorEvent.getContactBody() instanceof IcyGuy){
            game.setGameOver(true);
        }

    }

    @Override
    public void endContact(SensorEvent sensorEvent) {

    }

    @Override
    public void preStep(StepEvent stepEvent) {
        // the ground moves with the view when the view has parallax scrolling in order to make the icy guy dying
        // more convenient
        if(currentLevel instanceof Level5){
            // ground is set that it's not visible within the view but constantly moves with the view
//            currentLevel.getGroundGameOver().setPosition(new Vec2(0,game.getTrackerY().getCounter()-25));
        } else if(currentLevel instanceof Level3 || currentLevel instanceof Level4){
            currentLevel.getGroundGameOver().setPosition(new Vec2(game.getTrackerX().getCounter()-20,0));
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }
}
