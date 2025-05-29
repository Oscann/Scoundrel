package core.gamestate;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import core.App;
import core.gamestate.constants.GameRenderingConstants;
import ui.Window;
import ui.components.buttons.Button;

public class MainMenu extends State {

    private static final String GAME_TITLE = "SCOUNDREL";

    private static final int TITLE_FONT_SIZE = 3 * Window.screenSizeUnit;
    private static final int TITLE_BUTTONS_GAP = 2 * Window.screenSizeUnit;
    private static final int BUTTON_WIDTH = 7 * Window.screenSizeUnit;
    private static final int BUTTON_HEIGHT = 3 * Window.screenSizeUnit;
    private static final int BUTTONS_GAP = Window.screenSizeUnit;

    private ArrayList<Button> buttons = new ArrayList<>();

    public MainMenu() {
        createButtons();
    }

    @Override
    public void render(Graphics g) {
        renderTitle(g);
        renderButtons(g);
    }

    @Override
    public void update() {
    }

    @Override
    public void restart() {
    }

    public void createButtons() {
        int buttonsX = (Window.screenWidth - BUTTON_WIDTH) / 2;
        int topPadding = (Window.screenHeight - getSafeZoneHeight()) / 2 + TITLE_FONT_SIZE + TITLE_BUTTONS_GAP;

        Button startGameButton = new Button("Start Game",
                new Rectangle(buttonsX, topPadding, BUTTON_WIDTH, BUTTON_HEIGHT));
        startGameButton.setClickEvent((e) -> {
            App.setCurrState(new Game());
        });

        buttons.add(startGameButton);

        Button exitButton = new Button("Exit",
                new Rectangle(buttonsX, topPadding + (BUTTONS_GAP + BUTTON_HEIGHT) * buttons.size(), BUTTON_WIDTH,
                        BUTTON_HEIGHT));

        exitButton.setClickEvent((e) -> {
            System.exit(0);
        });

        buttons.add(exitButton);
    }

    public void renderTitle(Graphics g) {
        g.setColor(Color.white);

        g.setFont(new Font(GameRenderingConstants.DEFAULT_FONT, Font.PLAIN, TITLE_FONT_SIZE));

        FontMetrics fontMetrics = g.getFontMetrics();

        int textY = (Window.screenHeight - getSafeZoneHeight()) / 2 + TITLE_FONT_SIZE;
        int textX = (Window.screenWidth - fontMetrics.stringWidth(GAME_TITLE)) / 2;

        g.drawString(GAME_TITLE, textX, textY);
    }

    public void renderButtons(Graphics g) {
        for (Button btn : buttons) {
            btn.render(g);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        for (Button btn : buttons) {
            if (btn.isInBounds(e.getPoint()))
                btn.handleClick(e);
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

    public int getSafeZoneHeight() {
        return TITLE_FONT_SIZE + (Window.screenSizeUnit + BUTTON_HEIGHT) * buttons.size() + TITLE_BUTTONS_GAP;
    }
}
