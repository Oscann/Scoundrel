package objects;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import objects.Card.ECardSuits;
import ui.Window;

public class Deck extends GameObject {
    private LinkedList<Card> content;

    public Deck() {
        width = (int) (Card.BASE_WIDTH + 2 * Window.screenSizeUnit);
        height = (int) (Card.BASE_HEIGHT + 2 * Window.screenSizeUnit);

        startDeck();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.getHSBColor(32, 82, 54));

        int cardsX = (int) (this.x + Window.screenSizeUnit);
        int cardsY = (int) (this.y + Window.screenSizeUnit);

        g.drawRect(this.x, this.y, width, height);
        g.fillRect(cardsX, cardsY, Card.BASE_WIDTH, Card.BASE_HEIGHT);
    }

    @Override
    public void update() {
    }

    public void startDeck() {
        content = new LinkedList<Card>();

        for (ECardSuits suit : ECardSuits.values()) {
            int maxCards = suit == ECardSuits.CLUBS || suit == ECardSuits.SPADES ? 13 : 10;

            for (int i = 1; i <= maxCards; i++) {
                addCard(new Card(i, suit));
            }
        }

        Collections.shuffle(content);
    }

    public void addCard(Card c) {
        content.add(c);
    }

    public void enqueueCards(Collection<Card> cards) {
        content.addAll(cards);
    }

    public Card drawCard() {
        return content.remove();
    }
}
