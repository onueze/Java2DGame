package game;

import levels.GameLevel;
import org.jbox2d.common.Vec2;
import platforms.IceBox;
import view.GameView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
//use step listener to make boxes disappear
public class IceBoxPlacer implements MouseListener {

    private final GameView view;
    private final GameLevel world;





    public IceBoxPlacer(GameView v, GameLevel w){
        view = v;
        world = w;


    }

    @Override
    // if mouse is clicked , an ice box is created at the position of the mouseclick
    public void mouseClicked(MouseEvent e) {
        Vec2 worldPos = view.viewToWorld(e.getPoint());
        IceBox icebox = new IceBox(world);
        icebox.setPosition(worldPos);

    }

    @Override
    public void mousePressed(MouseEvent e) {


    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

}
