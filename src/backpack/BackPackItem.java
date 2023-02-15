package backpack;

import city.cs.engine.AttachedImage;
import city.cs.engine.BodyImage;
import city.cs.engine.SoundClip;
import game.IcyGuy;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.awt.*;
import java.io.IOException;

public abstract class BackPackItem {
    // protected fields enable the subclasses to view the fields
    protected IcyGuy icyGuy;

    protected BodyImage image;

    protected AttachedImage aImage;



    public BackPackItem(IcyGuy icyGuy){
        this.icyGuy = icyGuy;
    }

    public abstract String getType(); // name of item as string

    public abstract void operate();

    public abstract Image itemImage();

    // put on method add attached image to characters frame
    public void putOn(){
        aImage = icyGuy.addImage(image);
        if(icyGuy.getDirection().equals("right"))
            aImage.flipHorizontal();


    }


    public AttachedImage getaImage() {
        return aImage;
    } // attached image for item

    public void takeOff(){
        icyGuy.removeAttachedImage(aImage);
    }

}
