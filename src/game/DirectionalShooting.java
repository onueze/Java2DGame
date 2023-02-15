package game;

import org.jbox2d.common.Vec2;
import view.GameView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class DirectionalShooting implements MouseListener {
    private final IcyGuy icyGuy;
    private final GameView view;
    //directional shooting not used yet, may be used for future game ideas
    public DirectionalShooting(IcyGuy i, GameView v){
        this.icyGuy = i;
        view = v;
    }
    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
//        Vec2 worldPoint = view.viewToWorld(e.getPoint());
//
//        Vec2 t = worldPoint;
//        Vec2 dir = t.sub(icyGuy.getPosition());
//        dir.normalize();
//
//        icyGuy.setPosition(icyGuy.getPosition().add(dir.mul(10f)));
//        icyGuy.setLinearVelocity(dir.mul(5000));





    }

    @Override
    public void mouseReleased(MouseEvent e) {
        icyGuy.setLinearVelocity(new Vec2(0,0));

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
