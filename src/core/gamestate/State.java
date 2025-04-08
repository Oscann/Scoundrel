package core.gamestate;

import java.awt.Graphics;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public abstract class State implements MouseListener, MouseMotionListener {
    public abstract void render(Graphics g);

    public abstract void update();

    public abstract void restart();
}
