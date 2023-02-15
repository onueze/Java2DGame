package enemies;

import city.cs.engine.*;
import levels.GameLevel;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

//enemy class
public class Bat extends Walker implements StepListener {
    // Polygon shape for the enemy
    private static final Shape batShape = new PolygonShape(-2.1f,0.76f,
            2.38f,0.86f,
            1.15f,-1.68f,
            -0.92f,-2.13f,
            -2.2f,-0.53f);

    // Body image for the enemy
    private static final BodyImage batLeft = new BodyImage("data/BatLeft.gif", 5f);

    private static final BodyImage batRight = new BodyImage("data/BatRight.gif", 5f);

    public static final float RANGE = 2;

    private String direction;

    private State state;

    private final GameLevel currentLevel;

    private Vec2 dir;

    private float currentPos;

    private enum State {
        TOWARDS_ENEMY , PATROL_REGION

    }

    public static SoundClip getBatHurt() {
        return batHurt;
    }

    // checks if enemy is in range from left
    public boolean inRangeLeft() {
        Body a = currentLevel.getIcyGuy();
        float gap = getPosition().x - a.getPosition().x;
        return gap < RANGE && gap > 0;    //gap in (0,RANGE)
    }

    // checks if enemy is in range from right
    public boolean inRangeRight() {
        Body a = currentLevel.getIcyGuy();
        float gap = getPosition().x - a.getPosition().x;
        return gap > -RANGE && gap < 0;  //gap in (-RANGE, 0)
    }

    private static SoundClip batHurt;

    static {
        try {
            batHurt = new SoundClip("data/BatDefeat.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }



    public Bat(GameLevel world) {
        super(world, batShape);
        currentLevel = world;
        world.addStepListener(this);
        addImage(batLeft);
        this.direction = "left";
        setGravityScale(0);
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
            this.addImage(batLeft);
            direction = "left";

        } else {
            this.removeAllImages();
            this.addImage(batRight);
            direction = "right";

        }


    }
    @Override
    public void preStep(StepEvent stepEvent) {
        // bat enemy will move directly towards icy guy if character is in range
        if (inRangeRight() || inRangeLeft()) {
            if (state != State.TOWARDS_ENEMY) {
                state = State.TOWARDS_ENEMY;
                Vec2 dir = currentLevel.getIcyGuy().getPosition().sub(this.getPosition());
                this.setLinearVelocity(dir);
            }
            // patrols the area
        } else {
            if (state != State.PATROL_REGION) {
                state = State.PATROL_REGION;
                if (this.getPosition().x > currentPos + 2) {
                    this.startWalking(-3);
                } else if (this.getPosition().x < currentPos - 2)
                    this.startWalking(3);

            }
        }
        refreshRoll();
    }

    private void refreshRoll() {
        switch (state){
            case TOWARDS_ENEMY:
                Vec2 dir = currentLevel.getIcyGuy().getPosition().sub(this.getPosition());
                this.setLinearVelocity(dir);
                break;
            default:

        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }

    @Override
    public void destroy()
    {if(!currentLevel.getGame().isMuted()) {
        batHurt.play();
    }

        super.destroy();
    }
}

