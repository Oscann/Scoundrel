package objects;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

public abstract class GameObject {
    protected int x, y, width, height;

    public abstract void render(Graphics g);

    public abstract void update();

    public Point getCenterPoint() {
        return new Point(x + width / 2, y + height / 2);
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return this.width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return this.height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void setBounds(Rectangle bounds) {
        this.width = bounds.width;
        this.height = bounds.height;
        this.x = bounds.x;
        this.y = bounds.y;
    }

    public boolean isInBounds(Point p) {
        return this.getBounds().contains(p);
    }
}
