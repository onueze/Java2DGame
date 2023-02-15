package backpack;

import backpack.BackPackItem;
import city.cs.engine.BodyImage;
import game.Game;
import game.IcyGuy;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;

// skateboard item
public class SkateBoard extends BackPackItem {

    private final Image skateboardImage = new ImageIcon("data/SkateBoard.png").getImage().getScaledInstance(30,30,0);



    private Game game;


    public SkateBoard(IcyGuy icyGuy) {
        super(icyGuy);
        image = new BodyImage("data/SkateBoard.png",5f);
    }

    @Override
    public String getType() {
        return "Skateboard";
    }


    @Override
    public void operate() {
        icyGuy.setLinearVelocity(new Vec2(17,-5));




    }

    @Override
    public Image itemImage() {
        return skateboardImage;
    }


    @Override
    public void putOn() {
        super.putOn();
        if (icyGuy.getDirection().equals("right")) {
            aImage.setOffset(new Vec2(1f, -2));
        }
        else{
            aImage.setOffset(new Vec2(1f, -2));
        }
    }
}
