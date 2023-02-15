package backpack;

import backpack.BackPackItem;
import city.cs.engine.BodyImage;
import game.IcyGuy;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;

// jetpack class parent class is backpack item
public class JetPack extends BackPackItem {


    private final Image jetPackImage = new ImageIcon("data/JetPack.png").getImage().getScaledInstance(30,30,0);



    public JetPack(IcyGuy icyGuy) {
        super(icyGuy);
        image = new BodyImage("data/JetPack.png",2f);

    }

    @Override
    public String getType() {
        return "Jetpack";
    }



    @Override
    public void operate() {
        icyGuy.setLinearVelocity(new Vec2(0, 10));
    }

    @Override
    public Image itemImage() {
        return jetPackImage;
    }

    @Override
    public void putOn() {
        super.putOn();
        aImage.setOffset(new Vec2(1, 0));
    }



}
