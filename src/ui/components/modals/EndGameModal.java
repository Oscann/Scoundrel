package ui.components.modals;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import core.App;
import core.gamestate.Game;
import core.gamestate.MainMenu;
import core.gamestate.Game.EGameStatus;
import core.gamestate.constants.GameRenderingConstants;
import ui.Window;
import ui.components.buttons.Button;

public class EndGameModal extends AbstractCenteredModal {

    private static int END_GAME_MODAL_WIDTH = 20 * Window.screenSizeUnit;
    private static int END_GAME_MODAL_HEIGHT = 15 * Window.screenSizeUnit;

    private Game game;
    private ArrayList<Button> buttons = new ArrayList<>();

    public EndGameModal(Game game) {
        super(END_GAME_MODAL_WIDTH, END_GAME_MODAL_HEIGHT);
        this.game = game;

        createButtons();
    }

    @Override
    public void handleClick(MouseEvent mouseEvent) {
        for (Button btn : buttons) {
            if (btn.isInBounds(mouseEvent.getPoint()))
                btn.handleClick(mouseEvent);
        }
    }

    @Override
    public void render(Graphics g) {
        super.render(g);

        renderMessage(g);
        renderButtons(g);
    }

    @Override
    public void update() {
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    public void renderMessage(Graphics g) {
        g.setFont(new Font(GameRenderingConstants.DEFAULT_FONT, Font.PLAIN, 2 * Window.screenSizeUnit));

        FontMetrics metrics = g.getFontMetrics();

        String endGameMessage = game.getGameStatus() == EGameStatus.LOST ? "YOU LOST" : "YOU WON";

        int messageX = (int) (this.getCenterPoint().getX()) - metrics.stringWidth(endGameMessage) / 2;
        int messageY = y + Window.screenSizeUnit * 5;

        g.drawString(endGameMessage, messageX, messageY);
    }

    public void renderButtons(Graphics g) {
        for (Button btn : buttons) {
            btn.render(g);
        }
    }

    public void createButtons() {
        int buttonWidth = (this.width - 3 * Window.screenSizeUnit) / 2;
        int buttonHeight = 3 * Window.screenSizeUnit;
        int firstButtonX = this.x + Window.screenSizeUnit;
        int buttonY = this.y + this.height - Window.screenSizeUnit - buttonHeight;

        Button restartButton = new Button("RESTART", new Rectangle(firstButtonX, buttonY, buttonWidth, buttonHeight));
        restartButton.setClickEvent((event) -> {
            App.setCurrState(new Game());
        });

        this.buttons.add(restartButton);

        int secondButtonX = firstButtonX + buttonWidth + Window.screenSizeUnit;

        Button exitButton = new Button("EXIT", new Rectangle(secondButtonX, buttonY, buttonWidth, buttonHeight));
        exitButton.setClickEvent((event) -> {
            App.setCurrState(new MainMenu());
        });

        this.buttons.add(exitButton);
    }
}
