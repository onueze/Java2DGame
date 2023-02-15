package view;

import view.GameView;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GiveFocus implements MouseListener {

    private final GameView view;

    public GiveFocus(GameView v){
        this.view = v;
    } // focuses when mouse is on the view

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        view.requestFocus();
    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
