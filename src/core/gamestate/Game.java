package core.gamestate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;

import objects.Card;
import objects.Deck;
import objects.WeaponSlot;
import objects.Card.ECardSuits;
import ui.Window;
import ui.components.panels.PlayerActionsPanel;
import ui.components.panels.PlayerActionsPanel.EState;

public class Game extends State {
    private int ROOM_FIRST_CARD_POSITION_X = (int) Window.screenSizeUnit * 50 - Card.BASE_WIDTH / 2;
    private int ROOM_CARD_POSITION_Y = (int) (10 * Window.screenSizeUnit);
    private int ROOM_WIDTH = (int) (Card.BASE_WIDTH * 4 + 5 * Window.screenSizeUnit);
    private int ROOM_HEIGHT = (int) (Card.BASE_HEIGHT + 2 * Window.screenSizeUnit);
    private int DECK_POSITION_X = (int) Window.screenSizeUnit * 20;
    private int SAFE_ZONE_LEFT_CORNER = (int) Window.screenSizeUnit * 50 - Card.BASE_WIDTH / 2
            + (int) (Card.BASE_WIDTH * 4 + 5 * Window.screenSizeUnit);

    private Deck deck;
    private WeaponSlot weapon;
    private PlayerActionsPanel panel;
    private int nRoom = 1;
    private Card[] room = new Card[4];
    private int attackTargetCardIndex = -1;

    private int playerLifePoints = 20;

    public Game() {
        createDeck();
        createWeaponSlot();
        createActionsPanel();
    }

    @Override
    public void render(Graphics g) {

        deck.render(g);
        weapon.render(g);
        panel.render(g);
        renderRoomWrapper(g);

        for (int i = 0; i < room.length; i++) {

            if (room[i] == null)
                continue;

            room[i].render(g);
        }
    }

    @Override
    public void update() {
        int nCardsInRoom = getNCardsInRoom();

        if (nCardsInRoom <= 1) {
            populateRoom();
        }
    }

    @Override
    public void restart() {
    }

    public void createDeck() {
        deck = new Deck();
        deck.setX((int) (DECK_POSITION_X - Window.screenSizeUnit));
        deck.setY((int) (ROOM_CARD_POSITION_Y - Window.screenSizeUnit));
    }

    public void createWeaponSlot() {
        weapon = new WeaponSlot();

        int weaponX = (int) (ROOM_FIRST_CARD_POSITION_X - Window.screenSizeUnit);
        int weaponY = (int) (ROOM_CARD_POSITION_Y + Card.BASE_HEIGHT + 2 * Window.screenSizeUnit);

        weapon.setX(weaponX);
        weapon.setY(weaponY);
    }

    public void createActionsPanel() {

        int panelX = (int) (ROOM_FIRST_CARD_POSITION_X + Card.BASE_WIDTH + 3 * Window.screenSizeUnit);
        int panelY = (int) (ROOM_CARD_POSITION_Y + Card.BASE_HEIGHT + 2 * Window.screenSizeUnit);
        int panelWidth = (int) (SAFE_ZONE_LEFT_CORNER - panelX - Window.screenSizeUnit);
        int panelHeight = (int) (Card.BASE_HEIGHT + 2 * Window.screenSizeUnit);

        panel = new PlayerActionsPanel(this, new Rectangle(panelX, panelY, panelWidth, panelHeight));
    }

    private void populateRoom() {
        for (int i = 0; i < room.length; i++) {
            if (room[i] == null)
                drawCardFromDeck();
        }

        nRoom++;
    }

    private void drawCardFromDeck() {
        Card c = deck.drawCard();

        addCardToRoom(c);
    }

    private void addCardToRoom(Card c) {
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

    public void handleCardClick(Card c, int index) {
        ECardSuits cardSuit = c.getSuit();

        if (cardSuit == ECardSuits.DIAMONDS) {
            weapon.setWeapon(c);
            room[index] = null;
        } else if (cardSuit == ECardSuits.HEARTS) {
            playerLifePoints = Math.min(20, playerLifePoints + c.getNumber());
            room[index] = null;
        } else {
            attackTargetCardIndex = index;

            if (weapon.getCurrWeapon() != null && weapon.getLastEnemy().getNumber() > c.getNumber())
                panel.setState(EState.ATTACKING);
            else {
                handleAttack(false);
            }
        }
    }

    public void handleAttack(boolean armed) {
        Card attackedCard = room[attackTargetCardIndex];
        Card weaponCard = weapon.getCurrWeapon();

        if (weaponCard == null)
            armed = false;

        int damage;

        if (armed) {
            damage = Math.max(attackedCard.getNumber() - weaponCard.getNumber(), 0);
            weapon.addEnemyToStack(attackedCard);
        } else {
            damage = attackedCard.getNumber();
        }

        playerLifePoints -= damage;

        room[attackTargetCardIndex] = null;

        panel.setState(EState.DEFAULT);
    }

    public void run() {
        System.out.println("Running");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point clickPoint = e.getPoint();

        if (deck.isInBounds(clickPoint)) {
            drawCardFromDeck();
        }

        if (panel.isInBounds(clickPoint)) {
            panel.handleClick(e);
        }

        for (int i = 0; i < room.length; i++) {
            Card c = room[i];

            if (c != null && c.isInBounds(clickPoint)) {
                handleCardClick(c, i);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public int getNCardsInRoom() {
        int count = 0;

        for (Card c : room) {
            if (c != null)
                count++;
        }

        return count;
    }

    public int getLifePoints() {
        return this.playerLifePoints;
    }
}
