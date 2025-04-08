package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

import ui.Window;

public class WeaponSlot extends GameObject {

    Card currWeapon = null;
    LinkedList<Card> enemiesStack = new LinkedList<>();

    public WeaponSlot() {
        width = (int) (Card.BASE_WIDTH + 2 * Window.screenSizeUnit);
        height = (int) (Card.BASE_HEIGHT + 2 * Window.screenSizeUnit);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.white);

        g.drawRect(x, y, width, height);

        if (currWeapon != null) {
            currWeapon.render(g);
        }

        Card lastEnemy = getLastEnemy();

        if (lastEnemy != null) {
            lastEnemy.render(g);
        }
    }

    @Override
    public void update() {
    }

    public void disposeCards() {
        currWeapon = null;
        enemiesStack.clear();
    }

    public void addEnemyToStack(Card c) {
        c.setX((int) (x + 2 * Window.screenSizeUnit));
        c.setY((int) (y + 2 * Window.screenSizeUnit));
        enemiesStack.add(c);
    }

    public void setWeapon(Card c) {
        currWeapon = c;
        int cardX = (int) (x + Window.screenSizeUnit);
        int cardY = (int) (y + Window.screenSizeUnit);

        c.setX(cardX);
        c.setY(cardY);
    }

    public Card getCurrWeapon() {
        return currWeapon;
    }

    public Card getLastEnemy() {
        return enemiesStack.peekLast();
    }

}
