package game;

import backpack.BackPack;
import city.cs.engine.*;
import city.cs.engine.Shape;
import levels.GameLevel;
import levels.Level5;
import levels.Level6;
import org.jbox2d.common.Vec2;
import projectiles.Ice;
import view.GameView;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.List;

/**
 *  Class for the main character of the game
 *  Character is called icyGuy has the properties of the walker class.
 *  The character can jump, run, crouch, and shoot projectiles.
 *  Shapes of the character are all made in form of polygon shapes.
 *
 *  The characters' health starts with 100, abd decreases with different type of collisions
 *  This class keeps track of the enemy's the character defeatedm, the health that is still left
 *  and also the lives that are left.
 *
 * The character carries a backpack which contains specific items depending
 * on the level.
 *
 * Sounds that the character makes are included in this class in addition to the shooting
 *
 */

public class IcyGuy extends Walker {
    // IceBall projectile passed into class
    /** private field for the projectiles called Ice */
    private Ice ice;
    /** private field for health */
    private int health;
    /** polygon shape for standing*/
    // creation of shapes
    private static final Shape icyGuyShapeStanding = new PolygonShape(-0.97f,-2.4f,
            2.37f,-2.34f,
            0.92f,1.49f,
            0.16f,1.92f,
            -1.77f,-0.04f);

    /** polygon shape for crouching*/
    private static final Shape icyGuyShapeCrouch = new PolygonShape(-0.31f, -0.58f,
            0.17f, -0.46f,
            0.68f, -0.73f,
            1.12f, -2.3f,
            -1.11f, -2.59f,
            -1.29f, -2.27f,
            -0.69f, -1.02f);

    /** polygon shape for walking*/
    private static final Shape icyGuyShapeWalk = new PolygonShape(-0.92f,-2.8f,
            1.42f,-2.77f,
            1.47f,0.77f,
            -0.37f,0.82f,
            -0.88f,-0.72f);

    /** polygon shape for jumping right*/
    private static final Shape icyGuyShapeJumpRight = new PolygonShape(
            -1.04f,0.19f,
            -0.46f,0.05f,
            0.67f,-2.14f,
            0.7f,-2.34f, -1.46f,-2.44f, -1.93f,-2.07f, -2.01f,-0.23f
    );

    /** polygon shape for jumping left */
    private static final Shape icyGuyShapeJumpLeft = new PolygonShape(-0.71f,-2.15f,
            0.32f,0.08f,
            1.09f,0.27f,
            1.92f,-0.36f,
            1.8f,-2.42f,
            -0.63f,-2.44f);


    /** Body image for walking right*/
    private static final BodyImage walkRight =
            new BodyImage("data/IcyGuyRight.gif", 6f);

    /** Body image for walking left*/
    private static final BodyImage walkLeft =
            new BodyImage("data/IcyGuyLeft.gif", 6f);

    /** Body image for standing right*/
    private static final BodyImage standingRight =
            new BodyImage("data/IcyGuyStandingRight.gif", 5f);

    /** Body image for standing left*/
    private static final BodyImage standingLeft =
            new BodyImage("data/IcyGuyStandingLeft.gif", 5f);

    /** Body image for jumping right*/
    private static final BodyImage jumpRight = (new BodyImage("data/IcyGuyJumpingRight.png", 5f));

    /** Body image for jumping left*/
    private static final BodyImage jumpLeft = (new BodyImage("data/IcyGuyJumpingLeft.png", 5f));

    /** Body image for crouching right*/
    private static final BodyImage crouchRight = (new BodyImage("data/IcyGuyCrouchRight.gif", 6f));

    /** Body image for crouching left*/
    private static final BodyImage crouchLeft = (new BodyImage("data/IcyGuyCrouchLeft.gif", 6f));

    /** shows which direction the character is facing */
    private String direction;

    /** this is used in order to change the shapes of the character */
    private Fixture fixture;

    /** backpack which can contain backpack items */
    private final BackPack backPack;

    /** is used in order to appropriately remove the image of the character
     * without causing difficulties with backpack item images */
    private AttachedImage aImage;

    /** private field for coins*/
    private int coins;

    /** private field for enemys defeated*/
    private int enemysDefeated;

    /** private field for lives*/
    private int lives;

    /** sounds which the character makes when jumping */
    private static SoundClip jumpSound;

    /** sound which the character makes when hurt */
    private static SoundClip icyGuyHurt;

    /** Sounds that are stored in soundManager class */
    private SoundManager soundManager;

