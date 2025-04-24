package ui.components.panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import core.gamestate.Game;
import core.gamestate.constants.GameRenderingConstants;
import objects.GameObject;
import ui.Window;
import ui.components.buttons.Button;

public class PlayerActionsPanel extends GameObject {

    private Game game;
    private EPanelState state = EPanelState.DEFAULT;
    private ArrayList<Button> DEFAULT_STATE_BUTTONS = new ArrayList<>();
    private ArrayList<Button> ATTACKING_STATE_BUTTONS = new ArrayList<>();
    private ArrayList<Button> CHANGE_WEAPON_STATE_BUTTONS = new ArrayList<>();
    private int buttonHeight = (int) (2 * Window.screenSizeUnit);
    private int buttonWidth = (int) (4 * Window.screenSizeUnit);
    private int BUTTON_AREA_PADDING = (int) Window.screenSizeUnit;

    public PlayerActionsPanel(Game game, Rectangle bounds) {
        this.game = game;
        this.setBounds(bounds);

        this.buttonWidth = width / 2;

        createButtons();
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);

        g.drawRect(x, y, width, height);

        renderLifePoints(g);
        renderButtons(g);
        renderRoomLabel(g);
    }

    @Override
    public void update() {

    }

    public void createButtons() {
        createDefaultStateButtons();
        createAttackingStateButtons();
        createWeaponChangeStateButtons();
    }

    public void createDefaultStateButtons() {
        Button runButton = new Button("RUN");

        runButton.setWidth(buttonWidth);
        runButton.setHeight(buttonHeight);
        runButton.setX(this.x + BUTTON_AREA_PADDING);
        runButton.setY(this.y + BUTTON_AREA_PADDING);

        runButton.setClickEvent((e) -> {
            game.run();
        });

        DEFAULT_STATE_BUTTONS.add(runButton);
    }

    public void createAttackingStateButtons() {
        Button armedAttackButton = new Button("ARMED ATTACK");

        armedAttackButton.setWidth(buttonWidth);
        armedAttackButton.setHeight(buttonHeight);
        armedAttackButton.setX(this.x + BUTTON_AREA_PADDING);
        armedAttackButton.setY(this.y + BUTTON_AREA_PADDING);
        armedAttackButton.setClickEvent((e) -> {
            game.handleAttack(true);
        });

        Button bareHandedAttackButton = new Button("BARE HANDED");

        bareHandedAttackButton.setWidth(buttonWidth);
        bareHandedAttackButton.setHeight(buttonHeight);
        bareHandedAttackButton.setX((int) (this.x + BUTTON_AREA_PADDING));
        bareHandedAttackButton.setY((int) (this.y + BUTTON_AREA_PADDING + Window.screenSizeUnit + buttonHeight));
        bareHandedAttackButton.setClickEvent((e) -> {
            game.handleAttack(false);
        });

        ATTACKING_STATE_BUTTONS.add(armedAttackButton);
        ATTACKING_STATE_BUTTONS.add(bareHandedAttackButton);
    }

    public void createWeaponChangeStateButtons() {
        Button disposeWeaponButton = new Button("DISPOSE WEAPON");

        disposeWeaponButton.setWidth(buttonWidth);
        disposeWeaponButton.setHeight(buttonHeight);
        disposeWeaponButton.setX(this.x + BUTTON_AREA_PADDING);
        disposeWeaponButton.setY(this.y + BUTTON_AREA_PADDING);
        disposeWeaponButton.setClickEvent(e -> {
            game.handleWeaponChange();
        });

        Button keepWeaponButton = new Button("KEEP WEAPON");

        keepWeaponButton.setWidth(buttonWidth);
        keepWeaponButton.setHeight(buttonHeight);
        keepWeaponButton.setX((int) (this.x + BUTTON_AREA_PADDING));
        keepWeaponButton.setY((int) (this.y + BUTTON_AREA_PADDING + Window.screenSizeUnit + buttonHeight));
        keepWeaponButton.setClickEvent(e -> {
            game.resetTarget();
            this.setState(EPanelState.DEFAULT);
        });

        CHANGE_WEAPON_STATE_BUTTONS.add(disposeWeaponButton);
        CHANGE_WEAPON_STATE_BUTTONS.add(keepWeaponButton);
    }

    private void renderLifePoints(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font(GameRenderingConstants.DEFAULT_FONT, Font.PLAIN, Window.screenSizeUnit));

        int lifePointSize = (int) Window.screenSizeUnit * 2;

        int lifePointX = this.x + this.width - lifePointSize;
        int lifePointY = (int) (this.y + Window.screenSizeUnit);

        g.drawString(Integer.toString(game.getLifePoints()), lifePointX, lifePointY);
    }

    public void renderRoomLabel(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font(GameRenderingConstants.DEFAULT_FONT, Font.PLAIN, Window.screenSizeUnit));

        int roomLabelSize = (int) Window.screenSizeUnit * 2;

        int roomLabelX = this.x + this.width - roomLabelSize;
        int roomLabelY = (int) (this.y + 2 * Window.screenSizeUnit);

        g.drawString(Integer.toString(game.getNRoom()), roomLabelX, roomLabelY);
    }

    public void renderButtons(Graphics g) {
        for (Button btn : getCurrentStateButtons()) {
            if (state != EPanelState.DEFAULT || game.getNRoom() > game.getLastRoomRunned() + 1)
                btn.render(g);
        }
    }

    public void handleClick(MouseEvent mouseEvent) {
        Point clickPoint = mouseEvent.getPoint();

        for (Button btn : getCurrentStateButtons()) {
            if (btn.isInBounds(clickPoint) && state != EPanelState.DEFAULT
                    || game.getNRoom() > game.getLastRoomRunned() + 1)
                btn.handleClick(mouseEvent);
        }
    }

    public ArrayList<Button> getCurrentStateButtons() {
        return state == EPanelState.DEFAULT ? DEFAULT_STATE_BUTTONS
                : state == EPanelState.ATTACKING ? ATTACKING_STATE_BUTTONS : CHANGE_WEAPON_STATE_BUTTONS;
    }

    public void setState(EPanelState state) {
        this.state = state;
    }

    public static enum EPanelState {
        DEFAULT,
        ATTACKING,
        CHANGE_WEAPON
    }
}
