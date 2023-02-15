package backpack;

import backpack.BackPackItem;
import city.cs.engine.BodyImage;
import game.IcyGuy;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;

public class IcePistol extends BackPackItem {

    private final Image icePistolImage = new ImageIcon("data/Pistol.png").getImage().getScaledInstance(40,40,0);




    public IcePistol(IcyGuy icyGuy) {
        super(icyGuy);
        image = new BodyImage("data/Pistol.png",2f); // image of ice pistol

    }

    @Override
    public String getType() {
        return "IcePistol";
    }


    @Override
    public void operate() {
        icyGuy.shoot(); // icy guy shoots

    }

    @Override
    public Image itemImage() {
        return icePistolImage;
    }

    @Override
    public void putOn() {
        super.putOn();
        aImage.setOffset(new Vec2(-1, 0)); // attached image of ice pistol is placed on icy guy
    }
}