    static {
        try {
            jumpSound = new SoundClip("data/JumpingSound.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    static {
        {
            try {
                icyGuyHurt = new SoundClip("data/IcyGuyHurtSound.wav");
            } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
                System.out.println(e);
            }
        }
    }

    /** sound when icy Guy increases health */
    private static SoundClip icyGuyYes;

    /** @return getter for yes sound */
    public static SoundClip getIcyGuyYes() {
        return icyGuyYes;
    }

    static {
        try {
            icyGuyYes = new SoundClip("data/IcyGuyYes.mp3");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }
    private final GameLevel currentLevel;


    /**
     * IcyGuy character is initialised with an initial image and other before
     * mentioned properties.
     * @param currentLevel the currentLevel that is played
     */
    public IcyGuy(GameLevel currentLevel) {
        super(currentLevel);
        this.currentLevel = currentLevel;
        fixture = new SolidFixture(this, icyGuyShapeStanding);
        aImage = addImage(standingRight);
        direction = "right";
        health = 100;
        coins = 0;
        enemysDefeated = 0;
        lives = 3;
        backPack = new BackPack();
        setGravityScale(1.1f);
//        setAlwaysOutline(true);

    }

    /** @return getter for hurt sound */
    public static SoundClip getIcyGuyHurt(){
        return icyGuyHurt;
    }


    /** @return getter for lives */
    public int getLives(){
        return lives;
    }
    /** setter for lives */
    public void setLives(int lives){
        this.lives = lives ;
    }


    /** @return getter for health */
    public int getHealth() {
        return health;
    }

    /** setter for health */
    public void setHealth(int health) {
        this.health = health;
    }


    /** this method is used for making the player walk in a specific direction
     * Also contains the direction in which the player is facing
     * @param speed is a float in which the speed the player should walk with is stored
     */
    @Override
    // Override function to change walk direction depending on what direction character is facing
    public void startWalking(float speed) {
        super.startWalking(speed);

        //if character faces right and wants to walk left
        if (speed < 0 && direction.equals("right")) {
            fixture.destroy();
            fixture = new SolidFixture(this, icyGuyShapeWalk);
            // take item off
            this.getBackPack().getCurrentItem().takeOff();
            this.removeAttachedImage(aImage);
            aImage = addImage(walkRight);
            // put item on
            this.getBackPack().getCurrentItem().putOn();
            List<AttachedImage> allImages = this.getImages();
            for (AttachedImage im : allImages) {
                im.flipHorizontal();
            }
            direction = "left";


        }
        // if character faces left and wants to walk right
        else if (speed > 0 && direction.equals("left")) {
            fixture.destroy();
            fixture = new SolidFixture(this, icyGuyShapeWalk);
            this.getBackPack().getCurrentItem().takeOff();
            this.removeAttachedImage(aImage);
            aImage = addImage(walkLeft);
            this.getBackPack().getCurrentItem().putOn();
            List<AttachedImage> allImages = this.getImages();
            for (AttachedImage im : allImages) {
                im.flipHorizontal();
            }

            direction = "right";

            // if character faces left and walks left
        } else if (speed < 0 && direction.equals("left")) {
            fixture.destroy();
            fixture = new SolidFixture(this, icyGuyShapeWalk);
            this.getBackPack().getCurrentItem().takeOff();
            this.removeAttachedImage(aImage);
            aImage = addImage(walkLeft);
            // put item on
            this.getBackPack().getCurrentItem().putOn();

            // if character faces right and walks right
        } else if (speed > 0 && direction.equals("right")) {
            fixture.destroy();
            fixture = new SolidFixture(this, icyGuyShapeWalk);
            this.getBackPack().getCurrentItem().takeOff();
            this.removeAttachedImage(aImage);
            aImage = addImage(walkRight);
            // put item on
            this.getBackPack().getCurrentItem().putOn();

        }
    }

    /** this method is used for making the player jump.
     * Also contains the direction in which the player is facing
     * and placed body images accordingly
     * @param speed is a float in which the speed the player should jump with is stored
     */
    @Override
    // Override function to change jumping direction depending on what direction character is facing
    public void jump(float speed) {
        super.jump(speed);
        if(!currentLevel.getGame().isMuted()) {
            jumpSound.play();
            jumpSound.setVolume(0.4f);
        }
        // if character faces right
        if (direction.equals("right")) {
            fixture.destroy();
            fixture = new SolidFixture(this, icyGuyShapeJumpRight);
            this.getBackPack().getCurrentItem().takeOff();
            this.removeAttachedImage(aImage);
            aImage = addImage(jumpRight);
            this.getBackPack().getCurrentItem().putOn();


            // if character faces left

        } else {
            fixture.destroy();
            fixture = new SolidFixture(this, icyGuyShapeJumpLeft);
            this.getBackPack().getCurrentItem().takeOff();
            this.removeAttachedImage(aImage);
            aImage = addImage(jumpLeft);
            this.getBackPack().getCurrentItem().putOn();
//            }
        }

    }


    /** this method is used for making the player stop walking
     * Also contains the direction in which the player is facing
     * to determine if a body image for standing left or right should be placed
     */
    @Override
    public void stopWalking() {
        super.stopWalking();
        // if character faces left
        if (direction.equals("left")) {
            fixture.destroy();
            fixture = new SolidFixture(this, icyGuyShapeStanding);
            this.getBackPack().getCurrentItem().takeOff();
            this.removeAttachedImage(aImage);
            aImage = addImage(standingLeft);
            this.getBackPack().getCurrentItem().putOn();

            // if character faces right
        } else if (direction.equals("right")) {
            fixture.destroy();
            fixture = new SolidFixture(this, icyGuyShapeStanding);
            this.getBackPack().getCurrentItem().takeOff();
            this.removeAttachedImage(aImage);
            aImage = addImage(standingRight);
            this.getBackPack().getCurrentItem().putOn();
        }


    }


    /** this method is used for making the player crouch
     * Also contains the direction in which the player is facing
     * to determine if a body image for crouching left or right should be placed
     */
    // crouch left and right
    public void crouch() {
        // if character faces right
        if (currentLevel instanceof Level6 || currentLevel instanceof Level5) {
            this.setLinearVelocity(new Vec2(0, -8));
        }
        if (direction.equals("right")) {
            fixture.destroy();
            fixture = new SolidFixture(this, icyGuyShapeCrouch);
            this.getBackPack().getCurrentItem().takeOff();
            this.removeAttachedImage(aImage);
            aImage = addImage(crouchRight);
        }
        // if character faces left
        else {
            fixture.destroy();
            fixture = new SolidFixture(this, icyGuyShapeCrouch);
            this.getBackPack().getCurrentItem().takeOff();
            this.removeAttachedImage(aImage);
            aImage = addImage(crouchLeft);

        }
    }


    /** @return getter for the direction */
    // accessor for direction in order to turn weapon rightfully
    public String getDirection(){
        return direction;
    }

    /**
     * The player can shoot left or right and the image of the ice projectile
     * is placed accordingly and keeps into account in which direction
     * the character is facing
     */
    // shooting with space
    public void shoot(){
            // ice object/shape and image added to projectile
            ice = new Ice(this.getWorld());
            if(!currentLevel.getGame().isMuted()) {
            Ice.getIceSound().play();
        }
            if (direction.equals("right")){
                ice.removeAllImages();
                ice.addImage(ice.getIceBallRight());
                ice.setPosition(new Vec2(this.getPosition().x+3, this.getPosition().y));
                ice.setLinearVelocity(new Vec2(25,0));

            } else{
                ice.setPosition(new Vec2(this.getPosition().x-3, this.getPosition().y));
                ice.setLinearVelocity(new Vec2(-25,0));

            }
        }

        /** @return getter for the backpack */
        // accessor for BackPack for accessing outside of class
        public BackPack getBackPack(){
            return backPack;
        }

    /**
     * This method may allow the player to shoot in a certain direction dependant
     * on the mouseclick
     * @param t describes the direction in which the player shoots */
    public void shoot(Vec2 t){
        DynamicBody projectile = new Ice(this.getWorld());

        Vec2 dir = t.sub(this.getPosition());
        dir.normalize();

        projectile.setPosition(this.getPosition().add(dir.mul(1f)));
        projectile.setLinearVelocity(dir.mul(30));
    }

    /** queries if the player has lost 100 health or if the player
     * has 0 lives left
     * the player will be destroyed when having 0 lives
     */
    public void healthLoss(){
        if( this.health <= 0){
            this.lives = lives -1;
            if(this.lives != 0) {
                this.health = 100;
                GameView.getHealthBar().setValue(this.health);
            }
        }if( this.lives == 0 && this.health <= 0) {
            SoundManager.getGameOverSound().play();
            this.destroy();


        }
    }


    /** @return getter for coins */
    public int getCoins() {
        return coins;
    }

    /** setter for coins */
    public void setCoins(int i) {
        coins = i;
    }

    /** @return getter for enemy's defeated */
    public int getEnemysDefeated() {
        return enemysDefeated;
    }


    /** setter for enemy's defeated */
    public void setEnemysDefeated(int enemysDefeated) {
        this.enemysDefeated = enemysDefeated;
    }
}

