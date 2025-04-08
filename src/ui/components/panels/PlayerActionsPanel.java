package ui.components.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import core.gamestate.Game;
import objects.GameObject;
import ui.Window;
import ui.components.buttons.Button;

public class PlayerActionsPanel extends GameObject {

    private Game game;
    private EState state = EState.DEFAULT;
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

        Button keepWeaponButton = new Button("KEEP WEAPON");

        keepWeaponButton.setWidth(buttonWidth);
        keepWeaponButton.setHeight(buttonHeight);
        keepWeaponButton.setX((int) (this.x + BUTTON_AREA_PADDING));
        keepWeaponButton.setY((int) (this.y + BUTTON_AREA_PADDING + Window.screenSizeUnit + buttonHeight));

        CHANGE_WEAPON_STATE_BUTTONS.add(disposeWeaponButton);
        CHANGE_WEAPON_STATE_BUTTONS.add(keepWeaponButton);
    }

    private void renderLifePoints(Graphics g) {
        int lifePointSize = (int) Window.screenSizeUnit * 2;

        int lifePointX = this.x + this.width - lifePointSize;
        int lifePointY = (int) (this.y + Window.screenSizeUnit);

        g.setColor(Color.red);

        g.drawString(Integer.toString(game.getLifePoints()), lifePointX, lifePointY);
    }

    public void renderButtons(Graphics g) {
        for (Button btn : getCurrentStateButtons()) {
            btn.render(g);
        }
    }

    public void handleClick(MouseEvent mouseEvent) {
        Point clickPoint = mouseEvent.getPoint();

        for (Button btn : getCurrentStateButtons()) {
            if (btn.isInBounds(clickPoint))
                btn.handleClick(mouseEvent);
        }
    }

    public ArrayList<Button> getCurrentStateButtons() {
        return state == EState.DEFAULT ? DEFAULT_STATE_BUTTONS
                : state == EState.ATTACKING ? ATTACKING_STATE_BUTTONS : CHANGE_WEAPON_STATE_BUTTONS;
    }

    public void setState(EState state) {
        this.state = state;
    }

    public static enum EState {
        DEFAULT,
        ATTACKING,
        CHANGE_WEAPON
    }
}
