package backpack;

import backpack.BackPackItem;
import city.cs.engine.BodyImage;
import game.IcyGuy;

import java.awt.*;

//when no item is selected
public class NoItem extends BackPackItem {




    public NoItem(IcyGuy icyGuy) {
        super(icyGuy);
        image = new BodyImage("data/noItem.png");
    }

    @Override
    public String getType() {
        return " ";
    }


    @Override
    public void operate() {

    }

    @Override
    public Image itemImage() {
        return null;
    }
}
