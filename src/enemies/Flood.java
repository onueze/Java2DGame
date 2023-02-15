package enemies;

import city.cs.engine.*;
import levels.GameLevel;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

//enemy class
public class Flood extends Walker implements StepListener {
    // Polygon shape for the enemy
    private static final Shape floodShape = new PolygonShape(-1.19f, -1.74f,
            0.16f, -1.73f,
            0.93f, -0.89f,
            1.25f, -0.18f,
            0.39f, 1.67f,
            -0.53f, 1.7f);

    // Body image for the enemy
    private static final BodyImage floodLeft = new BodyImage("data/FloodLeft.gif", 4f);

    private static final BodyImage floodRight = new BodyImage("data/FloodRight.gif", 4f);

    public static final float RANGE = 7;

    private String direction;

    private State state;

    private final GameLevel currentLevel;

    private float currentPos;

    private enum State {
        TOWARDS_LEFT, TOWARDS_RIGHT, PATROL_REGION // 3 states the flood can take in

    }

    public boolean inRangeLeft() {
        Body a = currentLevel.getIcyGuy();
        float gap = getPosition().x - a.getPosition().x;
        return gap < RANGE && gap > 0;    //gap in (0,RANGE)
    }

    public boolean inRangeRight() {
        Body a = currentLevel.getIcyGuy();
        float gap = getPosition().x - a.getPosition().x;
        return gap > -RANGE && gap < 0;  //gap in (-RANGE, 0)
    }

    private static SoundClip floodHurt; // sound when flood is hurt

    static {
        try {
            floodHurt = new SoundClip("data/EnemyHurt.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }


    public Flood(GameLevel world) {
        super(world, floodShape);
        currentLevel = world;
        world.addStepListener(this); // step listener added
        addImage(floodLeft);
        this.direction = "left"; // starts off walking in left direction
        this.startWalking(-3);

    }

    @Override
    public void setPosition(Vec2 v){
        super.setPosition(v);
        this.currentPos = v.x;

    }

    @Override
//     this is a startWalking method overridden specifically for the enemy
    public void startWalking(float speed) {
        super.startWalking(speed);
        if (speed < 0) {
            this.removeAllImages();
            this.addImage(floodLeft); // change of image as he walks left
            direction = "left";

        } else {
            this.removeAllImages();
            this.addImage(floodRight);
            direction = "right"; // change of image as he walks left

        }


    }

    @Override
    public void preStep(StepEvent stepEvent) {
        // if icy guy is in right range, flood will walk after him
        if (inRangeRight()) {
            if (state != State.TOWARDS_RIGHT) {
                state = State.TOWARDS_RIGHT;
                startWalking(4);
            }
        } else if (inRangeLeft()) {
            // if icy guy is in left range, flood will walk after him
            if (state != State.TOWARDS_LEFT) {
                state = State.TOWARDS_LEFT;
                startWalking(-4);
            }
        } else {
            // if icy guy is not in right range, flood will patrol area
            state = State.PATROL_REGION;
            if (this.getPosition().x > currentPos + 5) {
                this.startWalking(-3);
            } else if (this.getPosition().x < currentPos - 5)
                this.startWalking(3);
        }
        refreshRoll(); // refreshes FSM
    }


    public static SoundClip getFloodHurt() {
        return floodHurt;
    }

    private void refreshRoll() {
        switch (state) {
            case TOWARDS_LEFT:
                startWalking(-2);
                break;
            case TOWARDS_RIGHT:
                startWalking(2);
                break;
            default: // nothing to do
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }

    @Override
    public void destroy()
    {
        if(!currentLevel.getGame().isMuted()) { // checks if game is muted
            floodHurt.play();
        }
        super.destroy();
    }

}

