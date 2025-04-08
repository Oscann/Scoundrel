package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import ui.Window;

public class Card extends GameObject {
    public static final int BASE_WIDTH = (int) (2.00 * 4 * Window.screenSizeUnit);
    public static final int BASE_HEIGHT = (int) (2.90 * 4 * Window.screenSizeUnit);
    private int number;
    private ECardSuits suit;

    public Card(int number, ECardSuits suit) {
        this.number = number;
        this.suit = suit;

        defineBoundValues();
    }

    public Card(int number, ECardSuits suit, int x, int y) {
        this.number = number;
        this.suit = suit;
        this.x = x;
        this.y = y;

        defineBoundValues();
    }

    private void defineBoundValues() {
        width = BASE_WIDTH;
        height = BASE_HEIGHT;
    }

    @Override
    public void render(Graphics g) {

        g.setColor(Color.white);

        g.fillRect(getX(), getY(), width, height);

        g.setColor(suit == ECardSuits.SPADES || suit == ECardSuits.CLUBS ? Color.black : Color.RED);

        Point center = getCenterPoint();

        g.drawRect((int) center.getX(), (int) center.getY(), 20, 20);
        g.drawString(Integer.toString(number), (int) (x + Window.screenSizeUnit / 2),
                (int) (y + Window.screenSizeUnit));
    }

    @Override
    public void update() {

    }

    public ECardSuits getSuit() {
        return this.suit;
    }

    public int getNumber() {
        return this.number;
    }

    public static enum ECardSuits {
        SPADES,
        CLUBS,
        HEARTS,
        DIAMONDS;
    }
}
