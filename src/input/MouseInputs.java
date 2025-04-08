package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import core.App;

public class MouseInputs implements MouseMotionListener, MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        App.currState.mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        App.currState.mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        App.currState.mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        App.currState.mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        App.currState.mouseExited(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        App.currState.mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        App.currState.mouseMoved(e);
    }

}
