package view;

import city.cs.engine.StepEvent;
import city.cs.engine.StepListener;
import levels.GameLevel;
import view.GameView;

public class GameTime implements StepListener {
    private int timer; // timer for the game
    private final GameView view;
    private final GameLevel currentLevel;


    public void setTimer(int timer) {
        this.timer = timer;
    }

    public GameTime(GameLevel w, GameView v, int time){
        this.view = v;
        timer = time * 60; // time variable takes a value that is the gametime in seconds
        currentLevel = w;
        w.addStepListener(this);


    }
    public int getTimer() {
        return timer;
    }



    @Override
    public void preStep(StepEvent stepEvent) {
        timer--; // timer gets reduced when placed into level

    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }
}
