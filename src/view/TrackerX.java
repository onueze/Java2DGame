package view;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import game.IcyGuy;
import levels.*;
import org.jbox2d.common.Vec2;
import view.GameView;

public class TrackerX implements StepListener {
    private GameView view;
    private IcyGuy icyGuy;
    private float counter;
    private final float posX = 0;
    private GameLevel currentLevel;

    public float getCounter() {
        return counter;
    }

    public TrackerX(GameView view, IcyGuy icyGuy, GameLevel w, float counter){
        this.icyGuy = icyGuy;
        this.view = view;
        this.currentLevel = w;
        w.addStepListener(this);
        this.counter = counter;
    }

    public void preStep(StepEvent e) {
    }
    public void postStep(StepEvent e) {
        // for level 3 and 4 , the tracker moves horizontally
        if (currentLevel instanceof Level3 || currentLevel instanceof Level4) {
            view.setCentre(new Vec2(counter, 0));
            counter = counter + 0.1f; // counter describes the speed of camera movement

            if(counter > currentLevel.getPortal().getPosition().x){
                counter = currentLevel.getPortal().getPosition().x;
                // when portal is in sight, camera focuses on portal
            }
            // for level 1 and level 2, the camera is centred on icy guy
//        System.out.println(counter);
        }
        if (currentLevel instanceof Level1 || currentLevel instanceof Level2) {
            view.setCentre(new Vec2(currentLevel.getIcyGuy().getPosition().x, 0));
        }
    }
    // updating of the tracker
    public void updateTracker(GameView view, IcyGuy newIcyGuy, GameLevel w, float counter){
        this.view = view;
        this.icyGuy = newIcyGuy;
        this.currentLevel = w;
        this.counter = counter;
        w.addStepListener(this);
    }

    public void setCounter(float counter) {
        this.counter = counter;
    }
}
