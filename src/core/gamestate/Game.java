package core.gamestate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import objects.Card;
import objects.Deck;
import objects.WeaponSlot;
import objects.Card.ECardSuits;
import ui.Window;
import ui.components.panels.PlayerActionsPanel;
import ui.components.panels.PlayerActionsPanel.EPanelState;

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
    private int nRoom = 0;
    private Card[] room = new Card[4];
    private int targetCardIndex = -1;

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

        deck.update();

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

    private void renderRoomWrapper(Graphics g) {
        g.setColor(Color.getHSBColor(32, 82, 54));

        int roomX = (int) (ROOM_FIRST_CARD_POSITION_X - Window.screenSizeUnit);
        int roomY = (int) (ROOM_CARD_POSITION_Y - Window.screenSizeUnit);

        g.drawRect(roomX, roomY, ROOM_WIDTH, ROOM_HEIGHT);
    }

    public void handleCardClick(Card c, int index) {
        ECardSuits cardSuit = c.getSuit();

        if (cardSuit == ECardSuits.DIAMONDS) {
            targetCardIndex = index;

            if (weapon.getCurrWeapon() != null) {
                panel.setState(EPanelState.CHANGE_WEAPON);
                return;
            }

            handleWeaponChange();
        } else if (cardSuit == ECardSuits.HEARTS) {
            playerLifePoints = Math.min(20, playerLifePoints + c.getNumber());
            room[index] = null;
        } else {
            targetCardIndex = index;

            if (weapon.getCurrWeapon() != null
                    && (weapon.getLastEnemy() == null ||
                            weapon.getLastEnemy().getNumber() > c.getNumber())) {
                panel.setState(EPanelState.ATTACKING);
                return;
            }

            handleAttack(false);
        }
    }

    public void handleWeaponChange() {
        Card targetCard = room[targetCardIndex];

        if (weapon.getCurrWeapon() != null)
            weapon.disposeCards();

        weapon.setWeapon(targetCard);

        clearTargetCardSpace();
        panel.setState(EPanelState.DEFAULT);
    }

    public void handleAttack(boolean armed) {
        Card attackedCard = room[targetCardIndex];
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

        clearTargetCardSpace();

        panel.setState(EPanelState.DEFAULT);
    }

    public void clearTargetCardSpace() {
        room[targetCardIndex] = null;
        resetTarget();
    }

    public void resetTarget() {
        this.targetCardIndex = -1;
        panel.setState(EPanelState.DEFAULT);
    }

    public void run() {
        deck.enqueueCards(Arrays.asList(room));
        Arrays.fill(room, null);
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

    public int getNRoom() {
        return this.nRoom;
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

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        Point clickPoint = e.getPoint();

        if (targetCardIndex != -1 && !panel.isInBounds(clickPoint)) {
            resetTarget();
            return;
        }

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
}
