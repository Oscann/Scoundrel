package core.gamestate;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import core.gamestate.constants.GameRenderingConstants;
import objects.Card;
import objects.Deck;
import objects.WeaponSlot;
import objects.Card.ECardSuits;
import objects.animation.animations.CardDrawAnimation;
import ui.Window;
import ui.components.panels.PlayerActionsPanel;
import ui.components.panels.PlayerActionsPanel.EPanelState;

public class Game extends State {
    private final int SAFE_ZONE_TOP = (int) (Window.screenHeight / 2
            - GameRenderingConstants.HEIGHT_RATIO * Window.screenSizeUnit / 2);
    private final int SAFE_ZONE_LEFT = (int) (Window.screenWidth / 2
            - GameRenderingConstants.WIDTH_RATIO * Window.screenSizeUnit / 2);
    private final int X_PADDING = (int) (GameRenderingConstants.X_PADDING_RATIO * Window.screenSizeUnit);
    private final int Y_PADDING = (int) (GameRenderingConstants.Y_PADDING_RATIO * Window.screenSizeUnit);
    private final int EFFECTIVE_TOP = SAFE_ZONE_TOP + Y_PADDING;
    private final int EFFECTIVE_LEFT = SAFE_ZONE_LEFT + X_PADDING;

    private final int DECK_POSITION_X = EFFECTIVE_LEFT;
    private final int ROOM_POSITION_X = DECK_POSITION_X + Card.BASE_WIDTH + (int) (3 * Window.screenSizeUnit);
    private final int ROOM_FIRST_CARD_POSITION_X = ROOM_POSITION_X + (int) (Window.screenSizeUnit);
    private final int ROOM_CARDS_Y = EFFECTIVE_TOP + (int) (Window.screenSizeUnit);
    private final int ROOM_WIDTH = Card.BASE_WIDTH * 4 + (int) (5 * Window.screenSizeUnit);
    private final int ROOM_HEIGHT = Card.BASE_HEIGHT + (int) (2 * Window.screenSizeUnit);

    private Deck deck;
    private WeaponSlot weapon;
    private PlayerActionsPanel panel;
    private int nRoom = 0;
    private Card[] room = new Card[4];
    private int targetCardIndex = -1;
    private int playerLifePoints = 20;
    private Timer timer = new Timer();
    private boolean isDrawing = false;
    private boolean isPopulating = false;
    private int lastRoomRunned = -1;

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

        if (nCardsInRoom <= 1 && deck.getLength() != 0)
            isPopulating = true;

        if (isPopulating && !isDrawing)
            drawCardFromDeck();

        if (isPopulating && (nCardsInRoom == room.length || deck.getLength() == 0)) {
            isPopulating = false;
            nRoom++;
        }

        for (int i = 0; i < room.length; i++) {
            if (room[i] != null)
                room[i].update();
        }

    }

    @Override
    public void restart() {
    }

    public void createDeck() {
        deck = new Deck();
        deck.setX((int) (DECK_POSITION_X));
        deck.setY((int) (EFFECTIVE_TOP));
    }

    public void createWeaponSlot() {
        weapon = new WeaponSlot();

        int weaponX = ROOM_POSITION_X;
        int weaponY = (int) (EFFECTIVE_TOP + Card.BASE_HEIGHT + 3 * Window.screenSizeUnit);

        weapon.setX(weaponX);
        weapon.setY(weaponY);
    }

    public void createActionsPanel() {

        int panelX = (int) (ROOM_POSITION_X + Card.BASE_WIDTH + 3 * Window.screenSizeUnit);
        int panelY = (int) (EFFECTIVE_TOP + Card.BASE_HEIGHT + 3 * Window.screenSizeUnit);
        int panelWidth = (int) (ROOM_WIDTH - Card.BASE_WIDTH - 3 * Window.screenSizeUnit);
        int panelHeight = (int) (Card.BASE_HEIGHT + 2 * Window.screenSizeUnit);

        panel = new PlayerActionsPanel(this, new Rectangle(panelX, panelY, panelWidth, panelHeight));
    }

    private void renderRoomWrapper(Graphics g) {
        g.setColor(Color.getHSBColor(32, 82, 54));

        g.drawRect(ROOM_POSITION_X, EFFECTIVE_TOP, ROOM_WIDTH, ROOM_HEIGHT);
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
        lastRoomRunned = nRoom;
        deck.enqueueCards(Arrays.asList(room));
        Arrays.fill(room, null);
    }

    private void drawCardFromDeck() {
        Card c = deck.drawCard();

        if (c != null)
            addCardToRoom(c);
    }

    private void addCardToRoom(Card c) {
        int emptyIndex = getNextRoomEmptySpace();

        if (emptyIndex == -1)
            return;

        Point deckCoords = new Point(deck.getX() + Window.screenSizeUnit, ROOM_CARDS_Y);
        Point destinyCoords = new Point(ROOM_FIRST_CARD_POSITION_X + (Card.BASE_WIDTH +
                Window.screenSizeUnit) * emptyIndex, ROOM_CARDS_Y);

        room[emptyIndex] = c;
        isDrawing = true;
        c.setCurrAnimation(new CardDrawAnimation(c, deckCoords, destinyCoords));

        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                isDrawing = false;
            }

        }, 500);
    }

    public int getNextRoomEmptySpace() {
        int i = 0;

        while (i < room.length) {
            if (room[i] == null) {
                return i;
            }

            i++;
        }

        return -1;
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

    public int getLastRoomRunned() {
        return this.lastRoomRunned;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (isPopulating)
            return;

        Point clickPoint = e.getPoint();

        if (targetCardIndex != -1 && !panel.isInBounds(clickPoint)) {
            resetTarget();
            return;
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
