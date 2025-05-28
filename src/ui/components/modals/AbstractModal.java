package ui.components.modals;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import objects.GameObject;
import ui.components.interfaces.Interectable;

public abstract class AbstractModal extends GameObject implements Interectable {
    public AbstractModal(int x, int y, int width, int height) {
        this.setBounds(new Rectangle(x, y, width, height));
    }

    public void render(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(x, y, width, height);

        g.setColor(Color.white);
        g.drawRect(x, y, width, height);
    }
}