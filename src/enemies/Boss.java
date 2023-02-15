package enemies;

import city.cs.engine.*;
import projectiles.EnergyBall;
import projectiles.EnergyStrike;
import levels.GameLevel;
import org.jbox2d.common.Vec2;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

/** This class represents the boss character.
 *
 * Just like the icy Guy character, the boss character also inherits the walker class,
 * which enables jumping, walking, etc.
 *
 * The boss is implemented as a step listener in order to keep track of movements,
 * and time them accordingly.
 *
 * Finite state machines are used in order to put the boss in specific states.
 * The boss can be in an IDlE state, he can be in RUN state and in ENERGYSTRIKE
 * state.
 *
 *
 * Just like icy Guy, the boss also has 100 health and 3 lives and when lives reach 0
 * the boss dies.
 * */

public class Boss extends Walker implements StepListener {
    /** private field for Idle image */
    private final BodyImage bossIdle = new BodyImage("data/BossIdle.gif",5f);

    /** private field for running left image*/
    private final BodyImage bossRunLeft = new BodyImage("data/bossRunLeft.gif",5f);

    /** private field for running right image */
    private final BodyImage bossRunRight = new BodyImage("data/bossRunRight.gif",5f);

    /** private field for energy strike right image */
    private final BodyImage bossEnergyStrikeRight = new BodyImage("data/bossEnergyStrikeRight.gif",5f);

    /** private field for energy strike left image */
    private final BodyImage bossEnergyStrikeLeft = new BodyImage("data/bossEnergyStrikeLeft.gif",5f);


    /** polygon shape of the boss */
    private final Shape bossShape = new PolygonShape(1.36f,-2.01f,
            1.36f,2.21f,
            -0.93f,2.25f,
            -1.64f,-1.99f);


    /** private field for current level*/
    private final GameLevel currentLevel;

    /** private field for the fixture */
    private final Fixture fixture;

    /** private field for the range, which is used to determine
     * if the boss is in range of icy guy */
    public static final float RANGE = 30;

    /** setter for bosses health
     * @param health : health value can be entered */
    public void setHealth(int health) {
        this.health = health;
    }

    /** private field for direction, keeps track in which direction
     * the boss is facing */
    private String direction;

    /** private field for the state */
    private State state;

    /** private field for a vector. Is used for directional shooting of the enemy */
    private Vec2 dir;

    /** private field which describes the current position of the boss */
    private float currentPos;

    /** private field for the energy attack of the boss */
    private EnergyStrike energyStrike;




    /** this is an enumeration of states. The boss uses 3 different states */
    private enum State {
        IDLE, ENERGYSTRIKE , RUN

    }

    /** private field for timer */
    private int timer;

    /** private field for health */
    private int health;

    /** private field for lives*/
    private int lives;


    /** getter for health
     * @return retruns the health */
    public int getHealth() {
        return health;
    }

    /** setter for lives
     * @param lives life value can be entered */
    public void setLives(int lives) {
        this.lives = lives;
    }

    /** getter for lives
     * @return returns lives */
    public int getLives() {
        return lives;
    }

    /** constructor for boss class.
     * In this constructor most of the defined private fields are initialised.
     * The boss is placed inside the current level, the fixture is added to the boss,
     * the image is added.
     *
     * There also is a timer which is used to time the states that the boss can
     * be in.
     * @param currentLevel : the current level the character.*/
    public Boss(GameLevel currentLevel) {
        super(currentLevel);
        fixture = new SolidFixture(this, bossShape);
        this.currentLevel = currentLevel;
        addImage(bossIdle); // added image
        currentLevel.addStepListener(this); // steplistener added to world
        timer = 2 * 60; // timer
        health = 100;
        lives = 3;

    }

    /** checks if character is in right range of boss */
    public boolean inRangeLeft() {
        Body a = currentLevel.getIcyGuy(); // icy guy
        float gap = getPosition().x - a.getPosition().x; // gap between boss and icy guy
        return gap < RANGE && gap > 0;    //gap in (0,RANGE)
    }

    /** checks if character is in left range of boss */
    public boolean inRangeRight() {
        Body a = currentLevel.getIcyGuy(); // icy guy
        float gap = getPosition().x - a.getPosition().x; // gap between boss and icy guy
        return gap > -RANGE && gap < 0;  //gap in (-RANGE, 0)
    }


    /**
     * This method allows the boss to shoot in a certain direction dependant
     * on the icy Guys current position
     * @param t describes the direction in which the boss shoots */
    // directional shooting towards icy guy
    public void shoot(Vec2 t){
        energyStrike = new EnergyStrike(currentLevel);
        EnergyBall.getEnergySound().play();

        // vector towards icy guy
        Vec2 dir = t.sub(this.getPosition());
        dir.normalize();
        // shoots projectile towards icy guy
        energyStrike.setPosition(this.getPosition().add(dir.mul(2f)));
        energyStrike.setLinearVelocity(dir.mul(30));
    }

