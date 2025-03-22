package core.gamestate;

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.Queue;

import objects.Card;
import objects.Card.ECardSuits;
import ui.Window;

public class Game extends State {
    private int ROOM_FIRST_CARD_POSITION_X = (int) Window.screenSizeUnit * 50 - Card.BASE_WIDTH / 2;
    private int ROOM_CARD_POSITION_Y = (int) (10 * Window.screenSizeUnit);
    private int ROOM_WIDTH = (int) (Card.BASE_WIDTH * 4 + 5 * Window.screenSizeUnit);
    private int ROOM_HEIGHT = (int) (Card.BASE_HEIGHT + 2 * Window.screenSizeUnit);
    private int DECK_POSITION_X = (int) Window.screenSizeUnit * 20;

    private Queue<Card> deck;
    private int nRoom = 1;
    private Card[] room = new Card[4];

    public Game() {
        buildDeck();

        drawCardFromDeck();
    }

    @Override
    public void render(Graphics g) {

        for (int i = 0; i < room.length; i++) {

            if (room[i] == null)
                continue;

            room[i].render(g);
        }

        renderRoomWrapper(g);
        renderDeck(g);
    }

    @Override
    public void update() {
    }

    @Override
    public void restart() {
    }

    public void buildDeck() {
        deck = new LinkedList<Card>();

        deck.add(new Card(10, ECardSuits.CLUBS));

    }

    public void drawCardFromDeck() {
        Card c = deck.remove();

        addCardToRoom(c);
    }

    public void addCardToRoom(Card c) {
        int i = 0;

        while (i < room.length) {
            if (room[i] == null) {
                room[i] = c;
                c.setX((int) (ROOM_FIRST_CARD_POSITION_X + (Card.BASE_WIDTH + Window.screenSizeUnit) * i));
                c.setY(ROOM_CARD_POSITION_Y);
                break;
            }

            i++;
        }
    }

    private void renderRoomWrapper(Graphics g) {
        g.setColor(Color.getHSBColor(32, 82, 54));

        int roomX = (int) (ROOM_FIRST_CARD_POSITION_X - Window.screenSizeUnit);
        int roomY = (int) (ROOM_CARD_POSITION_Y - Window.screenSizeUnit);

        g.drawRect(roomX, roomY, ROOM_WIDTH, ROOM_HEIGHT);
    }

    private void renderDeck(Graphics g) {
        g.setColor(Color.getHSBColor(32, 82, 54));

        int deckX = DECK_POSITION_X;
        int deckY = ROOM_CARD_POSITION_Y;
        int deckWrapperX = (int) (DECK_POSITION_X - Window.screenSizeUnit);
        int deckWrapperY = (int) (ROOM_CARD_POSITION_Y - Window.screenSizeUnit);
        int deckWrapperWidth = (int) (Card.BASE_WIDTH + 2 * Window.screenSizeUnit);
        int deckWrapperHeight = (int) (Card.BASE_HEIGHT + 2 * Window.screenSizeUnit);

        g.drawRect(deckWrapperX, deckWrapperY, deckWrapperWidth, deckWrapperHeight);
        g.fillRect(deckX, deckY, Card.BASE_WIDTH, Card.BASE_HEIGHT);
    }
}
