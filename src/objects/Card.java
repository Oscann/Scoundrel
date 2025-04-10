package objects;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import objects.constants.CardConstants;
import ui.Window;
import ui.utils.ImageHandling;

public class Card extends GameObject {
    public static final int BASE_WIDTH = (int) (CardConstants.CARD_WIDTH_RATIO * Window.screenSizeUnit);
    public static final int BASE_HEIGHT = (int) (CardConstants.CARD_HEIGHT_RATIO * Window.screenSizeUnit);
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

        Font cardFont = new Font("Arial", Font.BOLD, (int) (Window.screenSizeUnit));
        g.setFont(cardFont);
        FontMetrics fontMetrics = g.getFontMetrics();
        String numberLabel = getNumberLabel();

        g.setColor(Color.white);
        g.fillRect(getX(), getY(), width, height);

        int iconSize = (int) (1.5 * Window.screenSizeUnit);
        int xPadding = (int) (x + Window.screenSizeUnit / 4);
        int yPadding = y + Window.screenSizeUnit;
        int cardNY = yPadding + fontMetrics.getHeight() / 2;
        int cardNX = xPadding + (iconSize - fontMetrics.stringWidth(numberLabel)) / 2;

        g.setColor(suit.getColor());

        g.drawString(numberLabel, cardNX,
                cardNY);
        g.drawImage(suit.getIcon(), xPadding, yPadding + Window.screenSizeUnit, iconSize, iconSize, null);
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

    public String getNumberLabel() {
        return number == 1 ? "A"
                : number == 11 ? "J"
                        : number == 12 ? "Q"
                                : number == 13 ? "K"
                                        : Integer.toString(number);
    }

    public static enum ECardSuits {
        SPADES(CardConstants.SPADES_ICON_PATH, CardConstants.CARDS_BLACK),
        CLUBS(CardConstants.CLUBS_ICON_PATH, CardConstants.CARDS_BLACK),
        HEARTS(CardConstants.HEARTS_ICON_PATH, CardConstants.CARDS_RED),
        DIAMONDS(CardConstants.DIAMONDS_ICON_PATH, CardConstants.CARDS_RED);

        private BufferedImage icon;
        private Color color;

        private ECardSuits(String iconPath, Color color) {
            this.icon = ImageHandling.loadImage(iconPath);
            this.color = color;
        }

        public BufferedImage getIcon() {
            return this.icon;
        }

        public Color getColor() {
            return this.color;
        }
    }
}