    /** queries if the boss has lost 100 health or if the boss
     * has 0 lives left
     * the boss will be destroyed when having 0 lives
     */
    public void healthLoss(){
        // if health is 0
        // the health gets set to 100
        if( this.health <= 0){
            this.lives = lives -1;
            if(this.lives != 0) {
                this.health = 100;
            }if(this.lives == 0) {
                // if lives are 0, the boss dies
                this.destroy();
                currentLevel.getGame().setGameWin(true);

            }
        }
    }


    /**  private field when boss is hurt
     */
    private static SoundClip bossHurt;

    static {
        try {
            bossHurt = new SoundClip("data/BatDefeat.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    /**
     * this method switches body images of the boss
     * @param b : describes the body image the boss switches to */
    public void switchSprite(BodyImage b){
        this.removeAllImages();
        this.addImage(b);
    }

    /** is an override method for setting the position of the character
     * @param v : describes the position*/
    @Override
    public void setPosition(Vec2 v){
        super.setPosition(v);
        this.currentPos = v.x;

    }

    /** This method keeps track of the steps of the simulation.
     * 60 steps make 1 second and when used, the code gets updated every
     * 60 steps.
     * @param stepEvent : can be used to count steps of simulation
     * */
    @Override
    public void preStep(StepEvent stepEvent) {
        timer--; // timer is used to delay when the boss shoots
        if(timer != 0) {
            if (state != State.RUN) {
                state = State.RUN; // running state
                // depending on if the icy guy is on the left or the right of the boss
                // the boss will walk towards the icy guy
                if (this.getPosition().x > currentLevel.getIcyGuy().getPosition().x) {
                    switchSprite(bossRunLeft);
                    this.startWalking(-8);
                } else if (this.getPosition().x < currentLevel.getIcyGuy().getPosition().x) {
                    switchSprite(bossRunRight);
                    this.startWalking(8);
                }

            }
        }
        // checks if icy guy is on the left or the right of the boss
        else if (inRangeLeft() || inRangeRight()) {
            if (state != State.ENERGYSTRIKE) {
                state = State.ENERGYSTRIKE; // state of energystrike
                // timer used to delay attack
                if (timer == 0) {
                    this.stopWalking();
                    if (this.getPosition().x > currentLevel.getIcyGuy().getPosition().x) {
                        switchSprite(bossEnergyStrikeLeft);
                        shoot(currentLevel.getIcyGuy().getPosition());
                    } else {
                        switchSprite(bossEnergyStrikeRight);
                        shoot(currentLevel.getIcyGuy().getPosition());
                    }
                    timer = 2 * 60; // timer resetted to 2 seconds
                }

                refreshRoll(); // refresh is called to update state
            }

        }
    }


    /** is used to refresh the states */
    private void refreshRoll() {
        // switch case argument to update the current state
        switch (state) {
            case ENERGYSTRIKE:
                // timer used to delay attack
                timer--;
                if(timer == 0) {
                    this.stopWalking();
                    if (this.getPosition().x > currentLevel.getIcyGuy().getPosition().x) {
                        switchSprite(bossEnergyStrikeLeft);
                        shoot(currentLevel.getIcyGuy().getPosition());
                    } else {
                        switchSprite(bossEnergyStrikeRight);
                        shoot(currentLevel.getIcyGuy().getPosition());
                    }
                    timer = 2 * 60;
                }
                break;
            case RUN:
                // running state
                if(this.getPosition().x > currentLevel.getIcyGuy().getPosition().x){
                    switchSprite(bossRunLeft);
                    this.startWalking(-8);
                } else{
                    switchSprite(bossRunRight);
                    this.startWalking(8);
                }
                break;
            default: // nothing to do
        }

    }

    /** This method keeps track of the steps of the simulation.
     * 60 steps make 1 second and when used, the world gets updated every
     * 60 steps.
     * @param stepEvent : can be used to count steps of simulation*/
    @Override
    public void postStep(StepEvent stepEvent) {

    }

    /** this method is used for making the boss walk in a specific direction
     * In this instance the method also contains the direction in which the boss is facing
     * @param speed is a float in which the speed the boss should walk with is stored
     */
    public void startWalking(float speed) {
        // makes the boss walk left or right
        // depending on parameter input
        super.startWalking(speed);
        if (speed < 0) {
            this.removeAllImages();
            this.addImage(bossRunLeft);
            direction = "left";

        } else {
            this.removeAllImages();
            this.addImage(bossRunRight);
            direction = "right";

        }
    }

    /**
     * getter for boss hurt sound
     * @return returns the sound*/
    public static SoundClip getBossHurt() {
        return bossHurt;
    }

    /** this method is used for making the boss stop walking
     */
    @Override
    public void stopWalking(){
        // boss stops walking
        super.stopWalking();
        this.removeAllImages();
        this.addImage(bossIdle);
    }


    /**
     * This method is responsible for removing the boss from the world*/
    @Override
    public void destroy(){
        // removes boss from world
        if(!currentLevel.getGame().isMuted()) {
        bossHurt.play();
    }
        super.destroy();
    }
}
