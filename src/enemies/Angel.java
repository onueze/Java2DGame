package enemies;

import city.cs.engine.*;
import projectiles.EnergyBall;
import levels.GameLevel;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class Angel extends Walker implements StepListener {

    // body image for idle
    private final BodyImage angelIdle = new BodyImage("data/AngelIdle.gif",5f);

    // body image for attack
    private final BodyImage angelAttack = new BodyImage("data/AngelAttack.gif",5f);

    //  polygon shape of the angel
    private final Shape angelShape = new PolygonShape(-1.83f,-0.05f,
            -0.18f,-1.08f,
            1.81f,-0.04f,
            0.82f,0.66f,
            -0.87f,0.77f);


    private final GameLevel currentLevel;

    private final Fixture angelFixture;

    public static final float RANGE = 7;

    private String direction;

    private State state;

    private Vec2 dir;

    private float currentPos;

    private EnergyBall energyBall;

    private enum State {
        ATTACK , PATROL_REGION

    }

    private int timer;


    public Angel(GameLevel currentLevel) {
        super(currentLevel);
        angelFixture = new SolidFixture(this,angelShape); // fixture for angel
        this.currentLevel = currentLevel;
        addImage(angelIdle);
        currentLevel.addStepListener(this); // adds step listener to world
        setGravityScale(0);
        this.startWalking(-3);
        timer = 0;
    }

    // checks if enemy is in range from left
    public boolean inRangeAbove() {
        Body a = currentLevel.getIcyGuy();
        float gap = getPosition().y - a.getPosition().y;
        return gap < RANGE && gap > 0;    //gap in (0,RANGE)
    }

    // checks if enemy is in range from right
    public boolean inRangeBelow() {
        Body a = currentLevel.getIcyGuy();
        float gap = getPosition().y - a.getPosition().y;
        return gap > -RANGE && gap < 0;  //gap in (-RANGE, 0)
    }

    // directional shooting towards icy guy
    public void shoot(Vec2 t){
        energyBall = new EnergyBall(currentLevel);
        EnergyBall.getEnergySound().play();

        Vec2 dir = t.sub(this.getPosition()); // towards icy guy
        dir.normalize();

       energyBall.setPosition(this.getPosition().add(dir.mul(2f)));
       energyBall.setLinearVelocity(dir.mul(30));
    }


    private static SoundClip batHurt;

    static {
        try {
            batHurt = new SoundClip("data/BatDefeat.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public void switchSprite(BodyImage b){ // switches body images
        this.removeAllImages();
        this.addImage(b);
    }

    @Override
    public void setPosition(Vec2 v){
        super.setPosition(v);
        this.currentPos = v.x;

    }

    @Override
    public void preStep(StepEvent stepEvent) {
        if (inRangeAbove()) {
            // timer used to delay shooting
            if (timer != 0) {
                timer--;
            }
            if (timer == 0) {
                state = State.ATTACK;
                switchSprite(angelAttack); // switches body image
                shoot(currentLevel.getIcyGuy().getPosition()); // shoots towards icy guy
                timer = 3 * 60;

            } else {
                state = State.PATROL_REGION; // patrols region
            }
        }
           if (inRangeBelow()) { // checks if icy guy is in range below
            if (timer != 0) {
                timer--;
            }
            if (timer == 0) {
                state = State.ATTACK;
                switchSprite(angelAttack);
                shoot(currentLevel.getIcyGuy().getPosition()); // shoots projectile towards icy guy
                timer = 2 * 60;
            } else {
                state = State.PATROL_REGION;
            }
            // patrols the area
        } else {
            state = State.PATROL_REGION;
            // flies back and forth
            if (this.getPosition().x > currentPos + 2) {
                switchSprite(angelIdle);
                this.startWalking(-3);

            } else if (this.getPosition().x < currentPos - 2) {
                switchSprite(angelIdle);
                this.startWalking(3);
            }

        }
        refreshRoll();
    }

    private void refreshRoll() {
        // refreshes the FSM
        if (state == State.ATTACK) {
            timer--;
            if(timer == 0) {
                state = State.ATTACK;
                switchSprite(angelAttack);
                shoot(currentLevel.getIcyGuy().getPosition());
            }
            else{
                state = State.PATROL_REGION;
            }
        }
    }

    @Override
    public void postStep(StepEvent stepEvent) {

    }

    @Override
    public void destroy()
    {if(!currentLevel.getGame().isMuted()) { // destroys angel
        batHurt.play();
    }
        super.destroy();
    }
}
