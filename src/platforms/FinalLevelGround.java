package platforms;

import city.cs.engine.*;
import view.GameView;
import game.IcyGuy;
import game.IcyGuyController;
import levels.GameLevel;
import projectiles.Ice;

import java.util.Random;

/**
 * This class describes the ground that is used in Level 6.
 * The ground in Level 6 is timed and changes textures every few seconds.
 *
 * The ground textures will have the same properties of the other platforms throughout the game combined.
 *
 * That is, the ground can turn into sand, which reduces the speed of the icy guy,
 * it can turn into a toxic platform, which reduces the icy guys' health. It also can turn into the health platform
 * that was seen in Level 4, which increases the health of the icy guy
 *
 * This class extends the static body and implements sensor listener and step listener.*/

public class FinalLevelGround extends StaticBody implements SensorListener, StepListener {

    /** private field for current level*/
    private final GameLevel currentLevel;

    /** private field for the shape of the ground*/
    private final Shape finalGroundShape = new BoxShape(50, 0.5f);

    /** body image of the ground as ice*/
    private final BodyImage iceGround = new BodyImage("data/IceGround.png",40f);

    /** body image of the ground as toxic*/
    private final BodyImage toxicGround = new BodyImage("data/SpookyAirplatform.png",60f);

    /** body image of the ground as sand*/
    private final BodyImage sandGround = new BodyImage("data/SandTexture.jpeg",40f);

    /** body image of the ground as health ground*/
    private final BodyImage healthGround = new BodyImage("data/LightAirPlatform.png",60f);

    /** private boolean checks if ground is ice*/
    private boolean ice;

    /** private boolean checks if ground is toxic*/
    private boolean toxic;

    /** private boolean checks if ground is sand*/
    private boolean sand;

    /** private boolean checks if ground is health*/
    private boolean health;

    /** private boolean checks if icy guy makes contact with the ground*/
    private boolean contact;



    /** sensor used in order to register contact with ground */
    private final Sensor groundSensor;

    /** a timer that times when the fround should switch textures */
    private int timer;

    /** fixture for the ground*/
    private final Fixture groundFixture;


    /** changes texture of the ground
     * @param b : describes the body image the ground will change to
     * */
    private void changeTexture(BodyImage b){
        this.removeAllImages();
        this.addImage(b);

    }


    /** constructor for the final level ground
     * fixture, sensor, sensor listener, timer are initialised
     * @param currentLevel :  describes current level
     * */
    public FinalLevelGround(GameLevel currentLevel) {
        super(currentLevel);
        this.currentLevel = currentLevel; // current level
        groundFixture = new SolidFixture(this,finalGroundShape); // fixture of the ground
        groundSensor = new Sensor(this,finalGroundShape); // sensor of the ground
        groundSensor.addSensorListener(this);
        timer = 3 * 60; // timer set to 3 seconds
        addImage(iceGround); // image added to ground
        setClipped(true);
        contact = false; // contact set to false
        currentLevel.addStepListener(this);
        setAlwaysOutline(true);
        ice = true; // starts off with ice ground

    }


    /** This method keeps track of the steps of the simulation.
     * 60 steps make 1 second and when used, the code gets updated every
     * 60 steps.
     * In this specific case, the ground randomly changes textures every 180 steps (3 seconds).
     * @param stepEvent : can be used to count steps of simulation
     * */
    @Override
    public void preStep(StepEvent stepEvent) {
        timer--;
        if(timer == 0){
            Random rand = new Random();
            int randomElement = rand.nextInt(0,4); // random element between 0 and 3
            // if 0, ice texture
            if(randomElement == 0){
                this.changeTexture(iceGround);
                this.setClipped(true);
                ice = true;
                toxic = false;
                health = false;
                sand = false;
            }
            // if 1, toxic texture
            else if(randomElement == 1){
                this.changeTexture(toxicGround);
                this.setClipped(true);
                toxic = true;
                ice = false;
                health = false;
                sand = false;
            }
            // if 2, sand texture
            else if(randomElement == 2){
                this.changeTexture(sandGround);
                this.setClipped(true);
                sand = true;
                toxic = false;
                ice = false;
                health = false;
            }
            // if 3, health texture
            else if(randomElement == 3){
                this.changeTexture(healthGround);
                this.setClipped(true);
                health = true;
                sand = false;
                toxic = false;
                ice = false;
            }
            timer = 3 * 60; // refreshes timer to 3 seconds
        }

    }

    /** This method keeps track of the steps of the simulation.
     * 60 steps make 1 second and when used, the code gets updated every
     * 60 steps.
     * @param stepEvent : can be used to count steps of simulation
     * */
    @Override
    public void postStep(StepEvent stepEvent) {

    }

    /** This method determines, what may happen, when a specific asset of the game makes contact
     * with the object of the class.
     * In this case, depending on what ground is currently present, the ground will have certain properties
     * @param sensorEvent : can keep track of contact body and the sensor of the reporting body
     * */
    @Override
    public void beginContact(SensorEvent sensorEvent) {
        contact = true;
        if(sand){
            // sand reduces the speed of icy guy
            IcyGuyController.setWalkingSpeed(2);
            IcyGuyController.setSprintingSpeed(6);
        }
        else if (toxic){
            // toxic reduces the health of icy guy
            currentLevel.icyGuy.setHealth(currentLevel.icyGuy.getHealth()-1);
            if(!currentLevel.getGame().isMuted()) {
                IcyGuy.getIcyGuyHurt().play();
            }
            // speed is resetted
            currentLevel.icyGuy.healthLoss();
            GameView.getHealthBar().setValue(currentLevel.icyGuy.getHealth());
            IcyGuyController.setWalkingSpeed(5);
            IcyGuyController.setSprintingSpeed(9);
        }
        else if (health){
            // health increases health of icy guy
            if(currentLevel.icyGuy.getHealth() < 100) {
                currentLevel.icyGuy.setHealth(currentLevel.icyGuy.getHealth() + 1);
                GameView.getHealthBar().setValue(currentLevel.icyGuy.getHealth()); // health bar updated
                IcyGuy.getIcyGuyYes().play(); // sound when icy guy increases health
            }
            IcyGuyController.setWalkingSpeed(5);
            IcyGuyController.setSprintingSpeed(9);

        }
        else if (sensorEvent.getContactBody() instanceof Ice){
            sensorEvent.getContactBody().destroy(); // ice projectile is destroyed if it makes contact with the ground
        }

    }


    /** This method determines, what may happen, when a specific asset of the game ends contact
     * with the object of the class.
     * In this case, if the icy guy leaves the ground, the properties of the sensor will not have an effect on him anymore
     * @param sensorEvent : can keep track of contact body and the sensor of the reporting body
     * */
    @Override
    public void endContact(SensorEvent sensorEvent) {
        contact = false;
        if(!sand){
            // reset walking speed
            IcyGuyController.setWalkingSpeed(5);
            IcyGuyController.setSprintingSpeed(9);
        }

    }
}
