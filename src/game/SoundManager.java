package game;

import city.cs.engine.SoundClip;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;

public class SoundManager {

    // Game sound level 1
    private static SoundClip level1Sound;

    static {
         try {
             level1Sound = new SoundClip("data/CityGameSound.wav");   // Open an audio input stream
         } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
             //code in here will deal with any errors
             //that might occur while loading/playing sound
             System.out.println(e);
         }
     }

    public static SoundClip getLevel1Sound() {
        return level1Sound;
    }


    // Game sound level 2
    private static SoundClip level2Sound;
    static {
        try {
            level2Sound = new SoundClip("data/SandStormSound.wav");   // Open an audio input stream
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println(e);
        }
    }

    public static SoundClip getLevel2Sound() {
        return level2Sound;
    }


//     Game sound level 3
    private static SoundClip level3Sound;
    static {
        try {
            level3Sound = new SoundClip("data/Level3Sound.wav");   // Open an audio input stream
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println(e);
        }
    }

    public static SoundClip getLevel3Sound() {
        return level3Sound;
    }

    // Game sound level 4

    private static SoundClip level4Sound;
    static {
        try {
            level4Sound = new SoundClip("data/Level4Sound.wav");   // Open an audio input stream
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println(e);
        }
    }

    public static SoundClip getLevel4Sound() {
        return level4Sound;
    }



    private static SoundClip level5Sound;
    static {
        try {
            level5Sound = new SoundClip("data/Level5Sound.wav");   // Open an audio input stream
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println(e);
        }
    }



    public static SoundClip getLevel5Sound(){
        return level5Sound;
    }


    private static SoundClip level6Sound;
    static {
        try {
            level6Sound = new SoundClip("data/BossLevelSound.wav");   // Open an audio input stream
            level6Sound.setVolume(0.25f);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            //code in here will deal with any errors
            //that might occur while loading/playing sound
            System.out.println(e);
        }
    }

    public static SoundClip getLevel6Sound(){
        return level6Sound;
    }


    private static SoundClip gameOverSound;



    static {
        try {
            gameOverSound = new SoundClip("data/CityGameGameOver.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public static SoundClip getGameOverSound() {
        return gameOverSound;
    }




    private static SoundClip gameWinSound;


    static {
        try {
            gameWinSound = new SoundClip("data/GameWin.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public static SoundClip getGameWinSound() {
        return gameWinSound;
    }

    private static SoundClip buttonPressSound;

    static {
        try {
            buttonPressSound = new SoundClip("data/ButtonPress.wav");
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println(e);
        }
    }

    public static SoundClip getButtonPressSound() {
        return buttonPressSound;
    }


}
