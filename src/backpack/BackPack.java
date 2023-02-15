package backpack;

import java.util.ArrayList;
// pack back for weapons and other items stored in arraylist
public class BackPack {

    private final ArrayList<BackPackItem> items;

    private int currentItem;

    public BackPack(){
        items =  new ArrayList<BackPackItem>();
        currentItem = -1;
    }

    public void addItem(BackPackItem item){
        items.add(item);
        currentItem = items.size()-1;
    }

    public int getCurrentItemInt(){
        return currentItem;
    }

    public BackPackItem getCurrentItem(){
        return items.get(currentItem);
    }

    // character can toggle through items by pressing "1"
    // it takes off the current item and increments to the next item
    public void toggle(){
        getCurrentItem().takeOff();
        currentItem++;
        if(currentItem == items.size())
            currentItem = 0;
        getCurrentItem().putOn();

    }
}
