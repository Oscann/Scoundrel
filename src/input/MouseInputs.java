package input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import core.App;

public class MouseInputs implements MouseMotionListener, MouseListener {

    @Override
    public void mouseClicked(MouseEvent e) {
        App.getCurrState().mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        App.getCurrState().mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        App.getCurrState().mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        App.getCurrState().mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        App.getCurrState().mouseExited(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        App.getCurrState().mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        App.getCurrState().mouseMoved(e);
    }

}
