package game;

import levels.GameLevel;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

public class IcyGuyController implements KeyListener {

    private static float WALKING_SPEED = 5;

    private static float SPRINTING_SPEED = 9;
    private IcyGuy icyGuy;

    public static float getWalkingSpeed() {
        return WALKING_SPEED;
    }

    public static void setWalkingSpeed(float walkingSpeed) {
        WALKING_SPEED = walkingSpeed;
    }

    public static float getSprintingSpeed() {
        return SPRINTING_SPEED;
    }

    public static void setSprintingSpeed(float sprintingSpeed) {
        SPRINTING_SPEED = sprintingSpeed;
    }

    // this is used to make the character running
    private static Boolean aPressed;
    private static Boolean dPressed;
    private Boolean ePressed;

    private Boolean qPressed;
    private Boolean wPressed;
    private static boolean spacePressed;

    public static boolean isSpacePressed() {
        return spacePressed;
    }

    public IcyGuyController(Game g, IcyGuy icyGuy) {
        this.icyGuy = icyGuy;
        aPressed = false;
        dPressed = false;
        ePressed = false;
        qPressed = false;
        spacePressed = false;
        game = g;
    }

    public Boolean getwPressed() {
        return wPressed;
    }

    public static Boolean getaPressed() {
        return aPressed;
    }

    public static Boolean getdPressed() {
        return dPressed;
    }

    private final Game game;

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        // other key commands omitted
        // change of direction considered
        if (code == KeyEvent.VK_A) {
            icyGuy.startWalking(-WALKING_SPEED);
            aPressed = true;

        } else if (code == KeyEvent.VK_D) {
            icyGuy.startWalking(WALKING_SPEED);
            dPressed = true;
        }
        // IcyGuy jumps with W
        else if (code == KeyEvent.VK_W) {
            icyGuy.jump(13);
            wPressed = true;
        }
        // IcyGuy crouches with S
        else if (code == KeyEvent.VK_S) {
            icyGuy.crouch();
        }
        // IcyGuy shoots with space
        else if (code == KeyEvent.VK_SPACE) {
            icyGuy.getBackPack().getCurrentItem().operate();
            spacePressed = true;
        } else if (code == KeyEvent.VK_Q) {
            qPressed = true;

        } else if (code == KeyEvent.VK_E) {
            ePressed = true;

        } else if (code == KeyEvent.VK_1) {
            icyGuy.getBackPack().toggle();

        } else if (code == KeyEvent.VK_ESCAPE) {
            game.toggleMenu();

        } else if (code == KeyEvent.VK_P) {
            try {
                GameSaverLoader.save("data/save.txt", game.getCurrentLevel());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        } else if (code == KeyEvent.VK_L) {
            GameLevel loadLevel;
            try {
                loadLevel = GameSaverLoader.load("data/save.txt",game);
                game.setLevel(loadLevel);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }

        // function for the character sprinting
        icyGuySprint();
    }



        @Override
        public void keyReleased (KeyEvent e){
            int code = e.getKeyCode();
            // if keys a or d are released their boolean is false
            if (code == KeyEvent.VK_A) {
                icyGuy.stopWalking();
                aPressed = false;
            } else if (code == KeyEvent.VK_D){
                icyGuy.stopWalking();
                dPressed = false;

                // stop walking function implies the standing image
            } else if (code == KeyEvent.VK_S) {
                icyGuy.stopWalking();
            }

            else if (code == KeyEvent.VK_E) {
                ePressed = false;
            }

            else if (code == KeyEvent.VK_Q) {
                qPressed = false;
            }
            else if (code == KeyEvent.VK_SPACE){
                spacePressed = false;

            } else if (code == KeyEvent.VK_W) {
                wPressed = false;
                icyGuy.stopWalking();
            }





        }

        @Override
        public void keyTyped(KeyEvent e){
            int code = e.getKeyCode();
            if (code == KeyEvent.VK_W) {
                icyGuy.jump(13);
            }
        }

    // Q and E are used in combination with D and A respectively to sprint
    // pressing Q or E by itself has no effect
        public void icyGuySprint(){
            if (aPressed && ePressed) {
                icyGuy.startWalking(-SPRINTING_SPEED);
            }


            else if (dPressed && qPressed) {
                icyGuy.startWalking(SPRINTING_SPEED);
            }

        }

        public void updateIcyGuy(IcyGuy newIcyGuy){
        icyGuy = newIcyGuy;
        }


    }

