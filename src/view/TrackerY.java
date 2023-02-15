package view;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.IcyGuy;
import levels.GameLevel;
import levels.Level6;
import org.jbox2d.common.Vec2;
import view.GameView;

public class TrackerY implements StepListener {
    private GameView view;
    private IcyGuy icyGuy;
    private float counter;
    private GameLevel currentLevel;

    public float getCounter() {
        return counter;
    }

    public TrackerY(GameView view, IcyGuy icyGuy, GameLevel w, float counter){
        this.icyGuy = icyGuy;
        this.view = view;
        this.currentLevel = w;
        this.counter = counter;
        w.addStepListener(this);
    }

    public void setCounter(float counter) {
        this.counter = counter;
    }

    public void preStep(StepEvent e) {}
    public void postStep(StepEvent e) {
        // camera moves vertically
        view.setCentre(new Vec2(0, counter));
        counter = counter + 0.03f; // camera speed
        if (!(currentLevel instanceof Level6)){
            if (counter > currentLevel.getPortal().getPosition().y) {
                counter = currentLevel.getPortal().getPosition().y;
            }
        }
        if (currentLevel instanceof Level6) {
            counter = 0;
        }
    }


    public void updateTracker(GameView view, IcyGuy newIcyGuy, GameLevel w,float counter){
        this.view = view;
        this.icyGuy = newIcyGuy;
        this.currentLevel = w;
        this.counter = counter;
        w.addStepListener(this);
    }

}
